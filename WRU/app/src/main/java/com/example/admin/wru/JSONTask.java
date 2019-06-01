package com.example.admin.wru;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONTask extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... args) {

        try {

//            JSONObject jsonObject = new JSONObject();
//            String moimname=args[1];
//            String numofppl=args[2];
//            String moimtime=args[3];
//            String moimpw=args[4];
//            String moimlocation=args[5];
//
//            jsonObject.put("moimname",moimname);
//            jsonObject.put("numofppl",Integer.parseInt(numofppl));
//            jsonObject.put("moimtime", moimtime);
//            jsonObject.put("moimpw",moimpw);
//            jsonObject.put("moimlocation",moimlocation);
//
//            Log.i(this.getClass().getName(),jsonObject.getString("moimname"));


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


}