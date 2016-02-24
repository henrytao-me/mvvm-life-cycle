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

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by henrytao on 2/22/16.
 */
public abstract class MVVMRecyclerViewAdapter<D, V extends MVVMRecyclerViewHolder<D>> extends RecyclerView.Adapter<V> {

  protected final List<D> mData;

  protected final MVVMObserver mObserver;

  public MVVMRecyclerViewAdapter(MVVMObserver observer, @NonNull List<D> data) {
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
}
