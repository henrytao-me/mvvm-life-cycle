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

package me.henrytao.mvvmlifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import me.henrytao.mvvmlifecycle.rx.SubscriptionManager;
import me.henrytao.mvvmlifecycle.rx.UnsubscribeLifeCycle;
import rx.Subscription;

/**
 * Created by henrytao on 11/10/15.
 * Reference: http://developer.android.com/training/basics/activity-lifecycle/starting.html
 */
public abstract class MVVMActivity extends AppCompatActivity implements MVVMLifeCycle, MVVMObserver {

  public abstract void onSetContentView(Bundle savedInstanceState);

  protected List<MVVMViewModel> mViewModels = new ArrayList<>();

  private List<MVVMViewModel> mAdapterViewModels = new ArrayList<>();

  private boolean mIsDestroy;

  private boolean mIsDestroyView;

  private boolean mIsPause;

  private boolean mIsStop;

  private Constants.State mState;

  private SubscriptionManager mSubscriptionManager = new SubscriptionManager();

  @Override
  public void addAdapterViewModel(MVVMViewModel viewModel) throws IllegalAccessException {
    addViewModel(viewModel);
    if (!mAdapterViewModels.contains(viewModel)) {
      mAdapterViewModels.add(viewModel);
    }
    throw new IllegalAccessException("This method should be used with care");
  }

  @Override
  public void addViewModel(MVVMViewModel viewModel) {
    if (!mViewModels.contains(viewModel)) {
      mViewModels.add(viewModel);
      propagateLifeCycle(viewModel);
    }
  }

  @Override
  public void manageSubscription(UnsubscribeLifeCycle unsubscribeLifeCycle, Subscription... subscriptions) {
    for (Subscription subscription : subscriptions) {
      manageSubscription(subscription, unsubscribeLifeCycle);
    }
  }

  @Override
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

  @Override
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

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    // dispatching
    for (MVVMViewModel viewModel : mViewModels) {
      viewModel.onActivityResult(requestCode, resultCode, data);
    }
  }

  @Override
  public void onCreate() {
    mState = Constants.State.ON_CREATE;
    mIsDestroy = false;
    // dispatching
    for (MVVMViewModel viewModel : mViewModels) {
      viewModel.onCreate();
    }
  }

  @Override
  public void onCreateView() {
    mState = Constants.State.ON_CREATE_VIEW;
    mIsDestroyView = false;
    // dispatching
    for (MVVMViewModel viewModel : mViewModels) {
      viewModel.onCreateView();
    }
  }

  @Override
  public void onDestroy() {
    onDestroyView();
    mState = Constants.State.ON_DESTROY;
    mIsDestroy = true;
    mSubscriptionManager.unsubscribe(UnsubscribeLifeCycle.DESTROY);
    mSubscriptionManager.unsubscribe();
    super.onDestroy();
    // dispatching
    for (MVVMViewModel viewModel : mViewModels) {
      viewModel.onDestroy();
    }
    mViewModels.clear();
  }

  @Override
  public void onDestroyView() {
    mState = Constants.State.ON_DESTROY_VIEW;
    mIsDestroyView = true;
    mSubscriptionManager.unsubscribe(UnsubscribeLifeCycle.DESTROY_VIEW);
    // dispatching
    for (MVVMViewModel viewModel : mViewModels) {
      viewModel.onDestroyView();
    }
    for (MVVMViewModel viewModel : mAdapterViewModels) {
      viewModel.onDestroy();
    }
    mViewModels.removeAll(mAdapterViewModels);
    mAdapterViewModels.clear();
  }

  @Override
  public void onPause() {
    mState = Constants.State.ON_PAUSE;
    mIsPause = true;
    mSubscriptionManager.unsubscribe(UnsubscribeLifeCycle.PAUSE);
    super.onPause();
    // dispatching
    for (MVVMViewModel viewModel : mViewModels) {
      viewModel.onPause();
    }
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    savedInstanceState = savedInstanceState != null ? savedInstanceState : new Bundle();
    super.onRestoreInstanceState(savedInstanceState);
    // dispatching
    for (MVVMViewModel viewModel : mViewModels) {
      viewModel.onRestoreInstanceState(savedInstanceState);
    }
  }

  @Override
  public void onResume() {
    mState = Constants.State.ON_RESUME;
    mIsPause = false;
    super.onResume();
    // dispatching
    for (MVVMViewModel viewModel : mViewModels) {
      viewModel.onResume();
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    // dispatching
    for (MVVMViewModel viewModel : mViewModels) {
      viewModel.onSaveInstanceState(outState);
    }
  }

  @Override
  public void onStart() {
    mState = Constants.State.ON_START;
    mIsStop = false;
    super.onStart();
    // dispatching
    for (MVVMViewModel viewModel : mViewModels) {
      viewModel.onStart();
    }
  }

  @Override
  public void onStop() {
    mState = Constants.State.ON_STOP;
    mIsStop = true;
    mSubscriptionManager.unsubscribe(UnsubscribeLifeCycle.STOP);
    super.onStop();
    // dispatching
    for (MVVMViewModel viewModel : mViewModels) {
      viewModel.onStop();
    }
  }

  @Override
  public void unsubscribe(String id) {
    mSubscriptionManager.unsubscribe(id);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mViewModels = new ArrayList<>();
    Bundle bundle = getIntent().getExtras();
    onInitializeBundle(bundle != null ? bundle : new Bundle(), savedInstanceState);
    onInitializeViewModels();
    onCreate();
    onSetContentView(savedInstanceState);
    onCreateView();
  }

  public void onInitializeBundle(Bundle bundle, Bundle savedInstanceState) {
  }

  private void propagateLifeCycle(@NonNull MVVMViewModel viewModel) {
    if (mState == null) {
      return;
    }
    int n = Constants.STATES.indexOf(mState) + 1;
    for (int i = 0; i < n; i++) {
      switch (Constants.STATES.get(i)) {
        case ON_CREATE:
          viewModel.onCreate();
          break;
        case ON_CREATE_VIEW:
          viewModel.onCreateView();
          break;
        case ON_START:
          viewModel.onStart();
          break;
        case ON_RESUME:
          viewModel.onResume();
          break;
        case ON_PAUSE:
          viewModel.onPause();
          break;
        case ON_STOP:
          viewModel.onStop();
          break;
        case ON_DESTROY_VIEW:
          viewModel.onDestroyView();
          break;
        case ON_DESTROY:
          viewModel.onDestroy();
          break;
      }
    }
  }
}
