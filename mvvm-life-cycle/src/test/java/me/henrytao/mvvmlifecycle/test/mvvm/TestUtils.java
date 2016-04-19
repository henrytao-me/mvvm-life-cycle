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

/**
 * Created by henrytao on 12/14/15.
 */
public class TestUtils {

  public static final String ON_CREATE = "onCreate";

  public static final String ON_CREATE_VIEW = "onCreateView";

  public static final String ON_DESTROY = "onDestroy";

  public static final String ON_DESTROY_VIEW = "onDestroyView";

  public static final String ON_PAUSE = "onPause";

  public static final String ON_RESTORE_INSTANCE_STATE = "onRestoreInstanceState";

  public static final String ON_RESUME = "onResume";

  public static final String ON_SAVE_INSTANCE_STATE = "onSaveInstanceState";

  public static final String ON_START = "onStart";

  public static final String ON_STOP = "onStop";

  private static int mIndex = 0;

  public static int getNextIndex() {
    return ++mIndex;
  }
}
