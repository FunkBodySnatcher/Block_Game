package com.example.gard.block_game;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import java.util.Random;

public class CustomButton extends AppCompatButton {
    private Random ran = new Random();

    public CustomButton(Context context) {
        super(context);
        styleButton();
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        styleButton();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        styleButton();
    }

    private void styleButton() {
        final int screenWidth = getResources().getDisplayMetrics().widthPixels;
        final int screenHeight = getResources().getDisplayMetrics().heightPixels;

        setBackgroundColor(Color.rgb(ran.nextInt(256),ran.nextInt(256),ran.nextInt(256)));
        setLayoutParams(new ConstraintLayout.LayoutParams(270, 270));

        int y = -(ran.nextInt(screenHeight)+20);
        setY(y);
        setX(ran.nextInt(screenWidth-getWidth()-100));
        setRotation(ran.nextInt(90));
    }
}
