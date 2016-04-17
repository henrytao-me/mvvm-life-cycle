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

import android.databinding.ObservableField;
import android.view.View;

import me.henrytao.mvvmlifecycledemo.data.model.Task;
import me.henrytao.mvvmlifecycledemo.ui.base.BaseViewModel;

/**
 * Created by henrytao on 4/15/16.
 */
public class TaskItemViewModel extends BaseViewModel {

  public ObservableField<String> description = new ObservableField<>();

  public ObservableField<String> title = new ObservableField<>();

  private Task mTask;

  public TaskItemViewModel() {
    register(this, Event.ON_TASK_ITEM_CLICK);
  }

  public void onItemClick(View view) {
    dispatch(Event.ON_TASK_ITEM_CLICK, mTask);
  }

  public void setTask(Task task) {
    mTask = task;
    title.set(task.getTitle());
    description.set(task.getDescription());
  }

  public enum Event {
    ON_TASK_ITEM_CLICK
  }
}
