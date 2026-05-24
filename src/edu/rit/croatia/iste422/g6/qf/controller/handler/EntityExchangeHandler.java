package edu.rit.croatia.iste422.g6.qf.controller.handler;

// Package imports
import edu.rit.croatia.iste422.g6.qf.controller.QueryfierManager;
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.model.db.Attribute;
import edu.rit.croatia.iste422.g6.qf.model.db.Entity;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;
import edu.rit.croatia.iste422.g6.qf.view.util.QueryfierStyle;

// Java imports
import java.util.List;

// JavaFX imports
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

public class EntityExchangeHandler implements EventHandler<Event> {

    private final QueryfierDisplay display;
    private final QueryfierStorage storage;
    private final QueryfierManager manager;

    public EntityExchangeHandler(QueryfierDisplay display, QueryfierStorage storage, QueryfierManager manager) {
        this.display = display;
        this.storage = storage;
        this.manager = manager;
    }

    @Override
    public void handle(Event event) {

        if (!(event.getSource() instanceof VBox)) {
            return;
        }

        for (Node node : this.display.getEntityGridPane().getChildren()) {
            if (node instanceof VBox vBox) {
                SVGPath svg = (SVGPath) vBox.getChildren().get(0);
                svg.setFill(QueryfierStyle.QF_COLOR_GRAY);
            }
        }

        VBox vBox = (VBox) event.getSource();

        SVGPath svg = (SVGPath) vBox.getChildren().get(0);
        svg.setFill(QueryfierStyle.QF_COLOR);

        String entityName = this.display.getSelectedEntity();
        final Entity entity = this.storage.findEntity(entityName);

        // User wants to edit the name of the entity
        // Check if the event came from the mouse,
        // primary mouse button and if it was double clicked
        if (event instanceof MouseEvent mouseEvent &&
                (mouseEvent.getButton().equals(MouseButton.PRIMARY))
                && (mouseEvent.getClickCount() == 2)) {
            String newEntityName = this.display.promptEditEntityNameDialog(entityName);

            // User canceled the operation
            if (newEntityName == null) {
                return;
            }

            // User entered the same name
            if (newEntityName.equals(entityName)) {
                return;
            }

            boolean nameExists = this.storage.getEntityList().stream()
                    .anyMatch(e -> e.getEntityName().equals(newEntityName));

            if (nameExists) {
                this.display.showSameNameEntityAlert();
                return;
            }

            entity.setEntityName(newEntityName);
            this.display.setSelectedEntityName(newEntityName);
        }

        final List<Attribute> attributesList = entity.getAttributes();

        if (attributesList.isEmpty()) {
            this.manager.defaultAttributeEditorScreen();
            return;
        }

        List<String> attributesViewList = attributesList.stream().map(Attribute::getAttributeName).toList();

        final Attribute attribute = attributesList.get(0);
        List<String> attributeInfoViewList = this.manager.getAttributeInfoViewList(attribute);

        this.display.setAttributesView(attributesViewList, "");
        this.display.setAttributeInfoView(attributeInfoViewList);
        this.manager.setAttributeDataTypeSelection(attribute.getDataType());
    }

}
