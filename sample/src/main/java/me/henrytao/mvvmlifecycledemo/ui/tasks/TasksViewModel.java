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
import me.henrytao.mvvmlifecycledemo.widget.rx.Transformer;

/**
 * Created by henrytao on 4/15/16.
 */
public class TasksViewModel extends BaseViewModel<TasksViewModel.State> {

  @Inject
  protected TaskService mTaskService;

  private List<Task> mTasks = new ArrayList<>();

  public TasksViewModel() {
    Injector.component.inject(this);
  }

  @Override
  public void onCreate() {
    super.onCreate();

    manageSubscription(subscribe(TaskItemViewModel.Event.ON_TASK_ITEM_CLICK, new Event1<>(this::onTaskItemClick)),
        UnsubscribeLifeCycle.DESTROY);
    manageSubscription(subscribe(TaskItemViewModel.Event.ON_ACTIVE_TASK_ITEM_CLICK, new Event1<>(this::onActiveTaskItemClick)),
        UnsubscribeLifeCycle.DESTROY);
    manageSubscription(subscribe(TaskItemViewModel.Event.ON_COMPLETE_TASK_ITEM_CLICK, new Event1<>(this::onCompleteTaskItemClick)),
        UnsubscribeLifeCycle.DESTROY);

    manageSubscription(mTaskService.observeTaskCreate().compose(Transformer.applyComputationScheduler())
        .subscribe(this::onTaskCreate, Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);

    manageSubscription(mTaskService.observeTaskUpdate().compose(Transformer.applyComputationScheduler())
        .subscribe(this::onTaskUpdate, Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);

    manageSubscription(mTaskService.observeTaskRemove().compose(Transformer.applyComputationScheduler())
        .subscribe(this::onTaskRemove, Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);

    reloadData(false);
  }

  public List<Task> getTasks() {
    return mTasks;
  }

  public void reloadData() {
    reloadData(false);
  }

  protected void onActiveTaskItemClick(Task task) {
    manageSubscription(mTaskService.active(task.getId())
        .compose(Transformer.applyComputationScheduler())
        .subscribe(aVoid -> setState(State.ACTIVE_TASK), Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);
  }

  protected void onCompleteTaskItemClick(Task task) {
    manageSubscription(mTaskService.complete(task.getId())
        .compose(Transformer.applyComputationScheduler())
        .subscribe(aVoid -> setState(State.COMPLETE_TASK), Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);
  }

  protected void onTaskCreate(Task task) {
    mTasks.add(task);
    setState(State.CREATED_TASK, Constants.Key.INDEX, mTasks.size() - 1);
  }

  protected void onTaskItemClick(Task task) {
    setState(State.CLICK_TASK, Constants.Key.ID, task.getId());
  }

  protected void onTaskRemove(Task task) {
    mTasks.remove(task);
    setState(State.REMOVED_TASK);
  }

  protected void onTaskUpdate(Task task) {
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
  }

  protected void reloadData(boolean init) {
    manageSubscription(mTaskService.getAll()
        .compose(Transformer.applyComputationScheduler())
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
