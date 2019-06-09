package com.example.admin.wru;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CreateMoim extends AppCompatActivity  {
    TextView tv;
    EditText et_moimlocation;
    EditText et_timeAndDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmoimform);


        tv=(TextView)findViewById(R.id.textView);

        EditText et_moimname = (EditText) findViewById(R.id.moimname);
        EditText et_numofppl=(EditText)findViewById(R.id.numofppl);
        EditText et_moimpw=(EditText)findViewById(R.id.moimpw);
        EditText et_moimrepw=(EditText)findViewById(R.id.moimrepw);
        et_moimlocation=(EditText)findViewById(R.id.moimlocation);
        et_timeAndDate=(EditText)findViewById(R.id.timeAndDate);

        Button btn_timeAndDate=(Button)findViewById(R.id.btn_timeAndDate);
        btn_timeAndDate.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TimeAndDate.class);
                startActivityForResult(intent,1);

            }
        });


        Button btn_location=(Button)findViewById(R.id.btn_location);
        btn_location.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SearchMap.class);
                startActivityForResult(intent,3000);


            }
        });



        Button btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new Button.OnClickListener() {

                                   @Override
                                   public void onClick(View view) {
                                       if (et_moimname.getText().toString().equals("") || et_numofppl.getText().toString().equals("")
                                               || et_moimpw.getText().toString().equals("") ||
                                               et_moimrepw.getText().toString().equals("") || et_moimlocation.getText().toString().equals(""))
                                           Toast.makeText(getApplicationContext(), "모든 항목을 입력해주세요.", Toast.LENGTH_LONG).show();
                                       else {

                                           String moimname = et_moimname.getText().toString();
                                           Integer numofppl = Integer.parseInt(et_numofppl.getText().toString());
                                           String moimpw = et_moimpw.getText().toString();
                                           String moimrepw = et_moimrepw.getText().toString();
                                           String moimlocation = et_moimlocation.getText().toString();
                                           String moimtime=et_timeAndDate.getText().toString();

                                           if (!moimpw.equals(moimrepw)) {
                                               AlertDialog.Builder alert_confirm = new AlertDialog.Builder(CreateMoim.this);
                                               alert_confirm.setMessage("비밀번호가 일치하지 않습니다.");
                                               alert_confirm.setPositiveButton("확인", null);
                                               AlertDialog alert = alert_confirm.create();
                                               alert.show();
                                           } else {


                    new JSONTask().execute("http://172.30.1.35:3000",moimname,numofppl.toString(),moimlocation,moimtime,moimpw);
                                           Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                           startActivity(intent);}


                                           }
                                       }
                                   }


        );

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 3000:
                    et_moimlocation.setText(data.getStringExtra("result"));
                    break;
                case 1:
                    et_timeAndDate.setText(data.getStringExtra("timeAndDate"));
                    break;

            }
        }
    }


    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {

            try {

                JSONObject jsonObject = new JSONObject();
                String moimname=args[1];
                String numofppl=args[2];
                String moimlocation=args[3];
                String moimtime=args[4];
                String moimpw=args[5];

                jsonObject.put("moimname",moimname);
                jsonObject.put("numofppl",Integer.parseInt(numofppl));
                jsonObject.put("moimtime", moimtime);
                jsonObject.put("moimpw",moimpw);
                jsonObject.put("moimlocation",moimlocation);

                Log.i(this.getClass().getName(),jsonObject.getString("moimname"));


                HttpURLConnection con = null;
                BufferedReader reader=null;
                try {
                    try {
                        //URL url = new URL("http://192.168.0.3:3000");
                        URL url = new URL(args[0]);
                        con = (HttpURLConnection) url.openConnection();

                        con.setRequestMethod("POST");
                        con.setRequestProperty("Accept-Charset","UTF-8");
                        con.setRequestProperty("Cache-Control","no-cache");
                        con.setRequestProperty("Content-Type","application/json");
                        con.setRequestProperty("Accept","application/json");

                        con.setDoOutput(true);
                        con.setDoInput(true);
                        con.connect();

                        OutputStreamWriter out=new OutputStreamWriter(con.getOutputStream());
                        out.write(jsonObject.toString());
                        out.close();

                        //OutputStream outputStream=con.getOutputStream();
                        //BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(outputStream));
                        //writer.flush();;
                        //writer.close();


                        InputStream stream = con.getInputStream();

                        reader = new BufferedReader(new InputStreamReader(stream));

                        StringBuffer buffer = new StringBuffer();

                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            buffer.append(line);
                        }

                        return buffer.toString();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (con != null) {
                            con.disconnect();
                        }
                        try {
                            if (reader != null) {
                                reader.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();


                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tv.setText(result);
        }
    }
}


