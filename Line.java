/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment8;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Andes Pena & Hoang Anh
 */
public class Line extends Shape {

    double endx, endy;
    public static final String NAME = "Line";

    /**
     * Constructor of Line
     *
     * @param x x - coordinate of first point
     * @param y y - coordinate of first point
     * @param endx coordinate of second point
     * @param endy coordinate of second point
     * @param strokeColor
     * @param fillColor
     * @param lineWidth
     */
    public Line(double x, double y, double endx, double endy, Color strokeColor, Color fillColor, int lineWidth) {
        super(x, y, strokeColor, fillColor, lineWidth, NAME);
        this.endx = endx;
        this.endy = endy;
    }

    public double getEndx() {
        return endx;
    }

    public void setEndx(double endx) {
        this.endx = endx;
    }

    public double getEndy() {
        return endy;
    }

    public void setEndy(double endy) {
        this.endy = endy;
    }

    /**
     * Draw a line between 2 points
     *
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);

        gc.strokeLine(super.getX(), super.getY(), endx, endy);
    }

    /**
     * to define a point is on the line or not
     *
     * @param x coordinate of mouse point
     * @param y coordinate of mouse point
     * @return true if mouse point is on the line when it matches the formula.
     */
    @Override
    public boolean isPointInArea(double originalX, double originalY) {
        
        double slope = (this.getEndy() - this.getY())/(this.getEndx() - this.getX());
        double b = this.getY() - this.getX() * slope;
        
        System.out.println("y = " + slope + "x + " + b);
        
        boolean isPoint = false;
        double x = (originalX-5);
        do {
            double y = (originalY-5);
            do {
                isPoint = ((int)y == (int)(slope * x + b));
                y++;
            } while(y <= (originalY + 5) && !isPoint);
            x++;
        } while(x <= (originalX + 5) && !isPoint);
        
        System.out.println(isPoint);
        return isPoint;
    }

    @Override
    public void move(double x, double y) {
        this.setEndx(x + this.getEndx() - this.getX());
        this.setX(x);

        this.setEndy(y + this.getEndy() - this.getY());
        this.setY(y);
    }
    
    /**
     * resize method to change width of line when drag mouse
     *
     * @param x
     * @param y
     */
    @Override
    public void resize(double x, double y) {
        this.setEndx(x);
        this.setEndy(y);
    }

}
