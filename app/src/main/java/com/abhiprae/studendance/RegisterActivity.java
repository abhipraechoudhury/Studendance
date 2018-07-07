package com.abhiprae.studendance;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import Model.Session;
import Model.User;

/**
 * Created by Abhiprae on 2/25/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText registrationId,password;
    Context context;
    User userS,userR;
    SharedPreferences pref;
    SQLiteDatabase db;
    ProgressDialog pd;
    public static final String MYPREF = "STUDENDANCE";
    static int backpress = 0;
    String timetable [][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        userS = new User();
        userR = new User();
        pref = getSharedPreferences(MYPREF,MODE_PRIVATE);

        registrationId = (TextInputEditText)findViewById(R.id.regno);
        password = (TextInputEditText)findViewById(R.id.password);
        timetable = new String[5][10];
    }

    public void onRegister(View view){
        userS.setDevice_id(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        Log.d("Device Id: ",userS.device_id);
        userS.setRegistration_id(registrationId.getText().toString());
        userS.setPassword(password.getText().toString());
        String token = getToken();
        userS.setToken(token);

        if(!isConnected()){
            Toast.makeText(getBaseContext(), "No Network Found !", Toast.LENGTH_SHORT).show();
            return;
        }

        pd = new ProgressDialog(context);
        pd.setMessage("Please Wait...");
        pd.setTitle("Registering");
        pd.setCancelable(false);
        pd.show();

        if(userS.getRegistration_id()!=null && userS.getPassword()!=null){
            new RegisterActivity.HttpAsyncTask().execute("http://abhipraechoudhury.esy.es/studendance/register.php");
        }
        else{
            pd.dismiss();
            Toast.makeText(getBaseContext(),"Enter all the fields !",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     **************************************SERVER**************************************************
     */

    /**
     * It returns true if network connectivity is available.
     */
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    public String POST(String url){

        InputStream inputStream = null;
        String result = "";
        String jsonData="";
        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);
            //3. Create data Object

            //4. Prepare Json object
            Gson gson = new Gson();
            jsonData = gson.toJson(userS,User.class);
            Log.d("Login",jsonData);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(jsonData);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        }catch (Exception e){
            e.printStackTrace();
        }

        Log.d("Result",result);
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls)
        {
            return POST(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if(result.equalsIgnoreCase("failed")){
                Toast.makeText(getBaseContext(), "Problem in Registering, Try Again!", Toast.LENGTH_SHORT).show();
            }
            else{
                Gson g = new Gson();
                String temp = result.substring(0,result.indexOf("tt"));
                userR = g.fromJson(temp,User.class);
                temp = result.substring((result.indexOf("tt")+2));
                Log.d("timetable ",temp);
                if(userR.getName()!=""){
                    timetable = g.fromJson(temp,String[][].class);
                    createDatabase();
                    if (userR.getType() == "student") {
                        for(int i=0; i<5; i++){
                            String q = "INSERT INTO timetable (one,two,three,four,five,six,seven,eight,nine,ten) VALUES" +
                                    "('"+timetable[i][0]+"', '"+timetable[i][1]+"', '"+timetable[i][2]+"', '"+timetable[i][3]+"', " +
                                    "'"+timetable[i][4]+"', '"+timetable[i][5]+"', '"+timetable[i][6]+"', '"+timetable[i][7]+"', " +
                                    "'"+timetable[i][8]+"', '"+timetable[i][9]+"');";
                            try{
                                db.execSQL(q);
                                Log.d("Time Table ","Saved Successfully");
                            }
                            catch(SQLException e){
                                e.printStackTrace();
                                Log.d("Exception: ",e.getMessage());
                            }
                        }
                    }
                    Log.d("Type ",userR.getType());
                    insertIntoDB();
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("first_run","no");
                    editor.putString("Registration_id",userR.getRegistration_id());
                    editor.putString("Type",userR.getType());
                    editor.commit();
                    Session.user = userR;
                    Toast.makeText(getBaseContext(),"Registered Successfully !",Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                    Intent i = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(i);
                }
                else{
                    pd.dismiss();
                    Toast.makeText(getBaseContext(), "Server Error, Try Again !", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    String getToken(){
        return SharedPrefManager.getInstance(this).getDeviceToken();
    }

    /******************************************************************************
     *********************************DATABASE*************************************
     ******************************************************************************/
    protected void createDatabase(){
        db=openOrCreateDatabase("Studendance", Context.MODE_PRIVATE, null);
        if (userR.getType().equals("student")){
            db.execSQL("CREATE TABLE IF NOT EXISTS user(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " registration_id VARCHAR, name VARCHAR, contact VARCHAR, email_id VARCHAR, password VARCHAR, " +
                    "department VARCHAR, year INTEGER, batch INTEGER, type VARCHAR, token VARCHAR);");
        }
        else{
            db.execSQL("CREATE TABLE IF NOT EXISTS user(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " registration_id VARCHAR, name VARCHAR, contact VARCHAR, email_id VARCHAR, password VARCHAR, " +
                    "department VARCHAR, type VARCHAR, token VARCHAR);");
        }
        db.execSQL("CREATE TABLE IF NOT EXISTS timetable(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, one VARCHAR, " +
                "two VARCHAR, three VARCHAR, four VARCHAR, five VARCHAR, six VARCHAR, seven VARCHAR, eight VARCHAR, " +
                "nine VARCHAR, ten VARCHAR);");
    }
    protected void insertIntoDB(){
        String query = "";
        for(int i=0;i<5;i++){

        }
        if (userR.getType().equals("faculty")){
            query = "INSERT INTO user (registration_id, name, contact, email_id, password, department," +
                    " type, token) VALUES('"+userR.registration_id+"','"+userR.name+"','"+userR.contact+"','"+userR.email_id+"'," +
                    "'"+userR.password+"','"+userR.department+"','"+userR.type+"', '"+userS.getToken()+"');";
        }
        else {
            query = "INSERT INTO user (registration_id, name, contact, email_id, password, department, year," +
                    " batch, type, token) VALUES('"+userR.registration_id+"','"+userR.name+"','"+userR.contact+"','"+userR.email_id+"'," +
                    "'"+userR.password+"','"+userR.department+"',"+userR.year+","+userR.batch+",'"+userR.type+"', '"+userS.getToken()+"');";
        }

        try{
            db.execSQL(query);
            Log.d("Database","Saved Successfully");
        }
        catch(SQLException e){
            e.printStackTrace();
            Log.d("Exception: ",e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backpress = (backpress + 1);
        Toast.makeText(getApplicationContext(), " Press Back again to Exit ", Toast.LENGTH_SHORT).show();

        if (backpress>1) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
    }
}
