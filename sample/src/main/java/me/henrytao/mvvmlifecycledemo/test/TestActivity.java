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

package me.henrytao.mvvmlifecycledemo.test;

import android.os.Bundle;
import android.view.View;

import me.henrytao.mvvmlifecycle.MVVMActivity;

/**
 * Created by henrytao on 4/24/16.
 */
public class TestActivity extends MVVMActivity {

  private TestLogger mTestLogger = TestLogger.getInstance();

  private TestViewModel mViewModel;

  @Override
  public void onCreate() {
    mTestLogger.push("TestActivity.onCreate");
    super.onCreate();
  }

  @Override
  public void onCreateView() {
    mTestLogger.push("TestActivity.onCreateView");
    super.onCreateView();
  }

  @Override
  public void onDestroy() {
    mTestLogger.push("TestActivity.onDestroy");
    super.onDestroy();
  }

  @Override
  public void onDestroyView() {
    mTestLogger.push("TestActivity.onDestroyView");
    super.onDestroyView();
  }

  @Override
  public void onInitializeViewModels() {
    mViewModel = new TestViewModel();
    addViewModel(mViewModel);
  }

  @Override
  public void onPause() {
    mTestLogger.push("TestActivity.onPause");
    super.onPause();
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    mTestLogger.push("TestActivity.onRestoreInstanceState");
    super.onRestoreInstanceState(savedInstanceState);
  }

  @Override
  public void onResume() {
    mTestLogger.push("TestActivity.onResume");
    super.onResume();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    mTestLogger.push("TestActivity.onSaveInstanceState");
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onSetContentView(Bundle savedInstanceState) {
    setContentView(new View(this));
  }

  @Override
  public void onStart() {
    mTestLogger.push("TestActivity.onStart");
    super.onStart();
  }

  @Override
  public void onStop() {
    mTestLogger.push("TestActivity.onStop");
    super.onStop();
  }
}
