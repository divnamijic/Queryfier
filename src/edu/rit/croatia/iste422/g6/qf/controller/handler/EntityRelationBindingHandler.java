package edu.rit.croatia.iste422.g6.qf.controller.handler;

// Package imports
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.model.db.Attribute;
import edu.rit.croatia.iste422.g6.qf.model.db.Entity;
import edu.rit.croatia.iste422.g6.qf.model.db.EntityRelationship;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;

// JavaFX imports
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;

public class EntityRelationBindingHandler implements EventHandler<Event> {

    private final QueryfierStorage storage;
    private final QueryfierDisplay display;

    public EntityRelationBindingHandler(QueryfierStorage storage, QueryfierDisplay display) {
        this.storage = storage;
        this.display = display;
    }

    @Override
    public void handle(Event event) {

        // First Entity
        String firstEntityName = this.display.getFirstEntityText();
        final Entity firstEntity = this.storage.findEntity(firstEntityName);

        if (firstEntity == null) {
            return;
        }

        // First Attribute
        String firstEntityAttributeName = this.display.getFirstEntityAttributeText();
        final Attribute firstEntityAttribute = firstEntity.getAttribute(firstEntityAttributeName);

        if (firstEntityAttribute == null) {
            return;
        }

        // Second Entity
        String secondEntityName = this.display.getSecondEntityText();
        final Entity secondEntity = this.storage.findEntity(secondEntityName);

        if (secondEntity == null) {
            return;
        }

        // Second Attribute
        String secondEntityAttributeName = this.display.getSecondEntityAttributeText();
        final Attribute secondEntityAttribute = secondEntity.getAttribute(secondEntityAttributeName);

        if (secondEntityAttribute == null) {
            return;
        }

        // Create the relationship
        final EntityRelationship newRelationship = new EntityRelationship(
                firstEntity, firstEntityAttribute, secondEntity, secondEntityAttribute);

        // Unbind
        if (this.storage.getEntityRelationships().contains(newRelationship)) {
            boolean toggle = this.storage.getEntityRelationships().remove(newRelationship);
            this.display.toggleRelationshipLine(!toggle);
            return;
        }

        // Check if it is the same entity and same entity attributes
        if (firstEntity.equals(secondEntity) && firstEntityAttribute.equals(secondEntityAttribute)) {
            String title = "Queryfier - Same Entity";
            String header = "Same Entity selected!";
            String content = "Same entity attribute connections are not supported. Please choose another attribute.";
            this.display.showAlert(AlertType.INFORMATION, title, header, content);
            return;
        }

        // Check if the first entity is primary, if it is, ask the user to make it
        if (!firstEntityAttribute.isPrimaryKey()) {
            boolean makePrimary = this.display.showAttributeKeyMismatchAlert();
            if (!makePrimary) {
                return;
            }
            firstEntityAttribute.setPrimaryKey(true);
        }

        // Check if the data types match each other
        if (!firstEntityAttribute.getDataType().equals(secondEntityAttribute.getDataType())) {
            boolean makeSameDataType = this.display.showAttributeDataTypeMismatchAlert();
            if (!makeSameDataType) {
                return;
            }
            secondEntityAttribute.setDataType(firstEntityAttribute.getDataType());
        }

        // Add the new relationship to the storage
        boolean success = this.storage.getEntityRelationships().add(newRelationship);
        this.display.toggleRelationshipLine(success);
    }

}
