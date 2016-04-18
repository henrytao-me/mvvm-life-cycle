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

import me.henrytao.mvvmlifecycle.rx.UnsubscribeLifeCycle;
import me.henrytao.mvvmlifecycledemo.data.service.TaskService;
import me.henrytao.mvvmlifecycledemo.di.Injector;
import me.henrytao.mvvmlifecycledemo.ui.base.BaseViewModel;
import me.henrytao.mvvmlifecycledemo.ui.base.State;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by henrytao on 4/5/16.
 */
public class TaskAddEditViewModel extends BaseViewModel {

  public static final String STATE_CREATED_TASK = "STATE_CREATED_TASK";

  public static final String STATE_CREATING_TASK = "STATE_CREATING_TASK";

  public static final String STATE_MISSING_TITLE = "STATE_MISSING_TITLE";

  public static final String STATE_UPDATED_TASK = "STATE_UPDATE_TASK";

  public static final String STATE_UPDATING_TASK = "STATE_UPDATING_TASK";

  private final String mTaskId;

  public ObservableField<String> description = new ObservableField<>();

  public ObservableField<String> title = new ObservableField<>();

  @Inject
  protected TaskService mTaskService;

  public TaskAddEditViewModel(String taskId) {
    Injector.component.inject(this);

    mTaskId = taskId;

    if (isInEditMode()) {
      manageSubscription(mTaskService.find(taskId)
          .subscribeOn(Schedulers.computation())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(task -> {
            title.set(task.getTitle());
            description.set(task.getDescription());
          }), UnsubscribeLifeCycle.DESTROY);
    }
  }

  public void onDoneClick() {
    if (isInEditMode()) {
      onUpdateTask();
    } else {
      onCreateTask();
    }
  }

  private boolean isInEditMode() {
    return !TextUtils.isEmpty(mTaskId);
  }

  private void onCreateTask() {
    String title = this.title.get();
    String description = this.description.get();
    boolean isValid = !TextUtils.isEmpty(title);

    if (!isValid) {
      setState(State.create(STATE_MISSING_TITLE));
    } else {
      setState(State.create(STATE_CREATING_TASK));
      manageSubscription(mTaskService.create(title, description)
          .subscribeOn(Schedulers.computation())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(task -> {
            setState(State.create(STATE_CREATED_TASK));
          }, Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);
    }
  }

  private void onUpdateTask() {
    String title = this.title.get();
    String description = this.description.get();
    boolean isValid = !TextUtils.isEmpty(title);

    if (!isValid) {
      setState(State.create(STATE_MISSING_TITLE));
    } else {
      setState(State.create(STATE_UPDATING_TASK));
      manageSubscription(mTaskService.update(mTaskId, title, description)
          .subscribeOn(Schedulers.computation())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(task -> {
            setState(State.create(STATE_UPDATED_TASK));
          }, Throwable::printStackTrace), UnsubscribeLifeCycle.DESTROY);
    }
  }
}
