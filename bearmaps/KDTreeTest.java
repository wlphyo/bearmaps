package bearmaps;

import edu.princeton.cs.introcs.Stopwatch;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.*;
/*
Produced by Josh Hug;
Source: https://www.youtube.com/watch?v=3IdQJm9tw6Y&feature=youtu.be
*/
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
    }
    @Test
    public void testNearest(){
        KDTree kd = buildLectureTree();
        Point actual = kd.nearest(0,7);
        Point expected = new Point(1,5);
       // assertEquals(expected,actual);
        assertEquals(expected.getX(),actual.getX(),0.1);
        assertEquals(expected.getY(),actual.getY(),0.1);
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
    public void testWith1000PointsAnd200Queries(){
        List<Point> points1000 = randomPoints(1000);
        NaivePointSet nps = new NaivePointSet(points1000);
        KDTree kd = new KDTree(points1000);
        List<Point> queries200 = randomPoints(200);
        for(Point p : queries200){
            Point expected = nps.nearest(p.getX(),p.getY());
            Point actual = kd.nearest(p.getX(),p.getY());
            //assertEquals(expected,actual);
            assertEquals(expected.getX(),actual.getX(),0.1);
            assertEquals(expected.getY(),actual.getY(),0.1);
        }
    }
    private void testWithNPointAndQQueries(int pointCt,int queryCt){
        List<Point> points = randomPoints(pointCt);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points);
        List<Point> queries = randomPoints(queryCt);
        for(Point p : queries){
            Point expected = nps.nearest(p.getX(),p.getY());
            Point actual = kd.nearest(p.getX(),p.getY());

           // assertEquals(expected,actual);
            assertEquals(expected.getX(),actual.getX(),0.1);
            assertEquals(expected.getY(),actual.getY(),0.1);
        }
    }
    @Test
    public void testWith10000PointsAnd2000Queries(){
        int pointCt = 10000;
        int queryCt = 2000;
        testWithNPointAndQQueries(pointCt,queryCt);
    }

    @Test
    public void testWith100000PointsAnd20000Queries(){
        int pointCt = 100000;
        int queryCt = 20000;
        testWithNPointAndQQueries(pointCt,queryCt);
    }
    private void timeWithNPointsAndQQueries(int pointCt,int queryCt){
        List<Point> points = randomPoints(pointCt);
        KDTree kd = new KDTree(points);
        Stopwatch sw = new Stopwatch();
        List<Point> queries = randomPoints(queryCt);
        for(Point p : queries){
            Point actual = kd.nearest(p.getX(),p.getY());
        }
        System.out.println("Time elapsed for " + queryCt + " queries on "+ pointCt +
                " points: " + sw.elapsedTime());
    }

    @Test
    public void timeWithVariousNumbersOfPoints(){
        List<Integer> pointCounts = List.of(1000,10000,100000,1000000);
        for(int N:pointCounts){
            timeWithNPointsAndQQueries(N,10000);
        }
    }
    @Test
    public void CompareTimingOfNaiveVsKdTree(){
        List<Point> randomPoint = randomPoints(100000);
        KDTree kd = new KDTree(randomPoint);
        NaivePointSet nps= new NaivePointSet(randomPoint);
        List<Point> queryPoints = randomPoints(10000);
        Stopwatch sw = new Stopwatch();
        for(Point p : queryPoints){
            nps.nearest(p.getX(),p.getY());
        }
        double time = sw.elapsedTime();
        System.out.println("Naive 10000 queries on 100000 points: " + time);

        sw = new Stopwatch();
        for(Point p : queryPoints){
            kd.nearest(p.getX(),p.getY());
        }
        time = sw.elapsedTime();
        System.out.println("KDTree 10000 queries on 100000 points: " + time);
    }
    @Test public void callBuildTreeWithDoubles(){
        buildTreeWithDoubles();
    }
}
