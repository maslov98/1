import math.Point2D;
import math.Vector2D;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class Being {
    private static final double MIN_DIST_TO_EAT = 0.1;
    private static final double DIRECTION_CHANGE = 0.5;
    public static final String PARAMETERS = "class;location;faceDirection;speed;senseRadius;energy";

    private Point2D location;
    private double speed;
    private Food currentTarget;
    private Vector2D faceDirection;
    private double senseRadius;
    private Field field;
    private List<Food> targets;
    private double energy;
    private Random random;

    public Being(Point2D location , double speed, double senseRadius, Field field, double energy){
        this.location = location;
        this.speed = speed;
        this.senseRadius = senseRadius;
        this.field = field;
        this.energy = energy;
        targets = new LinkedList<>();
        faceDirection = new Vector2D();
        random = new Random();
    }

    public void run(){
        currentTarget = updateTarget();
        move();
        spendEnergy();
        if(canEat()) {
            eat();
        }
    }

    private void move(){
        if(currentTarget != null){
            moveTo(currentTarget.getLocation());
        }
        else {
            randomizeMove();
            avoidWallsPath(faceDirection);
            location = moveAlong(faceDirection);
        }
    }

    private void moveTo(Point2D target){
        Vector2D path = new Vector2D(location, target);
        if(location.distTo(target) > speed){
            location = moveAlong(path);
        }
        else {
            location = target;
        }
        faceDirection = path.normalize();
    }

    private Point2D moveAlong(Vector2D path){
        Vector2D change = path.normalize().multiply(speed);
        return location.addVector(change);
    }

    private void avoidWallsPath(Vector2D path){
        Point2D top = new Point2D(location.x, location.y + senseRadius);
        Point2D bottom = new Point2D(location.x, location.y - senseRadius);
        if (!field.isInside(top)){
            path.y = - Math.abs(path.y);
        }
        else if (!field.isInside(bottom)){
            path.y = Math.abs(path.y);
        }

        Point2D right = new Point2D(location.x + senseRadius, location.y);
        Point2D left = new Point2D(location.x - senseRadius, location.y);
        if (!field.isInside(right)){
            path.x = - Math.abs(path.x);
        }
        else if (!field.isInside(left)){
            path.x = Math.abs(path.x);
        }
    }

    private void randomizeMove(){
        Vector2D change = faceDirection.getOrthogonal().normalize().multiply(DIRECTION_CHANGE * (random.nextDouble() * 2 - 1));
        faceDirection = faceDirection.add(change).normalize();
    }

    private void eat(){
        energy += currentTarget.getEnergy();
        currentTarget.setNotExist();
    }

    private Food updateTarget(){
        if ((currentTarget != null) && currentTarget.isExist()) {
            return currentTarget;
        }

        updateTargets();
        if (targets.isEmpty()){
            return null;
        }

        double minDist = location.distTo(targets.get(0).getLocation());
        Food minDistTarget = targets.get(0);
        for (Food f: targets){
            double dist = location.distTo(f.getLocation());
            if(minDist < dist){
                minDist = dist;
                minDistTarget = f;
            }
        }
        targets.remove(minDistTarget);
        return minDistTarget;
    }

    private boolean canEat(){
        return (currentTarget != null) &&
                (location.distTo(currentTarget.getLocation()) < MIN_DIST_TO_EAT) &&
                currentTarget.isExist();
    }

    private void updateTargets(){
        LinkedList<Food> newTargets = field.getLocalFood(location, senseRadius);
        targets.removeIf(f -> !f.isExist() || newTargets.contains(f));
        targets.addAll(newTargets);
    }

    public double getEnergy() {
        return energy;
    }

    private void spendEnergy(){
        energy -= speed * speed / 100;
        energy -= senseRadius / 200;
    }

    private double variate(double value){
//        return value + (random.nextDouble() > 0.5 ? value * 0.1 : -value * 0.1);
        return value + value * 0.1 * (random.nextDouble() * 2 - 1);
    }

    public Being getChild(){
        energy /= 2;
        double oldSpeed = speed;
        double oldRadius = senseRadius;
        speed = variate(speed);
        senseRadius = variate(senseRadius);
        return new Being(location.addVector(new Vector2D(1,1)), variate(oldSpeed), variate(oldRadius), field, energy);
    }

    public double getSpeed(){
        return speed;
    }

    public double getSenseRadius(){
        return senseRadius;
    }

    public Point2D getLocation(){
        return location.copy();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append(";");
        sb.append(location).append(";");
        sb.append(faceDirection).append(";");
        sb.append(speed).append(";");
        sb.append(senseRadius).append(";");
        sb.append(energy);
        return sb.toString();
    }
}
