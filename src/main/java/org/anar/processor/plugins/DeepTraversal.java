package org.anar.processor.plugins;

import org.anar.model.Vertex;

public interface DeepTraversal {

    int countWithStep(Vertex source, Vertex target, int maxSteps);

    int countTillLast(Vertex source, Vertex target, int maxSteps);

    int countTillMax(Vertex source, Vertex target, int maxDistance);

}
