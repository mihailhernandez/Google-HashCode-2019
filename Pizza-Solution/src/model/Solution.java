package model;

import java.util.ArrayList;
import java.util.Objects;

class Solution {

    private ArrayList<Slice> slices;

    Solution(ArrayList<Slice> slices) {
        this.slices = slices;
    }

    ArrayList<Slice> getSlices() {
        return slices;
    }

    void setSlices(ArrayList<Slice> slices) {
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
