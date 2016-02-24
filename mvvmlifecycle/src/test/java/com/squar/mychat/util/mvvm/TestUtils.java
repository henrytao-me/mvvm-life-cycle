/*
 * Copyright (C) 2015 MySQUAR. All rights reserved.
 *
 * This software is the confidential and proprietary information of MySQUAR or one of its
 * subsidiaries. You shall not disclose this confidential information and shall use it only in
 * accordance with the terms of the license agreement or other applicable agreement you entered into
 * with MySQUAR.
 *
 * MySQUAR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MySQUAR SHALL NOT BE LIABLE FOR ANY LOSSES
 * OR DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR
 * ITS DERIVATIVES.
 */

package com.squar.mychat.util.mvvm;

/**
 * Created by henrytao on 12/14/15.
 */
public class TestUtils {

  public static final String ON_CREATE = "onCreate";

  public static final String ON_CREATE_VIEW = "onCreateView";

  public static final String ON_DESTROY = "onDestroy";

  public static final String ON_DESTROY_VIEW = "onDestroyView";

  public static final String ON_PAUSE = "onPause";

  public static final String ON_RESTORE_INSTANCE_STATE = "onRestoreInstanceState";

  public static final String ON_RESUME = "onResume";

  public static final String ON_SAVE_INSTANCE_STATE = "onSaveInstanceState";

  public static final String ON_START = "onStart";

  public static final String ON_STOP = "onStop";

  private static int mIndex = 0;

  public static int getNextIndex() {
    return ++mIndex;
  }
}
