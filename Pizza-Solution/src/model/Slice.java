package model;

import java.util.Comparator;
import java.util.Objects;

class Slice implements Comparable {

    private int mushrooms;
    private int tomatoes;
    private int startRowIndex;
    private int endRowIndex;
    private int startColIndex;
    private int endColIndex;
    private int size;


    Slice(int startRowIndex, int startColIndex, int endRowIndex,
          int endColIndex, int mushrooms, int tomatoes) {
        this.startRowIndex = startRowIndex;
        this.endRowIndex = endRowIndex;
        this.startColIndex = startColIndex;
        this.endColIndex = endColIndex;
        this.mushrooms = mushrooms;
        this.tomatoes = tomatoes;
        this.size = (endRowIndex - startRowIndex) * (endColIndex - startColIndex);
    }

    int getSize() {
        return size;
    }

    void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slice slice = (Slice) o;
        return startRowIndex == slice.startRowIndex &&
                endRowIndex == slice.endRowIndex &&
                startColIndex == slice.startColIndex &&
                endColIndex == slice.endColIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mushrooms, tomatoes, startRowIndex, endRowIndex, startColIndex, endColIndex, size);
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

    int getMushrooms() {
        return mushrooms;
    }

    void setMushrooms(int mushrooms) {
        this.mushrooms = mushrooms;
    }

    int getTomatoes() {
        return tomatoes;
    }

    void setTomatoes(int tomatoes) {
        this.tomatoes = tomatoes;
    }

    @Override
    public int compareTo(Object o) {
        Slice slice = null;
        if (o instanceof Slice) {
            slice = (Slice) o;
        }
        if (Pizza.lessOf) {
            return this.mushrooms <= slice.getMushrooms()
                    ? slice.getMushrooms() - this.mushrooms
                    : this.mushrooms - slice.getMushrooms();
        } else {
            return this.tomatoes <= slice.getTomatoes()
                    ? slice.getTomatoes() - this.tomatoes
                    : this.tomatoes - slice.getTomatoes();
        }
    }

    @Override
    public String toString() {
        return "Slice{" +
                "mushrooms=" + mushrooms +
                ", tomatoes=" + tomatoes +
                ", startRowIndex=" + startRowIndex +
                ", endRowIndex=" + endRowIndex +
                ", startColIndex=" + startColIndex +
                ", endColIndex=" + endColIndex +
                ", size=" + size +
                '}';
    }
}
