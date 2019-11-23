import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;

/**
 * A square shape.
 */
public class Square extends MyShape {

    /**
     * Constructs a square object.
     */
    public Square() {
        points = Arrays.asList(null, null);
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (points.get(0) == null || points.get(1) == null)
            return;

        gc.setFill(color);
        Rectangle bound = getBound();
        gc.fillRect(bound.getX(), bound.getY(), bound.getWidth(), bound.getHeight());
    }

    @Override
    public void handle(MouseEvent e) {
        Point2D currentPoint = new Point2D(e.getX(), e.getY());
        if (e.getEventType() == MouseEvent.MOUSE_PRESSED)
            points.set(0, currentPoint);
        else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED)
            points.set(1, calcEndPoint(currentPoint));
        else if (e.getEventType() == MouseEvent.MOUSE_RELEASED)
            didFinishDrawingCallback.run();
    }

    /**
     * A helper method to calculate the end point based on the start point
     * and the dragging direction to make the shape to be a square.
     * @param endPoint current end point
     * @return the correct end point
     */
    private Point2D calcEndPoint(Point2D endPoint) {
        final Point2D startPoint = points.get(0).add(transform);
        final double width = Math.abs(startPoint.getX() - endPoint.getX());
        final double height = Math.abs(startPoint.getY() - endPoint.getY());
        final double side = Math.min(width, height);

        if (startPoint.getX() < endPoint.getX()) {
            // start \
            //        \
            //         \ end
            if (startPoint.getY() < endPoint.getY())
                return startPoint.add(side, side);
            //         / end
            //        /
            // start /
            else
                return startPoint.add(side, -side);
        } else {
            //       / start
            //      /
            // end /
            if (startPoint.getY() < endPoint.getY())
                return startPoint.add(-side, side);
            // end \
            //      \
            //       \ start
            else
                return startPoint.add(-side, -side);
        }
    }
}
