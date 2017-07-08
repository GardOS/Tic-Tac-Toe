package com.example.gard.tictactoe;

/**
 * Created by Gard on 29.03.2017.
 */

public class Player {
    private int id;
    private String username;
    private int score;
    private char playerType;

    public Player() {
    }

    public Player(String username, char playerType) {
        this.username = username;
        this.score = 0;
        this.playerType = playerType;
    }

    public int increaseAndReturnScore(){
        this.score++;
        return this.score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public char getPlayerType() {
        return playerType;
    }

    @Override
    public String toString() {
        return score + " victories. Player: " + username;
    }
}
