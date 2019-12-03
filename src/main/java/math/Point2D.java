package math;

import java.util.Random;

public class Point2D{
    public double x;
    public double y;

    public Point2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Point2D(double fromX, double toX, double fromY, double toY){
        Random r = new Random();
        x = r.nextDouble()*(toX - fromX) + fromX;
        y = r.nextDouble()*(toY - fromY) + fromY;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public double distTo(Point2D other){
        return Math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y));
    }

    public Point2D addVector(Vector2D v){
        return new Point2D(x + v.x, y + v.y);
    }

    public Point2D copy(){
        return new Point2D(x, y);
    }
}
