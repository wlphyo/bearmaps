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
        public Node(Point p, boolean o){
            point = p;
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
//        if(n== null) return best;
//        if(n.distance(goal) < best.distance(goal)) best = n;
//        best = nearest(n.left,goal,best);
//        best = nearest(n.right,goal,best);
//        return best;

        if(n== null) return best;
        if(Double.compare(n.distance(goal),best.distance(goal))<0) best = n;
        Node goodSide, badSide;
        /*if goal is less than n*/
        if(cmpNodeAndPoint(goal,n)){
            goodSide = n.left;
            badSide = n.right;
        }else{
            goodSide = n.right;
            badSide = n.left;
        }
        best = nearest(goodSide,goal,best);
        best = pruningRule(n,goal,best,badSide);
        return best;
    }
    /*bad side might have good points*/
    /*good sources:
    * https://sp19.datastructur.es/materials/discussion/disc09sol.pdf
    * https://www.youtube.com/watch?v=ivdmGcZo6U8
    * */
    private Node pruningRule(Node n,Point goal, Node best, Node badSide){
        if(n.orientation == horizontal){
            /*splitting plane, split on x*/
            Point horizontalPoint = new Point(n.xNode(),goal.getY()); //page 8 of pdf source
            Node tmp = new Node(horizontalPoint,false); //orientation should not matter
            /*if distance from query point is less than splitting plan (horizontalPoint),
            * visit badSide.
            * */
            if(Double.compare(tmp.distance(goal),best.distance(goal))<0){
                best = nearest(badSide,goal,best);
            }
            return best;
        }else if(n.orientation == vertical){
            Point verticalPoint = new Point(goal.getX(),n.yNode());
            Node tmp = new Node(verticalPoint,true); //orientation should not matter
            if(Double.compare(tmp.distance(goal),best.distance(goal))<0){
                best = nearest(badSide,goal,best);
            }
            return best;
        }else throw new Error("pruningRule function is producing an error.");
    }
    /*to compare Point goal and Node n's point*/
    private boolean cmpNodeAndPoint(Point p,Node n){
        if(n.orientation == horizontal){
            return Double.compare(p.getX(),n.point.getX()) > 0;
        }else if(n.orientation == vertical){
            return Double.compare(p.getY(),n.point.getY()) > 0;
        }else throw new Error("cmpNodeAndPoint is producing an error.");
    }
    public Point nearest(double x1, double y1){
        return nearest(root, new Point(x1,y1),root).point;
    }


}
