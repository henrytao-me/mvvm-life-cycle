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

package me.henrytao.mvvmlifecycledemo.data.adapter.local;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.henrytao.mvvmlifecycledemo.data.model.Task;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by henrytao on 4/14/16.
 */
public class LocalAdapter implements me.henrytao.mvvmlifecycledemo.data.adapter.LocalAdapter {

  private static final List<Task> sTasks = new ArrayList<>();

  static {
    Task task;
    for (int i = 0; i < 10; i++) {
      task = new Task(String.format(Locale.US, "task %d", i + 1), String.format(Locale.US, "description %d", i + 1));
      sTasks.add(task);
    }
  }

  private final PublishSubject<Task> mTaskChangedSubject = PublishSubject.create();

  private final PublishSubject<Task> mTaskCreatedSubject = PublishSubject.create();

  private final PublishSubject<Task> mTaskRemoveSubject = PublishSubject.create();

  @Override
  public void activeTask(String taskId) {
    Task task;
    int n = sTasks.size();
    for (int i = 0; i < n; i++) {
      task = sTasks.get(i);
      if (TextUtils.equals(task.getId(), taskId)) {
        task.active();
        mTaskChangedSubject.onNext(task);
        break;
      }
    }
  }

  @Override
  public void completeTask(String taskId) {
    Task task;
    int n = sTasks.size();
    for (int i = 0; i < n; i++) {
      task = sTasks.get(i);
      if (TextUtils.equals(task.getId(), taskId)) {
        task.complete();
        mTaskChangedSubject.onNext(task);
        break;
      }
    }
  }

  @Override
  public Task createTask(String title, String description) {
    Task task = new Task(title, description);
    sTasks.add(task);
    mTaskCreatedSubject.onNext(task);
    return task;
  }

  @Override
  public Task findTask(String taskId) {
    Task task = null;
    int n = sTasks.size();
    for (int i = 0; i < n; i++) {
      if (TextUtils.equals(sTasks.get(i).getId(), taskId)) {
        task = sTasks.get(i);
        break;
      }
    }
    return task;
  }

  @Override
  public List<Task> getTasks() {
    return new ArrayList<>(sTasks);
  }

  @Override
  public Observable<Task> observeTaskChange() {
    return mTaskChangedSubject;
  }

  @Override
  public Observable<Task> observeTaskCreate() {
    return mTaskCreatedSubject;
  }

  @Override
  public Observable<Task> observeTaskRemove() {
    return mTaskRemoveSubject;
  }

  @Override
  public Task removeTask(String taskId) {
    Task task = findTask(taskId);
    sTasks.remove(task);
    mTaskRemoveSubject.onNext(task);
    return task;
  }

  @Override
  public Task updateTask(String taskId, String title, String description) {
    Task task = findTask(taskId);
    task.setTitle(title);
    task.setDescription(description);
    mTaskChangedSubject.onNext(task);
    return task;
  }
}
