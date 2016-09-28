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

package me.henrytao.mvvmlifecycle.viewmodel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.os.Bundle;

import me.henrytao.mvvmlifecycle.MVVMLifeCycle;
import me.henrytao.mvvmlifecycle.rx.SubscriptionManager;
import me.henrytao.mvvmlifecycle.rx.UnsubscribeLifeCycle;
import rx.Subscription;

/**
 * Created by henrytao on 4/24/16.
 */
public class SimpleViewModel extends BaseObservable implements MVVMLifeCycle {

  private boolean mIsDestroy;

  private boolean mIsDestroyView;

  private boolean mIsPause;

  private boolean mIsStop;

  private SubscriptionManager mSubscriptionManager = new SubscriptionManager();

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    // do nothing
  }

  public void onCreate() {
    mIsDestroy = false;
  }

  public void onCreateView() {
    mIsDestroyView = false;
  }

  public void onDestroy() {
    mIsDestroy = true;
    mSubscriptionManager.unsubscribe(UnsubscribeLifeCycle.DESTROY);
    mSubscriptionManager.unsubscribe();
  }

  public void onDestroyView() {
    mIsDestroyView = true;
    mSubscriptionManager.unsubscribe(UnsubscribeLifeCycle.DESTROY_VIEW);
  }

  public void onPause() {
    mIsPause = true;
    mSubscriptionManager.unsubscribe(UnsubscribeLifeCycle.PAUSE);
  }

  public void onRestoreInstanceState(Bundle savedInstanceState) {
  }

  public void onResume() {
    mIsPause = false;
  }

  public void onSaveInstanceState(Bundle outState) {
  }

  public void onStart() {
    mIsStop = false;
  }

  public void onStop() {
    mIsStop = true;
    mSubscriptionManager.unsubscribe(UnsubscribeLifeCycle.STOP);
  }

  public void manageSubscription(UnsubscribeLifeCycle unsubscribeLifeCycle, Subscription... subscriptions) {
    for (Subscription subscription : subscriptions) {
      manageSubscription(subscription, unsubscribeLifeCycle);
    }
  }

  public void manageSubscription(Subscription subscription, UnsubscribeLifeCycle unsubscribeLifeCycle) {
    if ((unsubscribeLifeCycle == UnsubscribeLifeCycle.DESTROY && mIsDestroy) ||
        (unsubscribeLifeCycle == UnsubscribeLifeCycle.DESTROY_VIEW && mIsDestroyView) ||
        (unsubscribeLifeCycle == UnsubscribeLifeCycle.STOP && mIsStop) ||
        (unsubscribeLifeCycle == UnsubscribeLifeCycle.PAUSE && mIsPause)) {
      if (subscription != null && !subscription.isUnsubscribed()) {
        subscription.unsubscribe();
      }
      return;
    }
    mSubscriptionManager.manageSubscription(subscription, unsubscribeLifeCycle);
  }

  public void manageSubscription(String id, Subscription subscription, UnsubscribeLifeCycle unsubscribeLifeCycle) {
    if ((unsubscribeLifeCycle == UnsubscribeLifeCycle.DESTROY && mIsDestroy) ||
        (unsubscribeLifeCycle == UnsubscribeLifeCycle.DESTROY_VIEW && mIsDestroyView) ||
        (unsubscribeLifeCycle == UnsubscribeLifeCycle.STOP && mIsStop) ||
        (unsubscribeLifeCycle == UnsubscribeLifeCycle.PAUSE && mIsPause)) {
      if (subscription != null && !subscription.isUnsubscribed()) {
        subscription.unsubscribe();
      }
      return;
    }
    mSubscriptionManager.manageSubscription(id, subscription, unsubscribeLifeCycle);
  }

  public void unsubscribe(String id) {
    mSubscriptionManager.unsubscribe(id);
  }
}
