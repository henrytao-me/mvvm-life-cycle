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

package me.henrytao.mvvmlifecycle.test.mvvm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.henrytao.mvvmlifecycle.MVVMFragment;

/**
 * Created by henrytao on 12/14/15.
 */
public class TestFragment extends MVVMFragment {

  private Map<String, Integer> mEvents = new HashMap<>();

  private List<String> mKeys = new ArrayList<>();

  private TestViewModel mViewModel;

  @Override
  public void onCreate() {
    super.onCreate();
    log(TestUtils.ON_CREATE);
  }

  @Override
  public void onCreateView() {
    super.onCreateView();
    log(TestUtils.ON_CREATE_VIEW);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    log(TestUtils.ON_DESTROY);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    log(TestUtils.ON_DESTROY_VIEW);
  }

  @Override
  public View onInflateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return new View(getActivity());
  }

  @Override
  public void onInitializeViewModels() {
    mViewModel = new TestViewModel();
    mViewModels.add(mViewModel);
  }

  @Override
  public void onPause() {
    super.onPause();
    log(TestUtils.ON_PAUSE);
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    log(TestUtils.ON_RESTORE_INSTANCE_STATE);
  }

  @Override
  public void onResume() {
    super.onResume();
    log(TestUtils.ON_RESUME);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    log(TestUtils.ON_SAVE_INSTANCE_STATE);
  }

  @Override
  public void onStart() {
    super.onStart();
    log(TestUtils.ON_START);
  }

  @Override
  public void onStop() {
    super.onStop();
    log(TestUtils.ON_STOP);
  }

  public Map<String, Integer> getEvents() {
    return mEvents;
  }

  public List<String> getKeys() {
    return mKeys;
  }

  public TestViewModel getViewModel() {
    return mViewModel;
  }

  private void log(String key) {
    mKeys.add(key);
    mEvents.put(key, TestUtils.getNextIndex());
  }
}
