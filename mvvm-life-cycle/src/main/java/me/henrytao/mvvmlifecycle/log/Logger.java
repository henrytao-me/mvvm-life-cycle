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

package me.henrytao.mvvmlifecycle.log;

import android.util.Log;

public final class Logger {

  private final static String DEFAULT_TAG = "Logger";

  public static Logger newInstance(LogLevel level) {
    return new Logger(DEFAULT_TAG, level);
  }

  public static Logger newInstance(String tag, LogLevel logLevel) {
    return new Logger(tag, logLevel);
  }

  public final LogLevel mLogLevel;

  private final String mTag;

  protected Logger(String tag, LogLevel logLevel) {
    mTag = tag;
    mLogLevel = logLevel;
  }

  public void d(String format, Object... args) {
    if (shouldLog(LogLevel.DEBUG)) {
      Log.d(mTag, String.format(format, args));
    }
  }

  public void e(Throwable error) {
    e(error, "");
  }

  public void e(String format, Object... args) {
    e(null, format, args);
  }

  public void e(Throwable error, String format, Object... args) {
    if (shouldLog(LogLevel.ERROR)) {
      Log.e(mTag, String.format(format, args), error);
    }
  }

  public void i(String format, Object... args) {
    if (shouldLog(LogLevel.INFO)) {
      Log.i(mTag, String.format(format, args));
    }
  }

  public void v(String format, Object... args) {
    if (shouldLog(LogLevel.VERBOSE)) {
      Log.v(mTag, String.format(format, args));
    }
  }

  public void w(String format, Object... args) {
    if (shouldLog(LogLevel.WARN)) {
      Log.w(mTag, String.format(format, args));
    }
  }

  private boolean shouldLog(LogLevel level) {
    return mLogLevel.toInt() >= level.toInt();
  }

  public enum LogLevel {
    NONE(0),
    ERROR(1),
    WARN(2),
    INFO(3),
    DEBUG(4),
    VERBOSE(5);

    public static LogLevel valueOf(int value) {
      if (value == ERROR.toInt()) {
        return ERROR;
      } else if (value == WARN.toInt()) {
        return WARN;
      } else if (value == INFO.toInt()) {
        return INFO;
      } else if (value == DEBUG.toInt()) {
        return DEBUG;
      } else if (value == VERBOSE.toInt()) {
        return VERBOSE;
      } else {
        return NONE;
      }
    }

    private final int mValue;

    LogLevel(int value) {
      mValue = value;
    }

    public int toInt() {
      return mValue;
    }
  }
}