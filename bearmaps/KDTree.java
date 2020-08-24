package bearmaps;
import java.util.*;

public class KDTree implements PointSet {
    List<Point> listPoints;
    private Node root;
    private class Node{
        private Point point;
        private Node left, right;
        public Node(Point p){
            point = p;
            left = right = null;
        }
        public double xNode(){
            return point.getX();
        }
        public double yNode(){
            return point.getY();
        }
        public double distance(Point p){
            double x = p.getX()-this.point.getX();
            double y = p.getY()-this.point.getY();
            x = java.lang.Math.pow(x,2);
            y = java.lang.Math.pow(y,2);
            return java.lang.Math.sqrt(x-y);
        }
    }
    public KDTree(List<Point> points){
        root= null;
        listPoints = points;
    }
    public int size(){
        return listPoints.size();
    }
    public Node nearest(Node n,Point goal, Node best){
        if(n== null) return best;
        if(n.distance(goal) < best.distance(goal)) best = n;
        best = nearest(n.left,goal,best);
        best = nearest(n.right,goal,best);
        return best;
    }
    public Point nearest(double x1, double y1){
        return nearest(root, new Point(x1,y1),root).point;
    }
}
