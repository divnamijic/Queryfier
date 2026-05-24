package edu.rit.croatia.iste422.g6.qf.controller.handler;

// Java imports
import java.util.List;

// Package imports
import edu.rit.croatia.iste422.g6.qf.controller.QueryfierManager;
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.model.db.Attribute;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;

// JavaFX imports
import javafx.event.Event;
import javafx.event.EventHandler;

public class EntityShowAttributesHandler implements EventHandler<Event> {

    private final QueryfierStorage storage;
    private final QueryfierDisplay display;
    private final QueryfierManager manager;

    public EntityShowAttributesHandler(QueryfierStorage storage, QueryfierDisplay display, QueryfierManager manager) {
        this.storage = storage;
        this.display = display;
        this.manager = manager;
    }

    @Override
    public void handle(Event event) {
        final String entityName = this.display.getSelectedEntity();
        final String attributeName = this.display.getSelectedAttribute();

        if (entityName == null || attributeName == null) {
            return;
        }

        Attribute attribute = this.storage.getAttributeByEntity(entityName, attributeName);

        final List<String> attributeInfoViewList = this.manager.getAttributeInfoViewList(attribute);

        this.display.setAttributeInfoView(attributeInfoViewList);

        this.manager.setAttributeDataTypeSelection(attribute.getDataType());
    }

}
