package com.example.gard.block_game;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class TutorialActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private int screenWidth;
    private int screenHeight;
    private ConstraintLayout cl;
    private ConstraintSet clSet;
    private TextView tutorialText;
    private TextView scoreText;
    private TextView heartsText;
    private TextView scoreTracker;
    private ImageView heart1;
    private ImageView heart2;
    private ImageView heart3;
    private Random ran = new Random();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_tutorial);
        final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/sometimelater.otf");

        cl = (ConstraintLayout) findViewById(R.id.constraintLayoutTutorial);
        clSet = new ConstraintSet();
        tutorialText = (TextView) findViewById(R.id.tutorialText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        heartsText = (TextView) findViewById(R.id.heartsText);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        scoreTracker = (TextView) findViewById(R.id.scoreTrackerTextView);
        heart1 = (ImageView) findViewById(R.id.heart1);
        heart2 = (ImageView) findViewById(R.id.heart2);
        heart3 = (ImageView) findViewById(R.id.heart3);

        //Hide actionbar. May produce nullPointer....
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Hide status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        tutorialText.animate().scaleXBy(-1f).scaleYBy(-1f).setDuration(0);
        scoreText.animate().scaleXBy(-1f).scaleYBy(-1f).setDuration(0);
        heartsText.animate().scaleXBy(-1f).scaleYBy(-1f).setDuration(0);
        scoreTracker.animate().scaleXBy(-1f).scaleYBy(-1f).setDuration(0);
        heart1.animate().scaleXBy(-1f).scaleYBy(-1f).setDuration(0);
        heart2.animate().scaleXBy(-1f).scaleYBy(-1f).setDuration(0);
        heart3.animate().scaleXBy(-1f).scaleYBy(-1f).setDuration(0);

        tutorialText.setTypeface(typeface);
        scoreText.setTypeface(typeface);
        heartsText.setTypeface(typeface);
        scoreTracker.setTypeface(typeface);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textAnimationToScreen(tutorialText);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textAnimationFromScreen(tutorialText);
                        final CustomButton button = new CustomButton(getApplicationContext());
                        button.styleButton((screenWidth / 2) - ((screenWidth / 4) / 2), -400, 0, Color.rgb(230, 30, 100));
                        cl.addView(button);
                        button.animate()
                                .translationY((screenHeight / 2) - ((screenWidth / 4) / 2))
                                .rotationBy(720)
                                .setDuration(2500)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        scoreText.setText("Hold up. This is a Drizzle.");
                                        textAnimationToScreen(scoreText);
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                textAnimationFromScreen(scoreText);
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        scoreText.setText("This is your current score...");
                                                        textAnimationToScreen(scoreText);
                                                        textAnimationToScreen(scoreTracker);
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                heartsText.setText("...and this ...\nis your health");
                                                                textAnimationToScreen(heart1);
                                                                textAnimationToScreen(heart2);
                                                                textAnimationToScreen(heart3);
                                                                textAnimationToScreen(heartsText);
                                                                handler.postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        textAnimationFromScreen(heartsText);
                                                                        textAnimationFromScreen(scoreText);
                                                                        handler.postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                scoreText.setText("Tap the Drizzle to increase your score. Neat.");
                                                                                textAnimationToScreen(scoreText);
                                                                                handler.postDelayed(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        button.setOnClickListener(new View.OnClickListener() {
                                                                                            @Override
                                                                                            public void onClick(View v) {
                                                                                                scoreTracker.setText("1");
                                                                                                scoreText.setVisibility(View.INVISIBLE);
                                                                                                button.animate().scaleXBy(20).scaleYBy(20).setDuration(300).withEndAction(new Runnable() {
                                                                                                    @Override
                                                                                                    public void run() {
                                                                                                        button.setVisibility(View.GONE);
                                                                                                        cl.setBackgroundColor(button.getColor());
                                                                                                        scoreText.setText("OOhShi!, Don't get distracted!\nThe background tends to change");
                                                                                                        scoreText.setVisibility(View.VISIBLE);
                                                                                                        handler.postDelayed(new Runnable() {
                                                                                                            @Override
                                                                                                            public void run() {
                                                                                                                textAnimationFromScreen(scoreText);
                                                                                                                handler.postDelayed(new Runnable() {
                                                                                                                    @Override
                                                                                                                    public void run() {
                                                                                                                        scoreText.setText("If a block falls off the screen...");
                                                                                                                        textAnimationToScreen(scoreText);
                                                                                                                        final CustomButton button2 = new CustomButton(getApplicationContext());
                                                                                                                        button2.styleButton((screenWidth / 2) - ((screenWidth / 4) / 2), (screenHeight / 2) - ((screenWidth / 4) / 2), 0, Color.rgb(20, 30, 200));
                                                                                                                        cl.addView(button2);
                                                                                                                        handler.postDelayed(new Runnable() {
                                                                                                                            @Override
                                                                                                                            public void run() {
                                                                                                                                button2.animate()
                                                                                                                                        .translationY(screenHeight + screenHeight / 4)
                                                                                                                                        .rotationBy(720)
                                                                                                                                        .setDuration(2500)
                                                                                                                                        .withEndAction(new Runnable() {
                                                                                                                                            @Override
                                                                                                                                            public void run() {
                                                                                                                                                button2.setVisibility(View.GONE);
                                                                                                                                                heartsText.setText("...you will lose \none of your hearts!");
                                                                                                                                                textAnimationToScreen(heartsText);
                                                                                                                                                handler.postDelayed(new Runnable() {
                                                                                                                                                    @Override
                                                                                                                                                    public void run() {
                                                                                                                                                        heart3.setImageResource(R.drawable.blackheart);
                                                                                                                                                        handler.postDelayed(new Runnable() {
                                                                                                                                                            @Override
                                                                                                                                                            public void run() {
                                                                                                                                                                textAnimationFromScreen(heartsText);
                                                                                                                                                                textAnimationFromScreen(scoreText);
                                                                                                                                                                handler.postDelayed(new Runnable() {
                                                                                                                                                                    @Override
                                                                                                                                                                    public void run() {
                                                                                                                                                                        final HeartPower heartPower = new HeartPower(getApplicationContext());
                                                                                                                                                                        heartPower.tutorialHeart(screenWidth / 2 - (screenWidth / 5) / 2, -400);
                                                                                                                                                                        cl.addView(heartPower);
                                                                                                                                                                        heartPower.animate()
                                                                                                                                                                                .translationY(screenHeight / 2 - (screenWidth / 5) / 2)
                                                                                                                                                                                .rotation(720)
                                                                                                                                                                                .setDuration(2000)
                                                                                                                                                                                .withEndAction(new Runnable() {
                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void run() {
                                                                                                                                                                                        scoreText.setText("Tapping a falling heart will restore one of your lost hearts. Incredible!");
                                                                                                                                                                                        textAnimationToScreen(scoreText);
                                                                                                                                                                                        handler.postDelayed(new Runnable() {
                                                                                                                                                                                            @Override
                                                                                                                                                                                            public void run() {
                                                                                                                                                                                                heartPower.setOnClickListener(new View.OnClickListener() {
                                                                                                                                                                                                    @Override
                                                                                                                                                                                                    public void onClick(View v) {
                                                                                                                                                                                                        heartPower.setVisibility(View.GONE);
                                                                                                                                                                                                        heart3.setImageResource(R.drawable.heart);
                                                                                                                                                                                                        textAnimationFromScreen(scoreText);
                                                                                                                                                                                                        handler.postDelayed(new Runnable() {
                                                                                                                                                                                                            @Override
                                                                                                                                                                                                            public void run() {
                                                                                                                                                                                                                tutorialText.setText("You are now ready to play\nHave fun!\n\nMain Menu");
                                                                                                                                                                                                                textAnimationToScreen(tutorialText);
                                                                                                                                                                                                                animationMessage();
                                                                                                                                                                                                                handler.postDelayed(new Runnable() {
                                                                                                                                                                                                                    @Override
                                                                                                                                                                                                                    public void run() {
                                                                                                                                                                                                                        backgroundRain();
                                                                                                                                                                                                                        handler.postDelayed(new Runnable() {
                                                                                                                                                                                                                            @Override
                                                                                                                                                                                                                            public void run() {
                                                                                                                                                                                                                                tutorialText.setOnClickListener(new View.OnClickListener() {
                                                                                                                                                                                                                                    @Override
                                                                                                                                                                                                                                    public void onClick(View v) {
                                                                                                                                                                                                                                        startActivity(new Intent(getApplicationContext(), MainmenuActivity.class));
                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                });
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                        }, 1500);
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                }, 500);
                                                                                                                                                                                                            }
                                                                                                                                                                                                        }, 1500);
                                                                                                                                                                                                    }
                                                                                                                                                                                                });
                                                                                                                                                                                            }
                                                                                                                                                                                        }, 500);
                                                                                                                                                                                    }
                                                                                                                                                                                });
                                                                                                                                                                    }
                                                                                                                                                                }, 400);
                                                                                                                                                            }
                                                                                                                                                        }, 2500);
                                                                                                                                                    }
                                                                                                                                                }, 1000);
                                                                                                                                            }
                                                                                                                                        });
                                                                                                                            }
                                                                                                                        }, 1500);
                                                                                                                    }
                                                                                                                }, 700);
                                                                                                            }
                                                                                                        }, 3000);
                                                                                                    }
                                                                                                });
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                }, 300);
                                                                            }
                                                                        }, 700);
                                                                    }
                                                                }, 3000);
                                                            }
                                                        }, 2000);
                                                    }
                                                }, 700);
                                            }
                                        }, 3000);
                                    }
                                });
                    }
                }, 2500);
            }
        }, 1000);

    }



    private void textAnimationToScreen(View view) {
        view.setVisibility(View.VISIBLE);
        view.animate().scaleXBy(1f).scaleYBy(1f).setDuration(500);
    }

    private void textAnimationFromScreen(View view) {
        view.animate().scaleXBy(-1f).scaleYBy(-1f).setDuration(500);
        view.setVisibility(View.INVISIBLE);
    }

    private void newFall() {
        final CustomButton button = new CustomButton(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cl.addView(button);
                button.animate()
                        .translationY(screenHeight + screenHeight / 5)
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

    private void animationMessage(){

        AlertDialog.Builder alertMessage = new AlertDialog.Builder(TutorialActivity.this);
        alertMessage.setTitle("Animations");
        alertMessage.setMessage(" Hi there! Our game heavily relies on animations, and in order for it to work, you need to have your animations turned on!"+
                "These settings can be located in 'Settings' -> 'Developer settings'. Make sure your animation scaling is set to 1x."+
        "If you do not have the option to select 'Developer options', you are all set!");
        alertMessage.setPositiveButton("GOT IT!", null);
        alertMessage.show();

    }

    private void hideStatusBar(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
