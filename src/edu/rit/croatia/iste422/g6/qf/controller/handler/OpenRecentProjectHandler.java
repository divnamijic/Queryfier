package edu.rit.croatia.iste422.g6.qf.controller.handler;

// Package imports
import edu.rit.croatia.iste422.g6.qf.controller.QueryfierManager;
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;

// Java imports
import java.util.stream.Collectors;
import java.io.File;
import java.io.IOException;

// Library imports
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// JavaFX imports
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OpenRecentProjectHandler implements EventHandler<Event> {

    private static final Logger LOG = LogManager.getLogger(OpenRecentProjectHandler.class);

    private final QueryfierStorage storage;
    private final QueryfierDisplay display;
    private final QueryfierManager manager;

    public OpenRecentProjectHandler(QueryfierStorage storage, QueryfierDisplay display, QueryfierManager manager) {
        this.storage = storage;
        this.display = display;
        this.manager = manager;
    }

    @Override
    public void handle(Event event) {

        String fileName = "";

        if (event.getSource() instanceof Button btn) {
            fileName = btn.getParent().getChildrenUnmodifiable().stream()
                    .filter(VBox.class::isInstance)
                    .flatMap(vbox -> ((VBox) vbox).getChildren().stream())
                    .filter(Label.class::isInstance)
                    .map(label -> ((Label) label).getText())
                    .collect(Collectors.joining());
        }

        if (event.getSource() instanceof HBox hBox) {
            fileName = hBox.getChildren().stream().filter(VBox.class::isInstance)
                    .flatMap(vbox -> ((VBox) vbox).getChildren().stream())
                    .filter(Label.class::isInstance)
                    .map(label -> ((Label) label).getText())
                    .collect(Collectors.joining());
        }

        if (fileName.isEmpty()) {
            return;
        }

        final File openFile = this.manager.findFile(fileName);

        if (openFile == null) {
            return;
        }

        boolean success = this.storage.loadFile(openFile);

        if (!success) {
            this.display.showError(QueryfierDisplay.FILE_LOADING_ERROR);
            return;
        }

        try {
            this.display.goToAttributeEditorScreen();
        } catch (IOException e) {

            LOG.error(QueryfierDisplay.ATTRIBUTE_SCREEN_LOAD_ERROR, e);

        }

        this.storage.setPreviousOpenLocation(openFile);
        this.storage.getRecentFiles().put(openFile, this.storage.getEntityList().size());
        this.display.setProjectTitleText(openFile.getName());
        this.manager.setupAttributeEditorScreen();
    }

}
