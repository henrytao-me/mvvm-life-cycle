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
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import me.henrytao.mdcore.utils.ResourceUtils;
import me.henrytao.mvvmlifecycle.MVVMObserver;
import me.henrytao.mvvmlifecycle.recyclerview.RecyclerViewBindingAdapter;
import me.henrytao.mvvmlifecycle.rx.UnsubscribeLifeCycle;
import me.henrytao.mvvmlifecycledemo.R;
import me.henrytao.mvvmlifecycledemo.data.model.Task;
import me.henrytao.mvvmlifecycledemo.databinding.TasksFragmentBinding;
import me.henrytao.mvvmlifecycledemo.ui.base.BaseFragment;
import me.henrytao.mvvmlifecycledemo.ui.taskdetail.TaskDetailActivity;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by henrytao on 4/2/16.
 */
public class TasksFragment extends BaseFragment {

  public static TasksFragment newInstance() {
    return new TasksFragment();
  }

  private RecyclerView.Adapter mAdapter;

  private TasksViewModel mViewModel;

  private SwipeRefreshLayout vSwipeRefreshLayout;

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_tasks, menu);
    ResourceUtils.supportDrawableTint(getContext(), menu, ResourceUtils.Palette.PRIMARY);
  }

  @Override
  public void onCreateView() {
    super.onCreateView();
    setHasOptionsMenu(true);

    mAdapter = new RecyclerViewBindingAdapter<Task, TaskItemViewHolder>(this, mViewModel.getTasks()) {
      @Override
      public TaskItemViewHolder onCreateViewHolder(MVVMObserver observer, ViewGroup parent) {
        return new TaskItemViewHolder(observer, parent);
      }
    };

    RecyclerView recyclerView = (RecyclerView) getView().findViewById(android.R.id.list);
    recyclerView.setAdapter(mAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    vSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh_layout);
    vSwipeRefreshLayout.setOnRefreshListener(() -> mViewModel.reloadData());

    manageSubscription(mViewModel.getState().observeOn(AndroidSchedulers.mainThread()).subscribe(state -> {
      switch (state.getName()) {
        case TasksViewModel.STATE_ACTIVE_TASK:
          Snackbar.make(getView().findViewById(R.id.swipe_refresh_layout), R.string.task_marked_active, Snackbar.LENGTH_SHORT).show();
          break;
        case TasksViewModel.STATE_ADDED_TASK:
          mAdapter.notifyDataSetChanged();
          vSwipeRefreshLayout.setRefreshing(false);
          break;
        case TasksViewModel.STATE_CLICK_TASK:
          String taskId = (String) state.getData().get(TasksViewModel.KEY_ID);
          startActivity(TaskDetailActivity.newIntent(getContext(), taskId));
          break;
        case TasksViewModel.STATE_COMPLETE_TASK:
          Snackbar.make(getView().findViewById(R.id.swipe_refresh_layout), R.string.task_marked_complete, Snackbar.LENGTH_SHORT).show();
          break;
      }
    }), UnsubscribeLifeCycle.DESTROY_VIEW);
  }

  @Override
  public View onInflateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    TasksFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.tasks_fragment, container, false);
    return binding.getRoot();
  }

  @Override
  public void onInitializeViewModels() {
    mViewModel = new TasksViewModel();
    addViewModel(mViewModel);
  }
}
