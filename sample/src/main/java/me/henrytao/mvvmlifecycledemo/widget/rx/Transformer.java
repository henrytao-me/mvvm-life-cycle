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

package me.henrytao.mvvmlifecycledemo.widget.rx;

import com.google.common.annotations.VisibleForTesting;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by henrytao on 4/23/16.
 */
public class Transformer {

  private static Scheduler sMainThreadScheduler;

  public static <T> Observable.Transformer<T, T> applyComputationScheduler() {
    return observable -> observable
        .subscribeOn(Schedulers.computation())
        .observeOn(getMainThreadScheduler());
  }

  public static <T> Observable.Transformer<T, T> applyIoScheduler() {
    return observable -> observable
        .subscribeOn(Schedulers.io())
        .observeOn(getMainThreadScheduler());
  }

  public static <T> Observable.Transformer<T, T> applyNewThreadScheduler() {
    return observable -> observable
        .subscribeOn(Schedulers.newThread())
        .observeOn(getMainThreadScheduler());
  }

  @VisibleForTesting
  public static void overrideMainThreadScheduler(Scheduler scheduler) {
    sMainThreadScheduler = scheduler;
  }

  public static void resetMainThreadScheduler() {
    sMainThreadScheduler = null;
  }

  private static Scheduler getMainThreadScheduler() {
    return sMainThreadScheduler != null ? sMainThreadScheduler : AndroidSchedulers.mainThread();
  }
}
