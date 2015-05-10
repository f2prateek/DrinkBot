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
package com.f2prateek.drinkbot.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public final class MainActivity extends FragmentActivity implements LogEntryListFragment.Listener {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(android.R.id.content, LogEntryListFragment.newInstance())
          .commit();
    }
  }

  @Override public void onDrinkClicked(long id) {
    /*
    getSupportFragmentManager().beginTransaction()
        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left,
            R.anim.slide_out_right)
        .replace(android.R.id.content, ItemsFragment.newInstance(id))
        .addToBackStack(null)
        .commit();
        */
  }

  @Override public void onNewDrinkClicked() {
    NewLogEntryFragment.newInstance().show(getSupportFragmentManager(), "new-drink");
  }
}
