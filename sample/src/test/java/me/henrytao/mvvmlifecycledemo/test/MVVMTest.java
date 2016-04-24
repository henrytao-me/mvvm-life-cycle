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

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import android.os.Bundle;

import java.util.Arrays;

import me.henrytao.mvvmlifecycledemo.BuildConfig;
import me.henrytao.mvvmlifecycledemo.RobolectricGradleTestRunner;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by henrytao on 4/24/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = RobolectricGradleTestRunner.SDK, constants = BuildConfig.class)
public class MVVMTest {

  @Before
  public void initialize() {
    TestLogger.getInstance(TestActivity.TAG).reset();
    TestLogger.getInstance(TestFragment.TAG).reset();
  }

  @Test
  public void testActivityLifeCycle() throws Exception {
    Robolectric.buildActivity(TestActivity.class)
        .create()
        .start()
        .restoreInstanceState(new Bundle())
        .resume()
        .pause()
        .saveInstanceState(new Bundle())
        .stop()
        .destroy();

    assertThat(TestLogger.getInstance(TestActivity.TAG).getLogs(), Matchers.equalTo(Arrays.asList(
        "TestViewModel.onCreate", "TestActivity.onCreate",
        "TestViewModel.onCreateView", "TestActivity.onCreateView",
        "TestViewModel.onStart", "TestActivity.onStart",
        "TestViewModel.onRestoreInstanceState", "TestActivity.onRestoreInstanceState",
        "TestViewModel.onResume", "TestActivity.onResume",
        "TestViewModel.onPause", "TestActivity.onPause",
        "TestViewModel.onSaveInstanceState", "TestActivity.onSaveInstanceState",
        "TestViewModel.onStop", "TestActivity.onStop",
        "TestViewModel.onDestroyView", "TestActivity.onDestroyView",
        "TestViewModel.onDestroy", "TestActivity.onDestroy")));
  }

  @Test
  public void testFragmentLifeCycle() throws Exception {
    ActivityController<TestActivity> activityController = Robolectric.buildActivity(TestActivity.class)
        .create()
        .start()
        .restoreInstanceState(new Bundle())
        .resume();

    TestFragment fragment = new TestFragment();

    activityController.get().getSupportFragmentManager()
        .beginTransaction()
        .add(fragment, null)
        .commit();

    activityController
        .pause()
        .saveInstanceState(new Bundle())
        .stop()
        .destroy();

    assertThat(TestLogger.getInstance(TestFragment.TAG).getLogs(), Matchers.equalTo(Arrays.asList(
        "TestViewModel.onCreate", "TestFragment.onCreate",
        "TestViewModel.onCreateView", "TestFragment.onCreateView",
        "TestViewModel.onStart", "TestFragment.onStart",
        "TestViewModel.onRestoreInstanceState", "TestFragment.onRestoreInstanceState",
        "TestViewModel.onResume", "TestFragment.onResume",
        "TestViewModel.onPause", "TestFragment.onPause",
        "TestViewModel.onSaveInstanceState", "TestFragment.onSaveInstanceState",
        "TestViewModel.onStop", "TestFragment.onStop",
        "TestViewModel.onDestroyView", "TestFragment.onDestroyView",
        "TestViewModel.onDestroy", "TestFragment.onDestroy")));
  }
}
