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

package me.henrytao.mvvmlifecycledemo.ui.taskaddedit;

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
import me.henrytao.mvvmlifecycledemo.databinding.TaskAddEditActivityBinding;
import me.henrytao.mvvmlifecycledemo.ui.base.BaseActivity;

/**
 * Created by henrytao on 4/2/16.
 */
public class TaskAddEditActivity extends BaseActivity {

  private static final String ARG_TASK_ID = "ARG_TASK_ID";

  public static Intent newIntent(Context context) {
    return newIntent(context, null);
  }

  public static Intent newIntent(Context context, String taskId) {
    Intent intent = new Intent(context, TaskAddEditActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString(ARG_TASK_ID, taskId);
    intent.putExtras(bundle);
    return intent;
  }

  private TaskAddEditActivityBinding mBinding;

  private TaskAddEditViewModel mViewModel;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_task_add_edit, menu);
    ResourceUtils.supportDrawableTint(this, menu, ResourceUtils.Palette.PRIMARY);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public void onInitializeViewModels() {
    Bundle bundle = getIntent().getExtras();
    String taskId = bundle.getString(ARG_TASK_ID);

    mViewModel = new TaskAddEditViewModel(taskId);
    addViewModel(mViewModel);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_done:
        mViewModel.onDoneClick();
        break;
    }
    return true;
  }

  @Override
  public void onSetContentView(Bundle savedInstanceState) {
    mBinding = DataBindingUtil.setContentView(this, R.layout.task_add_edit_activity);
    mBinding.setViewModel(mViewModel);

    setSupportActionBar(mBinding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    mBinding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    ResourceUtils.supportDrawableTint(this, mBinding.toolbar, ResourceUtils.Palette.PRIMARY);

    manageSubscription(mViewModel.getState().subscribe(state -> {
      switch (state.getName()) {
        case TaskAddEditViewModel.STATE_CREATED_TASK:
          finish();
          break;
        case TaskAddEditViewModel.STATE_CREATING_TASK:
          // TODO: should handle progressbar
          break;
        case TaskAddEditViewModel.STATE_MISSING_TITLE:
          Snackbar.make(findViewById(R.id.container), R.string.empty_task_message, Snackbar.LENGTH_SHORT).show();
          break;
        case TaskAddEditViewModel.STATE_UPDATING_TASK:
          // TODO: should handle progressbar
          break;
        case TaskAddEditViewModel.STATE_UPDATED_TASK:
          finish();
          break;
      }
    }), UnsubscribeLifeCycle.DESTROY_VIEW);
  }
}
