package com.abhiprae.studendance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import Model.Session;
import Model.User;

/**
 * Created by Abhiprae on 2/26/2017.
 */

public class UsingCardActivity extends AppCompatActivity {

    Context context;
    User user;
    ProgressDialog pd;
    String cardcontent;

    SQLiteDatabase db;
    Cursor cursor;
    private static final String SELECT_SQL = "SELECT * FROM user WHERE id=1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_card);
        context = this;
        cardcontent = getIntent().getExtras().getString("cardcontent");
        pd = new ProgressDialog(context);
        pd.setMessage("Please Wait...");
        pd.setTitle("Logging in");
        pd.setCancelable(false);
        pd.show();
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
        if(cardcontent.equals(user.getRegistration_id())) {
            Toast.makeText(getBaseContext(), "Login Successful !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getBaseContext(), "Invalid User !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }
    }

    void openDatabase(){
        db=openOrCreateDatabase("Studendance", Context.MODE_PRIVATE, null);
    }

    User getUser(){
        User u = new User();
        u.setRegistration_id(cursor.getString(1));
        u.setName(cursor.getString(2));
        u.setContact(cursor.getString(3));
        u.setEmail_id(cursor.getString(4));
        u.setPassword(cursor.getString(5));
        u.setDepartment(cursor.getString(6));
        u.setYear(Integer.parseInt(cursor.getString(7)));
        u.setBatch(Integer.parseInt(cursor.getString(8)));
        u.setType(cursor.getString(9));
        return u;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context,LoginActivity.class);
        startActivity(intent);
    }
}
