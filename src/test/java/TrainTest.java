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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.*;

public class TrainTest {

    private Builder builder = new BuilderImpl();
    private DistanceFinder distanceFinder;
    private RoadNetwork roadNetwork;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public TrainTest() {
        try {
            Set<Connection> connectionsSet = this.builder.parse("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7, AE7");
            Optional<RoadNetwork> roadNetworkOptional = this.builder.build(connectionsSet);
            if(roadNetworkOptional.isPresent()) {
                this.roadNetwork = roadNetworkOptional.get();
            }
            else {
                throw new UserException("Cannot build road");
            }
        } catch (UserException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void AssertThatDistanceABCEquals9() throws UserException {
        DistanceFinder distanceFinder = new DistanceFinderImpl(this.roadNetwork);
        List<Character> route = new ArrayList<>();
        route.add('A');
        route.add('B');
        route.add('C');
        assertEquals(distanceFinder.calc(route), 9);
    }

    @Test
    public void AssertThatDistanceADEquals5() throws UserException {
        DistanceFinder distanceFinder = new DistanceFinderImpl(this.roadNetwork);
        List<Character> route = new ArrayList<>();
        route.add('A');
        route.add('D');
        assertEquals(distanceFinder.calc(route), 5);
    }

    @Test
    public void AssertThatDistanceADCEquals13() throws UserException {
        DistanceFinder distanceFinder = new DistanceFinderImpl(this.roadNetwork);
        List<Character> route = new ArrayList<>();
        route.add('A');
        route.add('D');
        route.add('C');
        assertEquals(distanceFinder.calc(route), 13);
    }

    @Test
    public void AssertThatDistanceAEBCDquals22() throws UserException {
        DistanceFinder distanceFinder = new DistanceFinderImpl(this.roadNetwork);
        List<Character> route = new ArrayList<>();
        route.add('A');
        route.add('E');
        route.add('B');
        route.add('C');
        route.add('D');
        assertEquals(distanceFinder.calc(route), 22);
    }

    @Test
    public void AssertThatDistanceAEDDqualsNoSuchRoute() throws UserException {
        exception.expect(UserException.class);
        exception.expectMessage("NO SUCH ROUTE");
        DistanceFinder distanceFinder = new DistanceFinderImpl(this.roadNetwork);
        List<Character> route = new ArrayList<>();
        route.add('A');
        route.add('E');
        route.add('D');
        distanceFinder.calc(route);
    }

    @Test
    public void AssertThatNumberFromC2CWithMax3StopIs2() throws UserException {
        DeepTraversal deepTraversal = new TripsCounterWithConstraints(this.roadNetwork);
        assertEquals(deepTraversal.countWithStep(new Vertex('C'), new Vertex('C'), 3), 2);
    }

    @Test
    public void AssertThatNumberFromA2CWithExact4StopIs3() throws UserException {
        DeepTraversal deepTraversal = new TripsCounterWithConstraints(this.roadNetwork);
        assertEquals(deepTraversal.countTillLast(new Vertex('A'), new Vertex('C'), 4), 3);
    }

    @Test
    public void AssertThatLengthOfShortestPathFromA2CIs9() throws UserException {
        ShortestPathFinder shortestPathFinder = new ShortestPathFinderImpl(this.roadNetwork);
        Map<Vertex, Integer> dataSheet = shortestPathFinder.calc(new Vertex('A'));
        int length = dataSheet.get(new Vertex('C'));
        assertEquals(length , 9);
    }

    @Test
    public void AssertThatLengthOfShortestPathFromB2BIs9() throws UserException {
        ShortestPathFinder shortestPathFinder = new ShortestPathFinderImpl(this.roadNetwork);
        Map<Vertex, Integer> dataSheet = shortestPathFinder.calc(new Vertex('B'));
        int length = dataSheet.get(new Vertex('B'));
        assertEquals(length, 9);
    }

    @Test
    public void AssertThatNumberDiffRoutesC2CWithMaxDist30Is7() throws UserException {
        DeepTraversal deepTraversal = new TripsCounterWithConstraints(this.roadNetwork);
        assertEquals(deepTraversal.countTillMax(new Vertex('C'), new Vertex('C'), 30), 7);
    }

}
