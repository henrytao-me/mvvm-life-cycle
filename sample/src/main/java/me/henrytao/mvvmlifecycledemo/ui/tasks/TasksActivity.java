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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.henrytao.mvvmlifecycledemo.R;
import me.henrytao.mvvmlifecycledemo.base.BaseActivity;

public class TasksActivity extends BaseActivity {

  public static Intent newIntent(Context context) {
    return new Intent(context, TasksActivity.class);
  }

  @Bind(R.id.drawer_layout)
  DrawerLayout vDrawerLayout;

  @Bind(R.id.nav_view)
  NavigationView vNavigationView;

  @Bind(R.id.toolbar)
  Toolbar vToolbar;

  private ActionBarDrawerToggle mActionBarDrawerToggle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tasks_activity);
    ButterKnife.bind(this);

    setSupportActionBar(vToolbar);
    mActionBarDrawerToggle = new ActionBarDrawerToggle(this, vDrawerLayout, vToolbar, R.string.text_open, R.string.text_close);
    vDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
    mActionBarDrawerToggle.syncState();

    vNavigationView.setNavigationItemSelectedListener(item -> {
      switch (item.getItemId()) {
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
        default:
          break;
      }
      // Close the navigation drawer when an item is selected.
      //menuItem.setChecked(true);
      vDrawerLayout.closeDrawers();
      return true;
    });
  }
}
