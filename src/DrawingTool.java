import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.function.Supplier;

/**
 * A handler to handle drawing any shape.
 */
public class DrawingTool implements EventResponsible {

    /**
     * The canvas to draw.
     */
    private CanvasPane canvas;

    /**
     * The color of the shape.
     */
    private Color color;

    /**
     * Generates a new shape object.
     */
    private Supplier<MyShape> shapeSupplier;

    /**
     * Current being drawn shape.
     */
    private MyShape currentShape;

    /**
     * Constructs a new drawing handler.
     * @param canvas canvas to draw
     * @param shapeSupplier shape generator
     * @param color color of the shape
     */
    public DrawingTool(CanvasPane canvas, Supplier<MyShape> shapeSupplier, Color color) {
        this.canvas = canvas;
        this.shapeSupplier = shapeSupplier;
        this.color = color;
    }

    @Override
    public void handle(MouseEvent e) {
        // when the mouse is pressed, create a new shape and add to the canvas if there is no shape
        if (e.getEventType() == MouseEvent.MOUSE_PRESSED && currentShape == null)
            addNewShape();

        if (currentShape != null) {
            // clear the last shape if the user presses and releases immediately
            if (e.getEventType() == MouseEvent.MOUSE_RELEASED && !currentShape.isValidShape())
                canvas.getShapes().remove(currentShape);
            // otherwise, tell the shape to handle the mouse event
            else
                currentShape.handle(e);

            canvas.update();
        }
    }

    @Override
    public void handle(KeyEvent e) {
        if (currentShape != null)
            currentShape.handle(e);
    }

    /**
     * Adds a new shape object to the canvas.
     */
    private void addNewShape() {
        currentShape = shapeSupplier.get();
        currentShape.setDidFinishDrawingCallback(() -> currentShape = null);
        currentShape.setColor(color);
        canvas.getShapes().add(currentShape);
    }
}
