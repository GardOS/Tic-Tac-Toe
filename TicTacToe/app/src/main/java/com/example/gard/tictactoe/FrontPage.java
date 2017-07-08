package com.example.gard.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

public class FrontPage extends AppCompatActivity {
    private EditText playerXEditText;
    private EditText playerOEditText;
    private Button buttonPlay;
    private Button buttonHighscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        initWidgets();
        initListeners();
    }

    private void initWidgets(){
        playerXEditText = (EditText)findViewById(R.id.playerXEditText);
        playerOEditText = (EditText)findViewById(R.id.playerOEditText);
        buttonPlay = (Button)findViewById(R.id.buttonPlay);
        buttonHighscore = (Button)findViewById(R.id.buttonHighscore);
    }

    private void initListeners() {
        buttonPlay.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (playerXEditText.getText().length() == 0 || playerOEditText.getText().length() == 0){
                    Toast toast = Toast.makeText(v.getContext(), "Invalid username", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Intent intent = new Intent(v.getContext(), GameBoard.class);
                intent.putExtra("playerX", playerXEditText.getText().toString());
                intent.putExtra("playerO", playerOEditText.getText().toString());
                startActivity(intent);
            }
        });

        buttonHighscore.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Highscore.class);
                startActivity(intent);
            }
        });
    }
}
