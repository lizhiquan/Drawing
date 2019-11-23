import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;

/**
 * A circle shape.
 */
public class Circle extends MyShape {

    /**
     * Constructs a circle object.
     */
    public Circle() {
        // [0]: center
        // [1]: end point
        points = Arrays.asList(null, null);
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (points.get(0) == null || points.get(1) == null)
            return;

        gc.setFill(color);
        Rectangle bound = getBound();
        gc.fillOval(bound.getX(), bound.getY(), bound.getWidth(), bound.getHeight());
    }

    @Override
    public Point2D getTopLeftPoint() {
        final Point2D startPoint = points.get(0).add(transform);
        final Point2D endPoint = points.get(1).add(transform);
        final double radius = endPoint.distance(startPoint);
        return new Point2D(startPoint.getX() - radius, startPoint.getY() - radius);
    }

    @Override
    public Point2D getBottomRightPoint() {
        final Point2D startPoint = points.get(0).add(transform);
        final Point2D endPoint = points.get(1).add(transform);
        final double radius = endPoint.distance(startPoint);
        return new Point2D(startPoint.getX() + radius, startPoint.getY() + radius);
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
