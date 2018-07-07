package com.abhiprae.studendance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import Model.Session;
import Model.User;

/**
 * Created by Abhiprae on 2/26/2017.
 */

public class UsingCredentialsActivity extends AppCompatActivity {

    TextInputEditText registrationId,password;
    SQLiteDatabase db;
    Cursor cursor;
    String SELECT_SQL = "SELECT * FROM user WHERE id=1";
    Context context;
    User user;
    ProgressDialog pd;
    SharedPreferences pref;
    public static final String MYPREF = "STUDENDANCE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_credentials);
        registrationId = (TextInputEditText)findViewById(R.id.regno);
        password = (TextInputEditText)findViewById(R.id.password);
        context = this;
        pref = getSharedPreferences(MYPREF,MODE_PRIVATE);
        user = new User();
        openDatabase();
        try{
            cursor = db.rawQuery(SELECT_SQL,null);
            cursor.moveToFirst();
            user = getUser();
            Session.user = user;
        }
        catch(Exception e){
            Log.d("User",e.getMessage());
            return;
        }
    }

    public void onLogin(View view){
        pd = new ProgressDialog(context);
        pd.setMessage("Please Wait...");
        pd.setTitle("Logging in");
        pd.setCancelable(false);
        pd.show();
        String reg = registrationId.getText().toString();
        String pass = password.getText().toString();
        if(reg.equals(user.getRegistration_id()) && pass.equals(user.getPassword())){
            pd.dismiss();
            Toast.makeText(getBaseContext(),"Login Successful !",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context,MainActivity.class);
            startActivity(intent);
        }
        else{
            pd.dismiss();
            Toast.makeText(getBaseContext(),"Invalid User !",Toast.LENGTH_SHORT).show();
            return;
        }
    }

    void openDatabase(){
        db=openOrCreateDatabase("Studendance", Context.MODE_PRIVATE, null);
    }

    User getUser(){
        User u = new User();
        if(pref.getString("Type","faculty").equals("student")){
            u.setRegistration_id(cursor.getString(1));
            u.setName(cursor.getString(2));
            u.setContact(cursor.getString(3));
            u.setEmail_id(cursor.getString(4));
            u.setPassword(cursor.getString(5));
            u.setDepartment(cursor.getString(6));
            u.setYear(Integer.parseInt(cursor.getString(7)));
            u.setBatch(Integer.parseInt(cursor.getString(8)));
            u.setType(cursor.getString(9));
            u.setToken(cursor.getString(10));
            return u;
        }
        else{
            u.setRegistration_id(cursor.getString(1));
            u.setName(cursor.getString(2));
            u.setContact(cursor.getString(3));
            u.setEmail_id(cursor.getString(4));
            u.setPassword(cursor.getString(5));
            u.setDepartment(cursor.getString(6));
            u.setType(cursor.getString(7));
            u.setToken(cursor.getString(8));
            return u;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context,LoginActivity.class);
        startActivity(intent);
    }
}
