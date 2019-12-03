import math.Point2D;

public class Food {
    public static final String PARAMETERS = "class;location;energy;isExist";
    private Point2D location;
    private double energy;
    private boolean isExist = true;

    public Food(Point2D location, double energy) {
        this.location = location;
        this.energy = energy;
    }

    public Point2D getLocation() {
        return location;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setNotExist() {
        isExist = false;
    }

    public double getEnergy() {
        return energy;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append(";");
        sb.append(location).append(";");
        sb.append(energy).append(";");
        sb.append(isExist);
        return sb.toString();
    }
}
