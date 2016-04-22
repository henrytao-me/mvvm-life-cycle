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

package me.henrytao.mvvmlifecycledemo.data.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.henrytao.mvvmlifecycledemo.data.adapter.LocalAdapter;
import me.henrytao.mvvmlifecycledemo.data.exception.DataNotFoundException;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

/**
 * Created by henrytao on 4/21/16.
 */
public class TaskServiceTest {

  @Mock
  private LocalAdapter mLocalAdapter;

  private TaskService mTaskService;

  @Before
  public void initialize() {
    MockitoAnnotations.initMocks(this);
    mTaskService = new TaskService(mLocalAdapter);
  }

  @Test
  public void testActiveError() throws Exception {
    TestSubscriber<Void> testSubscriber = new TestSubscriber<>();
    DataNotFoundException exception = new DataNotFoundException();
    doThrow(exception).when(mLocalAdapter).activeTask(any());

    mTaskService.active("unknown").subscribe(testSubscriber);

    testSubscriber.assertError(exception);
    verify(mLocalAdapter).activeTask("unknown");
  }

  @Test
  public void testActiveSuccess() throws Exception {
    TestSubscriber<Void> testSubscriber = new TestSubscriber<>();

    mTaskService.active("taskId").subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    verify(mLocalAdapter).activeTask("taskId");
  }

  // TODO: cover more test cases
}
