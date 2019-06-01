package com.example.admin.wru;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
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



public class CreateMoim extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmoimform);



        Button btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                tv=(TextView)findViewById(R.id.textView);

                EditText et1 = (EditText) findViewById(R.id.moimname);
                EditText et2=(EditText)findViewById(R.id.numofppl);
                EditText et3 = (EditText) findViewById(R.id.moimtime);
                EditText et4=(EditText)findViewById(R.id.moimpw);
                EditText et5=(EditText)findViewById(R.id.moimrepw);
                EditText et6=(EditText)findViewById(R.id.moimlocation);
                String moimname = et1.getText().toString();
                Integer numofppl=Integer.parseInt(et2.getText().toString());
                String moimtime = et3.getText().toString();
                String moimpw=et4.getText().toString();
                String moimrepw=et5.getText().toString();
                String moimlocation=et6.getText().toString();

                if(!moimpw.equals(moimrepw)){
                    AlertDialog.Builder alert_confirm=new AlertDialog.Builder(CreateMoim.this);
                    alert_confirm.setMessage("비밀번호가 일치하지 않습니다.");
                    alert_confirm.setPositiveButton("확인",null);
                    AlertDialog alert=alert_confirm.create();
                    alert.show();
                }
                else{


                Toast.makeText(getApplicationContext(), moimtime + moimname, Toast.LENGTH_LONG).show();
                new JSONTask().execute("http://192.168.0.129:3000",moimname,numofppl.toString(),moimtime,moimpw,moimlocation);}

            }
        });
    }

    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {

            try {

                JSONObject jsonObject = new JSONObject();
                String moimname=args[1];
                String numofppl=args[2];
                String moimtime=args[3];
                String moimpw=args[4];
                String moimlocation=args[5];

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


