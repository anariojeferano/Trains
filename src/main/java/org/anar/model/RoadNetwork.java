package org.anar.model;

import java.util.Map;
import java.util.Set;

public class RoadNetwork {

    private Set<Vertex> towns;
    private Map<Vertex, Set<Vertex>> destinations;
    private Map<Connection, Integer> distances;

    public RoadNetwork(Set<Vertex> towns, Map<Vertex, Set<Vertex>> destinations, Map<Connection, Integer> distances) {
        this.towns = towns;
        this.destinations = destinations;
        this.distances = distances;
    }

    public Set<Vertex> getTowns() {
        return towns;
    }

    public void setTowns(Set<Vertex> towns) {
        this.towns = towns;
    }

    public Map<Vertex, Set<Vertex>> getDestinations() {
        return destinations;
    }

    public void setDestinations(Map<Vertex, Set<Vertex>> destinations) {
        this.destinations = destinations;
    }

    public Map<Connection, Integer> getDistances() {
        return distances;
    }

    public void setDistances(Map<Connection, Integer> distances) {
        this.distances = distances;
    }

    @Override
    public String toString() {
        return "RoadNetwork{" +
                "towns=" + towns +
                ", destinations=" + destinations +
                ", distances=" + distances +
                '}';
    }
}
