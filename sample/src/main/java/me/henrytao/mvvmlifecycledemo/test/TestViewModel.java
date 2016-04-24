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

import me.henrytao.mvvmlifecycle.MVVMViewModel;

/**
 * Created by henrytao on 4/24/16.
 */
public class TestViewModel extends MVVMViewModel {

  private TestLogger mTestLogger;

  public TestViewModel(String tag) {
    mTestLogger = TestLogger.getInstance(tag);
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mTestLogger.push("TestViewModel.onCreate");
  }

  @Override
  public void onCreateView() {
    super.onCreateView();
    mTestLogger.push("TestViewModel.onCreateView");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mTestLogger.push("TestViewModel.onDestroy");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mTestLogger.push("TestViewModel.onDestroyView");
  }

  @Override
  public void onPause() {
    super.onPause();
    mTestLogger.push("TestViewModel.onPause");
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    mTestLogger.push("TestViewModel.onRestoreInstanceState");
  }

  @Override
  public void onResume() {
    super.onResume();
    mTestLogger.push("TestViewModel.onResume");
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mTestLogger.push("TestViewModel.onSaveInstanceState");
  }

  @Override
  public void onStart() {
    super.onStart();
    mTestLogger.push("TestViewModel.onStart");
  }

  @Override
  public void onStop() {
    super.onStop();
    mTestLogger.push("TestViewModel.onStop");
  }
}
