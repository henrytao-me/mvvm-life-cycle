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

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import me.henrytao.mvvmlifecycle.MVVMObserver;

/**
 * Created by henrytao on 4/15/16.
 */
public abstract class RecyclerViewBindingAdapter<D, V extends RecyclerViewBindingViewHolder<D>> extends RecyclerView.Adapter<V> {

  public abstract V onCreateViewHolder(MVVMObserver observer, ViewGroup parent);

  protected final List<D> mData;

  protected final MVVMObserver mObserver;

  public RecyclerViewBindingAdapter(MVVMObserver observer, @NonNull List<D> data) {
    mObserver = observer;
    mData = data;
  }

  @Override
  public int getItemCount() {
    return mData.size();
  }

  @Override
  public void onBindViewHolder(V holder, int position) {
    holder.bind(mData.get(position));
  }

  @Override
  public V onCreateViewHolder(ViewGroup parent, int viewType) {
    return onCreateViewHolder(mObserver, parent);
  }
}
