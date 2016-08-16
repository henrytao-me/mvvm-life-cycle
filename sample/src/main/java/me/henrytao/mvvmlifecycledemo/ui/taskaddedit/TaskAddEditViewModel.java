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

package me.henrytao.mvvmlifecycledemo.ui.taskaddedit;

import android.databinding.ObservableField;
import android.text.TextUtils;

import javax.inject.Inject;

import me.henrytao.mvvmlifecycle.rx.Transformer;
import me.henrytao.mvvmlifecycle.rx.UnsubscribeLifeCycle;
import me.henrytao.mvvmlifecycledemo.data.model.Task;
import me.henrytao.mvvmlifecycledemo.data.service.TaskService;
import me.henrytao.mvvmlifecycledemo.di.Injector;
import me.henrytao.mvvmlifecycledemo.ui.base.BaseViewModel;

/**
 * Created by henrytao on 4/5/16.
 */
public class TaskAddEditViewModel extends BaseViewModel<TaskAddEditViewModel.State> {

  private final String mTaskId;

  public ObservableField<String> description = new ObservableField<>();

  public ObservableField<String> title = new ObservableField<>();

  @Inject
  protected TaskService mTaskService;

  public TaskAddEditViewModel(String taskId) {
    Injector.component.inject(this);
    mTaskId = taskId;
  }

  @Override
  public void onCreate() {
    super.onCreate();

    if (isInEditMode()) {
      manageSubscription(mTaskService.find(mTaskId).compose(Transformer.applyComputationScheduler())
          .subscribe(this::onTaskLoadInEditMode, Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);
    }
  }

  public void onDoneClick() {
    if (isInEditMode()) {
      onUpdateTaskClick();
    } else {
      onCreateTaskClick();
    }
  }

  protected boolean isInEditMode() {
    return !TextUtils.isEmpty(mTaskId);
  }

  protected void onCreateTaskClick() {
    String title = this.title.get();
    String description = this.description.get();
    boolean isValid = !TextUtils.isEmpty(title);

    if (!isValid) {
      setState(State.MISSING_TITLE);
    } else {
      setState(State.CREATING_TASK);
      manageSubscription(mTaskService.create(title, description)
          .compose(Transformer.applyComputationScheduler())
          .subscribe(task -> setState(State.CREATED_TASK), Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);
    }
  }

  protected void onTaskLoadInEditMode(Task task) {
    title.set(task.getTitle());
    description.set(task.getDescription());
  }

  protected void onUpdateTaskClick() {
    String title = this.title.get();
    String description = this.description.get();
    boolean isValid = !TextUtils.isEmpty(title);

    if (!isValid) {
      setState(State.MISSING_TITLE);
    } else {
      setState(State.UPDATING_TASK);
      manageSubscription(mTaskService.update(mTaskId, title, description)
          .compose(Transformer.applyComputationScheduler())
          .subscribe(task -> setState(State.UPDATED_TASK), Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);
    }
  }

  public enum State {
    MISSING_TITLE,

    CREATED_TASK,
    CREATING_TASK,

    UPDATED_TASK,
    UPDATING_TASK
  }
}
