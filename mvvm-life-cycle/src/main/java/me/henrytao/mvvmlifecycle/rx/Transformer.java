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

package me.henrytao.mvvmlifecycle.rx;

import android.os.Build;
import android.os.Looper;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by henrytao on 4/23/16.
 */
public class Transformer {

  private static SchedulerManager sComputationScheduler = new SchedulerManager(Schedulers::computation);

  private static SchedulerManager sIoScheduler = new SchedulerManager(Schedulers::io);

  private static SchedulerManager sMainThreadScheduler = new SchedulerManager(AndroidSchedulers::mainThread);

  private static SchedulerManager sNewThreadScheduler = new SchedulerManager(Schedulers::newThread);

  public static <T> Observable.Transformer<T, T> applyComputationScheduler() {
    return observable -> observable
        .subscribeOn(sComputationScheduler.get())
        .observeOn(sMainThreadScheduler.get());
  }

  public static <T> Observable.Transformer<T, T> applyIoScheduler() {
    return observable -> observable
        .subscribeOn(sIoScheduler.get())
        .observeOn(sMainThreadScheduler.get());
  }

  public static <T> Observable.Transformer<T, T> applyMainThreadScheduler() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (Looper.getMainLooper().isCurrentThread()) {
        return observable -> observable;
      }
    } else {
      if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
        return observable -> observable;
      }
    }
    return observable -> observable
        .subscribeOn(sMainThreadScheduler.get())
        .observeOn(sMainThreadScheduler.get());
  }

  public static <T> Observable.Transformer<T, T> applyNewThreadScheduler() {
    return observable -> observable
        .subscribeOn(sNewThreadScheduler.get())
        .observeOn(sMainThreadScheduler.get());
  }

  public static Scheduler getComputationScheduler() {
    return sComputationScheduler.get();
  }

  public static Scheduler getIoScheduler() {
    return sIoScheduler.get();
  }

  public static Scheduler getMainThreadScheduler() {
    return sMainThreadScheduler.get();
  }

  public static Scheduler getNewThreadScheduler() {
    return sNewThreadScheduler.get();
  }

  public static void overrideComputationScheduler(Scheduler scheduler) {
    sComputationScheduler.set(scheduler);
  }

  public static void overrideIoScheduler(Scheduler scheduler) {
    sIoScheduler.set(scheduler);
  }

  public static void overrideMainThreadScheduler(Scheduler scheduler) {
    sMainThreadScheduler.set(scheduler);
  }

  public static void overrideNewThreadScheduler(Scheduler scheduler) {
    sNewThreadScheduler.set(scheduler);
  }

  public static void resetComputationScheduler() {
    sComputationScheduler.reset();
  }

  public static void resetIoScheduler() {
    sIoScheduler.reset();
  }

  public static void resetMainThreadScheduler() {
    sMainThreadScheduler.reset();
  }

  public static void resetNewThreadScheduler() {
    sNewThreadScheduler.reset();
  }

  private static class SchedulerManager {

    private Callback mDefaultSchedulerCallback;

    private Scheduler mScheduler;

    public SchedulerManager(Callback defaultSchedulerCallback) {
      mDefaultSchedulerCallback = defaultSchedulerCallback;
    }

    public Scheduler get() {
      return mScheduler != null ? mScheduler : mDefaultSchedulerCallback.get();
    }

    public void reset() {
      set(null);
    }

    public void set(Scheduler scheduler) {
      mScheduler = scheduler;
    }

    private interface Callback {

      Scheduler get();
    }
  }
}
