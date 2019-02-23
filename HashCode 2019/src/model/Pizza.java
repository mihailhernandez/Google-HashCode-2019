package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Pizza {

    private int rows;
    private int cols;
    private int minIngredientsPerSlice;
    private int maxCellsPerSlice;
    private boolean[][] pizza;
    private ArrayList<Solution> solutions;

    public Pizza(String pizzaFile) {
        try {
            FileInputStream inputStream = new FileInputStream(pizzaFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            line = reader.readLine();
            String[] args = line.split(" ");
            this.rows = Integer.parseInt(args[0]);
            this.cols = Integer.parseInt(args[1]);
            this.minIngredientsPerSlice = Integer.parseInt(args[2]);
            this.maxCellsPerSlice = Integer.parseInt(args[3]);

            this.pizza = new boolean[rows][cols];

            for (int row = 0, col = 0; (line = reader.readLine()) != null; row++) {
                String[] cells = line.split("");
                for (String str : cells) {
                    this.pizza[row][col++] = str.equals("M");
                }
                col = 0;
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    // Implementing Misho's greedy idea
    public void solve() {
        int maxCellsRows = this.maxCellsPerSlice > this.rows ? this.rows : this.maxCellsPerSlice;
        int maxCellsColumns = this.maxCellsPerSlice > this.cols ? this.cols: this.maxCellsPerSlice;

        int tomatoesCountRow = 0;
        int mushroomsCountRow = 0;
        for (int i = 0; i < maxCellsRows; i++) {
            if (this.pizza[i][0]) {
                mushroomsCountRow++;
            } else {
                tomatoesCountRow++;
            }
        }

        int tomatoesCountColumn = 0;
        int mushroomsCountColumn = 0;
        for (int i = 0; i < maxCellsColumns; i++) {
            if (this.pizza[0][i]) {
                mushroomsCountRow++;
            } else {
                tomatoesCountRow++;
            }
        }

        // TODO: Calculate pizza weight and get the better one
    }

    public void printPizza() {

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                System.out.print(this.pizza[row][col] ? "1" : "0");
            }
            System.out.println();
        }
    }

}
