import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * An abstract class that represents a shape on the canvas.
 */
public abstract class MyShape implements EventResponsible {

    /**
     * A list of points to draw the shape.
     */
    protected List<Point2D> points;

    /**
     * The color of the shape.
     */
    protected Color color;

    /**
     * The selected state of the shape.
     */
    protected boolean selected;

    /**
     * Apply transform to the shape.
     * All points will be shifted a x and y distance.
     */
    protected Point2D transform;

    /**
     * A callback that is used to let the handler know
     * it should create a new shape in the next mouse click.
     */
    protected Runnable didFinishDrawingCallback;

    /**
     * Constructs a shape object.
     */
    public MyShape() {
        selected = false;
        transform = Point2D.ZERO;
    }

    /**
     * Setter for color.
     * @param color new color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Setter for selected.
     * @param selected selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Setter for transform.
     * @param transform new transform
     */
    public void setTransform(Point2D transform) {
        this.transform = transform;
    }

    /**
     * Setter for finish drawing callback.
     * @param didFinishDrawingCallback new callback
     */
    public void setDidFinishDrawingCallback(Runnable didFinishDrawingCallback) {
        this.didFinishDrawingCallback = didFinishDrawingCallback;
    }

    /**
     * Draws the shape using the context.
     * @param gc the context to draw on
     */
    public abstract void draw(final GraphicsContext gc);

    /**
     * Checks whether the shape is a valid shape.
     * A valid shape is a shape that doesn't have any null point.
     * @return true if the shape is valid.
     */
    public boolean isValidShape() {
        for (Point2D p : points)
            if (p == null)
                return false;

        return true;
    }

    /**
     * Check if a point is inside this shape.
     * @param point the point to check
     * @return true if the given point is inside this shape
     */
    public boolean contains(final Point2D point) {
        final Point2D topLeft = getTopLeftPoint();
        final Point2D bottomRight = getBottomRightPoint();

        return point.getX() >= topLeft.getX() && point.getX() <= bottomRight.getX()
                && point.getY() >= topLeft.getY() && point.getY() <= bottomRight.getY();
    }

    /**
     * Applies and persists the current transform distance
     * to all the points in this shape. After that, reset
     * the transform property back to zero.
     */
    public void applyTransform() {
        for (int i = 0; i < points.size(); i++) {
            Point2D p = points.get(i);
            points.set(i, new Point2D(p.getX() + transform.getX(), p.getY() + transform.getY()));
        }

        transform = Point2D.ZERO;
    }

    /**
     * Gets the top left point.
     * @return top left point
     */
    public Point2D getTopLeftPoint() {
        if (points.isEmpty())
            return null;

        double x = points.get(0).add(transform).getX();
        double y = points.get(0).add(transform).getY();

        for (int i = 1; i < points.size(); i++) {
            Point2D p = points.get(i).add(transform);
            if (p.getX() < x)
                x = p.getX();
            if (p.getY() < y)
                y = p.getY();
        }

        return new Point2D(x, y);
    }

    /**
     * Get the bottom right point.
     * @return bottom right point
     */
    public Point2D getBottomRightPoint() {
        if (points.isEmpty())
            return null;

        double x = points.get(0).add(transform).getX();
        double y = points.get(0).add(transform).getY();

        for (int i = 1; i < points.size(); i++) {
            Point2D p = points.get(i).add(transform);
            if (p.getX() > x)
                x = p.getX();
            if (p.getY() > y)
                y = p.getY();
        }

        return new Point2D(x, y);
    }

    /**
     * Gets the smallest rectangle bound that covers all the points of this shape.
     * @return
     */
    public Rectangle getBound() {
        final Point2D topLeft = getTopLeftPoint();
        final Point2D bottomRight = getBottomRightPoint();
        final Point2D delta = bottomRight.subtract(topLeft);
        return new Rectangle(topLeft.getX(), topLeft.getY(), delta.getX(), delta.getY());
    }
}
