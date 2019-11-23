import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * A handler to handle selecting and moving any shape.
 */
public class SelectionTool implements EventResponsible {

    /**
     * The canvas to draw.
     */
    private CanvasPane canvas;

    /**
     * The current selected shape.
     */
    private MyShape selectedShape;

    /**
     * Current pressed point.
     */
    private Point2D pressedPoint;

    /**
     * Constructs a new selection handler.
     * @param canvas canvas to handle selection
     */
    public SelectionTool(CanvasPane canvas) {
        this.canvas = canvas;
    }

    @Override
    public void handle(MouseEvent e) {
        Point2D currentPoint = new Point2D(e.getX(), e.getY());

        if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
            pressedPoint = currentPoint;
            selectedShape = null;
            for (MyShape obj : canvas.getShapes())
                if (obj.contains(currentPoint)) {
                    selectedShape = obj;
                    obj.setSelected(true);
                } else
                    obj.setSelected(false);

        } else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            if (selectedShape != null) {
                Point2D transform = new Point2D(currentPoint.getX() - pressedPoint.getX(),
                        currentPoint.getY() - pressedPoint.getY());
                selectedShape.setTransform(transform);
            }

        } else if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
            if (selectedShape != null)
                selectedShape.applyTransform();
        }

        canvas.update();
    }

    @Override
    public void handle(KeyEvent e) {
        if (selectedShape != null && e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE) {
            canvas.getShapes().remove(selectedShape);
            canvas.update();
        }
    }
}
