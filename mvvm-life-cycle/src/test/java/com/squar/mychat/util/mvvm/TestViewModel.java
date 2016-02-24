/*
 * Copyright (C) 2015 MySQUAR. All rights reserved.
 *
 * This software is the confidential and proprietary information of MySQUAR or one of its
 * subsidiaries. You shall not disclose this confidential information and shall use it only in
 * accordance with the terms of the license agreement or other applicable agreement you entered into
 * with MySQUAR.
 *
 * MySQUAR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MySQUAR SHALL NOT BE LIABLE FOR ANY LOSSES
 * OR DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR
 * ITS DERIVATIVES.
 */

package com.squar.mychat.util.mvvm;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by henrytao on 12/14/15.
 */
public class TestViewModel extends MVVMViewModel {

  private Map<String, Integer> mEvents = new HashMap<>();

  private List<String> mKeys = new ArrayList<>();

  public TestViewModel() {
  }

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

  private void log(String key) {
    mKeys.add(key);
    mEvents.put(key, TestUtils.getNextIndex());
  }
}
