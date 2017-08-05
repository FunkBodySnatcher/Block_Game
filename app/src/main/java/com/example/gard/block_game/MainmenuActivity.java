package com.example.gard.block_game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainmenuActivity extends AppCompatActivity {
    private TextView playText;
    private TextView highscoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        highscoreText = (TextView) findViewById(R.id.highscoreText);

        //Get score from MainActivity
        Intent intent = getIntent();
        int highscore = intent.getIntExtra("highscore", 0);

        //Set the highscore
        highscoreText.setText("Highscore: " + String.valueOf(highscore));

        //Hide actionbar. May produce nullPointer....
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Hide status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        playText = (TextView) findViewById(R.id.playText);
        playText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
