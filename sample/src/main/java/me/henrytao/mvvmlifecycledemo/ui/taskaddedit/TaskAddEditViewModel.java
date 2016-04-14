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

package me.henrytao.mvvmlifecycledemo.ui.taskaddedit;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import me.henrytao.mvvmlifecycledemo.ui.base.BaseViewModel;

/**
 * Created by henrytao on 4/5/16.
 */
public class TaskAddEditViewModel extends BaseViewModel {

  private final Listener mListener;

  public ObservableField<String> description = new ObservableField<>();

  public ObservableField<String> title = new ObservableField<>();

  public TaskAddEditViewModel(Listener listener) {
    mListener = listener;
  }

  public void onAddEditClick(View view) {
    String title = this.title.get();
    String description = this.description.get();
    boolean isValid = !TextUtils.isEmpty(title);

    if (!isValid && mListener != null) {
      mListener.onMissingTitle();
    } else if (isValid) {

    }
  }

  public interface Listener {

    void onMissingTitle();
  }
}
