package org.anar.processor.plugins;

import org.anar.model.Vertex;

import java.util.Map;

public interface ShortestPathFinder {

    Map<Vertex, Integer> calc(Vertex source);

}
