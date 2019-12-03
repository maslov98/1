import math.Point2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Field {
    private Point2D start;
    private double width;
    private double height;
    private LinkedList<Food> food;
    private LinkedList<Being> beings;

    public Field(double width, double height, Point2D start){
        this.width = width;
        this.height = height;
        this.start = start;
    }

    public void setBeings(LinkedList<Being> beings) {
        this.beings = beings;
    }

    public void setFood(LinkedList<Food> food) {
        this.food = food;
    }

    public boolean isInside(Point2D point){
        return (point.x > start.x) && (point.x < start.x + width) && (point.y > start.y) && (point.y < start.y + height);
    }

    public LinkedList<Food> getLocalFood(Point2D location, double radius){
        LinkedList<Food> localFood = new LinkedList<>();
        for (Food f: food){
            if (f.getLocation().distTo(location) < radius && f.isExist()){
                localFood.add(f);
            }
        }
        return localFood;
    }

    public LinkedList<Being> getBeings() {
        return (LinkedList<Being>)beings.clone();
    }

    public LinkedList<Food> getFood() {
        return (LinkedList<Food>)food.clone();
    }
}
