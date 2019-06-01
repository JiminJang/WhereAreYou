package com.example.admin.wru;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class MoimList extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moimlist);
        EditText search_moim_et=(EditText)findViewById(R.id.search_moim);
        String search_moim=search_moim_et.getText().toString();
        Button b=(Button)findViewById(R.id.button);
        b.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),search_moim,Toast.LENGTH_LONG).show();
                try{

                    JSONObject jsonObject=new JSONObject();
                    jsonObject.accumulate("search_moim",search_moim);
                    new JSONTask().execute("http://10.100.41.84:3000",jsonObject.toString());
                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
        });


    }

}

