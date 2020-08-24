package bearmaps;
import java.util.*;

public class NaivePointSet implements PointSet {
    private List<Point> pointsList;

    public NaivePointSet(List<Point> points){
        pointsList=points;
    }
    public Point nearest(double x1, double y1){
        /*use distance formula*/
        int pos = 0;
        List<Double> dist = new ArrayList<>();
        for(int i = 0;i< pointsList.size();i++){
            double x2 = pointsList.get(i).getX();
            double y2 = pointsList.get(i).getY();
            double x3 = x2-x1;
            double y3 = y2-y1;
            double x = java.lang.Math.pow(x3,2);
            double y = java.lang.Math.pow(y3,2);
            double ans = java.lang.Math.sqrt(x+y);
            dist.add(ans);
        }
        double currentSmall = dist.get(0);
        for(int i = 0; i < dist.size();i++){
            if(currentSmall>dist.get(i)) {
                currentSmall =dist.get(i);
                pos = i;
            }
        }
        return pointsList.get(pos);
    }
}
