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

            while ((line = reader.readLine()) != null) {
                String[] cells = line.split("");
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

}
