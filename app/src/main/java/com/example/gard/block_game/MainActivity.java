package com.example.gard.block_game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Random ran = new Random();

    //Define text
    private TextView scoreTracker;
    private ImageView heart1;
    private ImageView heart2;
    private ImageView heart3;
    private ConstraintLayout cl;
    private View.OnClickListener onClickListener;
    private TextView countdown;

    private int speed;
    private int hp;
    private int score;
    private int highscore;
    private Double chanceLimit;
    private Handler handler = new Handler();

    //Ending stuff
    private TextView gameoverText;
    private TextView mainMenuText;
    private TextView tryAgainText;

    TextView testText; //REMOVE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testText = (TextView) findViewById(R.id.testText); //REMOVE
        cl = (ConstraintLayout) findViewById(R.id.constraintLayout);
        scoreTracker = (TextView) findViewById(R.id.scoreTrackerTextView);
        heart1 = (ImageView)findViewById(R.id.heart1);
        heart2 = (ImageView)findViewById(R.id.heart2);
        heart3 = (ImageView)findViewById(R.id.heart3);
        gameoverText = (TextView) findViewById(R.id.gameoverText);
        mainMenuText = (TextView) findViewById(R.id.mainMenuText);
        tryAgainText = (TextView) findViewById(R.id.tryAgainText);
        countdown = (TextView) findViewById(R.id.countdownText);

        //Hide actionbar. May produce nullPointer....
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Hide status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        cl.setBackgroundColor(Color.LTGRAY);

        countdown();
        handler.postDelayed(new Runnable() {
            public void run() {
                startGame();
            }
        }, 2400);
    }

    private void startGame(){
        speed = 5000;
        hp = 3;
        score = 0;
        chanceLimit = 99.5;

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomButton button = (CustomButton) v;

                button.setStroke(0, Color.BLACK);

                //Disable button, preventing player from clicking button twice in a row.
                button.setEnabled(false);

                //Stop button where it's pressed.
                button.animate().cancel();

                //Scale button
                button.animate().scaleXBy(50).scaleYBy(50).setDuration(500);

//                Wait 0.5 seconds before removing button and setting background color to the color of the pressed button.
                newFall();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 0.5 seconds
                        //Remove button.
                        button.setVisibility(View.GONE);

                        //Set background color of pressed button.
                        cl.setBackgroundColor(button.getColor());
                    }
                }, 500);

                int chance = ran.nextInt(101);
                if (chance > chanceLimit) {
                    newFall();
                }
                incScore();

                if (score % 20 == 0) {
                    if (speed > 2000) {
                        speed-=350;
                    }
                    chanceLimit-=0.4;
                }
            }
        };

        scoreTracker.setText(String.valueOf(score));
        newFall();
        newFall();
    }

    private void newFall() {
        final int screenHeight = getResources().getDisplayMetrics().heightPixels;
        final CustomButton button = new CustomButton(this);
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
                        button.setVisibility(View.GONE);
                        takeDmg();
                        if (hp != 0) {
                            newFall();
                        }
                    }
                });
    }

    //inc score
    private void incScore() {
        if (hp != 0) {
            score++;
            scoreTracker.setText(String.valueOf(score));
        }
    }

    private void takeDmg() {
        hp--;
        switch (hp) {
            case 2:
//                heart3.setAlpha(0.1f);
                heart3.setImageResource(R.drawable.blackheart);
                break;
            case 1:
//                heart2.setAlpha(0.1f);
                heart2.setImageResource(R.drawable.blackheart);
                break;
            case 0:
//                heart1.setAlpha(0.1f);
                heart1.setImageResource(R.drawable.blackheart);
                setEnding();
                break;
            default:
                break;
        }
    }

    private void setEnding() {

        gameoverText.setVisibility(View.VISIBLE);
        mainMenuText.setVisibility(View.VISIBLE);
        tryAgainText.setVisibility(View.VISIBLE);

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        highscore = prefs.getInt("score", 0);

        if (highscore < score) {

            highscore = score;
            prefs.edit().putInt("score", highscore).apply();

        }

        tryAgainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

        mainMenuText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainmenuActivity.class);

                //Make score info available to be reached by other activities.
                intent.putExtra("highscore", highscore);
                startActivity(intent);

            }
        });
    }

    private void countdown() {
        countdown.setVisibility(View.VISIBLE);
        countdown.animate().scaleXBy(-1f).scaleYBy(-1f).setDuration(0).withEndAction(new Runnable() {
            @Override
            public void run() {
                countdown.animate().scaleXBy(1f).scaleYBy(1f).setDuration(400).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        countdown.animate().scaleXBy(-1f).scaleYBy(-1f).setDuration(400).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                countdown.setText(String.valueOf(2));
                                countdown.animate().scaleXBy(1f).scaleYBy(1f).setDuration(400).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        countdown.animate().scaleXBy(-1f).scaleYBy(-1f).setDuration(400).withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                countdown.setText(String.valueOf(1));
                                                countdown.animate().scaleXBy(1f).scaleYBy(1f).setDuration(400).withEndAction(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        countdown.animate().scaleXBy(-1f).scaleYBy(-1f).setDuration(400).withEndAction(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                countdown.setVisibility(View.INVISIBLE);
                                                            }
                                                        });
                                                    }
                                                });

                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
}
