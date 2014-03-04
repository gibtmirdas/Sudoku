package com.example.Sudoku.db;

/**
 * Created by thomas on 04/03/14.
 */
public class HighScore {

    private int id;
    private float score;
    private String userName;

    public HighScore(float score, String userName) {
        this.score = score;
        this.userName = userName;
    }

    public HighScore() {
    }

    public float getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
