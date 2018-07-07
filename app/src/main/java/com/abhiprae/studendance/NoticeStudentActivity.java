package com.abhiprae.studendance;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListView;
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

import Model.Notice;
import Model.Session;
import Model.User;

/**
 * Created by Abhiprae on 3/15/2017.
 */

public class NoticeStudentActivity extends AppCompatActivity {

    ProgressDialog pd;
    Context context;
    User userS;
    Notice notices[];
    ExpandableListView notice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_student);
        context = this;
        pd = new ProgressDialog(context);
        pd.setMessage("Please Wait ...");
        pd.setTitle("Fetching Notices");
        pd.show();
        userS = new User();
        notice = (ExpandableListView)findViewById(R.id.notices);
        if(!isConnected()){
            Toast.makeText(getBaseContext(),"No Internet Available !",Toast.LENGTH_SHORT).show();
            pd.dismiss();
            Intent intent = new Intent(context,MainActivity.class);
            startActivity(intent);
        }
        else{
            userS.setRegistration_id(Session.user.getRegistration_id());
            userS.setDepartment(Session.user.getDepartment());
            userS.setYear(Session.user.getYear());
            new NoticeStudentActivity.HttpAsyncTask().execute("http://abhipraechoudhury.esy.es/studendance/read_notice.php");
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
            jsonData = gson.toJson(userS, User.class);
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
                Toast.makeText(getBaseContext(),"Server Problem, Try Again !",Toast.LENGTH_SHORT).show();
                pd.dismiss();
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
            }
            else{
                Gson g = new Gson();
                notices = g.fromJson(result,Notice[].class);
                pd.dismiss();
                String topics[] = new String[notices.length];
                String messages[] = new String[notices.length];
                for(int i=0; i<notices.length; i++){
                    topics[i] = notices[i].getTopic();
                    messages[i] = notices[i].getMessage();
                }
                MyCustomAdapter arrayAdapter = new MyCustomAdapter(context,topics,messages);
                notice.setAdapter(arrayAdapter);
            }
        }
    }
}
