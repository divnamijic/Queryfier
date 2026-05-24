package edu.rit.croatia.iste422.g6.qf.controller.handler.menu;

// Package imports
import edu.rit.croatia.iste422.g6.qf.controller.QueryfierManager;
import edu.rit.croatia.iste422.g6.qf.io.parser.util.FileExtension;
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;

// Java imports
import java.io.File;

// JavaFX imports
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SaveAsProjectMenuHandler implements EventHandler<ActionEvent> {

    private final QueryfierStorage storage;
    private final QueryfierDisplay display;
    private final QueryfierManager manager;

    public SaveAsProjectMenuHandler(QueryfierStorage storage, QueryfierDisplay display, QueryfierManager manager) {
        this.storage = storage;
        this.display = display;
        this.manager = manager;
    }

    @Override
    public void handle(ActionEvent event) {

        if (this.manager.isStorageEmpty("Save As")) {
            return;
        }

        File previouslySavedFile = this.storage.getPreviousSaveLocation();
        File saveFile = this.display.promptSaveFileDialog(previouslySavedFile, "." + FileExtension.SAVE.getExtension());

        if (saveFile == null) {
            return;
        }

        this.storage.setPreviousSaveLocation(saveFile);

        boolean success = this.storage.saveFile(saveFile);

        this.display.showFileSaveFeedback(success);
    }

}
