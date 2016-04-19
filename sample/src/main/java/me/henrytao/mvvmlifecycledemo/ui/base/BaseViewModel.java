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

package me.henrytao.mvvmlifecycledemo.ui.base;

import java.util.HashMap;
import java.util.Map;

import me.henrytao.mvvmlifecycle.MVVMViewModelWithEventDispatcher;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by henrytao on 4/5/16.
 */
public abstract class BaseViewModel<T> extends MVVMViewModelWithEventDispatcher {

  private BehaviorSubject<State<T>> mState = BehaviorSubject.create();

  public Observable<State<T>> getState() {
    return mState;
  }

  public void setState(T name, Object... objects) {
    mState.onNext(new State<>(name, objects));
  }

  public static class State<T> {

    private final Map<String, Object> mData = new HashMap<>();

    private final T mName;

    protected State(T name, Object... data) {
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

    public T getName() {
      return mName;
    }
  }
}
