package com.example.onlinemysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/*********************************************************
 *        Condition to Retrieve Data From mysql
 *********************************************************/

public class MainActivity4 extends Activity {

    EditText ET_10th , ET_12th, ET_cgpa;
    String tenth , twelth, cgpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        ET_10th = (EditText)findViewById(R.id.get_10);
        ET_12th = (EditText)findViewById(R.id.get_12);
        ET_cgpa = (EditText)findViewById(R.id.get_cgpa);

    }

    public void getData(View view) {
        tenth = ET_10th.getText().toString();
        twelth = ET_12th.getText().toString();
        cgpa = ET_cgpa.getText().toString();

        Intent intent = new Intent(this, MainActivity3.class);
        Bundle b = new Bundle();
        b.putString("tenth", tenth);
        b.putString("twelth", twelth);
        b.putString("cgpa", cgpa);

        intent.putExtras(b);
        startActivity(intent);

        finish();
    }
}