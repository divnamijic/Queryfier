package edu.rit.croatia.iste422.g6.qf;

// Package imports
import edu.rit.croatia.iste422.g6.qf.controller.QueryfierManager;
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;

// JavaFX imports
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * <h3>Main entry of Queryfier</h3>
 * 
 * It starts the application with the storage, display and the manager.
 * 
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Michel Brassard
 * @author Petra Cesar
 * @author Divna Mijic
 */
// The class has been created by "The Sixth Sense" Group.
public class QueryfierRunner extends Application {

    /**
     * The main method.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The method instantiates the storage and display and starts the stage on the
     * display.
     * <p>
     * After the display has been started. A new manager is started with the storage
     * and display.
     * 
     * @param {@inheritDoc}
     */
    @Override
    public void start(Stage stage) {

        // Initialization of storage and display
        final QueryfierStorage storage = new QueryfierStorage();
        final QueryfierDisplay display = new QueryfierDisplay();

        // Starting the stage
        display.start(stage);

        // Initialization of the manager
        new QueryfierManager(storage, display);
    }

}
