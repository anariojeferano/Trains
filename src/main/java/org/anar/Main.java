package org.anar;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.anar.builder.Builder;
import org.anar.builder.impl.BuilderImpl;
import org.anar.model.Connection;
import org.anar.model.RoadNetwork;
import org.anar.model.UserException;
import org.anar.model.Vertex;
import org.anar.processor.plugins.DeepTraversal;
import org.anar.processor.plugins.DistanceFinder;
import org.anar.processor.plugins.ShortestPathFinder;
import org.anar.processor.plugins.impl.DistanceFinderImpl;
import org.anar.processor.plugins.impl.ShortestPathFinderImpl;
import org.anar.processor.plugins.impl.TripsCounterWithConstraints;

import java.util.*;

public class Main {

  public static void main(String[] args) {

      Builder roadNetworkBuilder = new BuilderImpl();
      String start;
      String end;

      try(Scanner scanner = new Scanner(System.in)) {
          System.out.println("Please input graph: (AB5, BC4, CD8)");
          String data = scanner.nextLine();;
          System.out.println(data);
          Set<Connection> connections = roadNetworkBuilder.parse(data);
          Optional<RoadNetwork> roadNetwork = roadNetworkBuilder.build(connections);
          if(roadNetwork.isPresent()) {
              System.out.println("Please choose and enter task id (1, 2...)");
              System.out.println("1. Calculate distance of route (A-B-C, C-D and etc)");
              System.out.println("2. Length of shortest distance (A-C, E-B)");
              System.out.println("3. Count of routes from X to Z where distance is less than Y");
              System.out.println("4. Count of trip from X to Z with max stop count Y");
              System.out.println("5. Count of trip from X to Z with stop count equal to Y");
              switch(scanner.nextInt()) {
                  case 1:

                      System.out.println("Input route via - (A-B-C-D)");

                      String routes = scanner.next();
                      String[] route = routes.split("-");

                      List<Character> list = new ArrayList();
                      if(route != null && route.length > 0) {
                          for (String r : route) {
                              list.add(r.charAt(0));
                          }

                          DistanceFinder distanceFinder = new DistanceFinderImpl(roadNetwork.get());
                          System.out.println(distanceFinder.calc(list));
                      }

                      break;

                  case 2:

                      System.out.println("Please enter start point");
                      start = scanner.next();
                      System.out.println("Please enter end point");
                      end = scanner.next();

                      if(start.length() ==  1 && end.length() == 1) {
                          ShortestPathFinder shortestPathFinder = new ShortestPathFinderImpl(roadNetwork.get());
                          Map<Vertex, Integer> dataSheet = shortestPathFinder.calc(new Vertex(start));
                          if(dataSheet != null) {
                              int length = dataSheet.get(new Vertex(end));
                              System.out.println("Shortest length from " + start + " to " + end + " is " + length);
                          }
                          else {
                              throw new UserException("Shortest path finder cannot calculate");
                          }
                      }
                      else {
                          System.out.println("Wrong arguments supplied");
                      }

                      break;

                  case 3:

                      System.out.println("Please enter start point");
                      start = scanner.next();
                      System.out.println("Please enter end point");
                      end = scanner.next();
                      System.out.println("Please enter max distance");
                      int maxDist = scanner.nextInt();
                      if(start.length() == 1 && end.length() == 1 && maxDist >= 0) {
                          DeepTraversal deepTraversal = new TripsCounterWithConstraints(roadNetwork.get());
                          System.out.println(deepTraversal.countTillMax(new Vertex(start), new Vertex(end), maxDist));
                      }
                      else {
                          System.out.println("Wrong arguments supplied");
                      }

                      break;

                  case 4:
                      System.out.println("Please enter start point");
                      start = scanner.next();
                      System.out.println("Please enter end point");
                      end = scanner.next();
                      System.out.println("Please enter max stop count");
                      int maxStop = scanner.nextInt();
                      if(start.length() == 1 && end.length() == 1 && maxStop >= 0) {
                          DeepTraversal deepTraversal = new TripsCounterWithConstraints(roadNetwork.get());
                          System.out.println(deepTraversal.countWithStep(new Vertex(start), new Vertex(end), maxStop));
                      }
                      else {
                          System.out.println("Wrong arguments supplied");
                      }
                      break;

                  case 5:
                      System.out.println("Please enter start point");
                      start = scanner.next();
                      System.out.println("Please enter end point");
                      end = scanner.next();
                      System.out.println("Please enter equals stop count");
                      int equalsStop = scanner.nextInt();
                      if(start.length() == 1 && end.length() == 1 && equalsStop >= 0) {
                          DeepTraversal deepTraversal = new TripsCounterWithConstraints(roadNetwork.get());
                          System.out.println(deepTraversal.countTillLast(new Vertex(start), new Vertex(end), equalsStop));
                      }
                      else {
                          System.out.println("Wrong arguments supplied");
                      }
                      break;

                  default:
                      System.out.println("Wrong arguments supplied");
                      break;

              }
          }
          else {
              throw new UserException("Please input normal graph");
          }
      }
      catch(Exception e) {
          System.out.println(e.getMessage());
      }

  }
}