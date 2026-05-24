package edu.rit.croatia.iste422.g6.qf.controller.handler.welcome;

// Package imports
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.util.FileUtil;

// Java imports
import java.util.List;
import java.util.stream.Collectors;

// JavaFX imports
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

public class DragOverFileHandler implements EventHandler<DragEvent> {

    private final QueryfierStorage storage;

    public DragOverFileHandler(QueryfierStorage storage) {
        this.storage = storage;
    }

    @Override
    public void handle(DragEvent event) {
        if (event.getDragboard().hasFiles()) {

            final List<String> validExtensions = this.storage.getFileExtensionList();

            if (!validExtensions.containsAll(
                    event.getDragboard().getFiles().stream()
                            .map(file -> FileUtil.getExtension(file.getName()))
                            .collect(Collectors.toList()))) {

                event.consume();
                return;
            }
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }


}
