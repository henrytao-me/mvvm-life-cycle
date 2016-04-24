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

package me.henrytao.mvvmlifecycledemo.util;

import org.junit.After;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

import me.henrytao.mvvmlifecycledemo.widget.rx.Transformer;
import rx.schedulers.Schedulers;

/**
 * Created by henrytao on 4/23/16.
 */
public class BaseTest {

  @Before
  public void initialize() {
    MockitoAnnotations.initMocks(this);
    Transformer.overrideComputationScheduler(Schedulers.immediate());
    Transformer.overrideIoScheduler(Schedulers.immediate());
    Transformer.overrideMainThreadScheduler(Schedulers.immediate());
    Transformer.overrideNewThreadScheduler(Schedulers.immediate());
  }

  @After
  public void release() {
    Transformer.resetComputationScheduler();
    Transformer.resetIoScheduler();
    Transformer.resetMainThreadScheduler();
    Transformer.resetNewThreadScheduler();
  }
}
