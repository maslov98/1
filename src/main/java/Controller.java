import math.Point2D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class Controller {
    private Field field;
    private int countOfBeings;
    private ViewMaker viewMaker;

    public Controller(Field field) {
        this.field = field;
        countOfBeings = Creator.B_INITIAL_COUNT;
    }

    public void run(int steps){
        for (int i = 0; i < steps; i++) {
            if (i == 0) {
                display();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                setPlot();
            }
//            if(i == 9999)
//                FileUtils.safeThisStep(field, i);
//            printStatistic(i);
            runBeings();
            updateBeings();
            updateFood();
        }
    }

    private void runBeings(){
        for (Being being: field.getBeings()){
            being.run();
        }
    }

    private void updateFood(){
        LinkedList<Food> food = field.getFood();
        for (int i = 0; i < Creator.F_INITIAL_COUNT; i++) {
            if(!food.get(i).isExist()){
                food.set(i, Creator.createFood());
            }
        }
        field.setFood(food);
    }

    private void updateBeings(){
        LinkedList<Being> newBeings = new LinkedList<>();
        LinkedList<Being> beings = field.getBeings();
        Iterator<Being> iterator = beings.iterator();
        while (iterator.hasNext()) {
            Being b = iterator.next();
            if (b.getEnergy() < 0) {
                iterator.remove();
            }
            else if(b.getEnergy() > 20){
                newBeings.add(b.getChild());
            }
        }
        beings.addAll(newBeings);
        field.setBeings(beings);
        countOfBeings = beings.size();
    }

    private void printStatistic(int step){
        double r = 0;
        double s = 0;
        for (Being b: field.getBeings()){
            s += b.getSpeed();
            r += b.getSenseRadius();
        }
        System.out.println(step + " c=" + countOfBeings + " s=" + s/countOfBeings + " r=" + r/countOfBeings);
    }

    private void display(){
        double[] data = getData();
        SwingUtilities.invokeLater(() -> {
            viewMaker = new ViewMaker();
            viewMaker.showBarChart(data, 20, true);
        });
    }

    private void setPlot(){
        double[] data = getData();
        viewMaker.showBarChart(data, 20, false);
    }

    private double[] getData(){
        LinkedList<Being> beings = field.getBeings();
        double[] data = new double[beings.size()];
        for (int i = 0; i < beings.size(); i++) {
            data[i] = beings.get(i).getSpeed();
        }
        return data;
    }
}
