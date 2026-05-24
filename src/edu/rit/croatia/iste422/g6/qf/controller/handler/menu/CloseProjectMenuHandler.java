package edu.rit.croatia.iste422.g6.qf.controller.handler.menu;

// Package imports
import edu.rit.croatia.iste422.g6.qf.controller.QueryfierManager;
import edu.rit.croatia.iste422.g6.qf.io.parser.util.FileExtension;
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;
import edu.rit.croatia.iste422.g6.qf.util.*;

// Java imports
import java.io.File;

// JavaFX imports
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;

public class CloseProjectMenuHandler implements EventHandler<ActionEvent> {

    private final QueryfierStorage storage;
    private final QueryfierDisplay display;
    private final QueryfierManager manager;

    public CloseProjectMenuHandler(QueryfierStorage storage, QueryfierDisplay display, QueryfierManager manager) {
        this.storage = storage;
        this.display = display;
        this.manager = manager;
    }

    @Override
    public void handle(ActionEvent event) {

        File previouslySavedFile = this.storage.getPreviousSaveLocation();

        if (previouslySavedFile != null && FileUtil.removeExtension(previouslySavedFile.getName()).equals(this.display.getProjectTitleText())) {
            this.storage.saveConfiguration();
            this.manager.setupWelcomeScreen();
            return;
        }
        
        ButtonType saveChoice = this.display.showUnsavedChanges();

        if (saveChoice.equals(ButtonType.CANCEL)) {
            return;
        }

        if (saveChoice.equals(ButtonType.YES) || saveChoice.equals(ButtonType.NO)) {
            this.storage.saveConfiguration();
            this.manager.setupWelcomeScreen();
            return;
        }

        File file = this.display.promptSaveFileDialog(previouslySavedFile, "." + FileExtension.SAVE.getExtension());

        if (file == null) {
            this.storage.saveConfiguration();
            this.manager.setupWelcomeScreen();
            return;
        }

        this.storage.setPreviousSaveLocation(file);

        boolean success = this.storage.saveFile(file);
        this.display.showFileSaveFeedback(success);
    }


    
}
