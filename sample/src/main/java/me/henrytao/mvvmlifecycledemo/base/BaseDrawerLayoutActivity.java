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

package me.henrytao.mvvmlifecycledemo.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.henrytao.mvvmlifecycledemo.R;

/**
 * Created by henrytao on 4/2/16.
 */
public abstract class BaseDrawerLayoutActivity extends BaseActivity {

  protected abstract Fragment onCreateFragment();

  protected static final int DELAY_TIMEOUT = 200;

  @Bind(R.id.drawer_layout)
  DrawerLayout vDrawerLayout;

  @Bind(R.id.nav_view)
  NavigationView vNavigationView;

  @Bind(R.id.toolbar)
  Toolbar vToolbar;

  private ActionBarDrawerToggle mActionBarDrawerToggle;

  private Handler mHandler;

  private Runnable mRunner;

  private int mSelectedMenuItemId;

  @Override
  public void onDestroy() {
    super.onDestroy();
    mRunner = null;
    mHandler = null;
  }

  @Override
  public void onPause() {
    super.onPause();
    mHandler.removeCallbacks(mRunner);
  }

  @Override
  public void onSetContentView(Bundle savedInstanceState) {
    setContentView(R.layout.base_drawer_layout_activity);
    ButterKnife.bind(this);

    setSupportActionBar(vToolbar);
    mActionBarDrawerToggle = new ActionBarDrawerToggle(this, vDrawerLayout, vToolbar, R.string.text_open, R.string.text_close);
    vDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
    mActionBarDrawerToggle.syncState();

    mHandler = new Handler();
    mRunner = () -> {
      switch (mSelectedMenuItemId) {
        case R.id.list_navigation_menu_item:
          // Do nothing, we're already on that screen
          break;
        case R.id.statistics_navigation_menu_item:
          //Intent intent =
          //    new Intent(TasksActivity.this, StatisticsActivity.class);
          //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
          //    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
          //startActivity(intent);
          break;
      }
    };

    vNavigationView.setNavigationItemSelectedListener(item -> {
      mSelectedMenuItemId = item.getItemId();
      vDrawerLayout.closeDrawers();
      mHandler.removeCallbacks(mRunner);
      mHandler.postDelayed(mRunner, DELAY_TIMEOUT);
      return true;
    });

    if (savedInstanceState == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.container, onCreateFragment())
          .commit();
    }
  }
}
