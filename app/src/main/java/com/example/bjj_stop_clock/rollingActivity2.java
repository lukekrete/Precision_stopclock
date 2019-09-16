package com.example.bjj_stop_clock;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class rollingActivity2 extends AppCompatActivity {
    private static SeekBar seek_bar;
    private static TextView text_view;
    private static SeekBar roundTime;
    private static SeekBar restTime;
    private static TextView roundTime_text;
    private static TextView restTime_text;
    private static TextView stageTV;
    private static int rolls;
    private static int rolls_counter;
    private static int roll_seconds;
    private static int roll_minutes;
    private static int rest_seconds;
    private static int rest_minutes;

    private TextView countDownText;
    private Button countDownStart;
    private Button backButton;
    private Button restartButton;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliSeconds;
    private long timeLeftInMilliSecondsRest;

    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_rolling2);
        seekbar();
        seekbar_roll();
        seekbar_rest();
        stageTV = findViewById(R.id.stageTV);
        countDownText = findViewById(R.id.countdownText);
        countDownStart = findViewById(R.id.START);
        backButton = findViewById(R.id.BACK);
        restartButton = findViewById(R.id.RESTART);
        countDownStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(timeLeftInMilliSeconds > 0 && rolls > 0) {
                    startStop();
                }
            }
        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                try {
                    stopTimer();
                    rolls_counter = 0;
                    Intent rollingIntent = new Intent(getApplicationContext(), rollingActivity2.class);
                    startActivity(rollingIntent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                try {
                    if(timerRunning) {
                        stopTimer();
                    }
                    Intent MainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(MainIntent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public void playWarning(){
        final MediaPlayer warningMP = MediaPlayer.create(this,R.raw.finishim);
        warningMP.start();
    }
    public void playRest(){
        final MediaPlayer restMP = MediaPlayer.create(this, R.raw.flawless);
        restMP.start();
    }
    public void playFight(){
        final MediaPlayer fightMP = MediaPlayer.create(this, R.raw.fight);
        fightMP.start();
    }

    public void startStop(){
        if(timerRunning){
            stopTimer();
        }else{
            startTimer();
        }
    }
    public void startTimer(){
        playFight();
        stageTV.setText("Roll " + (rolls_counter + 1));
        countDownTimer = new CountDownTimer(timeLeftInMilliSeconds, 1000) {
        @Override
        public void onTick(long l) {
            timeLeftInMilliSeconds = l;
            updateTimer();
        }

        @Override
        public void onFinish() {
            //countDownText.setText("FINISHED");
            playRest();
            stageTV.setText("Rest "+ (rolls_counter + 1));
            countDownTimer = new CountDownTimer(timeLeftInMilliSecondsRest,1000) {
                @Override
                public void onTick(long l) {
                    timeLeftInMilliSecondsRest = l;
                    int min_temp = (int) timeLeftInMilliSecondsRest / 60000;
                    int sec_temp = (int) timeLeftInMilliSecondsRest % 60000 / 1000;
                    String timeLeftText;

                    timeLeftText = "" + min_temp;
                    timeLeftText += ":";
                    if(sec_temp < 10) timeLeftText += "0";
                    timeLeftText += sec_temp;

                    countDownText.setText(timeLeftText);

                }

                @Override
                public void onFinish() {
                    if(((rolls-rolls_counter)-1)>0) {
                        rolls_counter++;
                        timeLeftInMilliSeconds = (roll_seconds * 1000) + (roll_minutes * 60000);
                        timeLeftInMilliSecondsRest = (rest_seconds * 1000) + (rest_minutes * 60000);
                        startTimer();
                    }else{
                        countDownText.setText("00:00");
                    }
                }
            }.start();

        }
    }.start();
    countDownStart.setText("PAUSE");
    timerRunning = true;
    }
    public void stopTimer(){
        countDownTimer.cancel();
        countDownStart.setText("START");
        timerRunning = false;
    }

    public void updateTimer(){
        int min_temp = (int) timeLeftInMilliSeconds / 60000;
        int sec_temp = (int) timeLeftInMilliSeconds % 60000 / 1000;
        String timeLeftText;

        timeLeftText = "" + min_temp;
        timeLeftText += ":";
        if(sec_temp < 10) timeLeftText += "0";
        timeLeftText += sec_temp;
        if(min_temp == 0 && sec_temp == 30){
                playWarning();
        }
        countDownText.setText(timeLeftText);

    }


    public void seekbar(){
        seek_bar = (SeekBar)findViewById(R.id.seekBar2);
        seek_bar.setMax(20);
        text_view = (TextView)findViewById(R.id.editText);
        text_view.setText("Number of Rolls : " + String.valueOf(seek_bar.getProgress()));

        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress_value = progress;
                rolls = progress;
                text_view.setText("Number of Rolls : " + String.valueOf(progress));
                //Toast.makeText(rollingActivity2.this, "SeekBar in Progress", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(rollingActivity2.this, "SeekBar in StartTracking", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                text_view.setText("Number of Rolls : " + progress_value);
                rolls = progress_value;
                //Toast.makeText(rollingActivity2.this, "SeekBar in StopTracking", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void seekbar_roll(){
        roundTime = (SeekBar)findViewById(R.id.seekBar);
        roundTime.setMax(900);
        //roundTime.setProgress(30);
        roundTime_text = (TextView)findViewById(R.id.textView2);

        roundTime_text.setText("Roll time : 0:00");

        roundTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int value;
            int minutes;
            int seconds;
            String secondstring;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value = progress;
                minutes = progress /60;
                roll_minutes = minutes;
                seconds = progress - minutes * 60;
                roll_seconds = seconds;
                secondstring = Integer.toString(seconds);

                if(seconds == 0){
                    secondstring = "00";
                }else if(seconds < 10){
                    secondstring = "0"+Integer.toString(seconds);
                }
                roundTime_text.setText("Roll time : " + Integer.toString(minutes) + ":" + secondstring);

                //Toast.makeText(rollingActivity2.this, "SeekBar in Progress", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(rollingActivity2.this, "SeekBar in StartTracking", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                minutes = value /60;
                roll_minutes = minutes;
                seconds = value - minutes * 60;
                roll_seconds = seconds;
                secondstring = Integer.toString(seconds);

                if(seconds == 0){
                    secondstring = "00";
                }else if(seconds < 10){
                secondstring = "0"+Integer.toString(seconds);
                }
                roundTime_text.setText("Roll time : " + Integer.toString(minutes) + ":" + secondstring);
                //Toast.makeText(rollingActivity2.this, "SeekBar in StopTracking", Toast.LENGTH_LONG).show();
                timeLeftInMilliSeconds = (roll_seconds * 1000) + (roll_minutes * 60000);

            }
        });
    }

    public void seekbar_rest(){
        restTime = (SeekBar)findViewById(R.id.seekBar3);
        restTime.setMax(120);
        //roundTime.setProgress(30);
        restTime_text = (TextView)findViewById(R.id.textView3);

        restTime_text.setText("Rest time : 0:00");

        restTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int value2;
            int minutes2;
            int seconds2;
            String secondstring2;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value2 = progress;
                minutes2 = progress /60;
                rest_minutes = minutes2;
                seconds2 = progress - minutes2 * 60;
                rest_seconds = seconds2;
                secondstring2 = Integer.toString(seconds2);

                if(seconds2 == 0){
                    secondstring2 = "00";
                }else if(seconds2 < 10){
                    secondstring2 = "0"+Integer.toString(seconds2);
                }
                restTime_text.setText("Rest time : " + Integer.toString(minutes2) + ":" + secondstring2);
                timeLeftInMilliSecondsRest = (rest_seconds * 1000) + (rest_minutes * 60000);
                //Toast.makeText(rollingActivity2.this, "SeekBar in Progress", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(rollingActivity2.this, "SeekBar in StartTracking", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                minutes2 = value2 /60;
                seconds2 = value2 - minutes2 * 60;
                secondstring2 = Integer.toString(seconds2);
                rest_minutes = minutes2;
                rest_seconds = seconds2;
                if(seconds2 == 0){
                    secondstring2 = "00";
                }else if(seconds2 < 10){
                    secondstring2 = "0"+Integer.toString(seconds2);
                }
                restTime_text.setText("Rest time : " + Integer.toString(minutes2) + ":" + secondstring2);
                //Toast.makeText(rollingActivity2.this, "SeekBar in StopTracking", Toast.LENGTH_LONG).show();
                timeLeftInMilliSecondsRest = (rest_seconds * 1000) + (rest_minutes * 60000);


            }
        });
    }

}
