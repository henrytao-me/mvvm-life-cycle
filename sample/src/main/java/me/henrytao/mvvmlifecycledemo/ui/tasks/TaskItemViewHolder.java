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

package me.henrytao.mvvmlifecycledemo.ui.tasks;

import android.databinding.DataBindingUtil;
import android.view.ViewGroup;

import me.henrytao.mvvmlifecycle.MVVMObserver;
import me.henrytao.mvvmlifecycle.recyclerview.RecyclerViewBindingViewHolder;
import me.henrytao.mvvmlifecycledemo.R;
import me.henrytao.mvvmlifecycledemo.data.model.Task;
import me.henrytao.mvvmlifecycledemo.databinding.TaskItemViewHolderBinding;

/**
 * Created by henrytao on 4/15/16.
 */
public class TaskItemViewHolder extends RecyclerViewBindingViewHolder<Task> {

  private final TaskItemViewHolderBinding mBinding;

  private TaskItemViewModel mViewModel;

  public TaskItemViewHolder(MVVMObserver observer, ViewGroup parent) {
    super(observer, parent, R.layout.task_item_view_holder);

    mBinding = DataBindingUtil.bind(itemView);
    mBinding.setViewModel(mViewModel);
  }

  @Override
  public void bind(Task data) {
    mViewModel.setTask(data);
  }

  @Override
  public void onInitializeViewModels() {
    mViewModel = new TaskItemViewModel();
    addViewModel(mViewModel);
  }
}
