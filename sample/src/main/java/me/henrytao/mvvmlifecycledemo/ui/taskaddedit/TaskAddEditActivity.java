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
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.henrytao.mdcore.utils.ResourceUtils;
import me.henrytao.mvvmlifecycledemo.R;
import me.henrytao.mvvmlifecycledemo.base.BaseActivity;

/**
 * Created by henrytao on 4/2/16.
 */
public class TaskAddEditActivity extends BaseActivity {

  public static Intent newIntent(Context context) {
    Intent intent = new Intent(context, TaskAddEditActivity.class);
    return intent;
  }

  @Bind(R.id.toolbar)
  Toolbar vToolbar;

  @Override
  public void onInitializeViewModels() {
  }

  @Override
  public void onSetContentView(Bundle savedInstanceState) {
    setContentView(R.layout.task_add_edit_activity);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);

    setSupportActionBar(vToolbar);
    vToolbar.setNavigationOnClickListener(v -> onBackPressed());
    ResourceUtils.supportDrawableTint(this, vToolbar, ResourceUtils.Palette.PRIMARY);
  }
}
