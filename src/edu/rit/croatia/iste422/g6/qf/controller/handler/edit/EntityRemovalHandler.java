package edu.rit.croatia.iste422.g6.qf.controller.handler.edit;

// Package imports
import edu.rit.croatia.iste422.g6.qf.controller.QueryfierManager;
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.model.db.Attribute;
import edu.rit.croatia.iste422.g6.qf.model.db.Entity;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;
import edu.rit.croatia.iste422.g6.qf.view.util.QueryfierStyle;

// JavaFX imports
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

// Java imports
import java.util.List;

public class EntityRemovalHandler implements EventHandler<Event> {

    private final QueryfierStorage storage;
    private final QueryfierDisplay display;
    private final QueryfierManager manager;

    public EntityRemovalHandler(QueryfierStorage storage, QueryfierDisplay display, QueryfierManager manager) {
        this.storage = storage;
        this.display = display;
        this.manager = manager;
    }

    @Override
    public void handle(Event event) {

        if (event instanceof MouseEvent mouseEvent &&
                (mouseEvent.getButton().equals(MouseButton.PRIMARY))
                && !(mouseEvent.isControlDown())) {

            boolean delete = this.display.showDeleteEntityAlert();

            if (!delete) {
                return;
            }
        }

        String entityNameToDelete = this.display.getSelectedEntity();

        int deletedIndex = this.display.removeEntityFromEntitiesView(entityNameToDelete);

        if (deletedIndex == -1) {
            this.display.showError("There has been an issue trying to remove the entity.");
        }

        boolean success = this.storage.removeEntity(entityNameToDelete);

        if (!success) {
            this.display.showError("There has been an issue trying to remove the entity from the storage.");
        }

        final ObservableList<Node> entityNodes = this.display.getEntityGridPane().getChildren();

        if (entityNodes.isEmpty()) {
            this.display.disableEntityRemoveButton(true);
            this.display.resetAttributesView();
            List<String> defaultAttributeList = this.manager.getAttributeInfoViewList(new Attribute());
            this.display.setAttributeInfoView(defaultAttributeList);
            return;
        }

        int lastIndex = entityNodes.size() - 1;
        int index = Math.min(deletedIndex, lastIndex);

        VBox vBox = (VBox) entityNodes.get(index);
        SVGPath svg = (SVGPath) vBox.getChildren().get(0);
        svg.setFill(QueryfierStyle.QF_COLOR);

        String entityName = this.display.getSelectedEntity();

        Entity entity = this.storage.findEntity(entityName);
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
    }

}
