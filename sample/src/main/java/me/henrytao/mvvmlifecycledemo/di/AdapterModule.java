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

package me.henrytao.mvvmlifecycledemo.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.henrytao.mvvmlifecycledemo.data.adapter.LocalAdapter;

/**
 * Created by henrytao on 4/15/16.
 */
@Module
public class AdapterModule {

  @Singleton
  @Provides
  LocalAdapter provideLocalAdapter() {
    return new me.henrytao.mvvmlifecycledemo.data.adapter.local.LocalAdapter();
  }
}
