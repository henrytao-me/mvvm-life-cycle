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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import me.henrytao.mdcore.utils.ResourceUtils;
import me.henrytao.mvvmlifecycledemo.R;
import me.henrytao.mvvmlifecycledemo.databinding.TaskAddEditActivityBinding;
import me.henrytao.mvvmlifecycledemo.ui.base.BaseActivity;

/**
 * Created by henrytao on 4/2/16.
 */
public class TaskAddEditActivity extends BaseActivity implements TaskAddEditViewModel.Listener {

  public static Intent newIntent(Context context) {
    return new Intent(context, TaskAddEditActivity.class);
  }

  private TaskAddEditViewModel mViewModel;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_task_add_edit, menu);
    ResourceUtils.supportDrawableTint(this, menu, ResourceUtils.Palette.PRIMARY);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public void onInitializeViewModels() {
    mViewModel = new TaskAddEditViewModel(this);
    addViewModel(mViewModel);
  }

  @Override
  public void onMissingTitle() {
    Snackbar.make(findViewById(R.id.coordinator_layout), R.string.empty_task_message, Snackbar.LENGTH_LONG).show();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_done:
        mViewModel.onAddEditClick(item.getActionView());
        break;
    }
    return true;
  }

  @Override
  public void onSetContentView(Bundle savedInstanceState) {
    TaskAddEditActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.task_add_edit_activity);
    binding.setViewModel(mViewModel);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toolbar.setNavigationOnClickListener(v -> onBackPressed());
    ResourceUtils.supportDrawableTint(this, toolbar, ResourceUtils.Palette.PRIMARY);
  }
}
