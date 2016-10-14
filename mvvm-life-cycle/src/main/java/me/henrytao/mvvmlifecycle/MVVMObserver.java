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

import me.henrytao.mvvmlifecycle.rx.UnsubscribeLifeCycle;
import rx.Subscription;

/**
 * Created by henrytao on 2/17/16.
 */
public interface MVVMObserver {

  void addAdapterViewModel(MVVMViewModel viewModel) throws IllegalAccessException;

  void addViewModel(MVVMViewModel viewModel);

  void manageSubscription(UnsubscribeLifeCycle unsubscribeLifeCycle, Subscription... subscriptions);

  void manageSubscription(String id, Subscription subscription, UnsubscribeLifeCycle unsubscribeLifeCycle);

  void manageSubscription(Subscription subscription, UnsubscribeLifeCycle unsubscribeLifeCycle);

  void onInitializeViewModels();

  void unsubscribe(String id);
}
