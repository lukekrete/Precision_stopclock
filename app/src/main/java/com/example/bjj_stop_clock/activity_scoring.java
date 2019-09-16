package com.example.bjj_stop_clock;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.EmptyStackException;
import java.util.Stack;

public class activity_scoring extends AppCompatActivity {
    private Button btnExit;
    private Button btnplus4Red;
    private Button btnplus4Blue;
    private Button btnplus3Red;
    private Button btnplus3Blue;
    private Button btnplus2Red;
    private Button btnplus2Blue;
    private Button undoScoreRed;
    private Button undoScoreBlue;
    private Button redAdv;
    private Button redPen;
    private Button blueAdv;
    private Button bluePen;
    private Button btnDQRed;
    private Button btnDQBlue;

    private Button minusMin;
    private Button plusMin;

    private int lastScore; //before DQ

    private Stack<Integer> redStack = new Stack();
    private Stack<Integer> blueStack = new Stack();

    private TextView advRed;
    private TextView penRed;
    private TextView advBlue;
    private TextView penBlue;
    private TextView redScore;
    private TextView blueScore;

    private boolean DQ = false;

    private Button resetTime;
    private Button resetMatch;

    private TextView timerText;
    private CountDownTimer countDownTimer;
    private Button countDownStart;
    private Button countDownPause;
    private long timeLeftInMilliSeconds = 300000; //5 mins standard
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);
        btnExit = findViewById(R.id.exit);
        btnplus4Red = findViewById(R.id.plus4);
        btnplus4Blue = findViewById(R.id.plus4blue);
        btnplus3Red = findViewById(R.id.plus3);
        btnplus3Blue = findViewById(R.id.plus3blue);
        btnplus2Red = findViewById(R.id.plus2);
        btnplus2Blue = findViewById(R.id.plus2blue);
        redScore = findViewById(R.id.scoreRed);
        blueScore = findViewById(R.id.scoreBlue);
        btnDQRed = findViewById(R.id.DQRed);
        btnDQBlue = findViewById(R.id.DQblue);
        //texviews of pen/adv
        advRed = findViewById(R.id.advRed);
        penRed = findViewById(R.id.penRed);
        advBlue = findViewById(R.id.advBlue);
        penBlue = findViewById(R.id.penBlue);
        //buttons of pen/adv
        redAdv = findViewById(R.id.plusAdv);
        redPen = findViewById(R.id.plusPen);
        blueAdv = findViewById(R.id.plusAdvBlue);
        bluePen = findViewById(R.id.plusPenBlue);

        undoScoreRed = findViewById(R.id.undoScoring);
        undoScoreBlue = findViewById(R.id.undoScoringBlue);
        countDownPause = findViewById(R.id.pause);
        countDownStart = findViewById(R.id.start);
        timerText = findViewById(R.id.timerText);
        minusMin = findViewById(R.id.minusMin);
        plusMin = findViewById(R.id.plusMin);
        resetTime = findViewById(R.id.resetTime);
        resetMatch = findViewById(R.id.resetMatch);


        resetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                pause();
                timeLeftInMilliSeconds = 300000;
                updateTimer();
            }
        });
        resetMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                try {
                    stopTimer();
                    Intent restart = new Intent(getApplicationContext(), activity_scoring.class);
                    startActivity(restart);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        countDownStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(timeLeftInMilliSeconds > 0) {
                    start();
                }
            }
        });

        countDownPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(timeLeftInMilliSeconds > 0) {
                    pause();
                }
            }
        });
        plusMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(timerRunning) {
                    pause();
                    timeLeftInMilliSeconds += 60000;
                    updateTimer();
                    start();
                }else{
                    timeLeftInMilliSeconds += 60000;
                    updateTimer();
                }
            }
        });
        minusMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(timerRunning && timeLeftInMilliSeconds > 60000) {
                    pause();
                    timeLeftInMilliSeconds -= 60000;
                    updateTimer();
                    start();
                }else{
                    if(timeLeftInMilliSeconds > 60000) {
                        timeLeftInMilliSeconds -= 60000;
                        updateTimer();
                    }
                }
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                try {
                    if(timerRunning) {
                        //stopTimer();
                    }
                    Intent MainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(MainIntent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btnplus4Red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                increaseScore(4,"redScore");
            }
        });

        btnplus4Blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                increaseScore(4,"blueScore");
            }
        });

        btnplus3Red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                increaseScore(3,"redScore");
            }
        });

        btnplus3Blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                increaseScore(3,"blueScore");
            }
        });

        btnplus2Red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                increaseScore(2,"redScore");
            }
        });

        btnplus2Blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                increaseScore(2,"blueScore");
            }
        });
        btnDQRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                redStack.push(-3);
                lastScore = (Integer.parseInt(redScore.getText().toString()));
                redScore.setText("DQ");
                pause();
                DQ = true;
            }
        });
        btnDQBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                blueStack.push(-3);
                lastScore = (Integer.parseInt(blueScore.getText().toString()));
                blueScore.setText("DQ");

                pause();
                DQ = true;
            }
        });

        undoScoreRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                undoScore("red");
            }
        });

        undoScoreBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                undoScore("blue");
            }
        });

        redAdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                adv_pen("red", "adv");
                redStack.push(-1); //-1 signifies that it's an advantage

            }
        });
        redPen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                adv_pen("red", "pen");
                redStack.push(-2); //-2 signifies that it's a penalty

            }
        });

        blueAdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                adv_pen("blue", "adv");
                blueStack.push(-1);
            }
        });
        bluePen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                adv_pen("blue", "pen");
                blueStack.push(-2);
            }
        });
    }
    public void playEnd(){
        final MediaPlayer endMP = MediaPlayer.create(this,R.raw.airhorn);
        endMP.start();
    }
    void adv_pen(String color, String action){
        if(!DQ) {
            if (color.equals("red")) {
                if (action.equals("adv")) {
                    int temp = (Integer.parseInt(advRed.getText().toString()));
                    temp += 1;
                    String score = Integer.toString(temp);
                    advRed.setText(score);
                } else if (action.equals("pen")) {
                    int temp = (Integer.parseInt(penRed.getText().toString()));
                    temp += 1;
                    String score = Integer.toString(temp);
                    penRed.setText(score);
                }
            } else {
                if (action.equals("adv")) {
                    int temp = (Integer.parseInt(advBlue.getText().toString()));
                    temp += 1;
                    String score = Integer.toString(temp);
                    advBlue.setText(score);
                } else if (action.equals("pen")) {
                    int temp = (Integer.parseInt(penBlue.getText().toString()));
                    temp += 1;
                    String score = Integer.toString(temp);
                    penBlue.setText(score);
                }
            }
        }
    }

    void increaseScore(int x, String s) {
        if (!DQ) {
            int temp;
            String score;
            //increment red's score
            if (s.equals("redScore")) {
                temp = (Integer.parseInt(redScore.getText().toString()));
                temp += x;
                score = Integer.toString(temp);
                redStack.push(x);
                redScore.setText(score);
            }
            //increment blue's score
            else if (s.equals("blueScore")) {
                temp = Integer.parseInt(blueScore.getText().toString());
                temp += x;
                score = Integer.toString(temp);
                blueStack.push(x);
                blueScore.setText(score);
            }
        }
    }

    void undoScore(String s) {
        //Need to push an indentifier onto the stack when +/- adv/pen then check for the identifier here
        int x;
        try {
            if (s.equals("red")) {
                x = redStack.pop();
                if (x > 0) {
                    int temp = (Integer.parseInt(redScore.getText().toString()));
                    temp -= x;
                    String end = Integer.toString(temp);
                    redScore.setText(end);
                    //red Advantage
                } else if (x == -1) {
                    int temp = (Integer.parseInt(advRed.getText().toString()));
                    if (temp > 0) {
                        temp -= 1;
                        String score = Integer.toString(temp);
                        advRed.setText(score);
                    }
                    //red Penalty
                } else if (x == -2) {
                    int temp = (Integer.parseInt(penRed.getText().toString()));
                    if (temp > 0) {
                        temp -= 1;
                        String score = Integer.toString(temp);
                        penRed.setText(score);
                    }
                }else if (x == -3){
                    redScore.setText(Integer.toString(lastScore));
                    DQ = false;
                }
            } else if (s.equals("blue")) {
                x = blueStack.pop();
                if (x > 0) {
                    int temp = (Integer.parseInt(blueScore.getText().toString()));
                    temp -= x;
                    String end = Integer.toString(temp);
                    blueScore.setText(end);
                    //blue Advantage
                } else if (x == -1) {
                    int temp = (Integer.parseInt(advBlue.getText().toString()));
                    if (temp > 0) {
                        temp -= 1;
                        String score = Integer.toString(temp);
                        advBlue.setText(score);
                    }
                    //blue Penalty
                } else if (x == -2) {
                    int temp = (Integer.parseInt(penBlue.getText().toString()));
                    if (temp > 0) {
                        temp -= 1;
                        String score = Integer.toString(temp);
                        penBlue.setText(score);
                    }
                }else if (x == -3){
                    blueScore.setText(Integer.toString(lastScore));
                    DQ = false;
                }
            }
            } catch (EmptyStackException e) {
                System.out.println("Empty Stack!");
            }
    }

    //TIMER FUNCTIONS------------------------------------------------------------------------------
    public void updateTimer(){
        if(!DQ) {
            int min_temp = (int) timeLeftInMilliSeconds / 60000;
            int sec_temp = (int) timeLeftInMilliSeconds % 60000 / 1000;
            String timeLeftText;

            timeLeftText = "" + min_temp;
            timeLeftText += ":";
            if (sec_temp < 10) timeLeftText += "0";
            timeLeftText += sec_temp;
            if (min_temp == 0 && sec_temp == 30) {
                //  playWarning();
            }
            timerText.setText(timeLeftText);
        }
    }
    public void stopTimer(){
        if(!DQ) {
            countDownTimer.cancel();
            timerRunning = false;
        }
    }
    public void start(){
        if(!timerRunning && !DQ){
            startTimer();
        }
    }
    public void pause(){
        if(timerRunning){
            stopTimer();
        }
    }
    public void startTimer() {
        //playFight();
        //stageTV.setText("Roll " + (rolls_counter + 1));
        if (!DQ) {
            countDownTimer = new CountDownTimer(timeLeftInMilliSeconds, 1000) {
                @Override
                public void onTick(long l) {
                    timeLeftInMilliSeconds = l;
                    updateTimer();
                }

                @Override
                public void onFinish() {
                    playEnd();
                    timerText.setText("0:00");
                }
            }.start();
            //countDownStart.setText("PAUSE");
            timerRunning = true;
        }
    }
}
