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

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import me.henrytao.mvvmlifecycledemo.data.model.Task;
import me.henrytao.mvvmlifecycledemo.ui.base.BaseViewModel;

/**
 * Created by henrytao on 4/15/16.
 */
public class TaskItemViewModel extends BaseViewModel {

  public ObservableBoolean completed = new ObservableBoolean();

  public ObservableField<String> description = new ObservableField<>();

  public ObservableField<String> title = new ObservableField<>();

  private Task mTask;

  public TaskItemViewModel() {
    register(this, Event.ON_TASK_ITEM_CLICK);
    register(this, Event.ON_TASK_ITEM_ACTIVE);
    register(this, Event.ON_TASK_ITEM_COMPLETE);
  }

  public void onItemCheckedChanged(boolean isChecked) {
    if (isChecked) {
      dispatch(Event.ON_TASK_ITEM_COMPLETE, mTask);
    } else {
      dispatch(Event.ON_TASK_ITEM_ACTIVE, mTask);
    }
  }

  public void onItemClick() {
    dispatch(Event.ON_TASK_ITEM_CLICK, mTask);
  }

  public void setTask(Task task) {
    mTask = task;
    title.set(task.getTitle());
    description.set(task.getDescription());
    completed.set(task.isCompleted());
  }

  public enum Event {
    ON_TASK_ITEM_CLICK,

    ON_TASK_ITEM_ACTIVE,
    ON_TASK_ITEM_COMPLETE
  }
}
