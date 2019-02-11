package Assignment8;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The parent class for Circle and Rectangle and line. Comments removed for
 * display in class. Constructors removed because they require a separate
 * discussion.
 *
 * @author Andes Pena & Hoang Anh
 */
public abstract class Shape {
// x and y coordinate of upper left conner
//Strokecolor for outline, fillColor for filling

    private double x, y;
    private Color strokeColor, fillColor;
    private int lineWidth;
    private String type;

    /**
     * Constructor of Shape in general
     *
     * @param x coordinate of upper left conner
     * @param y coordinate of upper left conner
     * @param strokeColor
     * @param fillColor
     * @param lineWidth
     * @param type
     */
    public Shape(double x, double y, Color strokeColor, Color fillColor, int lineWidth, String type) {
        this.x = x;
        this.y = y;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
        this.lineWidth = lineWidth;
        this.type = type;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public String getType() {
        return this.type;
    }

    public abstract boolean isPointInArea(double x, double y);
    public abstract void move(double x, double y);
    public abstract void resize(double x, double y);

    /**
     * Draw method: choose colors and line width
     *
     * @param gc
     */
    public void draw(GraphicsContext gc) {
        gc.setStroke(getStrokeColor());
        gc.setFill(getFillColor());
        gc.setLineWidth(getLineWidth());
    }

}
