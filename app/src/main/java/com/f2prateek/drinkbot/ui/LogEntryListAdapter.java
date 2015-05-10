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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.f2prateek.drinkbot.db.LogEntry;
import java.util.Collections;
import java.util.List;
import rx.functions.Action1;

final class LogEntryListAdapter extends BaseAdapter implements Action1<List<LogEntry>> {
  private final LayoutInflater inflater;

  private List<LogEntry> items = Collections.emptyList();

  public LogEntryListAdapter(Context context) {
    this.inflater = LayoutInflater.from(context);
  }

  @Override public void call(List<LogEntry> items) {
    this.items = items;
    notifyDataSetChanged();
  }

  @Override public int getCount() {
    return items.size();
  }

  @Override public LogEntry getItem(int position) {
    return items.get(position);
  }

  @Override public long getItemId(int position) {
    return getItem(position).id();
  }

  @Override public boolean hasStableIds() {
    return true;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
    }

    LogEntry item = getItem(position);
    ((TextView) convertView).setText(item.description() + " (" + item.volume() + ") on " //
        + item.date());

    return convertView;
  }
}
