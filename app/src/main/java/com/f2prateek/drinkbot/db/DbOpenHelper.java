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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Date;

final class DbOpenHelper extends SQLiteOpenHelper {
  private static final int VERSION = 1;

  private static final String CREATE_LIST = ""
      + "CREATE TABLE " + Drink.TABLE + "("
      + Drink.ID + " INTEGER NOT NULL PRIMARY KEY,"
      + Drink.VOLUME + " REAL NOT NULL,"
      + Drink.DATE + " TEXT NOT NULL,"
      + Drink.DESCRIPTION + " TEXT"
      + ")";

  public DbOpenHelper(Context context) {
    super(context, "drinks.db", null /* factory */, VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_LIST);

    db.insert(Drink.TABLE, null,
        new Drink.Builder().volume(541).date(new Date()).description("Test1").build());
    db.insert(Drink.TABLE, null,
        new Drink.Builder().volume(401).date(new Date()).description("Test2").build());
    db.insert(Drink.TABLE, null,
        new Drink.Builder().volume(901).date(new Date()).description("Test3").build());
    db.insert(Drink.TABLE, null,
        new Drink.Builder().volume(4).date(new Date()).description("Test4").build());
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }
}
