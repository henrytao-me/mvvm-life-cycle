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

package me.henrytao.mvvmlifecycledemo;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import me.henrytao.mvvmlifecycle.viewmodel.Data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by henrytao on 9/14/16.
 */
public class SampleTest {

  @Test
  public void test() throws Exception {
    Map<Object, Object> data = new HashMap<>();
    data.put("hello", "moto");
    String tmp = getData(String.class, data, "hello");
    assertThat(tmp, equalTo("moto"));
  }

  @Test
  public void testData() throws Exception {
    Data data = new Data();
    data.put("hello", new boolean[]{false, true});
    boolean[] result = data.getBooleanArray("hello");
    assertThat(result.length, equalTo(2));
    assertThat(result[0], equalTo(false));
    assertThat(result[1], equalTo(true));
  }

  @Test
  public void testData_array() throws Exception {
    Data data = new Data();
    data.put("hello", "moto");
    Data dataArray = new Data();
    dataArray.put("array", new Data[]{data});
    Data[] result = dataArray.getArray("array", Data.class);
    assertThat(result.length, equalTo(1));
    assertThat(result[0].getString("hello"), equalTo("moto"));
  }

  @Test
  public void testData_null() throws Exception {
    Data data = new Data();
    data.put("hello", new boolean[]{false, true});
    boolean[] result = data.getBooleanArray("hello_moto");
    assertThat(result, equalTo(null));
  }

  private <T> T getData(Class<T> tClass, Map<Object, Object> data, Object key) {
    return tClass.cast(data.get(key));
  }
}