package edu.rit.croatia.iste422.g6.qf.controller.handler.edit;

// Package imports
import edu.rit.croatia.iste422.g6.qf.controller.QueryfierManager;
import edu.rit.croatia.iste422.g6.qf.controller.handler.EntityExchangeHandler;
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.model.db.Entity;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;

// JavaFX imports
import javafx.event.Event;
import javafx.event.EventHandler;

// Java imports
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class EntityAdditionHandler implements EventHandler<Event> {

    private final QueryfierStorage storage;
    private final QueryfierDisplay display;
    private final QueryfierManager manager;

    private final Random rand = new SecureRandom();

    public EntityAdditionHandler(QueryfierStorage storage, QueryfierDisplay display, QueryfierManager manager) {
        this.storage = storage;
        this.display = display;
        this.manager = manager;
    }

    @Override
    public void handle(Event event) {

        final List<Entity> entitiesList = this.storage.getEntityList();

        String randomEntityName = "Name";

        if (!entitiesList.isEmpty()) {
            List<String> entitiesViewList = entitiesList.stream().map(Entity::getEntityName).toList();
            randomEntityName = entitiesViewList.get(rand.nextInt(entitiesViewList.size()));
        }

        String newEntityName = this.display.promptAddEntityDialog(randomEntityName);

        if (newEntityName == null) {
            return;
        }

        boolean nameExists = this.storage.getEntityList().stream()
                .anyMatch(e -> e.getEntityName().equals(newEntityName));

        if (nameExists) {
            this.display.showSameNameEntityAlert();
            return;
        }

        boolean success = this.storage.addEntity(newEntityName);

        if (!success) {
            this.display.showError("There has been an issue trying to add new entity.");
            return;
        }

        EntityExchangeHandler eh = new EntityExchangeHandler(this.display, this.storage, this.manager);
        this.display.addEntityToEntitiesView(newEntityName, eh);

        this.display.disableEntityRemoveButton(false);
    }

}
