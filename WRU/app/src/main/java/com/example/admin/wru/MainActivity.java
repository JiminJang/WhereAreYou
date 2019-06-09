package com.example.admin.wru;
import android.content.Intent;
import android.icu.util.Output;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = (Button) findViewById(R.id.createmoim);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(), CreateMoim.class);
                startActivity(intent);
            }
        });
        Button b2=(Button)findViewById(R.id.joinmoim);
        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(
                        getApplicationContext(),MoimList.class);
                startActivity(intent2);

            }
        });
        Button b3=(Button)findViewById(R.id.moimview);
        b3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(
                        getApplicationContext(),MoimView.class);
                startActivity(intent3);

            }
        });

    }
}


