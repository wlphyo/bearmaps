package bearmaps;
import java.util.*;

public class KDTree implements PointSet {
    List<Point> listPoints;
    private static final boolean horizontal = false;
    private static final boolean vertical = true;
    private HashSet<Point> pointHash; // to check if point already exits
    private Node root;
    private class Node{
        private boolean orientation;
        private Point point;
        private Node left, right; /*left is down child and right is up child*/
       // private int size;
        public Node(Point p, boolean o){
            point = p;
           // size = 1;
            left = right = null;
            orientation = o;
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
            return java.lang.Math.sqrt(x+y);
        }
    }
    public KDTree(List<Point> points){
        pointHash = new HashSet<>();
        listPoints = points;
        for(Point p : listPoints){
            if(!pointHash.contains(p)) {
                pointHash.add(p);
                root = add(root, p, horizontal);
            }
        }

    }

    public int size(){
        return pointHash.size();
    }
//    private int size(Node x){
//        if(x==null) return 0;
//        return x.size;
//    }

    private void insert(Point p){

    }
    /*compare two points based on their orientation*/
    private int comparePoints(Point a,Point b, boolean o){
        if(o == horizontal){
            return Double.compare(a.getX(),b.getX());
        }
        return Double.compare(a.getY(),b.getY());
    }
    private Node add(Node n, Point p,boolean o){
        if(n==null){
            return new Node(p,o);
        }
        if(p.equals(n.point)) return n; /*if there is an exiting same point, do nothing*/
        int cmp = comparePoints(n.point,p,o);
        if(cmp < 0){
            n.left = add(n.left,p,!(o));
        }else if (cmp >= 0){
            n.right = add(n.right,p,!(o));
        }
        return n;
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
