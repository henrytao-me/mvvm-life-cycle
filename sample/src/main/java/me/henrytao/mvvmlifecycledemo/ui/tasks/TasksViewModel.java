/*
 * Copyright 2016 "Henry Tao <hi@henrytao.me>"
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.henrytao.mvvmlifecycledemo.ui.tasks;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.henrytao.mvvmlifecycle.event.Event1;
import me.henrytao.mvvmlifecycle.rx.UnsubscribeLifeCycle;
import me.henrytao.mvvmlifecycledemo.data.model.Task;
import me.henrytao.mvvmlifecycledemo.data.service.TaskService;
import me.henrytao.mvvmlifecycledemo.di.Injector;
import me.henrytao.mvvmlifecycledemo.ui.base.BaseViewModel;
import me.henrytao.mvvmlifecycledemo.ui.base.State;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by henrytao on 4/15/16.
 */
public class TasksViewModel extends BaseViewModel {

  public static final String KEY_ID = "KEY_ID";

  public static final String STATE_ACTIVE_TASK = "STATE_ACTIVE_TASK";

  public static final String STATE_ADDED_TASK = "STATE_ADDED_TASK";

  public static final String STATE_CLICK_TASK = "STATE_CLICK_TASK";

  public static final String STATE_COMPLETE_TASK = "STATE_COMPLETE_TASK";

  @Inject
  protected TaskService mTaskService;

  private List<Task> mTasks = new ArrayList<>();

  public TasksViewModel() {
    Injector.component.inject(this);

    manageSubscription(subscribe(TaskItemViewModel.Event.ON_TASK_ITEM_CLICK, new Event1<>(this::onTaskItemClick)),
        UnsubscribeLifeCycle.DESTROY);
    manageSubscription(subscribe(TaskItemViewModel.Event.ON_TASK_ITEM_ACTIVE, new Event1<>(this::onTaskItemActive)),
        UnsubscribeLifeCycle.DESTROY);
    manageSubscription(subscribe(TaskItemViewModel.Event.ON_TASK_ITEM_COMPLETE, new Event1<>(this::onTaskItemComplete)),
        UnsubscribeLifeCycle.DESTROY);

    manageSubscription(mTaskService.observeTaskCreate()
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(task -> {
          mTasks.add(task);
          setState(State.create(STATE_ADDED_TASK));
        }), UnsubscribeLifeCycle.DESTROY);

    reloadData();
  }

  public List<Task> getTasks() {
    return mTasks;
  }

  public void reloadData() {
    manageSubscription(mTaskService.getAll()
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(tasks -> {
          mTasks.clear();
          mTasks.addAll(tasks);
          setState(State.create(STATE_ADDED_TASK));
        }), UnsubscribeLifeCycle.DESTROY);
  }

  private void onTaskItemActive(Task task) {
    manageSubscription(mTaskService.active(task.getId())
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(aVoid -> setState(State.create(STATE_ACTIVE_TASK)), Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);
  }

  private void onTaskItemClick(Task task) {
    setState(State.create(STATE_CLICK_TASK, KEY_ID, task.getId()));
  }

  private void onTaskItemComplete(Task task) {
    manageSubscription(mTaskService.complete(task.getId())
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(aVoid -> setState(State.create(STATE_COMPLETE_TASK)), Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);
  }
}
