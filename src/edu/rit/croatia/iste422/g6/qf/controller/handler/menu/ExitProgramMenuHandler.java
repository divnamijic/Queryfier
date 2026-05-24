package edu.rit.croatia.iste422.g6.qf.controller.handler.menu;

// Package imports
import edu.rit.croatia.iste422.g6.qf.io.parser.util.FileExtension;
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;
import edu.rit.croatia.iste422.g6.qf.util.FileUtil;

// Java imports
import java.io.File;

// JavaFX imports
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;

public class ExitProgramMenuHandler implements EventHandler<ActionEvent> {

    private final QueryfierStorage storage;
    private final QueryfierDisplay display;

    public ExitProgramMenuHandler(QueryfierStorage storage, QueryfierDisplay display) {
        this.storage = storage;
        this.display = display;
    }

    @Override
    public void handle(ActionEvent event) {

        File previouslySavedFile = this.storage.getPreviousSaveLocation();

        if (previouslySavedFile != null && FileUtil.removeExtension(previouslySavedFile.getName()).equals(this.display.getProjectTitleText())) {
            this.storage.saveConfiguration();
            Platform.exit();
            return;
        }

        ButtonType saveChoice = this.display.showUnsavedChanges();

        if (saveChoice.equals(ButtonType.CANCEL)) {
            return;
        }

        if (saveChoice.equals(ButtonType.YES) || saveChoice.equals(ButtonType.NO)) {
            this.storage.saveConfiguration();
            Platform.exit();
            return;
        }

        File file = this.display.promptSaveFileDialog(previouslySavedFile, "." + FileExtension.SAVE.getExtension());

        if (file == null) {
            this.storage.saveConfiguration();
            Platform.exit();
            return;
        }

        this.storage.setPreviousSaveLocation(file);
        
        boolean success = this.storage.saveFile(file);
        this.display.showFileSaveFeedback(success);
    }


}
