package edu.rit.croatia.iste422.g6.qf.controller.handler.menu;

// Package imports
import edu.rit.croatia.iste422.g6.qf.controller.QueryfierManager;
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;

// Java imports
import java.io.File;
import java.io.IOException;
import java.util.List;

// Library imports
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// JavaFX imports
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class OpenProjectMenuHandler implements EventHandler<ActionEvent> {

    private static final Logger LOG = LogManager.getLogger(OpenProjectMenuHandler.class);

    private final QueryfierStorage storage;
    private final QueryfierDisplay display;
    private final QueryfierManager manager;

    public OpenProjectMenuHandler(QueryfierStorage storage, QueryfierDisplay display, QueryfierManager manager) {
        this.storage = storage;
        this.display = display;
        this.manager = manager;
    }

    @Override
    public void handle(ActionEvent event) {

        final File previousOpenFile = this.storage.getPreviousOpenLocation();
        final List<String> allowedFileExtensions = this.storage.getFileExtensionList().stream()
                .map(extension -> "*." + extension).toList();

        final File openFile = this.display.promptOpenFileDialog(previousOpenFile, allowedFileExtensions);

        if (openFile == null) {
            return;
        }

        boolean success = this.storage.loadFile(openFile);

        if (!success) {
            this.display.showError(QueryfierDisplay.FILE_LOADING_ERROR);
            return;
        }

        if (this.display.getAttributeListView() != null) {
            try {
                this.display.goToAttributeEditorScreen();
            } catch (IOException e) {
                
                LOG.error(QueryfierDisplay.ATTRIBUTE_SCREEN_LOAD_ERROR, e);
            }

            this.storage.setPreviousOpenLocation(openFile);
            this.storage.getRecentFiles().put(openFile, this.storage.getEntityList().size());
            this.display.setProjectTitleText(openFile.getName());
            this.manager.setupAttributeEditorScreen();

        } else {
            try {
                this.display.goToEntityRelationsEditorScreen();
            } catch (IOException e) {
                
                LOG.error(QueryfierDisplay.ENTITY_RELATIONS_SCREEN_LOAD_ERROR, e);
            }

            this.storage.setPreviousOpenLocation(openFile);
            this.storage.getRecentFiles().put(openFile, this.storage.getEntityList().size());
            this.display.setProjectTitleText(openFile.getName());
            this.manager.setupEntityRelationsEditorScreen();
        }

    }

}
