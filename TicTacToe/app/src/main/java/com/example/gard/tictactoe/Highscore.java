package com.example.gard.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class Highscore extends AppCompatActivity {

    private Button buttonBack;
    private Button buttonReset;
    private ListView highScores;
    private ArrayAdapter<Player> playerAdapter;
    private List<Player> players;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        initWidgets();
        initListeners();

        dbHandler = new DBHandler(this);
        players = dbHandler.getTopTen();
        playerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, players);
        highScores.setAdapter(playerAdapter);
    }

    private void initWidgets(){
        buttonBack = (Button)findViewById(R.id.buttonBack);
        buttonReset = (Button)findViewById(R.id.buttonReset);
        highScores = (ListView)findViewById(R.id.highScores);
    }

    private void initListeners(){
        buttonBack.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FrontPage.class);
                startActivity(intent);
            }
        });

        buttonReset.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                dbHandler.clearDB();
                players.clear();
                playerAdapter.notifyDataSetChanged();
                highScores.setAdapter(playerAdapter);
            }
        });
    }

}
