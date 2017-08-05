package com.example.gard.block_game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainmenuActivity extends AppCompatActivity {
    private TextView playText;
    private TextView highscoreText;

    private int screenWidth;
    private int screenHeight;

    private Handler handler = new Handler();
    private ConstraintLayout cl;
    private Random ran = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        cl = (ConstraintLayout) findViewById(R.id.constraintLayoutMenu);
        highscoreText = (TextView) findViewById(R.id.highscoreText);

        screenHeight = getResources().getDisplayMetrics().heightPixels;
        screenWidth = getResources().getDisplayMetrics().widthPixels;

        //Get score from MainActivity
        Intent intent = getIntent();
        int highscore = intent.getIntExtra("highscore", 0);

        //Set the highscore
        highscoreText.setText("Highscore: " + String.valueOf(highscore));

        //Hide actionbar. May produce nullPointer....
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Hide status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        playText = (TextView) findViewById(R.id.playText);
        playText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
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
                        .rotation(1080)
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
}
