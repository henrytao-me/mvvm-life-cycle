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

import java.util.Arrays;
import java.util.List;

/**
 * Created by henrytao on 2/19/16.
 */
public class Constants {

  public static final List<State> STATES = Arrays.asList(
      State.ON_CREATE, State.ON_CREATE_VIEW, State.ON_START, State.ON_RESUME,
      State.ON_PAUSE, State.ON_STOP, State.ON_DESTROY_VIEW, State.ON_DESTROY);

  public enum State {
    ON_CREATE,
    ON_CREATE_VIEW,
    ON_DESTROY,
    ON_DESTROY_VIEW,
    ON_PAUSE,
    ON_RESUME,
    ON_START,
    ON_STOP
  }
}
