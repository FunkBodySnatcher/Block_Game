package com.example.gard.block_game;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Random ran = new Random();

    //Define text
    private TextView startText;
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
                newFall();
            }
        };
    }

    public void startGame(View view){

        startText.setVisibility(View.INVISIBLE);
        newFall();
        newFall();
        newFall();
    }

    public void newFall() {

        Button button = new Button(this);
        button.setBackgroundColor(Color.rgb(ran.nextInt(256),ran.nextInt(256),ran.nextInt(256)));
        button.setOnClickListener(onClickListener);
        cl.addView(button);

        int y = -(ran.nextInt(screenHeight)+50);
        button.setY(y);
        //button.setText(String.valueOf(y));
        button.setX(ran.nextInt(700));

        button.animate().translationYBy(screenHeight + button.getHeight() - y).rotation(1080).setDuration(5000);
    }

}
