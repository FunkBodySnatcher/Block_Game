package com.example.gard.block_game;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(30);
        int color = Color.rgb(ran.nextInt(256),ran.nextInt(256),ran.nextInt(256));
        shape.setColor(color);
        setTag(color);
        shape.setSize(screenWidth/5, screenWidth/4);
        shape.setStroke(1, Color.BLACK);
        setBackgroundDrawable(shape);

//        setBackgroundColor(Color.rgb(ran.nextInt(256),ran.nextInt(256),ran.nextInt(256)));
//        setLayoutParams(new ConstraintLayout.LayoutParams(screenWidth/5, screenWidth/5));

        int y = -(ran.nextInt(screenHeight)+40);
        setY(y);
        setX(ran.nextInt(screenWidth-280));
        setRotation(ran.nextInt(90));
    }
}
