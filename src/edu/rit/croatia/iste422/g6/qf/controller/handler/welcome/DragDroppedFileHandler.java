package edu.rit.croatia.iste422.g6.qf.controller.handler.welcome;

// Package imports
import edu.rit.croatia.iste422.g6.qf.controller.QueryfierManager;
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;

// Java imports
import java.io.File;
import java.io.IOException;

// Library imports
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// JavaFX imports
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;

public class DragDroppedFileHandler implements EventHandler<DragEvent> {

    private static final Logger LOG = LogManager.getLogger(DragDroppedFileHandler.class);

    private final QueryfierStorage storage;
    private final QueryfierDisplay display;
    private final QueryfierManager manager;

    public DragDroppedFileHandler(QueryfierStorage storage, QueryfierDisplay display, QueryfierManager manager) {
        this.storage = storage;
        this.display = display;
        this.manager = manager;
    }

    @Override
    public void handle(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {

            final File openFile = db.getFiles().get(0);

            boolean loaded = this.storage.loadFile(openFile);

            if (!loaded) {
                this.display.showError(QueryfierDisplay.FILE_LOADING_ERROR);
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

            success = true;
        }

        event.setDropCompleted(success);

        event.consume();
    }

}
