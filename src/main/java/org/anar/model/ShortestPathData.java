package org.anar.model;

public class ShortestPathData {
    private int distance;
    private Vertex previous;

    public ShortestPathData(int distance, Vertex previous) {
        this.distance = distance;
        this.previous = previous;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Vertex getPrevious() {
        return previous;
    }

    public void setPrevious(Vertex previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return "ShortestPathData{" +
                "distance=" + distance +
                ", previous=" + previous +
                '}';
    }
}
