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

import Model.Notice;
import Model.Session;
import Model.User;

/**
 * Created by Abhiprae on 3/14/2017.
 */

public class NoticeFacultyActivity extends AppCompatActivity {

    TextInputEditText topic,message,subjectCode;
    Notice notice;
    Context context;
    ProgressDialog pd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_faculty);
        context = this;
        notice = new Notice();
        topic = (TextInputEditText)findViewById(R.id.topic);
        message = (TextInputEditText)findViewById(R.id.message);
        subjectCode = (TextInputEditText)findViewById(R.id.subject_code);
    }

    public void onSend(View view){
        notice.setTopic(topic.getText().toString());
        notice.setMessage(message.getText().toString());
        notice.setSubjectCode(subjectCode.getText().toString());
        notice.setRegistration_id(Session.user.getRegistration_id());
        if(!isConnected()){
            Toast.makeText(getBaseContext(), "No Network Found !", Toast.LENGTH_SHORT).show();
            return;
        }
        pd = new ProgressDialog(context);
        pd.setMessage("Please Wait...");
        pd.setTitle("Sending");
        pd.setCancelable(false);
        pd.show();
        if(notice.getMessage()!=null && notice.getTopic()!=null){
            new NoticeFacultyActivity.HttpAsyncTask().execute("http://abhipraechoudhury.esy.es/studendance/send_notice.php");
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
            jsonData = gson.toJson(notice, Notice.class);
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
                pd.dismiss();
                Toast.makeText(getBaseContext(), "Problem in Registering, Try Again!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
            }
            else{
                pd.dismiss();
                Toast.makeText(getBaseContext(), "Notice Sent!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
