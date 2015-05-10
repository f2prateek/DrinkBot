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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rx.functions.Func1;

@AutoParcel
public abstract class Drink {
  public static final String TABLE = "drink_list";

  public static String LIST_ALL_QUERY = "SELECT * FROM " + Drink.TABLE;

  public static final String ID = "_id";
  public static final String VOLUME = "volume";
  public static final String DATE = "date";
  public static final String DESCRIPTION = "description";

  public abstract long id();

  public abstract double volume();

  public abstract Date date();

  public abstract String description();

  public static Func1<Cursor, List<Drink>> MAP = cursor -> {
    try {
      List<Drink> values = new ArrayList<>(cursor.getCount());

      while (cursor.moveToNext()) {
        long id = Db.getLong(cursor, ID);
        double volume = Db.getDouble(cursor, VOLUME);
        Date date = Db.getDate(cursor, DATE);
        String description = Db.getString(cursor, DESCRIPTION);
        values.add(new AutoParcel_Drink(id, volume, date, description));
      }
      return values;
    } finally {
      cursor.close();
    }
  };

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
      values.put(DATE, Db.ISO_8601_DATE_FORMAT.format(date));
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
}
