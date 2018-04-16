package com.example.ayush.mylocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText fromLocation, toLocation;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fromLocation = (EditText) findViewById(R.id.fromLocation);
        toLocation = (EditText) findViewById(R.id.toLocation);
        submit = (Button) findViewById(R.id.Submit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.Submit) {
            String fromLocationString = fromLocation.getText().toString();
            String toLocationString = toLocation.getText().toString();
            Intent mapActivity = new Intent(MainActivity.this, MapActivity.class);
            mapActivity.putExtra("from", fromLocationString);
            mapActivity.putExtra("to", toLocationString);
            startActivity(mapActivity);
        }
    }
}
