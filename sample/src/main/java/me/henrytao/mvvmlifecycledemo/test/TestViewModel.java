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

  private TestLogger mTestLogger = TestLogger.getInstance();

  public TestViewModel() {
  }

  @Override
  public void onCreate() {
    mTestLogger.push("TestViewModel.onCreate");
    super.onCreate();
  }

  @Override
  public void onCreateView() {
    mTestLogger.push("TestViewModel.onCreateView");
    super.onCreateView();
  }

  @Override
  public void onDestroy() {
    mTestLogger.push("TestViewModel.onDestroy");
    super.onDestroy();
  }

  @Override
  public void onDestroyView() {
    mTestLogger.push("TestViewModel.onDestroyView");
    super.onDestroyView();
  }

  @Override
  public void onPause() {
    mTestLogger.push("TestViewModel.onPause");
    super.onPause();
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    mTestLogger.push("TestViewModel.onRestoreInstanceState");
    super.onRestoreInstanceState(savedInstanceState);
  }

  @Override
  public void onResume() {
    mTestLogger.push("TestViewModel.onResume");
    super.onResume();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    mTestLogger.push("TestViewModel.onSaveInstanceState");
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onStart() {
    mTestLogger.push("TestViewModel.onStart");
    super.onStart();
  }

  @Override
  public void onStop() {
    mTestLogger.push("TestViewModel.onStop");
    super.onStop();
  }
}
