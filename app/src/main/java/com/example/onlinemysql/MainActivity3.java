package com.example.onlinemysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import static android.icu.lang.UCharacter.toUpperCase;

/*******************************************
 *         Retrieve Data from mysql
 *******************************************/

public class MainActivity3 extends Activity {

    ListView listView;
    ArrayAdapter<String> adapter;
    String line=null;
    HttpURLConnection urlConnection = null;
    InputStream is = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        String tenth, twelth, cgpa;
        Bundle b = getIntent().getExtras();
        tenth = b.getString("tenth");
        twelth = b.getString("twelth");
        cgpa = b.getString("cgpa");

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        Connection connection = new Connection();
        connection.execute(tenth, twelth, cgpa);

    }

    class Connection extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            String result = "";

            String tenth = params[0];
            String twelth = params[1];
            String cgpa = params[2];

            String host = "https://ajaychidambaram.000webhostapp.com/condition.php" ;


            try {

                URL url = new URL("https://ajaychidambaram.000webhostapp.com/condition.php?tenth=" + tenth + "&twelth=" + twelth + "&cgpa=" + cgpa);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.connect();
                is = urlConnection.getInputStream();
            }
            catch (Exception e)
            {
                Log.e("Fail 1", e.toString());
            }

            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
            }
            catch(Exception e)
            {
                Log.e("Fail 2", e.toString());
            }

            /*
            ///// OLD WORKING CODE
            try {
                ///Send Condition to the php script

                /////// Retrieve Data
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(host));
                HttpResponse response = client.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer stringBuffer = new StringBuffer("");

                String line = "";//text response will be appended

                while((line = reader.readLine()) != null) {
                    stringBuffer.append(line);
                    break;
                }

                reader.close();
                result = stringBuffer.toString();
            }
            catch (Exception e) {
                return new String("There is Exception : " + e.getMessage());
            }
            */


            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(getApplicationContext(), s,Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonResult = new JSONObject(s);
                int success = jsonResult.getInt("success");
                if(1 == success) {
                    final JSONArray data = jsonResult.getJSONArray("data");

                    for(int i=0;i<data.length();i++) {
                        JSONObject index = data.getJSONObject(i);
                        String name = (index.getString("name")).toUpperCase();
                        String rollnum = (index.getString("rollnum")).toUpperCase();
                        int tenth = index.getInt("tenth");
                        int twelth = index.getInt("twelth");
                        double cpga = index.getDouble("cgpa");

                        String line = name;

                        adapter.add(line);
                    }

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            try {

                                JSONObject index = data.getJSONObject(i);
                                //Toast.makeText(getApplicationContext(), "clicking " + index.getString("name"), Toast.LENGTH_LONG).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity3.this);
                                builder.setTitle("Information ");
                                builder.setMessage("Name : " + index.getString("name") + "\n" + "Roll Number : " + index.getString("rollnum") +
                                        "\n" + "10th Mark (%) : " + index.getString("tenth") + "\n" + "12th Mark (%) : " + index.getString("twelth") +
                                        "\n" + "CGPA : " + index.getString("cgpa"));
                                builder.show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "SORRY", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "$$$$$$" + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

}