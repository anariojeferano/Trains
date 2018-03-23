package org.anar.processor.plugins.impl;

import org.anar.model.Connection;
import org.anar.model.RoadNetwork;
import org.anar.model.Vertex;
import org.anar.processor.plugins.DeepTraversal;

import java.util.Map;
import java.util.Set;

public class TripsCounterWithConstraints implements DeepTraversal{

    private Map<Vertex, Set<Vertex>> destinations;
    private Map<Connection, Integer> distance;

    public TripsCounterWithConstraints(RoadNetwork roadNetwork) {
        this.destinations = roadNetwork.getDestinations();
        this.distance = roadNetwork.getDistances();
    }

    @Override
    public int countWithStep(Vertex source, Vertex target, int maxSteps) {

        int countAccumulator = 0;

        for (int step = 1; step <= maxSteps; step++) {
            countAccumulator += countPath(source, target, step, 0);
        }

        return countAccumulator;

    }

    @Override
    public int countTillLast(Vertex source, Vertex target, int maxSteps) {
        return countPath(source, target, maxSteps, 0);
    }

    @Override
    public int countTillMax(Vertex source, Vertex target, int maxDistance) {
        return countUntilMax(source, target, maxDistance, 0);
    }

    private int countPath(Vertex source, Vertex target, int maxStepConstraint, int stepper) {
        int accumulator = 0;
        if ((maxStepConstraint == stepper) && (source.equals(target))) {
            return 1;
        }
        if ((maxStepConstraint == stepper) && (!source.equals(target))) {
            return 0;
        }
        Set<Vertex> neighbors = this.destinations.get(source);
        if(neighbors != null && neighbors.size() > 0) {
            for (final Vertex town : neighbors) {
                accumulator += countPath(town, target, maxStepConstraint, stepper + 1);
            }
        }
        else {
            return 0;
        }
        return accumulator;
    }

    private int countUntilMax(
            Vertex source,
            Vertex target,
            int maxDistance,
            int currentDistance) {

        int accumulator = 0;

        if (currentDistance >= maxDistance)
            return 0;

        if ((currentDistance > 0) && (currentDistance < maxDistance) && (source.equals(target)))
            accumulator++;

        Set<Vertex> neighbors = this.destinations.get(source);


        if(neighbors != null && neighbors.size() > 0) {
            for (Vertex neighbor : neighbors) {
                int accumulatorNeighbor = currentDistance + this.distance.get(new Connection(source, neighbor, 0));

                accumulator += countUntilMax(
                        neighbor,
                        target,
                        maxDistance,
                        accumulatorNeighbor);
            }
        }
        else {
            return 0;
        }
        return accumulator;
    }



}
