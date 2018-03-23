package org.anar.processor.plugins.impl;

import org.anar.model.Connection;
import org.anar.model.RoadNetwork;
import org.anar.model.UserException;
import org.anar.model.Vertex;
import org.anar.processor.plugins.DistanceFinder;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DistanceFinderImpl implements DistanceFinder {

    private RoadNetwork roadNetwork;

    public DistanceFinderImpl(RoadNetwork network) {
        this.roadNetwork = network;
    }

    @Override
    public int calc(List<Character> route) throws UserException {

        int compound = 0;
        Map<Vertex, Set<Vertex>> dest = roadNetwork.getDestinations();
        Map<Connection, Integer> dist = roadNetwork.getDistances();

        for(int i = 0; i < route.size() - 1; i++) {
            Vertex town = new Vertex(route.get(i));
            Set<Vertex> neighboors = dest.get(town);
            if(neighboors != null) {
                Vertex a2b = new Vertex(route.get(i + 1));
                if(neighboors.contains(a2b)) {
                    Integer dst = dist.get(new Connection(town, a2b, 0));
                    compound += dst != null ? dst : 0;
                }
                else {
                    compound = 0;
                    throw new UserException("NO SUCH ROUTE");
                }
            }
        }

        return compound;
    }
}
