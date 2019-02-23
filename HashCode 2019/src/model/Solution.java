package model;

import java.util.ArrayList;
import java.util.Objects;

public class Solution {

    private ArrayList<Slice> slices;

    public Solution(ArrayList<Slice> slices) {
        this.slices = slices;
    }

    public ArrayList<Slice> getSlices() {
        return slices;
    }

    public void setSlices(ArrayList<Slice> slices) {
        this.slices = slices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution solution = (Solution) o;
        return slices.equals(solution.slices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slices);
    }
}
