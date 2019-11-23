import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * The event handler interface used to create a handler that
 * handles mouse and keyboard events when using different tools.
 */
public interface EventResponsible {
    /**
     * Mouse events handler.
     * @param e a mouse event
     */
    void handle(MouseEvent e);

    /**
     * Keyboard events handler.
     * @param e a keyboard event
     */
    void handle(KeyEvent e);
}
