package Assignment8;

import javafx.scene.input.MouseEvent;
import static java.lang.System.gc;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Andes Pena & Hoang Anh
 */
public class FXGUIPaint extends Application {

    // TODO: Instance Variables for View Components and Model
    private Button circle, rectangle, line, clear, reset, delete, front, back;
    private TextField width;
    private Label instruction, outline, fill, strokeWidth, select;

    GraphicsContext gc;
    ColorPicker fillColor, outlineColor;
    Canvas c;
    String selected;
    Shape s;
    boolean pressing;
    ArrayList<Shape> shapes = new ArrayList<>();

    // TODO: Private Event Handlers and Helper Methods
    /**
     * Reset color
     *
     * @param e
     */
    public void ResetHandler(ActionEvent e) {
        fillColor.setValue(Color.WHITE);
        outlineColor.setValue(Color.WHITE);
    }

    /**
     * clear all shapes in the canvas
     *
     * @param e
     */
    public void ClearHandler(ActionEvent e) {
        gc.setFill(Color.DARKSEAGREEN);
        gc.fillRect(0, 0, 1000, 500);
        shapes.removeAll(shapes);
    }

    /**
     * Delete the selected object
     *
     * @param e
     */
    public void DeleteHandler(ActionEvent e) {
        if (s != null) {
            shapes.remove(s);
            drawAllShapes();
            s = null;
            disableActionButtons();
        }
    }

    /**
     * Move an object to front of others
     *
     * @param e
     */
    public void MoveToFrontHandler(ActionEvent e) {

        if (s != null) {
            Shape temp = s;
            shapes.remove(s);
            shapes.add(s);
            drawAllShapes();
            disableActionButtons();
        }
        s = null;

    }

    /**
     * Move an object to back of others
     *
     * @param e
     */
    public void MoveToBackHandler(ActionEvent e) {

        if (s != null) {
            Shape temp = s;
            shapes.remove(s);
            shapes.add(0, s);
            drawAllShapes();
            disableActionButtons();
        }
        s = null;
    }

    /**
     * Button circle handler
     *
     * @param e
     */
    public void CircleHandler(ActionEvent e) {
        unSelectAllButtons();
        if (selected != null && selected.equals(Circle.NAME)) {
            selected = null;
        } else {
            selected = Circle.NAME;
            selectButton(circle);
        }
    }

    /**
     * Button Rectangle handler
     *
     * @param e
     */
    public void RectHandler(ActionEvent e) {
        unSelectAllButtons();
        if (selected != null && selected.equals(Rectangle.NAME)) {
            selected = null;
        } else {
            selected = Rectangle.NAME;
            selectButton(rectangle);
        }
    }

    /**
     * Button Line handler
     *
     * @param e
     */
    public void LineHandler(ActionEvent e) {
        unSelectAllButtons();
        if (selected != null && selected.equals(Line.NAME)) {
            selected = null;
        } else {
            selected = Line.NAME;
            selectButton(line);
        }
    }

    /**
     * Pressed mouse on Primary key to choose and draw an object the selected
     * object is saved in Arraylist shapes.
     *
     * @param me
     */
    public void pressedMouse(MouseEvent me) {
        if (me.getButton().equals(MouseButton.PRIMARY)) {
            try {

                int n = Integer.parseInt(width.getText());

                //draw a shape
                if (selected != null && !selected.isEmpty()) {
                    switch (selected) {
                        case Circle.NAME:
                            s = new Circle(me.getX(), me.getY(), outlineColor.getValue(), fillColor.getValue(), n, 5.0);
                            break;
                        case Rectangle.NAME:
                            s = new Rectangle(me.getX(), me.getY(), 5.0, 4.0, outlineColor.getValue(), fillColor.getValue(), n);
                            break;
                        case Line.NAME:
                            s = new Line(me.getX(), me.getY(), me.getX() + 5.0, me.getY() + 5.0, outlineColor.getValue(), fillColor.getValue(), n);

                            break;
                        default:
                            break;
                    }
                    s.draw(gc);
                    shapes.add(s);

                } else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Oops");
                    alert.setContentText("Please choose shape to draw!");

                    alert.showAndWait();
                }
            } catch (NumberFormatException ex) {

                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Oops, sorry");
                    alert.setContentText("Something wrong happenned!");

                    alert.showAndWait();

            }
        }
    }

// click to choose object

    /**
     * Click mouse on Secondary key to choose an object
     *
     * @param me
     */
    public void clickedMouse(MouseEvent me) {
        //select a specific shape
        if (me.getButton().equals(MouseButton.SECONDARY)) {
            s = getShapeFromPoint(me.getX(), me.getY());

            if (s != null) {
                Color realColor;
                if(s.getType() == Line.NAME){
                    realColor = s.getStrokeColor();
                    s.setStrokeColor(Color.TOMATO);
                } else {
                    realColor = s.getFillColor();
                    s.setFillColor(Color.TOMATO);
                }
                drawAllShapes();
                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.millis(100),
                        ae -> {
//                            s.setFillColor(realColor);
                            if(s.getType() == Line.NAME){
                                s.setStrokeColor(realColor);
                            } else {
                                s.setFillColor(realColor);
                            }
                            drawAllShapes();
                        }
                ));
                timeline.play();
                enableActionButtons();
            }
        }
    }

    /**
     * Drag mouse on Primary key to change size an object drag mouse on
     * Secondary key to move the object to new position.
     *
     * @param me
     */
    public void draggedMouse(MouseEvent me) {
        if (me.getButton().equals(MouseButton.PRIMARY)) {
            if (selected != null && s != null) {
                s.resize(me.getX(), me.getY());
            }

        } else if (me.getButton().equals(MouseButton.SECONDARY)) {
            if (s != null) {
                s.move(me.getX(), me.getY());
            }
        }
        drawAllShapes();
    }

    /**
     * draw all shapes in array list.
     */
    private void drawAllShapes() {
        gc.setFill(Color.DARKSEAGREEN);
        gc.fillRect(0, 0, 1100, 500);
        for (Shape sp : shapes) {
            sp.draw(gc);
        }
    }

    /**
     * This is where you create your components and the model and add event
     * handlers.
     *
     * @param stage The main stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1100, 700); // set the size here
        stage.setTitle("FX GUI Template"); // set the window title here
        stage.setScene(scene);
        // TODO: Add your GUI-building code here

        // 1. Create the model
        //     ArrayList< Shape> shapes = new ArrayList<>();
        // 2. Create the GUI components
        c = new Canvas(1100, 500);
        circle = new Button("Circle");
        rectangle = new Button("Rectangle");
        line = new Button("Line");
        clear = new Button("Clear");
        reset = new Button("Reset Colors");
        delete = new Button("Delete");
        front = new Button("To front");
        back = new Button("To back");

        fillColor = new ColorPicker();
        outlineColor = new ColorPicker();

        width = new TextField();

        instruction = new Label("INSTRUCTION \n\n"
                + "1. To draw: Select the shape then left click and drag \n"
                + "2. To select shape: Right click on the shape \n"
                + "3. To move shape: Select shape first (2), then drag \n"
                + "4. To delete shape: Select shape first (2),\n then click on delete button \n"
                + "5. To set to front/back shape: Select shape first (2),\n then click on the front/back button \n"
        );
        outline = new Label("outline");
        fill = new Label("fill");
        strokeWidth = new Label("stroke width");
        select = new Label("Select");

        // 3. Add components to the root
        root.getChildren().addAll(c, circle, rectangle, line, clear, reset, delete, front, back, instruction, outline, fill, strokeWidth, select, width, fillColor, outlineColor);
        // 4. Configure the components (colors, fonts, size, location)
        gc = c.getGraphicsContext2D();
        gc.setFill(Color.DARKSEAGREEN);
        gc.fillRect(0, 0, 1100, 500);

        circle.relocate(10, 520);
        circle.setPrefWidth(100);
        circle.setFont(new Font("Arial", 15));
        circle.setStyle(" -fx-background-color: skyblue; -fx-text-fill: navy;");

        rectangle.relocate(130, 520);
        rectangle.setPrefWidth(100);
        rectangle.setFont(new Font("Arial", 15));
        rectangle.setStyle(" -fx-background-color: skyblue; -fx-text-fill: navy;");

        line.relocate(250, 520);
        line.setPrefWidth(100);
        line.setFont(new Font("Arial", 15));
        line.setStyle(" -fx-background-color: skyblue; -fx-text-fill: navy;");

        clear.relocate(420, 520);
        clear.setPrefWidth(100);
        clear.setFont(new Font("Arial", 15));
        clear.setStyle(" -fx-background-color: skyblue; -fx-text-fill: navy;");

        reset.relocate(540, 520);
        reset.setPrefWidth(150);
        reset.setFont(new Font("Arial", 15));
        reset.setStyle(" -fx-background-color: skyblue; -fx-text-fill: navy;");

        instruction.relocate(700, 520);
        instruction.setFont(new Font("Arial", 15));
        instruction.setStyle(" -fx-text-fill: navy;");

        outline.relocate(10, 580);
        outline.setFont(new Font("Arial", 20));
        outline.setStyle(" -fx-text-fill: navy;");

        fill.relocate(130, 580);
        fill.setFont(new Font("Arial", 20));
        fill.setStyle(" -fx-text-fill: navy;");

        strokeWidth.relocate(250, 580);
        strokeWidth.setFont(new Font("Arial", 20));
        strokeWidth.setStyle(" -fx-text-fill: navy;");

        outlineColor.relocate(10, 620);
        outlineColor.setPrefWidth(100);
        outlineColor.setStyle(" -fx-background-color: skyblue; -fx-text-fill: navy;");

        fillColor.relocate(130, 620);
        fillColor.setPrefWidth(100);
        fillColor.setStyle(" -fx-background-color: skyblue; -fx-text-fill: navy;");

        width.relocate(250, 620);
        width.setPrefWidth(100);
        width.setStyle(" -fx-background-color: lavender; -fx-text-fill: navy;");

        select.relocate(470, 580);
        select.setFont(new Font("Arial", 20));
        select.setStyle(" -fx-text-fill: navy;");

        delete.relocate(400, 620);
        delete.setPrefWidth(80);
        delete.setFont(new Font("Arial", 15));

        front.relocate(500, 620);
        front.setPrefWidth(80);
        front.setFont(new Font("Arial", 15));

        back.relocate(600, 620);
        back.setPrefWidth(80);
        back.setFont(new Font("Arial", 15));

        disableActionButtons();

        // 5. Add Event Handlers and do final setup
        circle.setOnAction(this::CircleHandler);
        rectangle.setOnAction(this::RectHandler);
        line.setOnAction(this::LineHandler);
        delete.setOnAction(this::DeleteHandler);
        front.setOnAction(this::MoveToFrontHandler);
        back.setOnAction(this::MoveToBackHandler);
        clear.setOnAction(this::ClearHandler);
        reset.setOnAction(this::ResetHandler);
        width.setText("1");

        c.addEventHandler(MouseEvent.MOUSE_PRESSED, this::pressedMouse);
        c.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::draggedMouse);
        c.addEventHandler(MouseEvent.MOUSE_CLICKED, this::clickedMouse);

        // 6. Show the stage
        stage.show();
    }

    /**
     * Make no changes here.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void selectButton(Button btn) {
        btn.setStyle(" -fx-background-color: tomato; -fx-text-fill: navy;");
    }

    private void unSelectButton(Button btn) {
        btn.setStyle(" -fx-background-color: skyblue; -fx-text-fill: navy;");
    }

    private void unSelectAllButtons() {
        unSelectButton(circle);
        unSelectButton(line);
        unSelectButton(rectangle);
    }

    private void disableButton(Button btn) {
        btn.setStyle(" -fx-background-color: gray; -fx-text-fill: white;");
    }

    private void enableButton(Button btn) {
        btn.setStyle(" -fx-background-color: skyblue; -fx-text-fill: navy;");
    }

    private void enableActionButtons() {
        enableButton(delete);
        enableButton(front);
        enableButton(back);
    }

    private void disableActionButtons() {
        disableButton(delete);
        disableButton(front);
        disableButton(back);
    }

    private Shape getShapeFromPoint(double x, double y) {
        Shape tmpShape = null;
        for (Shape sp : shapes) {
            if (sp.isPointInArea(x, y)) {
                tmpShape = sp;
            }
        }
        return tmpShape;
    }
}
