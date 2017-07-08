package com.example.gard.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameBoard extends AppCompatActivity {
    private BoardLogic boardLogic;
    private TextView playerXName;
    private TextView playerOName;
    private Button buttonBack;
    private Button buttonNewGame;
    private Button[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        buttons = new Button[3][3];
        boardLogic = new BoardLogic(buttons, this);

        initWidgets();
        initListeners();
        boardLogic.resetBoard();
    }

    private void initWidgets(){
        playerXName = (TextView)findViewById(R.id.playerXName);
        playerOName = (TextView)findViewById(R.id.playerOName);
        buttonBack = (Button)findViewById(R.id.buttonBack);
        buttonNewGame = (Button)findViewById(R.id.buttonNewGame);

        int buttonCount = 0;
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                buttonCount++;
                int resourceId = getResources().getIdentifier("button" + buttonCount, "id", getPackageName());
                buttons[i][j] = (Button)findViewById(resourceId);
            }
        }

        Intent intent = getIntent();
        playerXName.setText(intent.getStringExtra("playerX"));
        playerOName.setText(intent.getStringExtra("playerO"));
    }

    private void initListeners() {
        buttonBack.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FrontPage.class);
                startActivity(intent);
            }
        });

        buttonNewGame.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                boardLogic.resetBoard();
            }
        });

        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                buttons[i][j].setOnClickListener(new BoardButtonListener(i, j, boardLogic));
            }
        }
    }
}