package Assignment8;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The Circle class after inheritance. Comments removed for display in class.
 * Constructors removed because they require a separate discussion.
 *
 * @author Andes Pena & Hoang Anh
 */
public class Circle extends Shape {
// radius of circle

    private double radius;
    public static final String NAME = "Circle";

    /**
     * Constructor of circle
     *
     * @param x coordinate of upper left conner
     * @param y coordinate of upper left conner
     * @param strokeColor
     * @param fillColor
     * @param lineWidth
     * @param radius
     */
    public Circle(double x, double y, Color strokeColor, Color fillColor, int lineWidth, double radius) {
        super(x, y, strokeColor, fillColor, lineWidth, NAME);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {

        this.radius = radius;

    }

    /**
     * draw a circle
     *
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        gc.fillOval(super.getX() - radius, super.getY() - radius, radius * 2, radius * 2);
        gc.strokeOval(super.getX() - radius, super.getY() - radius, radius * 2, radius * 2);
    }

    /**
     * to define a point is inside the circle or not
     *
     * @param x coordinate of mouse point
     * @param y coordinate of mouse point
     * @return true if a mouse point is inside the circle when it matches the
     * formula.
     */
    @Override
    public boolean isPointInArea(double x, double y) {
        double d = Math.sqrt(Math.pow(x - this.getX(), 2) + Math.pow(y - this.getY(), 2));
        return d <= this.getRadius();
    }

    @Override
    public void move(double x, double y) {
        this.setX(x);
        this.setY(y);
    }
    
    /**
     * resize method to change radious of circle when drag mouse
     *
     * @param x
     * @param y
     */
    @Override
    public void resize(double x, double y){
        double newRad = Math.sqrt(Math.pow(x - this.getX(), 2) + Math.pow(y - this.getY(), 2));
        this.setRadius(newRad);
    }

}
