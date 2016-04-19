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
import me.henrytao.mvvmlifecycledemo.data.exception.DataNotFoundException;
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

  public Observable<Void> active(String taskId) {
    return Observable.create(subscriber -> {
      try {
        mLocalAdapter.activeTask(taskId);
        SubscriptionUtils.onNextAndComplete(subscriber);
      } catch (Exception e) {
        SubscriptionUtils.onError(subscriber, e);
      }
    });
  }

  public Observable<Void> complete(String taskId) {
    return Observable.create(subscriber -> {
      try {
        mLocalAdapter.completeTask(taskId);
        SubscriptionUtils.onNextAndComplete(subscriber);
      } catch (Exception e) {
        SubscriptionUtils.onError(subscriber, e);
      }
    });
  }

  public Observable<Task> create(String title, String description) {
    return Observable.create(subscriber -> {
      Task task = mLocalAdapter.createTask(title, description);
      SubscriptionUtils.onNextAndComplete(subscriber, task);
    });
  }

  public Observable<Task> find(String taskId) {
    return Observable.create(subscriber -> {
      Task task = mLocalAdapter.findTask(taskId);
      if (task != null) {
        SubscriptionUtils.onNextAndComplete(subscriber, task);
      } else {
        SubscriptionUtils.onError(subscriber, new DataNotFoundException());
      }
    });
  }

  public Observable<List<Task>> getAll() {
    return Observable.create(subscriber -> {
      List<Task> tasks = mLocalAdapter.getTasks();
      SubscriptionUtils.onNextAndComplete(subscriber, tasks);
    });
  }

  public Observable<Task> observeTaskChange() {
    return mLocalAdapter.observeTaskUpdate();
  }

  public Observable<Task> observeTaskCreate() {
    return mLocalAdapter.observeTaskAdd();
  }

  public Observable<Task> observeTaskRemove() {
    return mLocalAdapter.observeTaskRemove();
  }

  public Observable<Void> remove(String taskId) {
    return Observable.create(subscriber -> {
      try {
        mLocalAdapter.removeTask(taskId);
        SubscriptionUtils.onNextAndComplete(subscriber);
      } catch (Exception e) {
        SubscriptionUtils.onError(subscriber, e);
      }
    });
  }

  public Observable<Void> update(String taskId, String title, String description) {
    return Observable.create(subscriber -> {
      try {
        mLocalAdapter.updateTask(taskId, title, description);
        SubscriptionUtils.onNextAndComplete(subscriber);
      } catch (Exception e) {
        SubscriptionUtils.onError(subscriber, e);
      }
    });
  }
}
