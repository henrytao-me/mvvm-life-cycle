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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

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

  @Test
  public void activityLifeCycleTest() throws Exception {
    Robolectric.buildActivity(TestActivity.class)
        .create()
        .start()
        .restoreInstanceState(new Bundle())
        .resume()
        .pause()
        .saveInstanceState(new Bundle())
        .stop()
        .destroy()
        .get();

    TestLogger logger = TestLogger.getInstance();
    assertThat(logger.getLogs(), Matchers.equalTo(Arrays.asList(
        "TestActivity.onCreate", "TestViewModel.onCreate",
        "TestActivity.onCreateView", "TestViewModel.onCreateView",
        "TestActivity.onStart", "TestViewModel.onStart",
        "TestActivity.onRestoreInstanceState", "TestViewModel.onRestoreInstanceState",
        "TestActivity.onResume", "TestViewModel.onResume",
        "TestActivity.onPause", "TestViewModel.onPause",
        "TestActivity.onSaveInstanceState", "TestViewModel.onSaveInstanceState",
        "TestActivity.onStop", "TestViewModel.onStop",
        "TestActivity.onDestroyView", "TestViewModel.onDestroyView",
        "TestActivity.onDestroy", "TestViewModel.onDestroy")));
  }
}
