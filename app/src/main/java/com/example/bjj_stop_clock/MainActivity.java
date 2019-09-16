package com.example.bjj_stop_clock;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRedrawHandler.sleep(100);
        setContentView(R.layout.activity_main);
        Button btnRolling = findViewById(R.id.push_button);
        Button btnScoring = findViewById(R.id.scoring);

        btnRolling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                try {
                    Intent RollingIntent = new Intent(getApplicationContext(), rollingActivity2.class);
                    startActivity(RollingIntent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btnScoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                try {
                    Intent ScoringIntent = new Intent(getApplicationContext(), activity_scoring.class);
                    startActivity(ScoringIntent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    private RefreshHandler mRedrawHandler = new RefreshHandler();

    class RefreshHandler extends Handler {
        public void handleMessage(Message msg) {
            String curDateTime = DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());
            ((TextView)findViewById(R.id.textView)).setText(curDateTime);
            sleep(100); }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }
}
