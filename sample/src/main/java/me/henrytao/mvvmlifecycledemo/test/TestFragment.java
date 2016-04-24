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
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.henrytao.mvvmlifecycle.MVVMFragment;

/**
 * Created by henrytao on 4/24/16.
 */
public class TestFragment extends MVVMFragment {

  public static final String TAG = TestFragment.class.toString();

  private TestLogger mTestLogger = TestLogger.getInstance(TAG);

  private TestViewModel mViewModel;

  @Override
  public void onCreate() {
    super.onCreate();
    mTestLogger.push("TestFragment.onCreate");
  }

  @Override
  public void onCreateView() {
    super.onCreateView();
    mTestLogger.push("TestFragment.onCreateView");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mTestLogger.push("TestFragment.onDestroy");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mTestLogger.push("TestFragment.onDestroyView");
  }

  @Override
  public View onInflateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return new View(getContext());
  }

  @Override
  public void onInitializeViewModels() {
    mViewModel = new TestViewModel(TAG);
    addViewModel(mViewModel);
  }

  @Override
  public void onPause() {
    super.onPause();
    mTestLogger.push("TestFragment.onPause");
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    mTestLogger.push("TestFragment.onRestoreInstanceState");
  }

  @Override
  public void onResume() {
    super.onResume();
    mTestLogger.push("TestFragment.onResume");
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mTestLogger.push("TestFragment.onSaveInstanceState");
  }

  @Override
  public void onStart() {
    super.onStart();
    mTestLogger.push("TestFragment.onStart");
  }

  @Override
  public void onStop() {
    super.onStop();
    mTestLogger.push("TestFragment.onStop");
  }
}
