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

package me.henrytao.mvvmlifecycle.viewmodel;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import me.henrytao.mvvmlifecycle.event.Event;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by henrytao on 4/24/16.
 */
public class ViewModelWithEventDispatcher extends SimpleViewModel {

  private static Map<String, PublishSubject<Object>> sEventSubject = new HashMap<>();

  private static Map<String, String> sRegisteredEvents = new HashMap<>();

  private static void initEventSubject(String eventName) {
    if (!sEventSubject.containsKey(eventName)) {
      sEventSubject.put(eventName, PublishSubject.create());
    }
  }

  public void dispatch(Enum eventName, Object... objects) {
    dispatch(eventName.toString(), objects);
  }

  public void dispatch(String eventName, Object... objects) {
    if (sEventSubject.containsKey(eventName)) {
      sEventSubject.get(eventName).onNext(objects);
    }
  }

  public void register(Object owner, Enum event) {
    register(owner, event.toString());
  }

  public void register(Object owner, String eventName) {
    String ownerClassName = owner.getClass().toString();
    if (sRegisteredEvents.containsKey(eventName)) {
      if (!TextUtils.equals(sRegisteredEvents.get(eventName), ownerClassName)) {
        throw new DuplicatedEventException(
            String.format(Locale.US, "Event %s has already been registered in %s", eventName, sRegisteredEvents.get(eventName)));
      }
    } else {
      sRegisteredEvents.put(eventName, ownerClassName);
    }
    initEventSubject(eventName);
  }

  public Subscription subscribe(Enum eventName, Event event, Action1<Throwable> onError) {
    return subscribe(eventName.toString(), event, onError);
  }

  public Subscription subscribe(String eventName, Event event, Action1<Throwable> onError) {
    initEventSubject(eventName);
    return sEventSubject.get(eventName).subscribe(event, onError);
  }

  public Subscription subscribe(Enum eventName, Event event) {
    return subscribe(eventName.toString(), event);
  }

  public Subscription subscribe(String eventName, Event event) {
    initEventSubject(eventName);
    return sEventSubject.get(eventName).subscribe(event);
  }

  public static class DuplicatedEventException extends RuntimeException {

    public DuplicatedEventException(String detailMessage) {
      super(detailMessage);
    }
  }
}
