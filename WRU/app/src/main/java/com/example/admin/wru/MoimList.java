package com.example.admin.wru;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MoimList extends AppCompatActivity {
    TextView tv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moimlist);

        tv=(TextView)findViewById(R.id.search_page_title);
        try{
            new JSONTask2().execute("http://172.30.1.35:3000/moimlist","a");

        }catch(Exception e){
            e.printStackTrace();
        }
        Button search_button=(Button)findViewById(R.id.search_button);
        search_button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText search_moim_et=(EditText)findViewById(R.id.search_moim);
                String search_moim=search_moim_et.getText().toString();

                Toast.makeText(getApplicationContext(),"name:"+search_moim,Toast.LENGTH_LONG).show();
                try{

                    new JSONTask2().execute("http://172.30.1.35:3000/searchmoim_page",search_moim);

                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
        });

    }
    public class JSONTask2 extends AsyncTask<String, String, JSONArray> {
        @Override
        protected JSONArray doInBackground(String... args) {

            try {

                JSONObject jsonObject = new JSONObject();
                String search_moim=args[1];

                jsonObject.put("search_moim",search_moim);

                Log.i(this.getClass().getName(),jsonObject.getString("search_moim"));


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

                        return new JSONArray(buffer.toString());

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
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            int nDatCnt=0;
            ArrayList<ItemData> oData=new ArrayList<>();
            for (int i=0; i<result.length();i++){
                try{
                    JSONObject object=result.getJSONObject(i);
                    ItemData oItem=new ItemData();
                    oItem.moimname="모임이름: "+object.getString("moimname");
                    oItem.moimlocation="모임장소: "+object.getString("moimlocation");
                    oData.add(oItem);

                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            ListView m_oListView=(ListView)findViewById(R.id.listview);
            ListAdapter oAdapter=new ListAdapter(oData);
            m_oListView.setAdapter(oAdapter);



        }
    }

}

