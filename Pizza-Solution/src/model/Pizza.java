package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;

public class Pizza {

    static boolean lessOf;
    private ArrayList<Integer> maxCellsFactors;
    private int rows;
    private int cols;
    private int minIngredientsPerSlice;
    private int maxCellsPerSlice;
    private int totalMushrooms = 0;
    private int totalTomatoes = 0;
    private boolean[][] pizza;
    private boolean[][] map;
    private Solution solution;

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
            this.map = new boolean[rows][cols];

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

        countIngredients();
        setMaxCellFactors();
        lessOf = totalMushrooms >= totalTomatoes;
    }

    private void setMaxCellFactors() {
        this.maxCellsFactors = new ArrayList<>();
        for (int i = 2; i <= maxCellsPerSlice / 2; i++) {
            if (maxCellsPerSlice % i == 0) {
                this.maxCellsFactors.add(i);
            }
        }
    }

    private void countIngredients() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (pizza[row][col]) {
                    totalMushrooms++;
                } else {
                    totalTomatoes++;
                }
            }
        }
    }

    private int countTomatoes(int startRow, int startCol, int endRow, int endCol) {
        int tomatoes = 0;
        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                if (!pizza[row][col]) {
                    tomatoes++;
                }
            }
        }
        return tomatoes;
    }

    private int countMushrooms(int startRow, int startCol, int endRow, int endCol) {
        int mushrooms = 0;
        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                if (pizza[row][col]) {
                    mushrooms++;
                }
            }
        }
        return mushrooms;
    }

    // Implementing Misho's greedy idea
    public void solve() {
        ArrayList<Slice> solutionSlices = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (map[row][col]) {
                    continue;
                }
                ArrayList<Slice> slices = new ArrayList<>();
                slices.add(solveByCols(row, col));
                slices.add(solveByRows(row, col));
                slices.addAll(solveByDiag(row, col));
                for (Slice s : slices) {
                    System.out.println(s);
                }
                Slice bestSlice = slices.stream().min(Slice::compareTo).get();
                solutionSlices.add(bestSlice);
                fillMap(bestSlice);
            }
        }
        this.solution = new Solution(solutionSlices);
    }

    private void fillMap(Slice slice) {
        for (int row = slice.getStartRowIndex(); row < slice.getEndRowIndex(); row++) {
            for (int col = slice.getStartColIndex(); col < slice.getEndColIndex(); col++) {
                map[row][col] = true;
            }
        }
    }

    private int getEndRow(int startRow, int count) {
        if (count == 0) {
            count = maxCellsPerSlice;
        }
        return count + startRow > this.rows
                ? this.rows - 1
                : count + startRow;
    }

    private int getEndCol(int startCol, int count) {
        if (count == 0) {
            count = maxCellsPerSlice;
        }
        return count + startCol > this.cols
                ? this.cols - 1
                : count + startCol;
    }

    private HashSet<Slice> solveByDiag(int startRow, int startCol) {
        HashSet<Slice> slices = new HashSet<>();
        for (Integer factor : maxCellsFactors) {
            int endRow = getEndRow(startRow, factor);
            int endCol = getEndCol(startCol, maxCellsPerSlice / factor);
            int mushrooms = countMushrooms(startRow, startCol, endRow, endCol);
            int tomatoes = countTomatoes(startRow, startCol, endRow, endCol);
            if (mushrooms >= minIngredientsPerSlice && tomatoes >= minIngredientsPerSlice) {
                slices.add(new Slice(startRow, startCol, endRow, startCol, mushrooms, tomatoes));
            }
            endRow = getEndRow(startRow, maxCellsPerSlice / factor);
            endCol = getEndCol(startCol, factor);
            mushrooms = countMushrooms(startRow, startCol, endRow, endCol);
            tomatoes = countTomatoes(startRow, startCol, endRow, endCol);
            if (mushrooms >= minIngredientsPerSlice && tomatoes >= minIngredientsPerSlice) {
                slices.add(new Slice(startRow, startCol, endRow, startCol, mushrooms, tomatoes));
            }
        }
        return slices;
    }


    private Slice solveByRows(int startRow, int startCol) {
        int endRow = getEndRow(startRow, 0);
        int mushrooms = countMushrooms(startRow, startCol, endRow, startCol);
        int tomatoes = countTomatoes(startRow, startCol, endRow, startCol);
        if (mushrooms >= minIngredientsPerSlice && tomatoes >= minIngredientsPerSlice) {
            return new Slice(startRow, startCol, endRow, startCol, mushrooms, tomatoes);
        }
        return null;
    }

    private Slice solveByCols(int startRow, int startCol) {
        int endCol = getEndCol(startCol, 0);
        int mushrooms = countMushrooms(startRow, startCol, startRow, endCol);
        int tomatoes = countTomatoes(startRow, startCol, startRow, endCol);
        if (mushrooms >= minIngredientsPerSlice && tomatoes >= minIngredientsPerSlice) {
            return new Slice(startRow, startCol, startRow, endCol, mushrooms, tomatoes);
        }
        return null;
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
        try {
            writer = new PrintWriter("HashCode 2019/files/output/solution" +
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

    public Solution getSolutions() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }
}
