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

import me.henrytao.mvvmlifecycle.log.Ln;
import me.henrytao.mvvmlifecycledemo.R;
import me.henrytao.mvvmlifecycledemo.ui.base.BaseActivity;

/**
 * Created by henrytao on 4/2/16.
 */
public class TaskDetailActivity extends BaseActivity {

  private static final String ARG_TASK_ID = "ARG_TASK_ID";

  public static Intent newIntent(Context context, String taskId) {
    Intent intent = new Intent(context, TaskDetailActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString(ARG_TASK_ID, taskId);
    intent.putExtras(bundle);
    return intent;
  }

  @Override
  public void onInitializeViewModels() {

  }

  @Override
  public void onSetContentView(Bundle savedInstanceState) {
    DataBindingUtil.setContentView(this, R.layout.task_detail_activity);

    Bundle bundle = getIntent().getExtras();
    String taskId = bundle.getString(ARG_TASK_ID);

    Ln.d("custom TaskDetailActivity | %s", taskId);
  }
}
