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

package me.henrytao.mvvmlifecycledemo.data.service;

import java.util.List;

import me.henrytao.mvvmlifecycle.rx.SubscriptionUtils;
import me.henrytao.mvvmlifecycledemo.data.adapter.LocalAdapter;
import me.henrytao.mvvmlifecycledemo.data.model.Task;
import rx.Observable;

/**
 * Created by henrytao on 4/14/16.
 */
public class TaskService {

  private LocalAdapter mLocalAdapter;

  public TaskService(LocalAdapter localAdapter) {
    mLocalAdapter = localAdapter;
  }

  public Observable<Void> complete(String taskId) {
    return Observable.create(subscriber -> {
      mLocalAdapter.completeTask(taskId);
      SubscriptionUtils.onNextAndComplete(subscriber);
    });
  }

  public Observable<Void> active(String taskId) {
    return Observable.create(subscriber -> {
      mLocalAdapter.activeTask(taskId);
      SubscriptionUtils.onNextAndComplete(subscriber);
    });
  }

  public Observable<Task> create(String title, String description) {
    return Observable.create(subscriber -> {
      Task task = mLocalAdapter.createTask(title, description);
      SubscriptionUtils.onNextAndComplete(subscriber, task);
    });
  }

  public Observable<List<Task>> getAll() {
    return Observable.create(subscriber -> {
      List<Task> tasks = mLocalAdapter.getTasks();
      SubscriptionUtils.onNextAndComplete(subscriber, tasks);
    });
  }

  public Observable<Task> observeTaskCreate() {
    return mLocalAdapter.observeTaskCreate();
  }
}
