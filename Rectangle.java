package Assignment8;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The Rectangle class after inheritance. Comments removed for display in class.
 * Constructors removed because they require a separate discussion.
 *
 * @author Andes Pena & Hoang Anh
 */
public class Rectangle extends Shape {
// width and height of rectangle

    private double width, height;

    public static final String NAME = "Rectangle";

    /**
     * Constructor of Rect
     *
     * @param x coordinate of upper left conner
     * @param y coordinate of upper left conner
     * @param width
     * @param height
     * @param strokeColor
     * @param fillColor
     * @param lineWidth
     */
    public Rectangle(double x, double y, double width, double height, Color strokeColor, Color fillColor, int lineWidth) {
        super(x, y, strokeColor, fillColor, lineWidth, NAME);
        this.height = height;
        this.width = width;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * rezise method to change width and height of rectangle when drag mouse
     *
     * @param x
     * @param y
     */
    public void resize(double x, double y) {
        double rwidth = this.getWidth();
        if (x < this.getX()) {
            this.setWidth(Math.abs(x - this.getX()) + rwidth);
            this.setX(x);
        } else {
            rwidth = 0;
            this.setWidth(Math.abs(x - this.getX()) + rwidth);
        }

        double rheight = this.getHeight();
        if (y < this.getY()) {
            this.setHeight(Math.abs(y - this.getY()) + rheight);
            this.setY(y);
        } else {
            rheight = 0;
            this.setHeight(Math.abs(y - this.getY()) + rheight);
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        gc.fillRect(super.getX(), super.getY(), width, height);
        gc.strokeRect(super.getX(), super.getY(), width, height);
    }

    /**
     * to define a mouse point is in rectangle or not
     *
     * @param x x-coordinate of mouse
     * @param y y-coordinate of mouse
     * @return true if mouse point inside the rectangle (match the formula).
     */
    @Override
    public boolean isPointInArea(double x, double y) {
        return (x >= this.getX() && y >= this.getY() && Math.abs(x - getX()) <= this.width && Math.abs(y - getY()) <= this.height);

    }
    
    @Override
    public void move(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

}
