package com.example.Sudoku.db;

/**
 * Created by thomas on 04/03/14.
 */
public class Grid {

    private int id;
    private String grid;
    private int difficulty;

	public Grid(String grid, int difficulty) {
		this.grid = grid;
		this.difficulty = difficulty;
	}

	public Grid() {
    }

    public float getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
}
