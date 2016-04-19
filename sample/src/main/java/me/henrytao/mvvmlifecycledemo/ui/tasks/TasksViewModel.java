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

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.henrytao.mvvmlifecycle.event.Event1;
import me.henrytao.mvvmlifecycle.rx.UnsubscribeLifeCycle;
import me.henrytao.mvvmlifecycledemo.data.model.Task;
import me.henrytao.mvvmlifecycledemo.data.service.TaskService;
import me.henrytao.mvvmlifecycledemo.di.Injector;
import me.henrytao.mvvmlifecycledemo.ui.base.BaseViewModel;
import me.henrytao.mvvmlifecycledemo.ui.base.Constants;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by henrytao on 4/15/16.
 */
public class TasksViewModel extends BaseViewModel<TasksViewModel.State> {

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
          setState(State.CREATED_TASK, Constants.Key.INDEX, mTasks.size() - 1);
        }, Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);

    manageSubscription(mTaskService.observeTaskUpdate()
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(task -> {
          Task tmp;
          int n = mTasks.size();
          for (int i = 0; i < n; i++) {
            tmp = mTasks.get(i);
            if (TextUtils.equals(tmp.getId(), task.getId())) {
              tmp.setTitle(task.getTitle());
              tmp.setDescription(task.getDescription());
              tmp.setCompleted(task.isCompleted());
              setState(State.UPDATED_TASK, Constants.Key.INDEX, i);
              break;
            }
          }
        }, Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);

    manageSubscription(mTaskService.observeTaskRemove()
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(task -> {
          mTasks.remove(task);
          setState(State.REMOVED_TASK);
        }, Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);

    reloadData(false);
  }

  public List<Task> getTasks() {
    return mTasks;
  }

  public void reloadData() {
    reloadData(false);
  }

  private void onTaskItemActive(Task task) {
    manageSubscription(mTaskService.active(task.getId())
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(aVoid -> setState(State.ACTIVE_TASK), Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);
  }

  private void onTaskItemClick(Task task) {
    setState(State.CLICK_TASK, Constants.Key.ID, task.getId());
  }

  private void onTaskItemComplete(Task task) {
    manageSubscription(mTaskService.complete(task.getId())
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(aVoid -> setState(State.COMPLETE_TASK), Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);
  }

  private void reloadData(boolean init) {
    manageSubscription(mTaskService.getAll()
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(tasks -> {
          mTasks.clear();
          mTasks.addAll(tasks);
          if (!init) {
            setState(State.RELOADED_TASKS);
          }
        }), UnsubscribeLifeCycle.DESTROY);
  }

  public enum State {
    ACTIVE_TASK,
    COMPLETE_TASK,

    CLICK_TASK,

    RELOADED_TASKS,

    CREATED_TASK,
    REMOVED_TASK,
    UPDATED_TASK
  }
}
