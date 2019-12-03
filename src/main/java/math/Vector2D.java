package math;

import java.util.Random;

public class Vector2D{
    public double x;
    public double y;

    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2D(){
        Random r = new Random();
        x = r.nextDouble() * 2 - 1;
        y = r.nextDouble() * 2 - 1;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Vector2D(Point2D start, Point2D end){
        x = end.x - start.x;
        y = end.y - start.y;
    }

    public Vector2D sub(Vector2D other){
        return new Vector2D(x - other.x, y - other.y);
    }

    public Vector2D normalize(){
        return new Vector2D(x/mod(), y/mod());
    }

    public double mod(){
        return Math.sqrt(x*x + y*y);
    }

    public Vector2D multiply(double d){
        return new Vector2D(x*d, y*d);
    }

    public Vector2D getOrthogonal(){
        if (y > Double.MIN_VALUE) {
            return new Vector2D(1, -x / y);
        }
        else {
            return new Vector2D(0, 1);
        }
    }

    public Vector2D add(Vector2D other){
        return new Vector2D(x + other.x, y + other.y);
    }

    public Vector2D copy(){
        return new Vector2D(x, y);
    }
}
