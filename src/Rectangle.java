import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;

/**
 * A rectangle object.
 */
public class Rectangle extends MyShape {

    /**
     * Constructs a rectangle object.
     */
    public Rectangle() {
        points = Arrays.asList(null, null);
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (points.get(0) == null || points.get(1) == null)
            return;

        gc.setFill(color);
        javafx.scene.shape.Rectangle bound = getBound();
        gc.fillRect(bound.getX(), bound.getY(), bound.getWidth(), bound.getHeight());
    }

    @Override
    public void handle(MouseEvent e) {
        Point2D currentPoint = new Point2D(e.getX(), e.getY());
        if (e.getEventType() == MouseEvent.MOUSE_PRESSED)
            points.set(0, currentPoint);
        else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED)
            points.set(1, currentPoint);
        else if (e.getEventType() == MouseEvent.MOUSE_RELEASED)
            didFinishDrawingCallback.run();
    }
}
