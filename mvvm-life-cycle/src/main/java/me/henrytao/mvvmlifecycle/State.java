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

package me.henrytao.mvvmlifecycle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by henrytao on 4/18/16.
 */
public class State {

  public static State create(String name, Object... data) {
    return new State(name, data);
  }

  private final Map<String, Object> mData = new HashMap<>();

  private final String mName;

  protected State(String name, Object... data) {
    mName = name;
    int i = 0;
    while (i < data.length) {
      String key = (String) data[i++];
      Object value = data[i++];
      mData.put(key, value);
    }
  }

  public Map<String, Object> getData() {
    return mData;
  }

  public String getName() {
    return mName;
  }
}
