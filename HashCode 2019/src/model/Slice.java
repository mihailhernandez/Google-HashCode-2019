package model;

public class Slice {

    private int startRowIndex;
    private int endRowIndex;
    private int startColIndex;
    private int endColIndex;

    public int getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(int startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public int getEndRowIndex() {
        return endRowIndex;
    }

    public void setEndRowIndex(int endRowIndex) {
        this.endRowIndex = endRowIndex;
    }

    public int getStartColIndex() {
        return startColIndex;
    }

    public void setStartColIndex(int startColIndex) {
        this.startColIndex = startColIndex;
    }

    public int getEndColIndex() {
        return endColIndex;
    }

    public void setEndColIndex(int endColIndex) {
        this.endColIndex = endColIndex;
    }
}
