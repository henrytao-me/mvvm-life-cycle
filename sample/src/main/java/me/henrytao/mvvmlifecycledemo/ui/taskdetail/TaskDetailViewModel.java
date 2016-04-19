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

package me.henrytao.mvvmlifecycledemo.ui.taskdetail;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import javax.inject.Inject;

import me.henrytao.mvvmlifecycle.rx.UnsubscribeLifeCycle;
import me.henrytao.mvvmlifecycledemo.data.model.Task;
import me.henrytao.mvvmlifecycledemo.data.service.TaskService;
import me.henrytao.mvvmlifecycledemo.di.Injector;
import me.henrytao.mvvmlifecycledemo.ui.base.BaseViewModel;
import me.henrytao.mvvmlifecycledemo.ui.base.Constants;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by henrytao on 4/17/16.
 */
public class TaskDetailViewModel extends BaseViewModel<TaskDetailViewModel.State> {

  public ObservableBoolean completed = new ObservableBoolean();

  public ObservableField<String> description = new ObservableField<>();

  public ObservableField<String> title = new ObservableField<>();

  public ObservableBoolean visible = new ObservableBoolean();

  @Inject
  protected TaskService mTaskService;

  private Task mTask = null;

  public TaskDetailViewModel(String taskId) {
    Injector.component.inject(this);

    manageSubscription(mTaskService.find(taskId)
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(task -> {
          mTask = task;
          visible.set(true);
          title.set(task.getTitle());
          description.set(task.getDescription());
          completed.set(task.isCompleted());
        }), UnsubscribeLifeCycle.DESTROY);

    manageSubscription(mTaskService.observeTaskUpdate()
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(task -> {
          mTask = task;
          title.set(task.getTitle());
          description.set(task.getDescription());
          completed.set(task.isCompleted());
        }), UnsubscribeLifeCycle.DESTROY);
  }

  public void onDeleteTaskClick() {
    manageSubscription(mTaskService.remove(mTask.getId())
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(task -> setState(State.DELETE_TASK), Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);
  }

  public void onEditTaskClick() {
    setState(State.CLICK_EDIT_TASK, Constants.Key.ID, mTask.getId());
  }

  public void onTaskCheckedChanged(boolean isChecked) {
    if (isChecked) {
      manageSubscription(mTaskService.complete(mTask.getId())
          .subscribeOn(Schedulers.computation())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(aVoid -> setState(State.COMPLETE_TASK), Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);
    } else {
      manageSubscription(mTaskService.active(mTask.getId())
          .subscribeOn(Schedulers.computation())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(aVoid -> setState(State.ACTIVE_TASK), Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);
    }
  }

  public enum State {
    CLICK_EDIT_TASK,

    ACTIVE_TASK,
    COMPLETE_TASK,

    DELETE_TASK
  }
}
