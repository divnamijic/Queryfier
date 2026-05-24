package edu.rit.croatia.iste422.g6.qf.controller.handler;

// Package imports
import edu.rit.croatia.iste422.g6.qf.controller.QueryfierManager;
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.util.ExceptionFormatter;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;

// Java imports
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.*;

// JavaFX imports
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.LoadException;

public class CreateProjectHandler implements EventHandler<Event> {

    final QueryfierStorage storage;
    final QueryfierDisplay display;
    final QueryfierManager manager;

    private static final Logger LOG = LogManager.getLogger();

    public CreateProjectHandler(QueryfierStorage storage, QueryfierDisplay display, QueryfierManager manager) {
        this.storage = storage;
        this.display = display;
        this.manager = manager;
    }

    @Override
    public void handle(Event event) {
        LOG.info("Starting the opening of the file.");

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
            LOG.fatal("Failed to load file.");
            return;
        }

        LOG.info("Finished loading the file.");

        try {
            this.display.goToAttributeEditorScreen();
        } catch (LoadException le) {
            String stackTrace = ExceptionFormatter.format(le);
            LOG.fatal("{}|{}", QueryfierDisplay.ATTRIBUTE_SCREEN_LOAD_ERROR, stackTrace);
            System.exit(0);
        } catch (IOException e) {
            LOG.fatal("Failed to go to the attribute screen.", e);
            System.exit(0);
        }

        this.storage.setPreviousOpenLocation(openFile);
        this.storage.getRecentFiles().put(openFile, this.storage.getEntityList().size());
        this.display.setProjectTitleText(openFile.getName());
        this.manager.setupAttributeEditorScreen();
    }

}
