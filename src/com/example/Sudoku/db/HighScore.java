package com.example.Sudoku.db;

/**
 * Created by thomas on 04/03/14.
 */
public class HighScore {

    private int id;
    private int score;
    private String userName;

    public HighScore(int score, String userName) {
        this.score = score;
        this.userName = userName;
    }

    public HighScore() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
