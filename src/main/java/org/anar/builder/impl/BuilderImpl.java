package org.anar.builder.impl;

import org.anar.builder.Builder;
import org.anar.model.Connection;
import org.anar.model.RoadNetwork;
import org.anar.model.UserException;
import org.anar.model.Vertex;

import java.util.*;

public class BuilderImpl implements Builder {

    @Override
    public Set<Connection> parse(String graph) throws UserException {

        if(graph.isEmpty()) {
            throw new UserException("Task is empty");
        }

        StringTokenizer stringTokenizer = new StringTokenizer(graph.trim(), ",");

        if(stringTokenizer.countTokens() == 0) {
            throw new UserException("Task is provided is wrong format. User comma delimited values");
        }

        Set<Connection> result = new HashSet<>();

        while(stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken().trim();
            if(token.isEmpty()) {
                throw new UserException("One of comma delimited value is empty");
            }
            result.add(new Connection(new Vertex(token.charAt(0)), new Vertex(token.charAt(1)), Integer.valueOf(token.replaceAll("[A-Za-z]", ""))));
        }

        return result;
    }

    @Override
    public Optional<RoadNetwork> build(Set<Connection> connections) {

        RoadNetwork roadNetwork = null;
        Set<Vertex> towns = new HashSet<>();
        Map<Vertex, Set<Vertex>> destinations = new HashMap<>();
        Map<Connection, Integer> distance = new HashMap<>();

        if(!connections.isEmpty()) {
            Iterator<Connection> connectionIterator = connections.iterator();
            while(connectionIterator.hasNext()) {
                Connection connection = connectionIterator.next();
                //Towns set
                towns.add(connection.getDestination());
                towns.add(connection.getSource());
                //Destionation set
                Set<Vertex> destTowns = destinations.get(connection.getSource());
                if(destTowns == null) {
                    destTowns = new HashSet<>();
                    destinations.put(connection.getSource(), destTowns);
                }
                destTowns.add(connection.getDestination());
                //Distance
                distance.put(connection, connection.getDistance());
            }
            roadNetwork = new RoadNetwork(towns, destinations, distance);

        }
        return Optional.ofNullable(roadNetwork);

    }
}
