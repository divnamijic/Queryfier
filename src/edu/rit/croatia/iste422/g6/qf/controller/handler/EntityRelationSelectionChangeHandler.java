package edu.rit.croatia.iste422.g6.qf.controller.handler;

// Package imports
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.model.db.Attribute;
import edu.rit.croatia.iste422.g6.qf.model.db.Entity;
import edu.rit.croatia.iste422.g6.qf.model.db.EntityRelationship;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;

// JavaFX imports
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;

public class EntityRelationSelectionChangeHandler implements ChangeListener<TreeItem<String>> {

    private final QueryfierStorage storage;
    private final QueryfierDisplay display;

    public EntityRelationSelectionChangeHandler(QueryfierStorage storage, QueryfierDisplay display) {
        this.storage = storage;
        this.display = display;
    }

    @Override
    public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue,
            TreeItem<String> newValue) {
        if (newValue == null) {
            return;
        }

        if (!newValue.getParent().getValue().equals("Entities")) {

            String selectedEntity = newValue.getParent().getValue();
            String selectedAttribute = newValue.getValue();

            // Remove the selected attribute from the other tree view
            if (observable == display.getFirstEntityTreeView().getSelectionModel().selectedItemProperty()) {
                this.display.setFirstEntityText(selectedEntity);
                this.display.setFirstEntityAttributeText(selectedAttribute);

            } else if (observable == display.getSecondEntityTreeView().getSelectionModel().selectedItemProperty()) {
                this.display.setSecondEntityText(selectedEntity);
                this.display.setSecondEntityAttributeText(selectedAttribute);
            }
        } else if (newValue.getParent().getValue().equals("Entities")) {

            String selectedEntity = newValue.getValue();

            // Remove the selected attribute from the other tree view
            if (observable == display.getFirstEntityTreeView().getSelectionModel().selectedItemProperty()) {
                this.display.setFirstEntityText(selectedEntity);
                this.display.setFirstEntityAttributeText("");

            } else if (observable == display.getSecondEntityTreeView().getSelectionModel().selectedItemProperty()) {
                this.display.setSecondEntityText(selectedEntity);
                this.display.setSecondEntityAttributeText("");
            }

        }

        String firstEntityName = this.display.getFirstEntityText();
        String secondEntityName = this.display.getSecondEntityText();

        String firstEntityAttributeName = this.display.getFirstEntityAttributeText();
        String secondEntityAttributeName = this.display.getSecondEntityAttributeText();

        if (firstEntityAttributeName.isBlank() || secondEntityAttributeName.isBlank()) {
            return;
        }

        final Entity firstEntity = this.storage.findEntity(firstEntityName);
        final Entity secondEntity = this.storage.findEntity(secondEntityName);

        final Attribute firstEntityAttribute = firstEntity.getAttribute(firstEntityAttributeName);
        final Attribute secondEntityAttribute = secondEntity.getAttribute(secondEntityAttributeName);

        final EntityRelationship relationship = new EntityRelationship(firstEntity, firstEntityAttribute,
                secondEntity, secondEntityAttribute);

        boolean toggle = this.storage.getEntityRelationships().contains(relationship);

        this.display.toggleRelationshipLine(toggle);
    }

}
