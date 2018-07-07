package com.abhiprae.studendance;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Abhiprae on 4/6/2017.
 */

public class AttendanceAdapter extends BaseExpandableListAdapter {

    String subject[];
    int hr;
    Context context;
    int att;
    int present;

    public AttendanceAdapter(Context context,String s[],int h,int att,int present){
        this.context = context;
        subject = s;
        Log.d("Subject ",subject[0]);
        hr = h;
        this.att = att;
        this.present = present;
    }

    @Override
    public int getGroupCount() {
        return subject.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public String getGroup(int groupPosition) {
        return subject[groupPosition];
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return "";
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
        title.setText(subject[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.attendance, parent, false);
        }
        if(subject[groupPosition].equals("IT1110")) {
            TextView total = (TextView) convertView.findViewById(R.id.total);
            TextView attendance = (TextView) convertView.findViewById(R.id.attendance);
            TextView absent = (TextView)convertView.findViewById(R.id.absent);
            if(att==0&&hr>0) {
                attendance.setText(((present*100)/hr)+"");
                absent.setText((hr-present)+"");
            }
            else
                attendance.setText("0");
            total.setText(hr + "");
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
