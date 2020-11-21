package com.example.onlinemysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/*******************************************
 *              Main Screen
 *******************************************/
public class MainActivity extends Activity {

    Button b1, b2;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button)findViewById(R.id.button2);
        b2 = (Button)findViewById(R.id.button3);
        textView = (TextView)findViewById(R.id.textView);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            textView.setVisibility(View.INVISIBLE);
        } else {
            b1.setEnabled(false);
            b2.setEnabled(false);
        }
    }

    public void addInfo(View view) {
        startActivity(new Intent(this, MainActivity2.class));
    }

    public void viewInfo(View view) {
        startActivity(new Intent(this,MainActivity4.class));
    }

}