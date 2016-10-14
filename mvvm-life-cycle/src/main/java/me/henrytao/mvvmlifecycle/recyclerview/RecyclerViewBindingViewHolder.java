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

package me.henrytao.mvvmlifecycle.recyclerview;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.henrytao.mvvmlifecycle.MVVMObserver;
import me.henrytao.mvvmlifecycle.MVVMViewModel;
import me.henrytao.mvvmlifecycle.rx.UnsubscribeLifeCycle;
import rx.Subscription;

/**
 * Created by henrytao on 4/15/16.
 */
public abstract class RecyclerViewBindingViewHolder<D> extends RecyclerView.ViewHolder implements MVVMObserver {

  public abstract void bind(D data);

  protected MVVMObserver mObserver;

  /**
   * This constructor should be extended and set proper layoutId
   */
  public RecyclerViewBindingViewHolder(MVVMObserver observer, ViewGroup parent) {
    this(observer, parent, 0);
  }

  protected RecyclerViewBindingViewHolder(MVVMObserver observer, ViewGroup parent, View view) {
    super(view);
    mObserver = observer;
    onInitializeViewModels();
  }

  protected RecyclerViewBindingViewHolder(MVVMObserver observer, ViewGroup parent, @LayoutRes int layoutId) {
    this(observer, parent, LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
  }

  @Override
  public void addAdapterViewModel(MVVMViewModel viewModel) throws IllegalAccessException {
    throw new IllegalAccessException("This method should not be called");
  }

  @Override
  public void addViewModel(MVVMViewModel viewModel) {
    try {
      mObserver.addAdapterViewModel(viewModel);
    } catch (IllegalAccessException ignore) {
    }
  }

  @Override
  public void manageSubscription(UnsubscribeLifeCycle unsubscribeLifeCycle, Subscription... subscriptions) {
    mObserver.manageSubscription(unsubscribeLifeCycle, subscriptions);
  }

  @Override
  public void manageSubscription(String id, Subscription subscription, UnsubscribeLifeCycle unsubscribeLifeCycle) {
    mObserver.manageSubscription(id, subscription, unsubscribeLifeCycle);
  }

  @Override
  public void manageSubscription(Subscription subscription, UnsubscribeLifeCycle unsubscribeLifeCycle) {
    mObserver.manageSubscription(subscription, unsubscribeLifeCycle);
  }

  @Override
  public void unsubscribe(String id) {
    mObserver.unsubscribe(id);
  }
}
