package edu.rit.croatia.iste422.g6.qf.controller.handler.menu;

// Package imports
import edu.rit.croatia.iste422.g6.qf.controller.QueryfierManager;
import edu.rit.croatia.iste422.g6.qf.io.parser.util.FileExtension;
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.util.FileUtil;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;

// Java imports
import java.io.File;

// JavaFX imports
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class SaveProjectMenuHandler implements EventHandler<ActionEvent> {

    private final QueryfierStorage storage;
    private final QueryfierDisplay display;
    private final QueryfierManager manager;

    public SaveProjectMenuHandler(QueryfierStorage storage, QueryfierDisplay display, QueryfierManager manager) {
        this.storage = storage;
        this.display = display;
        this.manager = manager;
    }

    @Override
    public void handle(ActionEvent event) {

        if (this.manager.isStorageEmpty("Save")) {
            return;
        }

        File previouslySavedFile = this.storage.getPreviousSaveLocation();

        if (previouslySavedFile != null && FileUtil.removeExtension(previouslySavedFile.getName()).equals(this.display.getProjectTitleText())) {
            boolean success = this.storage.saveFile(previouslySavedFile);

            String originalText = this.display.getFooterText();
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(setEvent -> {
                Platform.runLater(() -> {
                    String projectInfo = "Project Saved!";
                    if (!success) {
                        projectInfo = "Project was not saved!";
                    }
                    this.display.setProjectTitleText(projectInfo);
                });

                PauseTransition revertPause = new PauseTransition(Duration.seconds(2));
                revertPause.setOnFinished(revertEvent -> Platform.runLater(() -> this.display.setProjectTitleText(originalText)));

                revertPause.play();
            });

            pause.play();
            return;
        }

        File saveFile = this.display.promptSaveFileDialog(previouslySavedFile, "." + FileExtension.SAVE.getExtension());

        if (saveFile == null) {
            return;
        }

        this.storage.setPreviousSaveLocation(saveFile);

        boolean success = this.storage.saveFile(saveFile);

        this.display.showFileSaveFeedback(success);
    }


}