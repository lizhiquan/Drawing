import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * The main class that drives the program.
 */
public class Main extends Application {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 800;

    @Override
    public void start(Stage primaryStage) throws Exception {
        CanvasPane canvas = new CanvasPane();
        VBox box = new VBox();
        box.getChildren().addAll(new MyToolBar(canvas), canvas);

        Scene scene = new Scene(box, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Let's draw!");
        primaryStage.setResizable(false);
        primaryStage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                canvas.getEventHandler().handle(event);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
