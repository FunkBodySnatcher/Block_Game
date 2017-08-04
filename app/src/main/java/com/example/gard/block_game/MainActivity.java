package com.example.gard.block_game;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Random ran = new Random();

    //Define text
    private TextView startText;
    private TextView scoreTracker;
    private ImageView heart1;
    private ImageView heart2;
    private ImageView heart3;
    private ConstraintLayout cl;
    private View.OnClickListener onClickListener;

    private ArrayList<CustomButton> buttons;
    private int speed;
    private int hp;
    private int score;
    private Double chanceLimit;

    //Ending stuff
    private TextView gameoverText;
    private TextView mainMenuText;
    private TextView tryAgainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cl = (ConstraintLayout) findViewById(R.id.constraintLayout);
        startText = (TextView) findViewById(R.id.startTextView);
        startText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        scoreTracker = (TextView) findViewById(R.id.scoreTrackerTextView);
        heart1 = (ImageView)findViewById(R.id.heart1);
        heart2 = (ImageView)findViewById(R.id.heart2);
        heart3 = (ImageView)findViewById(R.id.heart3);
        gameoverText = (TextView) findViewById(R.id.gameoverText);
        mainMenuText = (TextView) findViewById(R.id.mainMenuText);
        tryAgainText = (TextView) findViewById(R.id.tryAgainText);

        //Hide actionbar. May produce nullPointer....
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Hide status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        cl.setBackgroundColor(Color.LTGRAY);

    }

    private void startGame(){
        speed = 5000;
        hp = 3;
        score = 0;
        chanceLimit = 99.5;
        buttons = new ArrayList<>();

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
                        buttons.remove(button);
                        button.setVisibility(View.GONE);

                        //Set background color of pressed button.
                        ColorDrawable buttonColor = (ColorDrawable) button.getBackground();
                        cl.setBackgroundColor(buttonColor.getColor());
                    }
                }, 500);

                int chance = ran.nextInt(101);
                newFall();
                if (chance > chanceLimit) {
                    newFall();
                }
                incScore();

                if (score % 20 == 0) {
                    if (speed > 2000) {
                        speed-=200;
                    }
                    chanceLimit-=0.2;
                }
            }
        };

        scoreTracker.setText(String.valueOf(score));
        //Hide start text.
        startText.setVisibility(View.INVISIBLE);

        //Show scoreTracker and hearts.
        scoreTracker.setVisibility(View.VISIBLE);
        heart1.setVisibility(View.VISIBLE);
        heart2.setVisibility(View.VISIBLE);
        heart3.setVisibility(View.VISIBLE);

        //Set scoreTracker to front.
        //TODO: Currently working for API >= 21. Fix for API 20 and <
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            scoreTracker.setElevation(2);
            heart1.setElevation(2);
            heart2.setElevation(2);
            heart3.setElevation(2);
        } else {
            //?????? bringtofront fungerer ikke!!!
        }

        newFall();
        newFall();
    }

    private void newFall() {
        final int screenHeight = getResources().getDisplayMetrics().heightPixels;
        final CustomButton button = new CustomButton(this);
        buttons.add(button);
        if (hp > 0) {
            button.setOnClickListener(onClickListener);
        }
        cl.addView(button);
        button.animate()
                .translationY(screenHeight)
                .rotation(1080)
                .setDuration(speed)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        buttons.remove(button);
                        button.setVisibility(View.GONE);
                        takeDmg();
                        if (hp != 0) {
                            newFall();
                        }
                    }
                });
    }

    private void incScore() {
        score++;
        scoreTracker.setText(String.valueOf(score));
    }

    private void takeDmg() {
        hp--;
        switch (hp) {
            case 2:
                heart3.setVisibility(View.INVISIBLE);
                break;
            case 1:
                heart2.setVisibility(View.INVISIBLE);
                break;
            case 0:
                heart1.setVisibility(View.INVISIBLE);
                setEnding();
                break;
            default:
                break;
        }
    }

    private void setEnding() {
        for (CustomButton button : buttons) {
            button.animate().cancel();
        }
        gameoverText.setVisibility(View.VISIBLE);
        mainMenuText.setVisibility(View.VISIBLE);
        tryAgainText.setVisibility(View.VISIBLE);
        tryAgainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (CustomButton button : buttons) {
                    button.setVisibility(View.GONE);
                    buttons.remove(button);
                }
                gameoverText.setVisibility(View.INVISIBLE);
                mainMenuText.setVisibility(View.INVISIBLE);
                tryAgainText.setVisibility(View.INVISIBLE);
                startGame();
            }
        });
    }
}
