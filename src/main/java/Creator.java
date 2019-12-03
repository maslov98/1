import math.Point2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Creator {
    private static final double B_INITIAL_SENSE_RADIUS = 30;
    private static final double B_INITIAL_SPEED = 7;
    private static final double B_INITIAL_ENERGY = 10;
    private static final double F_INITIAL_ENERGY = 10;
    public static final int B_INITIAL_COUNT = 100;
    public static final int F_INITIAL_COUNT = 100;
    private static final double F_INITIAL_WIDTH = 600;
    private static final double F_INITIAL_HEIGHT = 600;
    private Field field;
    private Controller controller;

    public void createWorld(int steps){
        field = createField();
        controller = createController();

        LinkedList<Being> beings = new LinkedList<>();
        for (int i = 0; i < B_INITIAL_COUNT; i++) {
            beings.add(createBeing());
        }

        LinkedList<Food> food = new LinkedList<>();
        for (int i = 0; i < F_INITIAL_COUNT; i++) {
            food.add(createFood());
        }

        field.setBeings(beings);
        field.setFood(food);
        controller.run(steps);
    }

    private Field createField(){
        return new Field(F_INITIAL_WIDTH, F_INITIAL_HEIGHT, new Point2D(0, 0));
    }

    private Being createBeing(){
        Point2D location = new Point2D(1, F_INITIAL_WIDTH - 1, 1, F_INITIAL_HEIGHT - 1);
        return new Being(location, B_INITIAL_SPEED, B_INITIAL_SENSE_RADIUS, field, B_INITIAL_ENERGY);
    }

    static Food createFood(){
        Point2D location = new Point2D(1, F_INITIAL_WIDTH - 1, 1, F_INITIAL_HEIGHT - 1);
        return new Food(location, F_INITIAL_ENERGY);
    }

    private Controller createController(){
        return new Controller(field);
    }
}
