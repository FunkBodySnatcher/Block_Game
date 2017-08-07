package com.example.gard.block_game;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import java.util.Random;

public class HeartPower extends AppCompatImageView {
    private int screenWidth;
    private int screenHeight;
    private Random ran = new Random();

    public HeartPower(Context context) {
        super(context);
        init();
    }

    public HeartPower(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeartPower(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        setBackgroundResource(R.drawable.heart);
        setLayoutParams(new ConstraintLayout.LayoutParams((screenWidth/5), (screenWidth/5)));
    }

    public void setPos() {
        setY(-(screenWidth/5));
        setX(ran.nextInt(screenWidth - (screenWidth/5)));
    }

    public void tutorialHeart(int x, int y) {
        setX(x);
        setY(y);
    }
}
