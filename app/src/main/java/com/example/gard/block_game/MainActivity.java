package com.example.gard.block_game;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Random ran = new Random();

    //Define text
    private TextView startText;

    private TextView scoreTracker;

    private ConstraintLayout cl;

    private int screenWidth;
    private int screenHeight;

    private View.OnClickListener onClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cl = (ConstraintLayout) findViewById(R.id.constraintLayout);

        //Getting screen size
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        //Hide actionbar. May produce nullPointer....
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Hide status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        //Get text
        startText = (TextView) findViewById(R.id.startTextView);

        scoreTracker = (TextView) findViewById(R.id.scoreTrackerTextView);

        //Set scoretracker to invisible before pressing 'play'.
        scoreTracker.setVisibility(View.INVISIBLE);

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Button button = (Button) v;

                //Disable button, preventing player from clicking button twice in a row.
                button.setEnabled(false);

                //Stop button where it's pressed.
                button.animate().cancel();

                //Scale button
                button.animate().scaleXBy(50).scaleYBy(50).setDuration(500);

                //Wait 0.5 seconds before removing button and setting background color to the color of the pressed button.
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 0.5 seconds

                        //Remove button.
                        button.setVisibility(View.GONE);

                        //Set background color of pressed button.
                        ColorDrawable buttonColor = (ColorDrawable) button.getBackground();
                        cl.setBackgroundColor(buttonColor.getColor());
                    }
                }, 500);

                //button.setVisibility(View.INVISIBLE);

                int nr = ran.nextInt(3);
                switch (nr) {
                    case 0: case 1:
                        newFall();
                        break;
                    case 2:
                        newFall();
                        newFall();
                        break;
                    default:
                        break;
                }
                incScore();
            }
        };
    }

    public void startGame(View view){
        //Hide start text.
        startText.setVisibility(View.INVISIBLE);

        //Show scoreTracker.
        scoreTracker.setVisibility(View.VISIBLE);

        //Set scoreTracker to front.
        //TODO: Currently working for API >= 21. Fix for API 20 and <
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            scoreTracker.setElevation(2);
        } else {
            //?????? bringtofront fungerer ikke!!!
        }

        newFall();
        newFall();
        newFall();
    }

    public void newFall() {
        CustomButton button = new CustomButton(this);
        button.setOnClickListener(onClickListener);
        cl.addView(button);

        int y = -(ran.nextInt(screenHeight)+50);
        button.setY(y);
        button.setX(ran.nextInt(700));

        button.animate()
                .translationYBy(screenHeight + button.getHeight() - y)
                .rotation(1080)
                .setDuration(5000);
    }

    public void incScore() {
        int score = Integer.parseInt(scoreTracker.getText().toString());
        score++;
        scoreTracker.setText(String.valueOf(score));
    }
}
