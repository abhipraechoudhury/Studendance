package com.abhiprae.studendance;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Abhiprae on 4/1/2017.
 */

public class TimeTableActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Cursor cursor;
    String timetable[][];
    TextView day11,day12,day13,day14,day15,day16,day17,day18,day19,day110;
    TextView day21,day22,day23,day24,day25,day26,day27,day28,day29,day210;
    TextView day31,day32,day33,day34,day35,day36,day37,day38,day39,day310;
    TextView day41,day42,day43,day44,day45,day46,day47,day48,day49,day410;
    TextView day51,day52,day53,day54,day55,day56,day57,day58,day59,day510;
    private String SELECT_SQL = "SELECT * FROM timetable";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        day11 = (TextView)findViewById(R.id.day11);
        day12 = (TextView)findViewById(R.id.day12);
        day13 = (TextView)findViewById(R.id.day13);
        day14 = (TextView)findViewById(R.id.day14);
        day15 = (TextView)findViewById(R.id.day15);
        day16 = (TextView)findViewById(R.id.day16);
        day17 = (TextView)findViewById(R.id.day17);
        day18 = (TextView)findViewById(R.id.day18);
        day19 = (TextView)findViewById(R.id.day19);
        day110 = (TextView)findViewById(R.id.day110);
        day51 = (TextView)findViewById(R.id.day51);
        day52 = (TextView)findViewById(R.id.day52);
        day53 = (TextView)findViewById(R.id.day53);
        day54 = (TextView)findViewById(R.id.day54);
        day55 = (TextView)findViewById(R.id.day55);
        day56 = (TextView)findViewById(R.id.day56);
        day57 = (TextView)findViewById(R.id.day57);
        day58 = (TextView)findViewById(R.id.day58);
        day59 = (TextView)findViewById(R.id.day59);
        day510 = (TextView)findViewById(R.id.day510);
        day21 = (TextView)findViewById(R.id.day21);
        day22 = (TextView)findViewById(R.id.day22);
        day23 = (TextView)findViewById(R.id.day23);
        day24 = (TextView)findViewById(R.id.day24);
        day25 = (TextView)findViewById(R.id.day25);
        day26 = (TextView)findViewById(R.id.day26);
        day27 = (TextView)findViewById(R.id.day27);
        day28 = (TextView)findViewById(R.id.day28);
        day29 = (TextView)findViewById(R.id.day29);
        day210 = (TextView)findViewById(R.id.day210);
        day31 = (TextView)findViewById(R.id.day31);
        day32 = (TextView)findViewById(R.id.day32);
        day33 = (TextView)findViewById(R.id.day33);
        day34 = (TextView)findViewById(R.id.day34);
        day35 = (TextView)findViewById(R.id.day35);
        day36 = (TextView)findViewById(R.id.day36);
        day37 = (TextView)findViewById(R.id.day37);
        day38 = (TextView)findViewById(R.id.day38);
        day39 = (TextView)findViewById(R.id.day39);
        day310 = (TextView)findViewById(R.id.day310);
        day41 = (TextView)findViewById(R.id.day41);
        day42 = (TextView)findViewById(R.id.day42);
        day43 = (TextView)findViewById(R.id.day43);
        day44 = (TextView)findViewById(R.id.day44);
        day45 = (TextView)findViewById(R.id.day45);
        day46 = (TextView)findViewById(R.id.day46);
        day47 = (TextView)findViewById(R.id.day47);
        day48 = (TextView)findViewById(R.id.day48);
        day49 = (TextView)findViewById(R.id.day49);
        day410 = (TextView)findViewById(R.id.day410);
        openDatabase();
        try{
            cursor = db.rawQuery(SELECT_SQL,null);
        }
        catch(Exception e){
            Log.d("Time Table",e.getMessage());
            Toast.makeText(getBaseContext(),"Internal Error",Toast.LENGTH_SHORT).show();
            return;
        }
        cursor.moveToFirst();
        day11.setText(cursor.getString(1));
        day12.setText(cursor.getString(2));
        day13.setText(cursor.getString(3));
        day14.setText(cursor.getString(4));
        day15.setText(cursor.getString(5));
        day16.setText(cursor.getString(6));
        day17.setText(cursor.getString(7));
        day18.setText(cursor.getString(8));
        day19.setText(cursor.getString(9));
        day110.setText(cursor.getString(10));
        cursor.moveToNext();
        day21.setText(cursor.getString(1));
        day22.setText(cursor.getString(2));
        day23.setText(cursor.getString(3));
        day24.setText(cursor.getString(4));
        day25.setText(cursor.getString(5));
        day26.setText(cursor.getString(6));
        day27.setText(cursor.getString(7));
        day28.setText(cursor.getString(8));
        day29.setText(cursor.getString(9));
        day210.setText(cursor.getString(10));
        cursor.moveToNext();
        day31.setText(cursor.getString(1));
        day32.setText(cursor.getString(2));
        day33.setText(cursor.getString(3));
        day34.setText(cursor.getString(4));
        day35.setText(cursor.getString(5));
        day36.setText(cursor.getString(6));
        day37.setText(cursor.getString(7));
        day38.setText(cursor.getString(8));
        day39.setText(cursor.getString(9));
        day310.setText(cursor.getString(10));
        cursor.moveToNext();
        day41.setText(cursor.getString(1));
        day42.setText(cursor.getString(2));
        day43.setText(cursor.getString(3));
        day44.setText(cursor.getString(4));
        day45.setText(cursor.getString(5));
        day46.setText(cursor.getString(6));
        day47.setText(cursor.getString(7));
        day48.setText(cursor.getString(8));
        day49.setText(cursor.getString(9));
        day410.setText(cursor.getString(10));
        cursor.moveToNext();
        day51.setText(cursor.getString(1));
        day52.setText(cursor.getString(2));
        day53.setText(cursor.getString(3));
        day54.setText(cursor.getString(4));
        day55.setText(cursor.getString(5));
        day56.setText(cursor.getString(6));
        day57.setText(cursor.getString(7));
        day58.setText(cursor.getString(8));
        day59.setText(cursor.getString(9));
        day510.setText(cursor.getString(10));
    }

    void openDatabase(){
        db=openOrCreateDatabase("Studendance", Context.MODE_PRIVATE, null);
    }
}
