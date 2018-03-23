package org.anar.builder;

import org.anar.model.Connection;
import org.anar.model.RoadNetwork;
import org.anar.model.UserException;

import java.util.Optional;
import java.util.Set;

public interface Builder {

    Set<Connection> parse(String graph) throws UserException;

    Optional<RoadNetwork> build(Set<Connection> connections) throws UserException;

}
