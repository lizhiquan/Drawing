import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * The toolbar that holds drawing tools and color picker.
 */
public class MyToolBar extends ToolBar {

    /**
     * Default selected color.
     */
    public static final Color DEFAULT_COLOR = Color.BLUE;

    /**
     * The canvas to draw on.
     */
    private CanvasPane canvas;

    /**
     * Color picker.
     */
    private ColorPicker colorPicker;

    /**
     * Group of radio buttons.
     */
    private ToggleGroup group;

    /**
     * The current selected tool.
     */
    private Tool selectedTool;

    /**
     * Constructs a toolbar.
     * @param canvas the canvas to draw
     */
    public MyToolBar(CanvasPane canvas) {
        this.canvas = canvas;
        group = new ToggleGroup();
        colorPicker = new ColorPicker(DEFAULT_COLOR);
        setupItems();
        setupHandlers();
    }

    /**
     * Sets up ui controls.
     */
    private void setupItems() {
        final RadioButton circleButton = new RadioButton("Circle");
        final RadioButton ovalButton = new RadioButton("Oval");
        final RadioButton rectangleButton = new RadioButton("Rectangle");
        final RadioButton squareButton = new RadioButton("Square");
        final RadioButton polygonButton = new RadioButton("Polygon");
        final RadioButton selectButton = new RadioButton("Select");

        circleButton.setToggleGroup(group);
        ovalButton.setToggleGroup(group);
        rectangleButton.setToggleGroup(group);
        squareButton.setToggleGroup(group);
        selectButton.setToggleGroup(group);
        polygonButton.setToggleGroup(group);

        circleButton.setUserData(Tool.CIRCLE);
        ovalButton.setUserData(Tool.OVAL);
        rectangleButton.setUserData(Tool.RECTANGLE);
        squareButton.setUserData(Tool.SQUARE);
        selectButton.setUserData(Tool.SELECT);
        polygonButton.setUserData(Tool.POLYGON);

        // default selection
        selectedTool = Tool.CIRCLE;
        circleButton.setSelected(true);
        canvas.setEventHandler(createEventHandler());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinWidth(Region.USE_PREF_SIZE);
        getItems().addAll(circleButton, ovalButton, rectangleButton,
                squareButton, polygonButton, spacer, selectButton, colorPicker);
    }

    /**
     * Sets up handlers.
     */
    private void setupHandlers() {
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                selectedTool = (Tool) group.getSelectedToggle().getUserData();
                canvas.setEventHandler(createEventHandler());
            }
        });

        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                canvas.setEventHandler(createEventHandler());
            }
        });
    }

    /**
     * Creates event handler depending on the selected tool and color.
     * @return a new event handler
     */
    private EventResponsible createEventHandler() {
        Color selectedColor = colorPicker.getValue();
        switch (selectedTool) {
            case OVAL:
                return new DrawingTool(canvas, Oval::new, selectedColor);
            case CIRCLE:
                return new DrawingTool(canvas, Circle::new, selectedColor);
            case SQUARE:
                return new DrawingTool(canvas, Square::new, selectedColor);
            case RECTANGLE:
                return new DrawingTool(canvas, Rectangle::new, selectedColor);
            case POLYGON:
                return new DrawingTool(canvas, Polygon::new, selectedColor);
            case SELECT:
                return new SelectionTool(canvas);
            default:
                return null;
        }
    }
}
