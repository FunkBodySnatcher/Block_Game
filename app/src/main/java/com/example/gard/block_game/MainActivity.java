package com.example.gard.block_game;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

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
                Button button = (Button) v;

                ColorDrawable buttonColor = (ColorDrawable) button.getBackground();
                cl.setBackgroundColor(buttonColor.getColor());

                button.setVisibility(View.INVISIBLE);
                newFall();
            }
        };
    }

    public void startGame(View view){

        startText.setText("");
        newFall();
        newFall();
        newFall();
    }

    public void newFall() {
        Button button = new Button(this);
        button.setBackgroundColor(Color.rgb(ran.nextInt(256),ran.nextInt(256),ran.nextInt(256)));
        button.setOnClickListener(onClickListener);
        cl.addView(button);

        //TODO: I forhold te screensize
        int y = -(ran.nextInt(200)+50);
        button.setY(y);
        button.setText(String.valueOf(y));
        button.setX(ran.nextInt(700));

        button.animate().translationYBy(1500f).rotation(1080).setDuration(5000);
    }



}
