package edu.rit.croatia.iste422.g6.qf.controller.handler.attribute;

import java.util.List;

import org.apache.logging.log4j.core.appender.SyslogAppender;

// Package imports
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.model.db.Attribute;
import edu.rit.croatia.iste422.g6.qf.model.db.Entity;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;

// JavaFX imports
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

public class AttributePrimaryKeyHandler implements EventHandler<ActionEvent> {

    private final QueryfierDisplay display;
    private final QueryfierStorage storage;

    public AttributePrimaryKeyHandler(QueryfierDisplay display, QueryfierStorage storage) {
        this.display = display;
        this.storage = storage;
    }

    @Override
    public void handle(ActionEvent event) {

        boolean isToggled = event.getSource() instanceof CheckBox cBox && cBox.isSelected();

        final String entityName = this.display.getSelectedEntity();
        final String attributeName = this.display.getSelectedAttribute();

        if (entityName == null || attributeName == null) {
            return;
        }

        Entity entity = this.storage.findEntity(entityName);
        List<Attribute> attributesList = entity.getAttributes();
        List<String> attributesViewList = attributesList.stream().map(Attribute::getAttributeName).toList();
        Attribute attribute = this.storage.getAttributeByEntity(entityName, attributeName);

        System.out.println("weird but it mifh be" + attribute);
        if (isToggled == true) {
            attribute.setPrimaryKey(isToggled);
            System.out.print("Wowie!  Set pk: " + attribute);
        } else {
            attribute.setPrimaryKey(isToggled);
            System.out.print("Wowie! Unset pk: " + attribute);
        }

        // System.out.println(isToggled);
        this.display.setAttributesView(attributesViewList, attributeName);
    }

}
