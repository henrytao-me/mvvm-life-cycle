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
import android.support.v4.app.Fragment;
import android.view.View;

import me.henrytao.mvvmlifecycledemo.base.BaseDrawerLayoutActivity;
import me.henrytao.mvvmlifecycledemo.ui.taskaddedit.TaskAddEditActivity;

public class TasksActivity extends BaseDrawerLayoutActivity {

  public static Intent newIntent(Context context) {
    return new Intent(context, TasksActivity.class);
  }

  @Override
  public void onCreateView() {
    super.onCreateView();
    vFabAdd.setVisibility(View.VISIBLE);
    vFabAdd.setOnClickListener(v -> startActivity(TaskAddEditActivity.newIntent(this)));
  }

  @Override
  public void onInitializeViewModels() {
  }

  @Override
  protected Fragment onCreateFragment() {
    return new TasksFragment();
  }
}
