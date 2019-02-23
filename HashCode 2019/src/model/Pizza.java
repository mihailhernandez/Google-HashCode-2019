package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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

    public void printPizza() {
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                System.out.print(this.pizza[row][col] ? "1" : "0");
            }
            System.out.println();
        }
    }

    public void createSolutionFile() {
        PrintWriter writer = null;
        for (Solution solution : this.solutions) {
            try {
                writer = new PrintWriter("../files/output/solution" +
                        solution.hashCode() + ".txt", StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }

            assert writer != null;
            writer.println(solution.getSlices().size());
            for (Slice slice : solution.getSlices()) {
                writer.println(slice.getStartRowIndex() + " " + slice.getStartColIndex() + " " +
                        slice.getEndRowIndex() + " " + slice.getEndColIndex());
            }
            writer.close();
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getMinIngredientsPerSlice() {
        return minIngredientsPerSlice;
    }

    public void setMinIngredientsPerSlice(int minIngredientsPerSlice) {
        this.minIngredientsPerSlice = minIngredientsPerSlice;
    }

    public int getMaxCellsPerSlice() {
        return maxCellsPerSlice;
    }

    public void setMaxCellsPerSlice(int maxCellsPerSlice) {
        this.maxCellsPerSlice = maxCellsPerSlice;
    }

    public boolean[][] getPizza() {
        return pizza;
    }

    public void setPizza(boolean[][] pizza) {
        this.pizza = pizza;
    }

    public ArrayList<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(ArrayList<Solution> solutions) {
        this.solutions = solutions;
    }
}
