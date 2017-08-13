package com.example.gard.block_game;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

    private int screenWidth;
    private int screenHeight;

    //Ending stuff
    private TextView gameoverText;
    private TextView mainMenuText;
    private TextView tryAgainText;

    private int nrBtns = 3;
    private boolean gameover = false;

    MediaPlayer blockBoop;
    MediaPlayer heartLoss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cl = (ConstraintLayout) findViewById(R.id.constraintLayout);
        scoreTracker = (TextView) findViewById(R.id.scoreTrackerTextView);
        heart1 = (ImageView)findViewById(R.id.heart1);
        heart2 = (ImageView)findViewById(R.id.heart2);
        heart3 = (ImageView)findViewById(R.id.heart3);
        gameoverText = (TextView) findViewById(R.id.gameoverText);
        mainMenuText = (TextView) findViewById(R.id.mainMenuText);
        tryAgainText = (TextView) findViewById(R.id.tryAgainText);
        countdown = (TextView) findViewById(R.id.countdownText);

        blockBoop = MediaPlayer.create(getApplicationContext(), R.raw.bvop);

        heartLoss = MediaPlayer.create(getApplicationContext(), R.raw.bvoplow);

        //Get custom font from assets.
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/sometimelater.otf");

        scoreTracker.setTypeface(typeface);
        gameoverText.setTypeface(typeface);
        mainMenuText.setTypeface(typeface);
        tryAgainText.setTypeface(typeface);
        countdown.setTypeface(typeface);

        screenHeight = getResources().getDisplayMetrics().heightPixels;
        screenWidth = getResources().getDisplayMetrics().widthPixels;

        //Hide actionbar. May produce nullPointer....
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Hide status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        countdown();
        handler.postDelayed(new Runnable() {
            public void run() {
                startGame();
            }
        }, 2200);
    }

    private void startGame(){
        speed = 4800;
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

                //Play sound effect
                playLightBoop();

                //Wait 0.5 seconds before removing button and setting background color to the color of the pressed button.
                newFall();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 0.5 seconds
                        //Remove button.
                        button.setVisibility(View.GONE);

                        //Set background color to color of pressed button.
                        cl.setBackgroundColor(button.getColor());
                    }
                }, 500);

                int chance = ran.nextInt(100)+1;
                if (nrBtns <= 6 && chance > chanceLimit) {
                    newFall();
                    nrBtns++;
                }

                incScore();

                if (score % 20 == 0) {
                    if (score < 100) {
                        speed-=300;
                    } else if (score >= 100 && score < 150) {
                        speed-=200;
                    } else if (score >= 150 && speed > 999) {
                        speed-=100;
                    }
                    chanceLimit-=0.8;
                }
                if ((ran.nextInt(100)+1) > 94 && hp < 3 && score >= 50) {
                    spawnHeartPower();
                }
            }
        };

        scoreTracker.setText(String.valueOf(score));
        newFall();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                newFall();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newFall();
                    }
                }, 1000);
            }
        }, 1000);
    }

    private void newFall() {
        final CustomButton button = new CustomButton(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (hp > 0) {
                    button.setOnClickListener(onClickListener);
                }
                cl.addView(button);
                button.animate()
                        .translationY(screenHeight + screenHeight/5)
                        .rotationBy(1080)
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
        }, ran.nextInt(1001));
    }

    //inc score
    private void incScore() {
        if (!gameover) {
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
                playLowBoop();
                break;
            case 1:
//                heart2.setAlpha(0.1f);
                heart2.setImageResource(R.drawable.blackheart);
                playLowBoop();
                break;
            case 0:
//                heart1.setAlpha(0.1f);
                heart1.setImageResource(R.drawable.blackheart);
                playLowBoop();
                gameover = true;
                setEnding();
                break;
            default:
                break;
        }
    }

    private void setEnding() {

        gameoverText.setVisibility(View.VISIBLE);
        gameoverText.setOnClickListener(null);
        mainMenuText.setVisibility(View.VISIBLE);
        tryAgainText.setVisibility(View.VISIBLE);


        tryAgainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
                playLowBoop();
            }
        });

        mainMenuText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainmenuActivity.class);

                playLowBoop();

                //Make score available for other activities.
                intent.putExtra("SCORE", score);

                startActivity(intent);

            }
        });
    }

    private void countdown() {
        countdown.setVisibility(View.VISIBLE);
        playLowBoop();
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
                                playLowBoop();
                                countdown.animate().scaleXBy(1f).scaleYBy(1f).setDuration(400).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        countdown.animate().scaleXBy(-1f).scaleYBy(-1f).setDuration(400).withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                countdown.setText(String.valueOf(1));
                                                playLowBoop();
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

    private void spawnHeartPower() {
        final HeartPower heartPower = new HeartPower(this);
        cl.addView(heartPower);
        heartPower.setPos();
        heartPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hp > 0) {
                    switch (hp) {
                        case 1:
                            hp++;
                            heart2.setImageResource(R.drawable.heart);
                            heartPower.setVisibility(View.GONE);
                            break;
                        case 2:
                            hp++;
                            heart3.setImageResource(R.drawable.heart);
                            heartPower.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        heartPower.animate()
                .translationY(screenHeight)
                .rotationBy(1080)
                .setDuration(speed-500)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        heartPower.setVisibility(View.GONE);
                    }
                });
    }

    private void playLightBoop(){
        try {
            if(blockBoop.isPlaying()){
                blockBoop.stop();
                blockBoop.release();
                blockBoop = MediaPlayer.create(getApplicationContext(), R.raw.bvop);
            } blockBoop.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
