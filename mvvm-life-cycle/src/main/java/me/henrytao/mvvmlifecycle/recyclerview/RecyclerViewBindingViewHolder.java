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

/**
 * Created by henrytao on 4/15/16.
 */
public abstract class RecyclerViewBindingViewHolder<D> extends RecyclerView.ViewHolder {

  public abstract void bind(D data);

  public abstract void onInitializeViewModels();

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

  public void addViewModel(MVVMViewModel viewModel) {
    mObserver.addViewModel(viewModel);
  }
}
