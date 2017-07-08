package com.example.gard.tictactoe;

import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Gard on 29.03.2017.
 */

public class BoardLogic {
    private char[][] boardValues;
    private Button[][] boardButtons;
    private Activity activity;
    private int turnCount;
    private TextView playerXTurnIndicator;
    private TextView playerOTurnIndicator;
    private TextView playerXScore;
    private TextView playerOScore;
    private Player playerX;
    private Player playerO;
    private DBHandler dbHandler;

    public BoardLogic(Button[][] boardButtons, Activity activity) {
        this.boardButtons = boardButtons;
        this.boardValues = new char[3][3];
        this.activity = activity;
        playerXTurnIndicator = (TextView)activity.findViewById(R.id.playerXTurnIndicator);
        playerOTurnIndicator = (TextView)activity.findViewById(R.id.playerOTurnIndicator);
        playerXScore = (TextView)activity.findViewById(R.id.playerXScore);
        playerOScore = (TextView)activity.findViewById(R.id.playerOScore);
        playerX = new Player(activity.getIntent().getStringExtra("playerX") , 'X');
        playerO = new Player(activity.getIntent().getStringExtra("playerO") , 'O');
        dbHandler = new DBHandler(activity.getApplicationContext());
    }

    public void makeMove(int row, int column){
        turnCount++;
        if(turnCount % 2 == 1){
            boardValues[row][column] = 'X';
            boardButtons[row][column].setTextColor(activity.getApplicationContext().getColor(R.color.colorBlue));
            boardButtons[row][column].setText("X");
            playerXTurnIndicator.setText(" ");
            playerOTurnIndicator.setText("O");
            checkBoard(playerX.getPlayerType());
        } else {
            boardValues[row][column] = 'O';
            boardButtons[row][column].setTextColor(activity.getApplicationContext().getColor(R.color.colorRed));
            boardButtons[row][column].setText("O");
            playerXTurnIndicator.setText("X");
            playerOTurnIndicator.setText(" ");
            checkBoard(playerO.getPlayerType());
        }
        boardButtons[row][column].setEnabled(false);
    }

    public void resetBoard(){
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                boardButtons[i][j].setText("");
                boardButtons[i][j].setEnabled(true);
                boardValues[i][j] = ' ';
            }
        }
        playerXTurnIndicator.setText("X");
        playerOTurnIndicator.setText(" ");
        turnCount = 0;
    }

    private void gameOver(char player){
        if (player == 'X'){
            Toast toast = Toast.makeText(activity.getApplicationContext(), playerX.getUsername() + " has won!", Toast.LENGTH_SHORT);
            toast.show();
            playerXScore.setText("" + playerX.increaseAndReturnScore());
            if(dbHandler.checkIfTopTen(playerX)){
                playerX = dbHandler.addAndUpdatePlayer(playerX);
            }
        } else if (player == 'O'){
            Toast toast = Toast.makeText(activity.getApplicationContext(), playerO.getUsername() + " has won!", Toast.LENGTH_SHORT);
            toast.show();
            playerOScore.setText("" + playerO.increaseAndReturnScore());
            if(dbHandler.checkIfTopTen(playerO)){
                playerO = dbHandler.addAndUpdatePlayer(playerO);
            }
        } else {
            Toast toast = Toast.makeText(activity.getApplicationContext(), "Tie!", Toast.LENGTH_SHORT);
            toast.show();
        }
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                boardButtons[i][j].setEnabled(false);
            }
        }
    }

    private void checkBoard(char playerType){
        if (turnCount < 5){ //Impossible to win within 5 moves
            return;
        }

        if (boardValues[0][0] == playerType && boardValues[0][1] == playerType && boardValues[0][2] == playerType || // Row 1
            boardValues[1][0] == playerType && boardValues[1][1] == playerType && boardValues[1][2] == playerType || // Row 2
            boardValues[2][0] == playerType && boardValues[2][1] == playerType && boardValues[2][2] == playerType || // Row 3
            boardValues[0][0] == playerType && boardValues[1][0] == playerType && boardValues[2][0] == playerType || // Column 1
            boardValues[0][1] == playerType && boardValues[1][1] == playerType && boardValues[2][1] == playerType || // Column 2
            boardValues[0][2] == playerType && boardValues[1][2] == playerType && boardValues[2][2] == playerType || // Column 3
            boardValues[0][0] == playerType && boardValues[1][1] == playerType && boardValues[2][2] == playerType || // \ Diagonal
            boardValues[2][0] == playerType && boardValues[1][1] == playerType && boardValues[0][2] == playerType)   // / Diagonal
        {
            gameOver(playerType);
        } else if (turnCount >= 9){
            gameOver('T'); //T for tie. Parameter dont accept null
        }
    }

}
