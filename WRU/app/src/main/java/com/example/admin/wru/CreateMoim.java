package com.example.admin.wru;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class CreateMoim extends FragmentActivity implements OnMapReadyCallback {
    TextView tv;

    private GoogleMap mMap;
    private Geocoder geocoder;


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        geocoder=new Geocoder(this);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                MarkerOptions markerOptions=new MarkerOptions();
                markerOptions.title("ㅁㅏ커 좌표");
                Double latitude=point.latitude;
                Double longitude =point.longitude;
                markerOptions.snippet(latitude.toString()+","+longitude.toString());
                markerOptions.position((new LatLng(latitude,longitude)));

                googleMap.addMarker(markerOptions);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmoimform);


        tv=(TextView)findViewById(R.id.textView);

        EditText et1 = (EditText) findViewById(R.id.moimname);
        EditText et2=(EditText)findViewById(R.id.numofppl);
        EditText et3 = (EditText) findViewById(R.id.moimtime);
        EditText et4=(EditText)findViewById(R.id.moimpw);
        EditText et5=(EditText)findViewById(R.id.moimrepw);
        Button location_button=(Button)findViewById(R.id.location_button);
        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        location_button.setOnClickListener(new Button.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                EditText et6=(EditText)findViewById(R.id.moimlocation);
//
//                String moimlocation=et6.getText().toString();
//                List<Address> addressList=null;
//                try{
//                    addressList=geocoder.getFromLocationName(
//                            moimlocation,10);
//
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//
//                String []splitStr=addressList.get(0).toString().split(",");
//                String address=splitStr[0].substring(splitStr[0].indexOf("\"")+1,splitStr[0].length()-2);
//
//                String latitude=splitStr[10].substring(splitStr[10].indexOf("=")+1);
//                String longitude=splitStr[12].substring(splitStr[12].indexOf("=")+1);
//
//                LatLng point =new LatLng(Double.parseDouble(latitude),Double.parseDouble((longitude)));
//
//                MarkerOptions markerOptions2=new MarkerOptions();
//                markerOptions2.title("search result");
//                markerOptions2.snippet(address);
//                markerOptions2.position(point);
//
//                mMap.addMarker(markerOptions2);
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));
//
//            }
//        });
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));



        Button btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
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
                new JSONTask().execute("http://172.30.58.173:3000",moimname,numofppl.toString(),moimtime,moimpw,moimlocation);}

            }
        }

        );





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


