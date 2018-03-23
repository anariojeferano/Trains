package org.anar.processor.plugins;

import org.anar.model.RoadNetwork;
import org.anar.model.UserException;

import java.util.List;

public interface DistanceFinder {

    int calc(List<Character> route) throws UserException;

}
