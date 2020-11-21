package com.example.onlinemysql;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/*******************************************
 *           Add Data into mysql
 *******************************************/

public class MainActivity2 extends Activity {

    EditText ET_name, ET_rollnum, ET_tenth, ET_twelth , ET_cgpa;
    String name, rollnum, tenth, twelth, cgpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ET_name = (EditText)findViewById(R.id.name);
        ET_rollnum = (EditText)findViewById(R.id.rollNum);
        ET_tenth = (EditText)findViewById(R.id.tenth);
        ET_twelth = (EditText)findViewById(R.id.twelth);
        ET_cgpa = (EditText)findViewById(R.id.cgpa);

    }

    public void saveInfo(View view) {
        name = ET_name.getText().toString();
        rollnum = ET_rollnum.getText().toString();
        tenth = ET_tenth.getText().toString();
        twelth = ET_twelth.getText().toString();
        cgpa = ET_cgpa.getText().toString();

        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(name, rollnum, tenth, twelth, cgpa);
        finish();
    }

    class BackgroundTask extends AsyncTask <String, Void, String> {

        String add_info_url;

        @Override
        protected void onPreExecute() {
            add_info_url = "https://ajaychidambaram.000webhostapp.com/add_info.php";
            //add_info_url = "http://ajaychidambaram.000webhostapp.com/add_info.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String name, rollnum, tenth, twelth, cgpa;
            name = params[0];
            rollnum = params[1];
            tenth = params[2];
            twelth = params[3];
            cgpa = params[4];

            try {

                URL url = new URL(add_info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data_string = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name,"UTF-8") + "&" +
                        URLEncoder.encode("rollnum", "UTF-8") + "=" + URLEncoder.encode(rollnum,"UTF-8") + "&" +
                        URLEncoder.encode("tenth", "UTF-8") + "=" + URLEncoder.encode(tenth,"UTF-8") + "&" +
                        URLEncoder.encode("twelth", "UTF-8") + "=" + URLEncoder.encode(twelth,"UTF-8") + "&" +
                        URLEncoder.encode("cgpa", "UTF-8") + "=" + URLEncoder.encode(cgpa,"UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();

                name = name.toUpperCase();

                return "Thanks " + name + ". Your Data Has Been Added Successfully";

            } catch (MalformedURLException | UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);
            LinearLayout layout = (LinearLayout) toast.getView();
            if (layout.getChildCount() > 0) {
                TextView tv = (TextView) layout.getChildAt(0);
                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            }
            toast.show();

        }
    }
}