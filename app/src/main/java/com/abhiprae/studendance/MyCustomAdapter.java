package com.abhiprae.studendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * Created by Abhiprae on 3/28/2017.
 */

public class MyCustomAdapter extends BaseExpandableListAdapter {

    Context context = null;
    String titles[];
    String messages[];

    public MyCustomAdapter(Context context,String title[], String message[]){
        this.context = context;
        this.titles = title;
        this.messages = message;
    }

    @Override
    public int getGroupCount() {
        return titles.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public String getGroup(int groupPosition) {
        return titles[groupPosition];
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return messages[childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.notice_title, parent, false);
        }
        TextView title = (TextView)convertView.findViewById(R.id.title);
        title.setText(titles[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.notice_message, parent, false);
        }
        TextView message = (TextView)convertView.findViewById(R.id.message);
        message.setText(messages[groupPosition]);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
