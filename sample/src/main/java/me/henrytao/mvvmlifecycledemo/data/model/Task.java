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

package me.henrytao.mvvmlifecycledemo.data.model;

import com.google.common.base.Objects;

import java.util.UUID;

/**
 * Created by henrytao on 4/14/16.
 */
public class Task {

  private boolean mCompleted;

  private String mDescription;

  private String mId;

  private String mTitle;

  public Task(String id, String title, String description) {
    mId = id;
    mTitle = title;
    mDescription = description;
  }

  public Task(String title, String description) {
    this(UUID.randomUUID().toString(), title, description);

  }

  @Override
  public int hashCode() {
    return Objects.hashCode(mId, mTitle, mDescription, mCompleted);
  }

  public void active() {
    mCompleted = false;
  }

  public void complete() {
    mCompleted = true;
  }

  public String getDescription() {
    return mDescription;
  }

  public void setDescription(String description) {
    mDescription = description;
  }

  public String getId() {
    return mId;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public boolean isActive() {
    return !isCompleted();
  }

  public boolean isCompleted() {
    return mCompleted;
  }

  public void setCompleted(boolean completed) {
    mCompleted = completed;
  }
}
