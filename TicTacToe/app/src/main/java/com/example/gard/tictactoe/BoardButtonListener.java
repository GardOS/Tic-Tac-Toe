package com.example.gard.tictactoe;

import android.util.Log;
import android.view.View;

/**
 * Created by Gard on 29.03.2017.
 */

//Each button will know its own position and pass it to boardLogic when clicked.
public class BoardButtonListener implements View.OnClickListener {
    int row;
    int column;
    BoardLogic boardLogic;

    public BoardButtonListener(int row, int column, BoardLogic boardLogic) {
        this.row = row;
        this.column = column;
        this.boardLogic = boardLogic;
    }

    @Override
    public void onClick(View v) {
        boardLogic.makeMove(row, column);
    }
}
