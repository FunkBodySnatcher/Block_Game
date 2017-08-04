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
    private int index;

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

        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(30);
        shape.setColor(color);
        shape.setSize(screenWidth/5, screenWidth/4);
        setStroke(10, Color.BLACK);
        setBackgroundDrawable(shape);

        int y = -(ran.nextInt(screenHeight+100)+screenWidth/5);
        setY(y);
        setX(ran.nextInt(screenWidth-280));
        setRotation(ran.nextInt(90));
    }

    public void setStroke(int nr, int color) {
        shape.setStroke(nr, color);
    }

    public int getColor() {
        return color;
    }

    public void setIndex(int newIndex) {
        index = newIndex;
    }

    public int getIndex() {
        return index;
    }
}
