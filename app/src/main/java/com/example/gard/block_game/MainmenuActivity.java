package com.example.gard.block_game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

public class MainmenuActivity extends AppCompatActivity {
    private TextView titleText;
    private TextView playText;
    private TextView highscoreText;
    private TextView tutorialText;
    private TextView aboutTextView;

    private int screenWidth;
    private int screenHeight;

    private Handler handler = new Handler();
    private ConstraintLayout cl;
    private Random ran = new Random();

    MediaPlayer heartLoss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        //Get custom font from assets.
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/sometimelater.otf");

        cl = (ConstraintLayout) findViewById(R.id.constraintLayoutMenu);

        //Define texts.
        titleText = (TextView) findViewById(R.id.titleText);
        playText = (TextView) findViewById(R.id.playText);
        tutorialText = (TextView) findViewById(R.id.tutorialText);
        aboutTextView = (TextView) findViewById(R.id.aboutTextView);
        highscoreText = (TextView) findViewById(R.id.highscoreText);

        heartLoss = MediaPlayer.create(getApplicationContext(), R.raw.bvoplow);

        //Set custom font to texts.
        titleText.setTypeface(typeface);
        playText.setTypeface(typeface);
        tutorialText.setTypeface(typeface);
        aboutTextView.setTypeface(typeface);
        highscoreText.setTypeface(typeface);

        //If application if opened for the first time, start the tutorial.
        SharedPreferences firstRun = null;
        firstRun = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        if (firstRun.getBoolean("firstRun", true)){

            Intent tutorial = new Intent(getApplicationContext(), TutorialActivity.class);

            startActivity(tutorial);

            firstRun.edit().putBoolean("firstRun", false).apply();

        }


        screenHeight = getResources().getDisplayMetrics().heightPixels;
        screenWidth = getResources().getDisplayMetrics().widthPixels;

        //Get score from MainActivity
        int score = getIntent().getIntExtra("SCORE", 0);

        //Set text for last recorded highscore.
        highscoreText.setText("Highscore: " + score);

        //Use sharedPreferences to save current highscore.
        SharedPreferences settings = getSharedPreferences("HIGHSCORE", Context.MODE_PRIVATE);
        int highscore = settings.getInt("HIGHSCORE", 0);

        //Compare score to highscore in order to determine if there's been a new highscore.
        if (score > highscore){
            highscoreText.setText("Highscore: " + score);

            //If there has, apply new highscore.
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGHSCORE", score);
            editor.apply();

        } else { //If not

            highscoreText.setText("Highscore: " + highscore);

        }

        //Hide actionbar. May produce nullPointer....
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Hide status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        playText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playLowBoop();
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        tutorialText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playLowBoop();
                Intent intent = new Intent(v.getContext(), TutorialActivity.class);
                startActivity(intent);
            }
        });

        aboutTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                playLowBoop();
                Intent intent = new Intent(v.getContext(), AboutActivity.class);
                startActivity(intent);
            }
        });

        backgroundRain();
    }

    private void backgroundRain() {
        newFall();
        newFall();
        newFall();
        newFall();
        newFall();
        newFall();
        newFall();
        newFall();
        newFall();
        newFall();
        newFall();
    }

    private void newFall() {
        final CustomButton button = new CustomButton(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cl.addView(button);
                button.animate()
                        .translationY(screenHeight + screenHeight/5)
                        .rotationBy(1080)
                        .setDuration(ran.nextInt(1600) + 3001)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                button.setVisibility(View.GONE);
                                newFall();
                            }
                        });
            }
        }, ran.nextInt(4001));
    }

    private void playLowBoop(){
        try {
            if(heartLoss.isPlaying()){
                heartLoss.stop();
                heartLoss.release();
                heartLoss = MediaPlayer.create(getApplicationContext(), R.raw.bvoplow);
            } heartLoss.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
