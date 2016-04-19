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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import android.content.Intent;
import android.os.Bundle;

import java.util.Arrays;

import me.henrytao.mvvmlifecycle.BuildConfig;
import me.henrytao.mvvmlifecycle.test.RobolectricGradleTestRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = RobolectricGradleTestRunner.SDK, manifest = Config.NONE, constants = BuildConfig.class)
public class MVVMTest {

  @Test
  public void activityLifeCycleTest() throws Exception {
    TestActivity activity = Robolectric.buildActivity(TestActivity.class).withIntent(new Intent()).attach()
        .create()
        .start()
        .restoreInstanceState(new Bundle())
        .resume()
        .pause()
        .saveInstanceState(new Bundle())
        .stop()
        .destroy()
        .get();

    assertThat(activity.getKeys().toString(), equalTo(Arrays.asList(
        TestUtils.ON_CREATE, TestUtils.ON_CREATE_VIEW, TestUtils.ON_START, TestUtils.ON_RESTORE_INSTANCE_STATE, TestUtils.ON_RESUME,
        TestUtils.ON_PAUSE, TestUtils.ON_SAVE_INSTANCE_STATE, TestUtils.ON_STOP, TestUtils.ON_DESTROY_VIEW, TestUtils.ON_DESTROY)
        .toString()));
    assertThat(activity.getKeys().toString(), equalTo(activity.getViewModel().getKeys().toString()));

    assertThat(activity.getEvents().get(TestUtils.ON_CREATE),
        greaterThan(activity.getViewModel().getEvents().get(TestUtils.ON_CREATE)));
    assertThat(activity.getEvents().get(TestUtils.ON_CREATE_VIEW),
        greaterThan(activity.getViewModel().getEvents().get(TestUtils.ON_CREATE_VIEW)));
    assertThat(activity.getEvents().get(TestUtils.ON_START),
        greaterThan(activity.getViewModel().getEvents().get(TestUtils.ON_START)));
    assertThat(activity.getEvents().get(TestUtils.ON_RESTORE_INSTANCE_STATE),
        greaterThan(activity.getViewModel().getEvents().get(TestUtils.ON_RESTORE_INSTANCE_STATE)));
    assertThat(activity.getEvents().get(TestUtils.ON_RESUME),
        greaterThan(activity.getViewModel().getEvents().get(TestUtils.ON_RESUME)));
    assertThat(activity.getEvents().get(TestUtils.ON_PAUSE),
        greaterThan(activity.getViewModel().getEvents().get(TestUtils.ON_PAUSE)));
    assertThat(activity.getEvents().get(TestUtils.ON_SAVE_INSTANCE_STATE),
        greaterThan(activity.getViewModel().getEvents().get(TestUtils.ON_SAVE_INSTANCE_STATE)));
    assertThat(activity.getEvents().get(TestUtils.ON_STOP),
        greaterThan(activity.getViewModel().getEvents().get(TestUtils.ON_STOP)));
    assertThat(activity.getEvents().get(TestUtils.ON_DESTROY_VIEW),
        greaterThan(activity.getViewModel().getEvents().get(TestUtils.ON_DESTROY_VIEW)));
    assertThat(activity.getEvents().get(TestUtils.ON_DESTROY),
        greaterThan(activity.getViewModel().getEvents().get(TestUtils.ON_DESTROY)));
  }

  @Test
  public void fragmentLifeCycleTest() throws Exception {
    TestActivity activity = Robolectric.buildActivity(TestActivity.class).withIntent(new Intent()).attach()
        .create()
        .start()
        .restoreInstanceState(new Bundle())
        .resume()
        .get();

    TestFragment fragment = new TestFragment();

    activity.getSupportFragmentManager()
        .beginTransaction()
        .add(fragment, null)
        .commit();

    activity.getSupportFragmentManager()
        .beginTransaction()
        .remove(fragment)
        .commit();

    assertThat(fragment.getKeys().toString(), equalTo(Arrays.asList(
        TestUtils.ON_CREATE, TestUtils.ON_CREATE_VIEW, TestUtils.ON_START, TestUtils.ON_RESTORE_INSTANCE_STATE, TestUtils.ON_RESUME,
        TestUtils.ON_PAUSE, TestUtils.ON_STOP, TestUtils.ON_DESTROY_VIEW, TestUtils.ON_DESTROY)
        .toString()));
    assertThat(fragment.getKeys().toString(), equalTo(fragment.getViewModel().getKeys().toString()));

    assertThat(fragment.getEvents().get(TestUtils.ON_CREATE),
        greaterThan(fragment.getViewModel().getEvents().get(TestUtils.ON_CREATE)));
    assertThat(fragment.getEvents().get(TestUtils.ON_CREATE_VIEW),
        greaterThan(fragment.getViewModel().getEvents().get(TestUtils.ON_CREATE_VIEW)));
    assertThat(fragment.getEvents().get(TestUtils.ON_START),
        greaterThan(fragment.getViewModel().getEvents().get(TestUtils.ON_START)));
    assertThat(fragment.getEvents().get(TestUtils.ON_RESTORE_INSTANCE_STATE),
        greaterThan(fragment.getViewModel().getEvents().get(TestUtils.ON_RESTORE_INSTANCE_STATE)));
    assertThat(fragment.getEvents().get(TestUtils.ON_RESUME),
        greaterThan(fragment.getViewModel().getEvents().get(TestUtils.ON_RESUME)));
    assertThat(fragment.getEvents().get(TestUtils.ON_PAUSE),
        greaterThan(fragment.getViewModel().getEvents().get(TestUtils.ON_PAUSE)));
    assertThat(fragment.getEvents().get(TestUtils.ON_STOP),
        greaterThan(fragment.getViewModel().getEvents().get(TestUtils.ON_STOP)));
    assertThat(fragment.getEvents().get(TestUtils.ON_DESTROY_VIEW),
        greaterThan(fragment.getViewModel().getEvents().get(TestUtils.ON_DESTROY_VIEW)));
    assertThat(fragment.getEvents().get(TestUtils.ON_DESTROY),
        greaterThan(fragment.getViewModel().getEvents().get(TestUtils.ON_DESTROY)));
  }
}