/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.f2prateek.drinkbot.db;

import android.content.ContentValues;
import android.database.Cursor;
import auto.parcel.AutoParcel;
import com.squareup.sqlbrite.SqlBrite;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

@AutoParcel
public abstract class LogEntry {
  static final String ID = "_id";
  static final String VOLUME = "volume";
  static final String DATE = "date";
  static final String DESCRIPTION = "description";

  public abstract long id();

  public abstract double volume();

  public abstract Date date();

  public abstract String description();

  public static final class Builder {
    private final ContentValues values = new ContentValues();

    public Builder id(long id) {
      values.put(ID, id);
      return this;
    }

    public Builder volume(double volume) {
      values.put(VOLUME, volume);
      return this;
    }

    public Builder date(Date date) {
      values.put(DATE, CursorUtils.ISO_8601_DATE_FORMAT.format(date));
      return this;
    }

    public Builder description(String description) {
      values.put(DESCRIPTION, description);
      return this;
    }

    public ContentValues build() {
      return values; // TODO defensive copy?
    }
  }

  public static class Db {
    private static final String TABLE_NAME = "log_entry_list";
    // @formatter:off
    static final String CREATE_TABLE = ""
        + "CREATE TABLE " + TABLE_NAME + "("
        + LogEntry.ID + " INTEGER NOT NULL PRIMARY KEY,"
        + LogEntry.VOLUME + " REAL NOT NULL,"
        + LogEntry.DATE + " DATETIME NOT NULL,"
        + LogEntry.DESCRIPTION + " TEXT"
        + ")";
    // @formatter:on

    private static Func1<Cursor, List<LogEntry>> CURSOR_TO_LIST = cursor -> {
      try {
        List<LogEntry> values = new ArrayList<>(cursor.getCount());

        while (cursor.moveToNext()) {
          long id = CursorUtils.getLong(cursor, ID);
          double volume = CursorUtils.getDouble(cursor, VOLUME);
          Date date = CursorUtils.getDate(cursor, DATE);
          String description = CursorUtils.getString(cursor, DESCRIPTION);
          values.add(new AutoParcel_LogEntry(id, volume, date, description));
        }
        return values;
      } finally {
        cursor.close();
      }
    };

    private final SqlBrite sqlBrite;

    public Db(SqlBrite sqlBrite) {
      this.sqlBrite = sqlBrite;
    }

    public long insert(LogEntry.Builder builder) {
      return sqlBrite.insert(TABLE_NAME, builder.build());
    }

    /** Returns all {@link LogEntry} in the table. */
    public Observable<List<LogEntry>> entries() {
      return sqlBrite.createQuery(TABLE_NAME, "SELECT * FROM " + TABLE_NAME)
          .map(SqlBrite.Query::run)
          .map(CURSOR_TO_LIST);
    }
  }
}
