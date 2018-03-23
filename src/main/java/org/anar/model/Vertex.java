package org.anar.model;

public class Vertex {

    private char name;



    public Vertex(char name) {
        this.name = name;
    }

    public Vertex(String name) {
        this.name = name.charAt(0);
    }

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        return name == vertex.name;
    }

    @Override
    public int hashCode() {
        return ((int) name * 13);
    }
}
