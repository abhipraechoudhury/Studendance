package com.abhiprae.studendance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import Model.Session;

public class MainActivity extends AppCompatActivity {

    Context context;
    SharedPreferences pref;
    public static final String MYPREF = "STUDENDANCE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Name ", Session.user.getName());
        context = this;
        pref = getSharedPreferences(MYPREF,MODE_PRIVATE);
    }

    public void onNotices(View view){
        if (pref.getString("Type","faculty").equals("faculty")){
            Intent intent = new Intent(context,NoticeFacultyActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(context,NoticeStudentActivity.class);
            startActivity(intent);
        }
    }

    public void onProfile(View view){
        Intent intent = new Intent(context,ProfileActivity.class);
        startActivity(intent);
    }

    public void onTimetable(View view){
        Intent intent = new Intent(context,TimeTableActivity.class);
        startActivity(intent);
    }

    public void onTrack(View view){
        Intent intent = new Intent(context,TrackerActivity.class);
        startActivity(intent);
    }

    public void onAttendance(View view){
        if(Session.user.getType().equals("faculty")){
            Intent intent = new Intent(context,AttendanceFacultyActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(context,AttendanceStudentActivity.class);
            startActivity(intent);
        }
    }
}
