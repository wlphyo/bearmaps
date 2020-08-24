package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.*;
public class KDTreeTest {
    private static Random r = new Random(50);
    /*Test from Josh Hug*/
    private static KDTree buildLectureTree(){
        Point p1 = new Point(2,3);
        Point p2 = new Point(4,2);
        Point p3 = new Point(4,5);
        Point p4 = new Point(3,3);
        Point p5 = new Point(1,5);
        Point p6 = new Point(4,4);
        KDTree kd = new KDTree(List.of(p1,p2,p3,p4,p5,p6));
        return kd;
    }
    private static void buildTreeWithDoubles(){
        Point p1 = new Point(2,3);
        Point p2 = new Point(2,3);
        KDTree kd = new KDTree(List.of(p1,p2));
        System.out.println(kd.size());
    }
    @Test
    public void testNearest(){
        KDTree kd = buildLectureTree();
        Point actual = kd.nearest(0,7);
        Point expected = new Point(1,5);
        assertEquals(expected,actual);


    }
    private Point randomPoint(){
        double x = r.nextDouble();
        double y = r.nextDouble();
        return new Point(x,y);
    }
    private List<Point> randomPoints(int N){
        List<Point> points = new ArrayList<>();
        for(int i = 0; i < N;i++){
            points.add(randomPoint());
        }
        return points;
    }
    @Test
    public void testWith100Points(){
        List<Point> points1000 = randomPoints(1000);
        NaivePointSet nps = new NaivePointSet(points1000);
        KDTree kd = new KDTree(points1000);
        List<Point> queries200 = randomPoints(200);
        for(Point p : queries200){
            Point expected = nps.nearest(p.getX(),p.getY());
            Point actual = kd.nearest(p.getX(),p.getY());
            assertEquals(expected,actual);
        }
    }
    @Test public void callBuildTreeWithDoubles(){
        buildTreeWithDoubles();
    }
}
