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

package me.henrytao.mvvmlifecycledemo.ui.taskdetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;

import me.henrytao.mdcore.utils.ResourceUtils;
import me.henrytao.mvvmlifecycle.rx.UnsubscribeLifeCycle;
import me.henrytao.mvvmlifecycledemo.R;
import me.henrytao.mvvmlifecycledemo.databinding.TaskDetailActivityBinding;
import me.henrytao.mvvmlifecycledemo.ui.base.BaseActivity;
import me.henrytao.mvvmlifecycledemo.ui.base.Constants;
import me.henrytao.mvvmlifecycledemo.ui.taskaddedit.TaskAddEditActivity;

/**
 * Created by henrytao on 4/2/16.
 */
public class TaskDetailActivity extends BaseActivity {

  public static Intent newIntent(Context context, String taskId) {
    Intent intent = new Intent(context, TaskDetailActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString(Constants.Extra.ID, taskId);
    intent.putExtras(bundle);
    return intent;
  }

  private TaskDetailActivityBinding mBinding;

  private TaskDetailViewModel mViewModel;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_task_detail, menu);
    ResourceUtils.supportDrawableTint(this, menu, ResourceUtils.Palette.PRIMARY);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public void onInitializeViewModels() {
    Bundle bundle = getIntent().getExtras();
    String taskId = bundle.getString(Constants.Extra.ID);

    mViewModel = new TaskDetailViewModel(taskId);
    addViewModel(mViewModel);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_delete:
        mViewModel.onDeleteTaskClick();
        break;
    }
    return true;
  }

  @Override
  public void onSetContentView(Bundle savedInstanceState) {
    mBinding = DataBindingUtil.setContentView(this, R.layout.task_detail_activity);
    mBinding.setViewModel(mViewModel);

    setSupportActionBar(mBinding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    mBinding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    ResourceUtils.supportDrawableTint(this, mBinding.toolbar, ResourceUtils.Palette.PRIMARY);

    manageSubscription(mViewModel.getState().subscribe(state -> {
      switch (state.getName()) {
        case ACTIVE_TASK:
          Snackbar.make(mBinding.container, R.string.task_marked_active, Snackbar.LENGTH_SHORT).show();
          break;
        case COMPLETE_TASK:
          Snackbar.make(mBinding.container, R.string.task_marked_complete, Snackbar.LENGTH_SHORT).show();
          break;
        case CLICK_EDIT_TASK:
          startActivity(TaskAddEditActivity.newIntent(this, (String) state.getData().get(Constants.Key.ID)));
          break;
        case DELETE_TASK:
          finish();
          break;
      }
    }), UnsubscribeLifeCycle.DESTROY_VIEW);
  }
}
