package com.example.admin.wru;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class TimeAndDate extends AppCompatActivity implements TimePicker.OnTimeChangedListener {
    Calendar c;
    TimePicker tp;
    DatePicker dp;
    TextView tv_timeAndDate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_and_date);

        c=Calendar.getInstance();
        int year=c.get(c.YEAR);
        int month=c.get(c.MONTH);
        int day=c.get(c.DAY_OF_MONTH);

        int hour=c.get(Calendar.HOUR_OF_DAY);
        int minute=c.get(Calendar.MINUTE);

        tv_timeAndDate=(TextView)findViewById(R.id.tv_timeAndDate);
        tp=(TimePicker)findViewById(R.id.timePicker);
        dp=(DatePicker)findViewById(R.id.datePicker);

        tv_timeAndDate.setText(hour+":"+minute);
        tp.setOnTimeChangedListener(this);



        Button btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent=new Intent();
                intent.putExtra("timeAndDate",dp.getYear()+"/"+(dp.getMonth()+1)+"/"
                +dp.getDayOfMonth()+" "+tv_timeAndDate.getText());
                setResult(RESULT_OK,intent);
                finish();


            }
        });
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
        tv_timeAndDate.setText(hour+":"+minute);
    }
}
