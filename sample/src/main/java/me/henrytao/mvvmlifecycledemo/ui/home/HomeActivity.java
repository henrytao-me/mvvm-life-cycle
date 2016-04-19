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

package me.henrytao.mvvmlifecycledemo.ui.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import java.util.concurrent.TimeUnit;

import me.henrytao.mvvmlifecycle.rx.UnsubscribeLifeCycle;
import me.henrytao.mvvmlifecycledemo.R;
import me.henrytao.mvvmlifecycledemo.databinding.HomeActivityBinding;
import me.henrytao.mvvmlifecycledemo.ui.base.BaseActivity;
import me.henrytao.mvvmlifecycledemo.ui.base.Constants;
import me.henrytao.mvvmlifecycledemo.ui.taskaddedit.TaskAddEditActivity;
import me.henrytao.mvvmlifecycledemo.ui.tasks.TasksFragment;
import rx.Observable;

/**
 * Created by henrytao on 4/13/16.
 */
public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

  public static Intent newIntent(Context context) {
    return new Intent(context, HomeActivity.class);
  }

  private HomeActivityBinding mBinding;

  private HomeViewModel mViewModel;

  @Override
  public void onInitializeViewModels() {
    mViewModel = new HomeViewModel();
    addViewModel(mViewModel);
  }

  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    mBinding.drawerLayout.closeDrawers();
    manageSubscription(Observable
        .timer(Constants.Animation.SHORT, TimeUnit.MILLISECONDS)
        .subscribe(l -> onNavigationItemSelected(item.getItemId())), UnsubscribeLifeCycle.DESTROY_VIEW);
    return true;
  }

  @Override
  public void onSetContentView(Bundle savedInstanceState) {
    mBinding = DataBindingUtil.setContentView(this, R.layout.home_activity);
    mBinding.setViewModel(mViewModel);

    setSupportActionBar(mBinding.toolbar);

    ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
        this, mBinding.drawerLayout, mBinding.toolbar, R.string.text_open, R.string.text_close);
    mBinding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
    actionBarDrawerToggle.syncState();

    mBinding.navigationView.setNavigationItemSelectedListener(this);

    if (savedInstanceState == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.container, TasksFragment.newInstance())
          .commit();
    }

    manageSubscription(mViewModel.getState().subscribe(state -> {
      switch (state.getName()) {
        case STATE_CLICK_ADD_NEW_TASKS:
          startActivity(TaskAddEditActivity.newIntent(this));
          break;
      }
    }), UnsubscribeLifeCycle.DESTROY_VIEW);
  }

  private void onNavigationItemSelected(int id) {
    switch (id) {
      case R.id.menu_item_statistics_navigation:
        //Intent intent =
        //    new Intent(TasksActivity.this, StatisticsActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
        //    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //startActivity(intent);
        break;
    }
  }
}
