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
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import me.henrytao.mvvmlifecycledemo.R;
import me.henrytao.mvvmlifecycledemo.ui.base.BaseActivity;
import me.henrytao.mvvmlifecycledemo.ui.tasks.TasksFragment;

/**
 * Created by henrytao on 4/13/16.
 */
public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

  protected static final int DELAY_TIMEOUT = 200;

  public static Intent newIntent(Context context) {
    return new Intent(context, HomeActivity.class);
  }

  private Handler mHandler;

  private Runnable mRunner;

  private int mSelectedMenuItemId;

  private DrawerLayout vDrawerLayout;

  @Override
  public void onDestroy() {
    super.onDestroy();
    mRunner = null;
    mHandler = null;
  }

  @Override
  public void onInitializeViewModels() {
  }

  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    mSelectedMenuItemId = item.getItemId();
    vDrawerLayout.closeDrawers();
    mHandler.removeCallbacks(mRunner);
    mHandler.postDelayed(mRunner, DELAY_TIMEOUT);
    return true;
  }

  @Override
  public void onPause() {
    super.onPause();
    mHandler.removeCallbacks(mRunner);
  }

  @Override
  public void onSetContentView(Bundle savedInstanceState) {
    DataBindingUtil.setContentView(this, R.layout.home_activity);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    vDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, vDrawerLayout, toolbar,
        R.string.text_open, R.string.text_close);
    vDrawerLayout.addDrawerListener(actionBarDrawerToggle);
    actionBarDrawerToggle.syncState();

    mHandler = new Handler();
    mRunner = () -> onNavigationItemSelected(mSelectedMenuItemId);

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    if (savedInstanceState == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.container, TasksFragment.newInstance())
          .commit();
    }
  }

  private void onNavigationItemSelected(int id) {
    switch (id) {
      case R.id.menu_item_list_navigation:
        // Do nothing, we're already on that screen
        break;
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
