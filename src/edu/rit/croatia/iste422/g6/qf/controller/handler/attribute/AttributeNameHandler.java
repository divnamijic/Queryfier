package edu.rit.croatia.iste422.g6.qf.controller.handler.attribute;

// Package imports
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.model.db.Attribute;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;

// JavaFX imports
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class AttributeNameHandler implements EventHandler<KeyEvent> {

    private final QueryfierDisplay display;
    private final QueryfierStorage storage;

    public AttributeNameHandler(QueryfierDisplay display, QueryfierStorage storage) {
        this.display = display;
        this.storage = storage;
    }

    @Override
    public void handle(KeyEvent event) {

        String newAttributeName = event.getSource() instanceof TextField tf ? tf.getText() : "";

        final String entityName = this.display.getSelectedEntity();
        final String attributeName = this.display.getSelectedAttribute();

        if (entityName == null || attributeName == null) {
            return;
        }

        Attribute attribute = this.storage.getAttributeByEntity(entityName, attributeName);

        attribute.setAttributeName(newAttributeName);

        this.display.renameCurrentlySelectedAttribute(newAttributeName);
    }

}
