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

package me.henrytao.mvvmlifecycledemo.ui.tasks;

import org.junit.Test;
import org.mockito.Mockito;

import me.henrytao.mvvmlifecycledemo.di.Injector;
import me.henrytao.mvvmlifecycledemo.util.BaseTest;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by henrytao on 4/23/16.
 */
public class TasksViewModelTest extends BaseTest {

  TasksViewModel mTasksViewModel;

  @Override
  public void initialize() {
    super.initialize();
    Injector.initialize(null);
    mTasksViewModel = spy(new TasksViewModel());
    mTasksViewModel.mTaskService = spy(mTasksViewModel.mTaskService);
  }

  @Test
  public void testOnActiveTaskItemClick() throws Exception {

  }

  @Test
  public void testOnCompleteTaskItemClick() throws Exception {

  }

  @Test
  public void testOnCreate() throws Exception {
    doReturn(Observable.just(null)).when(mTasksViewModel.mTaskService).observeTaskCreate();
    doReturn(Observable.just(null)).when(mTasksViewModel.mTaskService).observeTaskUpdate();
    doReturn(Observable.just(null)).when(mTasksViewModel.mTaskService).observeTaskRemove();
    doNothing().when(mTasksViewModel).onTaskCreate(null);
    doNothing().when(mTasksViewModel).onTaskUpdate(null);
    doNothing().when(mTasksViewModel).onTaskRemove(null);
    doNothing().when(mTasksViewModel).reloadData(anyBoolean());

    mTasksViewModel.onCreate();

    verify(mTasksViewModel, Mockito.times(3)).subscribe(any(Enum.class), any());
    verify(mTasksViewModel, times(1)).subscribe(eq(TaskItemViewModel.Event.ON_TASK_ITEM_CLICK), any());
    verify(mTasksViewModel, times(1)).subscribe(eq(TaskItemViewModel.Event.ON_ACTIVE_TASK_ITEM_CLICK), any());
    verify(mTasksViewModel, times(1)).subscribe(eq(TaskItemViewModel.Event.ON_COMPLETE_TASK_ITEM_CLICK), any());

    verify(mTasksViewModel, times(1)).onTaskCreate(null);
    verify(mTasksViewModel, times(1)).onTaskUpdate(null);
    verify(mTasksViewModel, times(1)).onTaskRemove(null);

    verify(mTasksViewModel, times(1)).reloadData(false);
  }

  @Test
  public void testOnTaskCreate() throws Exception {

  }

  @Test
  public void testOnTaskItemClick() throws Exception {

  }

  @Test
  public void testOnTaskRemove() throws Exception {

  }

  @Test
  public void testOnTaskUpdate() throws Exception {

  }

  @Test
  public void testReloadData() throws Exception {
    doNothing().when(mTasksViewModel).reloadData(anyBoolean());

    mTasksViewModel.reloadData();

    verify(mTasksViewModel, times(1)).reloadData(false);
  }
}
