package model;

class Slice {

    private int startRowIndex;
    private int endRowIndex;
    private int startColIndex;
    private int endColIndex;

    Slice(int startRowIndex, int endRowIndex, int startColIndex, int endColIndex) {
        this.startRowIndex = startRowIndex;
        this.endRowIndex = endRowIndex;
        this.startColIndex = startColIndex;
        this.endColIndex = endColIndex;
    }

    int getStartRowIndex() {
        return startRowIndex;
    }

    void setStartRowIndex(int startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    int getEndRowIndex() {
        return endRowIndex;
    }

    void setEndRowIndex(int endRowIndex) {
        this.endRowIndex = endRowIndex;
    }

    int getStartColIndex() {
        return startColIndex;
    }

    void setStartColIndex(int startColIndex) {
        this.startColIndex = startColIndex;
    }

    int getEndColIndex() {
        return endColIndex;
    }

    void setEndColIndex(int endColIndex) {
        this.endColIndex = endColIndex;
    }
}
