package edu.rit.croatia.iste422.g6.qf.controller.handler.attribute;

// Package imports
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.model.db.Attribute;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;

// JavaFX imports
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

public class AttributeAllowNullHandler implements EventHandler<ActionEvent> {

    private final QueryfierDisplay display;
    private final QueryfierStorage storage;

    public AttributeAllowNullHandler(QueryfierDisplay display, QueryfierStorage storage) {
        this.display = display;
        this.storage = storage;
    }

    @Override
    public void handle(ActionEvent event) {

        boolean newAttributeAllowNullValue = event.getSource() instanceof CheckBox cBox && cBox.isSelected();

        final String entityName = this.display.getSelectedEntity();
        final String attributeName = this.display.getSelectedAttribute();

        if (entityName == null || attributeName == null) {
            return;
        }

        Attribute attribute = this.storage.getAttributeByEntity(entityName, attributeName);

        attribute.setAllowNull(newAttributeAllowNullValue);
    }

}
