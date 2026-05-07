import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EventGUI extends Application {

    public EventGUI() {}

    private static ArrayList<Event> events;

    public static void setEvents(ArrayList<Event> ev) {
        events = ev;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Events GUI");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label title = new Label("All Events");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ListView<String> listView = new ListView<>();

        for (Event e : events) {
            String info = e.getTitle() + " - " + e.getLocation() + " - " + e.getDateTime().toString() + " - " + e.eventType();
            listView.getItems().add(info);
        }

        root.getChildren().addAll(title, listView);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
