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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henrytao on 4/24/16.
 */
public class TestLogger {

  private static TestLogger sTestLogger;

  public static TestLogger getInstance() {
    if (sTestLogger == null) {
      sTestLogger = new TestLogger();
    }
    return sTestLogger;
  }

  private List<String> mLogs = new ArrayList<>();

  protected TestLogger() {
  }

  public List<String> getLogs() {
    return mLogs;
  }

  public void push(String log) {
    mLogs.add(log);
  }

  public void reset() {
    mLogs.clear();
  }
}
