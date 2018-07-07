package com.abhiprae.studendance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Abhiprae on 4/6/2017.
 */

public class AttendanceFacultyActivity extends AppCompatActivity {

    TextInputEditText subject_code;
    Context context;
    ProgressDialog pd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_faculty);
        subject_code = (TextInputEditText)findViewById(R.id.subject_code);
        context = this;
    }

    public void onEnable(View view){
        Intent intent = new Intent(context,ValidationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context,MainActivity.class);
        startActivity(intent);
    }
}
