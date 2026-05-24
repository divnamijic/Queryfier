package edu.rit.croatia.iste422.g6.qf.controller;

// Package imports
import edu.rit.croatia.iste422.g6.qf.controller.handler.CreateProjectHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.EntityExchangeHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.EntityRelationBindingHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.EntityRelationSelectionChangeHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.EntityShowAttributesHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.OpenRecentProjectHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.attribute.AttributeAllowNullHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.attribute.AttributeAutoIncrementHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.attribute.AttributeDataTypeHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.attribute.AttributeDefaultValueHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.attribute.AttributeLengthHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.attribute.AttributeNameHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.attribute.AttributePrimaryKeyHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.attribute.AttributeSignedHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.edit.EntityAdditionHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.edit.EntityRemovalHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.menu.AboutUsMenuHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.menu.CloseProjectMenuHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.menu.ContactUsMenuHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.menu.ExitProgramMenuHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.menu.OpenProjectMenuHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.menu.SaveAsProjectMenuHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.menu.SaveProjectMenuHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.welcome.DragDroppedFileHandler;
import edu.rit.croatia.iste422.g6.qf.controller.handler.welcome.DragOverFileHandler;
import edu.rit.croatia.iste422.g6.qf.model.QueryfierStorage;
import edu.rit.croatia.iste422.g6.qf.model.db.Attribute;
import edu.rit.croatia.iste422.g6.qf.model.db.AttributeDataType;
import edu.rit.croatia.iste422.g6.qf.model.db.Entity;
import edu.rit.croatia.iste422.g6.qf.util.ExceptionFormatter;
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;
import edu.rit.croatia.iste422.g6.qf.view.util.FileExportDefinition;

// Library imports
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Java imports
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

// JavaFX imports
import javafx.fxml.LoadException;

/**
 * <h3>Main Manager of Queryfier</h3>
 * 
 * The main controller class that contains the display and the storage
 * classes to establish communication between all three subsystems and ensure
 * the application runs smoothly and operates as intended.
 * 
 * @author Doroteja Krtalic
 * @author Swen Grgicevic
 * @author Michel Brassard
 * @author Petra Cesar
 * @author Divna Mijic
 */
// The class has been created by "The Sixth Sense" Group.
public class QueryfierManager {

    private static final Logger LOG = LogManager.getLogger(QueryfierManager.class);

    private final QueryfierStorage storage;
    private final QueryfierDisplay display;

    public QueryfierManager(QueryfierStorage storage, QueryfierDisplay display) {
        this.storage = storage;
        this.display = display;

        LOG.info("Initialization started");

        this.display.setOnCloseRequestHandler(event -> this.storage.saveConfiguration());

        this.storage.loadConfiguration();

        if (this.storage.getLastOpenProjectFile() != null) {
            this.storage.loadFile(this.storage.getLastOpenProjectFile());
            this.setupAttributeEditorScreen();
            return;
        }

        this.setupWelcomeScreen();

        LOG.info("Initialization finished");
    }

    public void setupWelcomeScreen() {

        LOG.info("Welcome Screen setup start");

        final Map<String, Integer> recentFilesMap = new LinkedHashMap<>();

        if (!this.storage.getRecentFiles().isEmpty()) {
            for (Entry<File, Integer> entry : this.storage.getRecentFiles().entrySet()) {
                recentFilesMap.put(entry.getKey().getName(), entry.getValue());
            }
        }

        try {
            this.display.goToWelcomeScreen();
        } catch (LoadException le) {
            this.handleLoadException(QueryfierDisplay.WELCOME_SCREEN_LOAD_ERROR, le);
        } catch (IOException e) {
            LOG.fatal(e.getMessage(), e);
            System.exit(0);
        }

        this.display.setRecentFiles(recentFilesMap, new OpenRecentProjectHandler(this.storage, this.display, this));
        this.setupWelcomeScreenHandlers();

        LOG.info("Welcome Screen setup finish");
    }

    public void setupAttributeEditorScreen() {

        LOG.info("Attribute Editor Screen setup start");

        final String projectTitle = this.display.getProjectTitleText();
        final String footerText = this.display.getFooterText();

        try {
            this.display.goToAttributeEditorScreen();
        } catch (LoadException le) {
            this.handleLoadException(QueryfierDisplay.ATTRIBUTE_SCREEN_LOAD_ERROR, le);
        } catch (IOException e) {
            LOG.fatal(e.getMessage(), e);
            System.exit(0);
        }

        this.display.setProjectTitleText(projectTitle);
        this.display.setFooterText(footerText);

        this.display.setAttributeDataTypeChoiceBox(this.storage.getAttributeDataTypes());

        final List<Entity> entitiesList = this.storage.getEntityList();

        if (!entitiesList.isEmpty()) {

            Entity entity = entitiesList.get(0);
            final List<Attribute> attributesList = entity.getAttributes();

            List<String> entitiesViewList = entitiesList.stream().map(Entity::getEntityName).toList();
            List<String> attributesViewList = attributesList.stream().map(Attribute::getAttributeName).toList();

            System.out.println("attributesViewList: " + attributesViewList);

            String pkAttribute = null;

            for (Attribute attribute : attributesList) {
                if (attribute.isPrimaryKey()) {
                    pkAttribute = attribute.getAttributeName();
                    System.out.println("Found PK: " + pkAttribute);
                    break;
                } else {
                    System.out.println("Not PK: " + attribute.getAttributeName());
                }
            }
            final Attribute attribute = attributesList.get(0);
            List<String> attributeInfoViewList = this.getAttributeInfoViewList(attribute);

            this.display.setEntitiesView(entitiesViewList, new EntityExchangeHandler(this.display, this.storage, this));
            this.display.setAttributesView(attributesViewList, pkAttribute);
            this.display.setAttributeInfoView(attributeInfoViewList);

            this.setAttributeDataTypeSelection(attribute.getDataType());
        } else {
            this.defaultAttributeEditorScreen();
            this.display.disableEntityRemoveButton(true);
        }

        this.setupAttributeEditorScreenHandlers();

        LOG.info("Attribute Editor Screen setup finish");
    }

    public List<String> getAttributeInfoViewList(Attribute attribute) {
        final List<String> attributeInfoViewList = new ArrayList<>();

        attributeInfoViewList.add(attribute.getAttributeName());
        attributeInfoViewList.add(attribute.getDataType().name());
        attributeInfoViewList.add(String.valueOf(attribute.getInputLength()));
        attributeInfoViewList.add(attribute.getDefaultValue());
        attributeInfoViewList.add(String.valueOf(attribute.isPrimaryKey()));
        attributeInfoViewList.add(String.valueOf(attribute.allowsNull()));
        attributeInfoViewList.add(String.valueOf(attribute.isSigned()));
        attributeInfoViewList.add(String.valueOf(attribute.isAutoIncremented()));

        return attributeInfoViewList;
    }

    public void defaultAttributeEditorScreen() {
        this.display.resetAttributesView();
        List<String> defaultAttributeList = this.getAttributeInfoViewList(new Attribute());
        this.display.setAttributeInfoView(defaultAttributeList);
        this.display.disableAttributeRemoveButton(true);
    }

    public void setAttributeDataTypeSelection(AttributeDataType dataType) {
        this.display.disableLengthTextField(!dataType.allowsLength());
        this.display.disableAutoIncrementCheckBox(!dataType.allowsAutoIncrement());
        this.display.disableSignedCheckBox(!dataType.isSignable());
    }

    public void setupEntityRelationsEditorScreen() {
        LOG.info("Entity Relations Editor Screen setup start");

        final String projectTitle = this.display.getProjectTitleText();
        final String footerText = this.display.getFooterText();

        try {
            this.display.goToEntityRelationsEditorScreen();
        } catch (LoadException le) {
            this.handleLoadException(QueryfierDisplay.ENTITY_RELATIONS_SCREEN_LOAD_ERROR, le);
            System.exit(0);
        } catch (IOException e) {
            LOG.fatal(e.getMessage(), e);
            System.exit(0);
        }

        this.setupEntityRelationsTreeView();

        this.display.setProjectTitleText(projectTitle);
        this.display.setFooterText(footerText);

        this.setupEntityRelationsEditorScreenHandlers();

        LOG.info("Entity Relations Editor Screen setup finish");
    }

    public void setupEntityRelationsTreeView() {
        final Map<String, List<String>> entityAttributesMap = new LinkedHashMap<>();

        for (final Entity entity : this.storage.getEntityList()) {
            List<String> attributeList = entity.getAttributes().stream().map(Attribute::getAttributeName).toList();
            entityAttributesMap.put(entity.getEntityName(), attributeList);
        }
        this.display.setEntitiesTreeView(entityAttributesMap);
    }

    public void setupMenuItemHandlers() {
        LOG.info("Menu Item Handlers setup start");

        // File Menu
        this.display.addEHOnOpenProjectMenuItem(new OpenProjectMenuHandler(this.storage, this.display, this));
        this.display.addEHOnSaveProjectMenuItem(new SaveProjectMenuHandler(this.storage, this.display, this));
        this.display.addEHOnSaveAsProjectMenuItem(new SaveAsProjectMenuHandler(this.storage, this.display, this));
        this.display.addEHOnExportProjectMenuItem(event -> this.exportFile());
        this.display.addEHOnCloseProjectMenuItem(new CloseProjectMenuHandler(this.storage, this.display, this));
        this.display.addEHOnExitProgramMenuItem(new ExitProgramMenuHandler(this.storage, this.display));

        // Edit Menu
        this.display.addEHOnAttributeEditorScreenMenuItem(event -> this.setupAttributeEditorScreen());
        this.display.addEHOnEntityRelationsEditorScreenMenuItem(event -> this.setupEntityRelationsEditorScreen());

        // Help Menu
        this.display.addEHOnContactMenuItem(new ContactUsMenuHandler(this.display));
        this.display.addEHOnAboutUsMenuItem(new AboutUsMenuHandler(this.display));

        // Export Menu Item
        this.display.addEHOnExportFileIcon(event -> this.exportFile());

        LOG.info("Menu Item Handlers setup finish");
    }

    public void setupWelcomeScreenHandlers() {
        LOG.info("Welcome Screen Handlers setup start");

        final CreateProjectHandler createHandler = new CreateProjectHandler(this.storage, this.display, this);
        this.display.addEHOnDragAndDropBoxClick(createHandler);
        this.display.addEHOnDragAndDropBoxDragOver(new DragOverFileHandler(this.storage));
        this.display.addEHOnDragAndDropBoxDragDropped(new DragDroppedFileHandler(this.storage, this.display, this));
        this.display.addEHOnOpenNewProjectButton(createHandler);

        LOG.info("Welcome Screen Handlers setup finish");
    }

    public void setupAttributeEditorScreenHandlers() {
        LOG.info("Attribute Editor Screen Handlers setup start");

        this.setupMenuItemHandlers();

        this.display.addEHOnDefineEntityRelationsButton(event -> this.setupEntityRelationsEditorScreen());

        this.display.addEHOnEntityAddButton(new EntityAdditionHandler(this.storage, this.display, this));
        this.display.addEHOnEntityRemoveButton(new EntityRemovalHandler(this.storage, this.display, this));
        this.display.addEHOnAttributeAddButton(null);
        this.display.addEHOnAttributeRemoveButton(null);

        this.display.addEHOnAttributeListView(new EntityShowAttributesHandler(this.storage, this.display, this));

        this.display.addEHOnAttributeNameTextField(new AttributeNameHandler(this.display, this.storage));
        this.display.addEHOnDataTypeChoiceBox(new AttributeDataTypeHandler(this.display, this.storage, this));
        this.display.addEHOnLengthTextField(new AttributeLengthHandler(this.display, this.storage));
        this.display.addEHOnDefaultValueTextField(new AttributeDefaultValueHandler(this.display, this.storage));
        this.display.addEHOnPrimaryKeyCheckBox(new AttributePrimaryKeyHandler(this.display, this.storage));
        this.display.addEHOnAllowNullCheckBox(new AttributeAllowNullHandler(this.display, this.storage));
        this.display.addEHOnSignedCheckBox(new AttributeSignedHandler(this.display, this.storage));
        this.display.addEHOnAutoIncrementCheckBox(new AttributeAutoIncrementHandler(this.display, this.storage));

        LOG.info("Attribute Editor Screen Handlers setup finish");
    }

    public void setupEntityRelationsEditorScreenHandlers() {
        LOG.info("Entity Relations Editor Screen Handlers setup start");

        this.setupMenuItemHandlers();

        this.display.addEHOnDefineEntityAttributeButton(event -> this.setupAttributeEditorScreen());
        this.display
                .addEHOnEntityRelationTreeView(new EntityRelationSelectionChangeHandler(this.storage, this.display));
        this.display.addEHOnBindUnbindEntityAttribute(new EntityRelationBindingHandler(this.storage, this.display));

        LOG.info("Entity Relations Editor Screen Handlers setup finish");
    }

    /**
     * Exports a file based on user input and displays feedback to the user.
     * <p>
     * This method retrieves available database systems from the storage, prompts
     * the user for an export file location and database system, and then performs
     * the export. It also updates the previous export location and displays
     * feedback to the user regarding the success of the export operation.
     */
    public void exportFile() {

        if (this.isStorageEmpty("Export")) {
            return;
        }

        List<String> dbSystems = this.storage.getAvailableDBSystems();
        File previouslyExportedFile = this.storage.getPreviousExportLocation();
        FileExportDefinition exportFileDef = this.display.promptExportFileDialog(previouslyExportedFile, dbSystems);

        if (exportFileDef == null) {
            return;
        }

        this.storage.setPreviousExportLocation(exportFileDef.exportFile());
        boolean success = this.storage.exportFile(exportFileDef.exportFile(), exportFileDef.databaseSystem());
        this.display.showFileExportFeedback(success);
    }

    public boolean isStorageEmpty(String action) {

        if (this.storage.getEntityList().isEmpty()) {
            this.display.showAtLeastEntityAlert(action);
            return true;
        }

        for (Entity entity : this.storage.getEntityList()) {
            if (entity.getAttributes().isEmpty()) {
                this.display.showAtLeastAttributeAlert(action);
                return true;
            }
        }

        return false;
    }

    /**
     * Handles a load exception by capturing its stack trace and logging it in a
     * specific format
     *
     * @param message Message to include in the log
     * @param load    The LoadException to handle
     */
    private void handleLoadException(String message, LoadException load) {
        String stackTrace = ExceptionFormatter.format(load);
        LOG.fatal("{}|{}", message, stackTrace);
        System.exit(0);
    }

    /**
     * Finds a file with the specified file name among the recent files from the
     * storage
     *
     * @param fileName The name of the file to search for
     * @return The File object representing the found file, or {@code null} if no
     *         matching file is found
     */
    public File findFile(String fileName) {
        for (final Entry<File, Integer> entry : this.storage.getRecentFiles().entrySet()) {
            if (entry.getKey().getName().equals(fileName)) {
                return entry.getKey();
            }
        }
        return null;
    }

}