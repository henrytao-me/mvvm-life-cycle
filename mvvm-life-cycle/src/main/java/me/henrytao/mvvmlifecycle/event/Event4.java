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

package me.henrytao.mvvmlifecycle.event;

import java.util.Locale;

/**
 * Created by henrytao on 12/2/15.
 */
public class Event4<T1, T2, T3, T4> implements Event {

  private static final int LENGTH = 4;

  private final Listener<T1, T2, T3, T4> mListener;

  public Event4(Listener<T1, T2, T3, T4> listener) {
    mListener = listener;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void call(Object o) {
    if (mListener != null && o instanceof Object[]) {
      Object[] objects = (Object[]) o;
      if (objects.length == LENGTH) {
        mListener.onEventTrigger((T1) objects[0], (T2) objects[1], (T3) objects[2], (T4) objects[3]);
        return;
      }
      throw new InvalidParams(String.format(Locale.US, "Required %d. Found %d", LENGTH, objects.length));
    }
  }

  public interface Listener<T1, T2, T3, T4> {

    void onEventTrigger(T1 t1, T2 t2, T3 t3, T4 t4);
  }
}
