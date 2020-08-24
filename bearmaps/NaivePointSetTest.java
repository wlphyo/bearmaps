package bearmaps;

import org.junit.Test;
import java.util.*;
import static org.junit.Assert.assertEquals;
import edu.princeton.cs.algs4.Stopwatch;

public class NaivePointSetTest {
    @Test
    public void testNearest(){
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret1 = nn.nearest(3.0, 4.0);
        //ret.getX(); // evaluates to 3.3
        assertEquals(3.3,ret1.getX(),0.1);
        assertEquals(4.4,ret1.getY(),0.1);
        Point ret2 = nn.nearest(-2.8, 4.1);
        assertEquals(-2.9,ret2.getX(),0.1);
        assertEquals(4.2,ret2.getY(),0.1);

    }

}
