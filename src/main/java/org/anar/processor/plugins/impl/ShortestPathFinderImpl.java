package org.anar.processor.plugins.impl;

import org.anar.model.Connection;
import org.anar.model.RoadNetwork;
import org.anar.model.Vertex;
import org.anar.processor.plugins.ShortestPathFinder;

import java.util.*;

/**
 * Created by imac on 3/23/18.
 */
public class ShortestPathFinderImpl implements ShortestPathFinder{

    final Map<Vertex, Set<Vertex>> destinations;
    final Map<Connection, Integer> routeLength;
    final Map<Vertex, Integer> distances = new HashMap<>();
    final Map<Vertex, Vertex> previous = new HashMap<>();
    final List<Vertex> queue = new ArrayList<>();

    public ShortestPathFinderImpl(RoadNetwork roadNetwork) {
        this.destinations = roadNetwork.getDestinations();
        this.routeLength = roadNetwork.getDistances();
        for (Vertex town : roadNetwork.getTowns()) {
            this.distances.put(town, Integer.MAX_VALUE);
            this.previous.put(town, null);
            this.queue.add(town);
        }

    }

    @Override
    public Map<Vertex, Integer> calc(Vertex source) {

        //Mark source as minimum in order algorithm start from source
        this.distances.put(source, 0);

        while(queue.size() != 0) {

            Vertex minimal = this.min();
            if(minimal == null) break;
            Integer minimalsDist = this.distances.get(minimal);
            if(minimalsDist != null && minimalsDist == Integer.MAX_VALUE)
                break;

            Set<Vertex> neighbours = this.destinations.get(minimal);
            if(neighbours == null || neighbours.size() == 0) {
                continue;
            }

            for(Vertex neighbour: neighbours) {
                int neiDist = this.routeLength.get(new Connection(minimal, neighbour, 0));
                neiDist = neiDist + (this.distances.get(minimal) != null ? this.distances.get(minimal) : 0);
                this.distances.put(neighbour, neiDist);
            }
            queue.remove(minimal);
        }

        return this.distances;

    }

    private Vertex min() {
        Vertex vmin = null;
        int vsize = Integer.MAX_VALUE;
        for(Vertex vv: this.queue) {
            Integer vvsize = this.distances.get(vv);
            if(vvsize != null && vvsize < vsize) {
                vsize = vvsize;
                vmin = vv;
            }
        }
        return vmin;
    }



}
