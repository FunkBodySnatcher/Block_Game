package com.example.gard.block_game;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import java.util.Random;

public class CustomButton extends AppCompatButton {
    private Random ran = new Random();
    private GradientDrawable shape = new GradientDrawable();
    private int color = Color.rgb(ran.nextInt(256),ran.nextInt(256),ran.nextInt(256));

    private int screenWidth;
    private int screenHeight;

    public CustomButton(Context context) {
        super(context);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        init();
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(30);
        shape.setColor(color);
        shape.setSize((screenWidth/4), (screenWidth/4));
        shape.setStroke(10, Color.BLACK);
        setBackgroundDrawable(shape);

        int y = -(ran.nextInt(screenHeight+100)+screenWidth/4);
        setY(y);
        setX(ran.nextInt(screenWidth-screenWidth/4));
        setRotation(ran.nextInt(90));

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            setTranslationZ(1);
        }
    }

    public void styleButton(int x, int y, int rotation, int colorSpes) {
        setY(y);
        setX(x);
        setRotation(rotation);
        color = colorSpes;
        shape.setColor(colorSpes);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            setTranslationZ(1);
        }
    }

    public void setStroke(int nr, int color) {
        shape.setStroke(nr, color);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int newColor) {
        shape.setColor(newColor);
        color = newColor;
    }
}
