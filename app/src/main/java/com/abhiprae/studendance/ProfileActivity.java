package com.abhiprae.studendance;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import Model.Session;

/**
 * Created by Abhiprae on 3/13/2017.
 */

public class ProfileActivity extends AppCompatActivity {

    public static final String MYPREF = "STUDENDANCE";
    SharedPreferences pref;
    TextView name,registrationid,email,contact,department,year,batch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences(MYPREF,MODE_PRIVATE);
        if(pref.getString("Type","").equals("faculty")){
            setContentView(R.layout.activity_profile_faculty);
        }
        else{
            setContentView(R.layout.activity_profile_student);
            year = (TextView)findViewById(R.id.year);
            batch = (TextView)findViewById(R.id.batch);
            year.setText(Session.user.getYear()+"");
            batch.setText(Session.user.getBatch()+"");
        }
        name = (TextView)findViewById(R.id.name);
        registrationid = (TextView)findViewById(R.id.registrationid);
        department = (TextView)findViewById(R.id.department);
        email = (TextView)findViewById(R.id.email);
        contact = (TextView)findViewById(R.id.contact);
        name.setText(Session.user.getName());
        registrationid.setText(Session.user.getRegistration_id());
        department.setText(Session.user.getDepartment());
        email.setText(Session.user.getEmail_id());
        contact.setText(Session.user.getContact());
    }

}
