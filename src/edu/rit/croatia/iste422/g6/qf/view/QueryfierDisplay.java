package edu.rit.croatia.iste422.g6.qf.view;

// Package imports
import edu.rit.croatia.iste422.g6.qf.view.util.DialogCreator;
import edu.rit.croatia.iste422.g6.qf.view.util.FileExportDefinition;
import edu.rit.croatia.iste422.g6.qf.view.util.RemoveStyling;
import edu.rit.croatia.iste422.g6.qf.view.util.QueryfierStyle;

// Java imports
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

// Java stage imports
import javafx.stage.FileChooser;
import javafx.stage.Stage;

// JavaFX imports
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;

/**
 * <h3>Main Display of Queryfier</h3>
 * 
 * The main GUI class in the program, responsible for displaying the interface
 * to the user in order for the program to receive user input. This class
 * contains the goToScreen() methods which loads the respective FXML file,
 * setting up and showing the scene with all the loaded data &&
 * the FXML controller set in place.
 * 
 * @author Doroteja Krtalic
 * @author Swen Grgicevic
 * @author Michel Brassard
 * @author Petra Cesar
 * @author Divna Mijic
 */
// The class has been created by "The Sixth Sense" Group.
public class QueryfierDisplay extends Application {

    private Stage stage;
    private Parent root;

    // Declaring window titles for all possible screens
    private static final String TITLE = "Queryfier";
    private static final String WELCOME_SCREEN_TITLE = TITLE + " - Welcome";
    private static final String ATTRIBUTE_SCREEN_TITLE = TITLE + " - Attribute Editor";
    private static final String ENTITY_SCREEN_TITLE = TITLE + " - Entity Relations Editor";

    // Declaring the initial paths for Screens and assets
    private static final String SCREEN_PATH = "screens/";
    private static final String ASSETS_PATH = "assets/";
    private static final String MEDIA_PATH = ASSETS_PATH + "media/";

    private static final String QF_ICON = MEDIA_PATH + "qfIcon.png";

    // Declaring the Screens
    private static final String WELCOME_SCREEN = SCREEN_PATH + "qfWelcomeScreen.fxml";
    private static final String ATTRIBUTE_SCREEN = SCREEN_PATH + "qfAttributeEditorScreen.fxml";
    private static final String ENTITY_SCREEN = SCREEN_PATH + "qfEntityRelationsEditorScreen.fxml";

    // Error Messages
    public static final String FILE_LOADING_ERROR = "Unable to load the file.";
    public static final String FILE_SAVING_ERROR = "Unable to save the file.";
    public static final String WELCOME_SCREEN_LOAD_ERROR = "Loading of Welcome screen failed.";
    public static final String ATTRIBUTE_SCREEN_LOAD_ERROR = "Loading of Attribute Editor screen failed.";
    public static final String ENTITY_RELATIONS_SCREEN_LOAD_ERROR = "Loading of Entity Relations Editor screen failed.";

    // Whole Program

    @FXML
    private Text qfFooterText;

    // Welcome Screen

    @FXML
    private Button openNewProjectButton;

    @FXML
    private VBox recentFilesVBox;

    @FXML
    private HBox dragAndDropHBox;

    // Attribute Editor & Entity Relations Screen

    @FXML
    private Text projectTitleText;

    @FXML
    private VBox exportIconVBox;

    @FXML
    private MenuItem openFileMenuItem;
    @FXML
    private MenuItem saveFileMenuItem;
    @FXML
    private MenuItem saveAsFileMenuItem;
    @FXML
    private MenuItem exportFileMenuItem;
    @FXML
    private MenuItem closeProjectMenuItem;
    @FXML
    private MenuItem exitProgramMenuItem;

    @FXML
    private MenuItem attributeEditorScreenMenuItem;
    @FXML
    private MenuItem entityRelationsEditorScreenMenuItem;

    @FXML
    private MenuItem contactMenuItem;
    @FXML
    private MenuItem aboutUsMenuItem;

    // Attribute Editor Screen

    @FXML
    private GridPane entityGridPane;

    @FXML
    private Button defineEntityRelationsButton;
    @FXML
    private Button entityAddButton;
    @FXML
    private Button entityRemoveButton;

    @FXML
    private Button attributeAddButton;
    @FXML
    private Button attributeRemoveButton;

    @FXML
    private ListView<String> attributeListView;

    @FXML
    private TextField attributeNameTextField;

    @FXML
    private ChoiceBox<String> dataTypeChoiceBox;

    @FXML
    private Text lengthText;
    @FXML
    private TextField lengthTextField;

    @FXML
    private TextField defaultValueTextField;

    @FXML
    private CheckBox primaryKeyCheckBox;
    @FXML
    private CheckBox allowNullCheckBox;
    @FXML
    private Text signedText;
    @FXML
    private CheckBox signedCheckBox;
    @FXML
    private Text autoIncrementText;
    @FXML
    private CheckBox autoIncrementCheckBox;

    // Entity Relations Editor Screen

    @FXML
    private TreeView<String> firstEntityTreeView;
    @FXML
    private TreeView<String> secondEntityTreeView;

    @FXML
    private Button defineEntityAttributeButton;

    @FXML
    private TextField firstEntityTextField;
    @FXML
    private TextField secondEntityTextField;

    @FXML
    private TextField firstEntityAttributeTextField;
    @FXML
    private TextField secondEntityAttributeTextField;

    @FXML
    private Button bindUnbindButton;

    @FXML
    private Rectangle visualConnectorRectangle;

    @FXML
    private Label firstEntityVisualLabel;
    @FXML
    private Label secondEntityVisualLabel;

    @FXML
    private Label firstEntityAttributeVisualLabel;
    @FXML
    private Label secondEntityAttributeVisualLabel;

    /**
     * The start() method takes in a Stage object in its parameters and sets it in
     * the display, along with the title of the application at the top of the
     * window by using the correct constant.
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.stage.getIcons().addAll(new Image(this.getClass().getResourceAsStream("/" + QF_ICON)));
    }

    /**
     * Returns the URL of a file located at the specified file path relative to the
     * root of the class path.
     *
     * @param fileURL The relative file path within the class path, including the
     *                filename.
     * @return The URL of the specified file, or null if the file is not found.
     */
    private URL getFileURL(String fileURL) {
        return this.getClass().getResource("/" + fileURL);
    }

    /**
     * Creates a new scene, and shows it
     */
    private void showScreen() {

        // Get the previous scene
        final Scene oldScene = this.stage.getScene();
        final Scene scene;

        // If the previous scene is not null,
        // set the new scene's width and height to the old scene,
        // Otherwise, just create a new scene
        if (oldScene != null) {
            scene = new Scene(this.root, oldScene.getWidth(), oldScene.getHeight());
        } else {
            scene = new Scene(this.root);
        }

        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * Prepares the screen for further usage.
     * The method loads the screen, sets this class attribute controller as its
     * controller, sets the main class to be this class and loads the
     * root from the file. Furthermore it removes any styling from the screen.
     * 
     * @param screenURL the path to the screen to prepare
     * @throws IOException if an error occurs during loading
     */
    private void prepareScreen(String screenURL) throws IOException {
        final FXMLLoader loader = new FXMLLoader(this.getFileURL(screenURL));

        // The controller class is the display, more accurately the current display
        // ("this") passed as a param
        loader.setController(this);

        // load() => parse fxml, inject FXML, initialize
        this.root = loader.load();
        RemoveStyling.of(this.root);
    }

    /**
     * Navigates to the welcome screen, preparing and displaying it
     *
     * @throws IOException If an error occurs while loading the welcome screen
     */
    public void goToWelcomeScreen() throws IOException {
        this.prepareScreen(WELCOME_SCREEN);
        this.stage.setTitle(WELCOME_SCREEN_TITLE);
        this.showScreen(); // Show the loaded screen
    }

    /**
     * Navigates to the attribute editor screen, preparing and displaying it
     *
     * @throws IOException If an error occurs while loading the attribute editor
     *                     screen
     */
    public void goToAttributeEditorScreen() throws IOException {
        this.prepareScreen(ATTRIBUTE_SCREEN);
        this.stage.setTitle(ATTRIBUTE_SCREEN_TITLE);
        this.showScreen(); // Show the loaded screen
    }

    /**
     * Navigates to the entity relations editor screen, preparing and displaying it
     *
     * @throws IOException If an error occurs while loading the entity relations
     *                     editor screen
     */
    public void goToEntityRelationsEditorScreen() throws IOException {
        this.prepareScreen(ENTITY_SCREEN);
        this.stage.setTitle(ENTITY_SCREEN_TITLE);
        this.showScreen(); // Show the loaded screen
    }

    public void setOnCloseRequestHandler(EventHandler<WindowEvent> eh) {
        this.stage.setOnCloseRequest(eh);
    }

    // Getters for TreeView
    public TreeView<String> getFirstEntityTreeView() {
        return firstEntityTreeView;
    }

    public TreeView<String> getSecondEntityTreeView() {
        return secondEntityTreeView;
    }

    // MENU ITEMS

    // File

    public void addEHOnOpenProjectMenuItem(EventHandler<ActionEvent> ehAe) {
        this.openFileMenuItem.setOnAction(ehAe);
    }

    public void addEHOnSaveProjectMenuItem(EventHandler<ActionEvent> ehAe) {
        this.saveFileMenuItem.setOnAction(ehAe);
    }

    public void addEHOnSaveAsProjectMenuItem(EventHandler<ActionEvent> ehAe) {
        this.saveAsFileMenuItem.setOnAction(ehAe);
    }

    public void addEHOnExportProjectMenuItem(EventHandler<ActionEvent> ehAe) {
        this.exportFileMenuItem.setOnAction(ehAe);
    }

    public void addEHOnCloseProjectMenuItem(EventHandler<ActionEvent> ehAe) {
        this.closeProjectMenuItem.setOnAction(ehAe);
    }

    public void addEHOnExitProgramMenuItem(EventHandler<ActionEvent> ehAe) {
        this.exitProgramMenuItem.setOnAction(ehAe);
    }

    // Edit

    public void addEHOnAttributeEditorScreenMenuItem(EventHandler<ActionEvent> ehAe) {
        this.attributeEditorScreenMenuItem.setOnAction(ehAe);
    }

    public void addEHOnEntityRelationsEditorScreenMenuItem(EventHandler<ActionEvent> ehAe) {
        this.entityRelationsEditorScreenMenuItem.setOnAction(ehAe);
    }

    // Help

    public void addEHOnContactMenuItem(EventHandler<ActionEvent> ehAe) {
        this.contactMenuItem.setOnAction(ehAe);
    }

    public void addEHOnAboutUsMenuItem(EventHandler<ActionEvent> ehAe) {
        this.aboutUsMenuItem.setOnAction(ehAe);
    }

    // WELCOME SCREEN

    public void addEHOnDragAndDropBoxClick(EventHandler<Event> eh) {
        this.dragAndDropHBox.setOnMouseClicked(eh);
    }

    public void addEHOnDragAndDropBoxDragOver(EventHandler<DragEvent> ehDe) {
        this.dragAndDropHBox.setOnDragOver(ehDe);
    }

    public void addEHOnDragAndDropBoxDragDropped(EventHandler<DragEvent> ehDe) {
        this.dragAndDropHBox.setOnDragDropped(ehDe);
    }

    public void addEHOnOpenNewProjectButton(EventHandler<Event> eh) {
        this.openNewProjectButton.setOnMouseClicked(eh);
    }

    // ENTITY EDITOR AND ENTITY RELATIONS EDITOR

    public void addEHOnExportFileIcon(EventHandler<Event> eh) {
        this.exportIconVBox.setOnMouseClicked(eh);
    }

    // ENTITY EDITOR SCREEN

    public void addEHOnDefineEntityRelationsButton(EventHandler<Event> eh) {
        this.defineEntityRelationsButton.setOnMouseClicked(eh);
    }

    public void addEHOnEntityAddButton(EventHandler<Event> eh) {
        this.entityAddButton.setOnMouseClicked(eh);
    }

    public void addEHOnEntityRemoveButton(EventHandler<Event> eh) {
        this.entityRemoveButton.setOnMouseClicked(eh);
    }

    public void addEHOnAttributeAddButton(EventHandler<Event> eh) {
        this.attributeAddButton.setOnMouseClicked(eh);
    }

    public void addEHOnAttributeRemoveButton(EventHandler<Event> eh) {
        this.attributeRemoveButton.setOnMouseClicked(eh);
    }

    public void addEHOnAttributeListView(EventHandler<Event> eh) {
        this.attributeListView.setOnMouseClicked(eh);
    }

    public void addEHOnAttributeNameTextField(EventHandler<KeyEvent> eh) {
        this.attributeNameTextField.setOnKeyReleased(eh);
    }

    public void addEHOnDataTypeChoiceBox(EventHandler<ActionEvent> ehAe) {
        this.dataTypeChoiceBox.setOnAction(ehAe);
    }

    public void addEHOnLengthTextField(EventHandler<KeyEvent> ehKe) {
        this.lengthTextField.setOnKeyReleased(ehKe);
    }

    public void addEHOnDefaultValueTextField(EventHandler<KeyEvent> ehKe) {
        this.defaultValueTextField.setOnKeyReleased(ehKe);
    }

    public void addEHOnPrimaryKeyCheckBox(EventHandler<ActionEvent> ehAe) {
        this.primaryKeyCheckBox.setOnAction(ehAe);
    }

    public void addEHOnAllowNullCheckBox(EventHandler<ActionEvent> ehAe) {
        this.allowNullCheckBox.setOnAction(ehAe);
    }

    public void addEHOnSignedCheckBox(EventHandler<ActionEvent> ehAe) {
        this.signedCheckBox.setOnAction(ehAe);
    }

    public void addEHOnAutoIncrementCheckBox(EventHandler<ActionEvent> ehAe) {
        this.autoIncrementCheckBox.setOnAction(ehAe);
    }

    // ENTITY RELATIONS EDITOR SCREEN

    public void addEHOnDefineEntityAttributeButton(EventHandler<Event> eh) {
        this.defineEntityAttributeButton.setOnMouseClicked(eh);
    }

    public void addEHOnEntityRelationTreeView(ChangeListener<? super TreeItem<String>> listener) {
        this.firstEntityTreeView.getSelectionModel().selectedItemProperty().addListener(listener);
        this.secondEntityTreeView.getSelectionModel().selectedItemProperty().addListener(listener);
    }

    public void addEHOnBindUnbindEntityAttribute(EventHandler<Event> eh) {
        this.bindUnbindButton.setOnMouseClicked(eh);
    }

    /**
     * Sets the project title based on the given title
     * 
     * @param title The title of the project
     */
    public void setProjectTitleText(String title) {
        this.projectTitleText.setText(title);
    }

    /**
     * Sets the footer text based on the given content
     * 
     * @param content The content to be set on the application footer
     */
    public void setFooterText(String content) {
        this.qfFooterText.setText(content);
    }

    public void setFooterTableCount(int count) {
        this.qfFooterText.setText("Tables: " + count);
    }

    public void disableLengthTextField(boolean disable) {
        this.lengthTextField.setDisable(disable);
        this.setDisableNodeOpacity(this.lengthTextField, disable);
        this.setDisableNodeOpacity(this.lengthText, disable);
    }

    public void disableAutoIncrementCheckBox(boolean disable) {
        this.autoIncrementCheckBox.setDisable(disable);
        this.setDisableNodeOpacity(this.autoIncrementCheckBox, disable);
        this.setDisableNodeOpacity(this.autoIncrementText, disable);
        this.setCheckBoxDefaultColor(this.autoIncrementCheckBox);
    }

    public void disableSignedCheckBox(boolean disable) {
        this.signedCheckBox.setDisable(disable);
        this.setDisableNodeOpacity(this.signedCheckBox, disable);
        this.setDisableNodeOpacity(this.signedText, disable);
        this.setCheckBoxDefaultColor(this.signedCheckBox);
    }

    private void setCheckBoxDefaultColor(CheckBox checkBox) {
        if (!checkBox.isDisabled()) {
            final BackgroundFill bgFill = new BackgroundFill(QueryfierStyle.QF_COLOR_TEXT_DARKER, null, null);
            checkBox.setBackground(new Background(bgFill));
        }
    }

    private void setDisableNodeOpacity(Node node, boolean disable) {
        if (disable) {
            node.setOpacity(0.4);
            return;
        }
        node.setOpacity(1);
    }

    public String getProjectTitleText() {
        return this.projectTitleText.getText();
    }

    public String getFooterText() {
        return this.qfFooterText.getText();
    }

    public String getFirstEntityText() {
        return this.firstEntityTextField.getText();
    }

    public String getSecondEntityText() {
        return this.secondEntityTextField.getText();
    }

    public String getFirstEntityAttributeText() {
        return this.firstEntityAttributeTextField.getText();
    }

    public String getSecondEntityAttributeText() {
        return this.secondEntityAttributeTextField.getText();
    }

    public void setFirstEntityText(String entityName) {
        this.firstEntityTextField.setText(entityName);
        this.firstEntityVisualLabel.setText(entityName);
    }

    public void setSecondEntityText(String entityName) {
        this.secondEntityTextField.setText(entityName);
        this.secondEntityVisualLabel.setText(entityName);
    }

    public void setFirstEntityAttributeText(String attributeName) {
        this.firstEntityAttributeTextField.setText(attributeName);
        this.firstEntityAttributeVisualLabel.setText(attributeName);
    }

    public void setSecondEntityAttributeText(String attributeName) {
        this.secondEntityAttributeTextField.setText(attributeName);
        this.secondEntityAttributeVisualLabel.setText(attributeName);
    }

    public void renameCurrentlySelectedAttribute(String attributeName) {
        final int index = this.attributeListView.getSelectionModel().getSelectedIndex();
        this.attributeListView.getItems().remove(index);
        this.attributeListView.getItems().add(index, attributeName);
        this.attributeListView.getSelectionModel().select(index);
    }

    public String getSelectedEntity() {
        Label label = this.getSelectedEntityLabel();
        if (label == null) {
            return null;
        }
        return label.getText();
    }

    public void setSelectedEntityName(String name) {
        Label label = this.getSelectedEntityLabel();
        if (label == null) {
            return;
        }
        label.setText(name);
    }

    private Label getSelectedEntityLabel() {
        for (final Node node : this.entityGridPane.getChildren()) {
            if (!(node instanceof VBox vBox) || vBox.getChildren().get(0) instanceof SVGPath svg &&
                    svg.getFill() != QueryfierStyle.QF_COLOR) {
                continue;
            }
            for (final Node vBoxNodes : vBox.getChildren()) {
                if (vBoxNodes instanceof Label label) {
                    return label;
                }
            }
        }

        return null;
    }

    private int getSelectedEntityIndex(String entityName) {

        for (int i = 0; i < this.entityGridPane.getChildren().size(); i++) {
            final Node node = this.entityGridPane.getChildren().get(i);

            if (!(node instanceof VBox vBox) || vBox.getChildren().get(0) instanceof SVGPath svg &&
                    svg.getFill() != QueryfierStyle.QF_COLOR) {
                continue;
            }
            for (final Node vBoxNodes : vBox.getChildren()) {
                if (vBoxNodes instanceof Label label && label.getText().equals(entityName)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public String getSelectedAttribute() {
        return this.attributeListView.getSelectionModel().getSelectedItem();
    }

    public ListView<String> getAttributeListView() {
        return this.attributeListView;
    }

    public GridPane getEntityGridPane() {
        return this.entityGridPane;
    }

    public void toggleRelationshipLine(boolean toggle) {
        this.visualConnectorRectangle.setVisible(toggle);
    }

    public void disableEntityRemoveButton(boolean disable) {
        this.entityRemoveButton.setDisable(disable);
    }

    public void disableAttributeRemoveButton(boolean disable) {
        this.attributeRemoveButton.setDisable(disable);
    }

    /**
     * Sets the view of entities by populating a GridPane with icons and labels for
     * each entity.
     *
     * @param entities A list of strings representing entities or table names to be
     *                 displayed.
     */
    public void setEntitiesView(List<String> entities, EventHandler<Event> eh) {

        for (String entity : entities) {
            this.addEntityToEntitiesView(entity, eh);
        }

        // Select the first entity
        VBox vBox = (VBox) this.entityGridPane.getChildren().get(0);
        SVGPath svg = (SVGPath) vBox.getChildren().get(0);
        svg.setFill(QueryfierStyle.QF_COLOR);

        this.setFooterTableCount(entities.size());
    }

    public void addEntityToEntitiesView(String entityName, EventHandler<Event> eh) {
        final VBox cellContent = this.createEntityGridEntity(entityName, eh);

        int size = this.entityGridPane.getChildren().size();

        // Current position
        int columnIndex = (size % 2 == 0 ? 1 : 0);
        int rowIndex = size / 2 - (columnIndex == 1 ? 1 : 0);

        // Calculate next position
        columnIndex++;

        if (columnIndex == 2) {
            columnIndex = 0;
            rowIndex++;
        }

        this.entityGridPane.add(cellContent, columnIndex, rowIndex);

        if (columnIndex == 0) {
            this.entityGridPane.getRowConstraints().add(rowIndex, new RowConstraints(135));
        }

        this.setFooterTableCount(this.entityGridPane.getChildren().size());
    }

    /**
     * Removes an entity from the grid of entities
     *
     * @param entityName The name of the entity to be removed
     * @return The index of the removed node in the GridPane, or {@code -1} if the
     *         entity was not found
     */
    public int removeEntityFromEntitiesView(String entityName) {
        int nodeToDelete = this.getSelectedEntityIndex(entityName);

        if (nodeToDelete == -1) {
            return -1;
        }

        this.entityGridPane.getChildren().remove(nodeToDelete);

        int size = this.entityGridPane.getChildren().size();

        for (int i = nodeToDelete; i < size; i++) {

            Node node = this.entityGridPane.getChildren().remove(nodeToDelete);

            int columnIndex = ((i + 1) % 2 == 0 ? 1 : 0);
            int rowIndex = (i + 1) / 2 - (columnIndex == 1 ? 1 : 0);

            this.entityGridPane.add(node, columnIndex, rowIndex);
        }

        int columnIndex = (size % 2 == 0 ? 1 : 0);
        int rowIndex = size / 2;

        int rowConstraintSize = this.entityGridPane.getRowConstraints().size();

        if ((rowConstraintSize > rowIndex) && (columnIndex == 1) && (rowConstraintSize - 1 >= 0)) {
            this.entityGridPane.getRowConstraints().remove(rowConstraintSize - 1);
        }

        this.setFooterTableCount(this.entityGridPane.getChildren().size());

        return nodeToDelete;
    }

    private VBox createEntityGridEntity(String entityName, EventHandler<Event> eh) {
        // Create an SVGPath for the entity icon
        SVGPath entityIconSvg = new SVGPath();
        entityIconSvg.setContent(QueryfierStyle.TABLE_ICON);
        entityIconSvg.setFill(QueryfierStyle.QF_COLOR_GRAY);

        // display entity/table name
        Label entityLabel = new Label(entityName);
        entityLabel.setTextFill(QueryfierStyle.QF_COLOR);

        // add the table icon to the table name
        final VBox cellContent = new VBox(entityIconSvg, entityLabel);
        cellContent.setSpacing(5);
        cellContent.setCursor(Cursor.HAND);
        cellContent.setAlignment(Pos.CENTER);

        cellContent.setOnMouseClicked(eh);

        return cellContent;
    }

    public void setEntitiesTreeView(Map<String, List<String>> entityAttributes) {

        final TreeItem<String> rootItem = new TreeItem<>("Entities");
        for (final Entry<String, List<String>> entry : entityAttributes.entrySet()) {
            final TreeItem<String> entityTreeItem = new TreeItem<>(entry.getKey());
            entityTreeItem.setExpanded(true);
            for (String attributeName : entry.getValue()) {
                entityTreeItem.getChildren().add(new TreeItem<>(attributeName));
            }
            rootItem.getChildren().add(entityTreeItem);
        }

        this.firstEntityTreeView.setRoot(rootItem);
        this.firstEntityTreeView.setShowRoot(false);

        final TreeItem<String> rootItem2 = new TreeItem<>("Entities");
        for (final Entry<String, List<String>> entry : entityAttributes.entrySet()) {
            final TreeItem<String> entityTreeItem = new TreeItem<>(entry.getKey());
            entityTreeItem.setExpanded(true);
            for (String attributeName : entry.getValue()) {
                entityTreeItem.getChildren().add(new TreeItem<>(attributeName));
            }
            rootItem2.getChildren().add(entityTreeItem);
        }

        this.secondEntityTreeView.setRoot(rootItem2);
        this.secondEntityTreeView.setShowRoot(false);
    }

    /**
     * Sets the attributes view of the ListView with a list of attribute names.
     *
     * @param attributes A List of String values representing attribute names to be
     *                   displayed.
     */
    public void setAttributesView(List<String> attributes, String pkAttribute) {
        // clear the existing items in the list view
        resetAttributesView();
        System.out.println("Setting attributes view with attributes: " + pkAttribute + " and " + attributes);

        // populate the list view with attributes, marking the primary key
        for (String attribute : attributes) {
            if (attribute.equals(pkAttribute)) {
                // Add the primary key attribute with "PK" label
                System.out.println("Primary key found, it is: " + attribute);
                this.attributeListView.getItems().add(attribute);

            } else {
                // Add other attributes without modification
                this.attributeListView.getItems().add(attribute);
                System.out.println("Normal attribute, it is: " + attribute);
            }
        }
        System.out.println("---" + pkAttribute + "the thing we're fucking with");

        this.attributeListView.setStyle(this.attributeListView.getStyle() + QueryfierStyle.FREDOKA_16PX);
        this.attributeListView.getSelectionModel().select(0);
    }

    public void resetAttributesView() {
        this.attributeListView.getItems().clear();
    }

    /**
     * Sets the attribute information view with the given list of attribute
     * information
     *
     * @param attributeInfoViewList A list containing attribute information in the
     *                              following order:
     *                              <ul>
     *                              <li>Attribute Name</li>
     *                              <li>Data Type</li>
     *                              <li>Length</li>
     *                              <li>Default Value</li>
     *                              <li>Is Primary Key (true/false)</li>
     *                              <li>Allow Null (true/false)</li>
     *                              <li>Is Signed (true/false)</li>
     *                              <li>Auto Increment (true/false)</li>
     *                              </ul>
     */
    public void setAttributeInfoView(List<String> attributeInfoViewList) {
        this.setTextField(this.attributeNameTextField, attributeInfoViewList.get(0));
        this.setChoiceBox(this.dataTypeChoiceBox, attributeInfoViewList.get(1));
        this.setTextField(this.lengthTextField, attributeInfoViewList.get(2));
        this.setTextField(this.defaultValueTextField, attributeInfoViewList.get(3));
        this.setCheckBox(this.primaryKeyCheckBox, Boolean.parseBoolean(attributeInfoViewList.get(4)));
        System.out.println("Setting pk checkbox to: " + attributeInfoViewList.get(4));
        this.setCheckBox(this.allowNullCheckBox, Boolean.parseBoolean(attributeInfoViewList.get(5)));
        this.setCheckBox(this.signedCheckBox, Boolean.parseBoolean(attributeInfoViewList.get(6)));
        this.setCheckBox(this.autoIncrementCheckBox, Boolean.parseBoolean(attributeInfoViewList.get(7)));

        this.showInvalidLength(false); // Resets the default style
    }

    /**
     * Sets the text value of a TextField, removing and restoring the KeyEvent
     * handler
     *
     * @param textField The TextField to set the text value for
     * @param value     The text value to set
     */
    private void setTextField(TextField textField, String value) {
        final EventHandler<? super KeyEvent> handler = textField.getOnKeyReleased();
        if (handler != null) {
            textField.removeEventHandler(KeyEvent.KEY_RELEASED, handler);
        }
        textField.setText(value);
        textField.setOnKeyReleased(handler);
    }

    /**
     * Sets the selected value of a ChoiceBox, removing and restoring the
     * ActionEvent handler
     *
     * @param choiceBox The ChoiceBox to set the selected value for
     * @param value     The value to select in the ChoiceBox
     */
    private void setChoiceBox(ChoiceBox<String> choiceBox, String value) {
        final EventHandler<ActionEvent> handler = choiceBox.getOnAction();
        if (handler != null) {
            choiceBox.setOnAction(null);
        }
        choiceBox.getSelectionModel().select(value);
        choiceBox.setOnAction(handler);
    }

    /**
     * Sets the selected state of a CheckBox, removing and restoring the ActionEvent
     * handler
     *
     * @param checkBox The CheckBox to set the selected state for
     * @param selected The boolean value indicating whether the CheckBox should be
     *                 selected
     */
    private void setCheckBox(CheckBox checkBox, boolean selected) {
        final EventHandler<ActionEvent> handler = checkBox.getOnAction();
        if (handler != null) {
            checkBox.setOnAction(null);
        }
        checkBox.setSelected(selected);
        checkBox.setOnAction(handler);
    }

    /**
     * Sets the choices for the attribute data type in a ChoiceBox
     *
     * @param attributeDataTypes A list of available attribute data types to
     *                           populate the ChoiceBox with
     */
    public void setAttributeDataTypeChoiceBox(List<String> attributeDataTypes) {
        this.dataTypeChoiceBox.getItems().clear();
        this.dataTypeChoiceBox.getItems().addAll(attributeDataTypes);
    }

    /**
     * Displays a file open dialog for selecting a file location.
     * <p>
     * This method shows a file open dialog that allows the user to select a file
     * location. It provides options to filter files by allowed file extensions and
     * preselects the directory based on the location of a previously opened file,
     * if available.
     * 
     * @param previouslyOpenedFile  The previously opened file, used to determine
     *                              the initial directory
     * @param allowedFileExtensions List of file extensions that can be loaded into
     *                              the application. File name extensions should be
     *                              specified in the {@code *.<extension>} format
     * @return The selected File object or {@code null} if no file is chosen
     */
    public File promptOpenFileDialog(File previouslyOpenedFile, List<String> allowedFileExtensions) {
        final FileChooser openFileChooser = new FileChooser();
        if (previouslyOpenedFile != null && previouslyOpenedFile.exists()
                && previouslyOpenedFile.getParentFile().isDirectory()) {
            openFileChooser.setInitialDirectory(previouslyOpenedFile.getParentFile());
        } else {
            openFileChooser.setInitialDirectory(new File("."));
        }
        openFileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Supported Files", allowedFileExtensions));
        return openFileChooser.showOpenDialog(this.stage);
    }

    /**
     * Displays a file save dialog for selecting a file location to save data.
     * <p>
     * This method shows a file save dialog that allows the user to select a file
     * location where data can be saved. It may preselect the directory and filename
     * based on the location of a previously saved file, if provided.
     *
     * @param previouslySavedFile The previously saved file, used to determine the
     *                            initial directory
     * @param saveFileExtension   The extension of the save file (i.e.
     *                            {@code ".sav"})
     * @return The selected File object or {@code null} if the save operation is
     *         canceled.
     */
    public File promptSaveFileDialog(File previouslySavedFile, String saveFileExtension) {
        return DialogCreator.saveDialog(stage, previouslySavedFile, this.projectTitleText.getText(), saveFileExtension);
    }

    /**
     * Displays a dialog for exporting project data, allowing the user to select
     * export options
     *
     * @param previouslyExportedFile The previously saved file used for exporting
     * @param dbSystems              A list of database systems available for export
     * @return A {@link FileExportDefinition} object containing the selected export
     *         format and file location, or {@code null} if the user cancels the
     *         export dialog
     */
    public FileExportDefinition promptExportFileDialog(File previouslyExportedFile, List<String> dbSystems) {
        return DialogCreator.exportDialog(stage, previouslyExportedFile, this.projectTitleText.getText(), dbSystems);
    }

    /**
     * Display a dialog for creating a new Entity with the specified name
     * 
     * @param entityName Name of the entity to have as the prompt of the text field
     * @return Typed in name of the entity
     */
    public String promptAddEntityDialog(String entityName) {
        return DialogCreator.nameDialog(stage, "Add new Entity", "New", "Entity", entityName);
    }

    /**
     * Display a dialog for editing a Entity with the specified name
     * 
     * @param entityName Name of the entity to have as the prompt of the text field
     * @return Typed in name of the entity
     */
    public String promptEditEntityNameDialog(String entityName) {
        String title = String.format("Edit %s Entity Name", entityName);
        return DialogCreator.nameDialog(stage, title, "Edit", "Entity", entityName);
    }

    /**
     * Sets the list of recent files to be displayed on the welcome screen
     *
     * @param recentFilesMap A Map of file names to the number of entities
     *                       associated with each file
     */
    public void setRecentFiles(Map<String, Integer> recentFilesMap, EventHandler<Event> eh) {

        if (this.recentFilesVBox == null) {
            return;
        }

        if (recentFilesMap == null || recentFilesMap.isEmpty()) {
            this.recentFilesVBox.getChildren().clear();
            this.recentFilesVBox.setAlignment(Pos.CENTER);
            Label recentFilesAppearLabel = new Label("Your recent files\nwill appear here");
            recentFilesAppearLabel.setTextFill(QueryfierStyle.QF_COLOR_TEXT);
            recentFilesAppearLabel.setStyle(QueryfierStyle.FREDOKA_20PX);
            this.recentFilesVBox.getChildren().add(recentFilesAppearLabel);
            return;
        }

        final String selectedRecentFileStyle = "-fx-background-color: #292929; -fx-border-color: #464646; -fx-border-width: 1; -fx-border-style: hidden hidden solid hidden;";
        final String defaultStyle = "-fx-border-color: transparent; -fx-border-width: 1; -fx-border-style: hidden hidden solid hidden;";

        for (Entry<String, Integer> entry : recentFilesMap.entrySet()) {

            HBox recentFileHBox = new HBox(10);
            recentFileHBox.setCursor(Cursor.HAND);
            recentFileHBox.setMinHeight(70);
            recentFileHBox.setPadding(new Insets(0, 10, 0, 10));
            recentFileHBox.setAlignment(Pos.CENTER_LEFT);
            recentFileHBox.setStyle(defaultStyle);

            recentFileHBox.setOnMouseEntered(event -> {
                for (Node node : this.recentFilesVBox.getChildren()) {
                    if (node instanceof HBox hBox) {
                        hBox.setStyle(defaultStyle);
                        this.setButtonVisible(hBox, false);
                    }
                }
                if (event.getSource() instanceof HBox sourceHBox) {
                    sourceHBox.setStyle(selectedRecentFileStyle);
                    this.setButtonVisible(sourceHBox, true);
                }
            });

            recentFileHBox.setOnMouseClicked(eh);

            SVGPath entityIconSvg = new SVGPath();
            entityIconSvg.setContent(QueryfierStyle.ENTITY_ICON);
            entityIconSvg.setFill(QueryfierStyle.QF_COLOR);
            recentFileHBox.getChildren().add(entityIconSvg);
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(vBox, Priority.ALWAYS);
            Label projectName = new Label(entry.getKey());
            projectName.setStyle(QueryfierStyle.FREDOKA_14PX);
            projectName.setTextFill(Color.WHITE);
            Text entityCount = new Text(entry.getValue() + " TABLES");
            entityCount.setStyle(QueryfierStyle.FREDOKA_12PX);
            entityCount.setFill(QueryfierStyle.QF_COLOR_TEXT);
            vBox.getChildren().addAll(projectName, entityCount);
            recentFileHBox.getChildren().add(vBox);

            Button btn = this.getOpenButton(eh);
            btn.setVisible(false);
            recentFileHBox.getChildren().add(btn);

            this.recentFilesVBox.getChildren().add(recentFileHBox);
        }

        Node node = this.recentFilesVBox.getChildren().get(0);

        if (node instanceof HBox firstRecentFileHBox) {
            firstRecentFileHBox.setStyle(selectedRecentFileStyle);
            this.setButtonVisible(firstRecentFileHBox, true);
        }

    }

    /**
     * Sets the visibility of all Button elements within a Pane to the specified
     * value
     * 
     * @param pane       The Pane containing the Button elements
     * @param setVisible True to make the Button elements visible, false to hide
     *                   them
     */
    private void setButtonVisible(Pane pane, boolean setVisible) {
        for (Node node : pane.getChildren()) {
            if (node instanceof Button btn) {
                btn.setVisible(setVisible);
            }
        }
    }

    /**
     * Creates and configures a Button for opening functionality with the provided
     * event handler.
     * <p>
     * This method creates a Button labeled "Open" and configures its appearance and
     * behavior for opening functionality using the provided event handler. It
     * returns the
     * configured Button.
     *
     * @param eh The event handler to be associated with the Button's mouse click
     *           event.
     * @return A configured Button for opening functionality.
     */
    private Button getOpenButton(EventHandler<Event> eh) {
        final Button openButton = new Button("Open");
        openButton.setTextFill(QueryfierStyle.QF_COLOR_TEXT_DARKER);
        openButton.getStyleClass().addAll(QueryfierStyle.CLASS_BLACK_BTN,
                QueryfierStyle.CLASS_WELCOME_BTN, QueryfierStyle.CLASS_SHADOW);
        openButton.setStyle("-fx-min-width: 55");

        openButton.setOnMouseClicked(eh);
        return openButton;
    }

    /**
     * Optional
     * Change the alert depending on the issue
     * Key mismatch errors can occur for various reasons, including:
     * 
     * Referential Integrity Violation: This happens when a foreign key in one table
     * does not match any corresponding primary key in another table, leading to
     * data inconsistencies.
     * 
     * Data Type Mismatch: When the data type of a foreign key does not match the
     * primary key it references, it can result in key mismatch errors.
     * 
     * Key Deletion or Modification: If a primary key value is modified or deleted
     * without updating the related foreign keys, it can lead to key mismatch
     * issues.
     * 
     * Incomplete or Incorrect Joins: When constructing SQL queries that involve
     * multiple tables and joins, errors in specifying the relationships between
     * tables can lead to key mismatches.
     */
    public boolean showAttributeKeyMismatchAlert() {
        String firstAttribute = this.getFirstEntityAttributeText();
        String title = "Queryfier - Key Mismatch";
        String header = "Key Mismatch!";
        String info = "There is a mismatch or inconsistency between the keys used to link data in related tables.";
        String content = String.format("%s Would you like to make the %s primary?", info, firstAttribute);
        return this.showPromptAlert(AlertType.ERROR, title, header, content);
    }

    public boolean showAttributeDataTypeMismatchAlert() {
        String firstAttribute = this.getFirstEntityAttributeText();
        String secondAttribute = this.getSecondEntityAttributeText();
        String title = "Queryfier - Attribute Data Type Mismatch";
        String header = "Attribute Data Type Mismatch!";
        String info = "There is a data type inconsistency between attributes.";
        String content = String.format("%s Would you like to make the %s the same data type as %s.",
                info, secondAttribute, firstAttribute);
        return this.showPromptAlert(AlertType.ERROR, title, header, content);
    }

    public void showSameNameEntityAlert() {
        String title = "Queryfier - Same Entity Name";
        String header = "Entity Name is not unique!";
        String content = "An entity with the same name already exists. Please choose another entity name.";
        this.showAlert(AlertType.WARNING, title, header, content);
    }

    public void showAtLeastEntityAlert(String action) {
        String title = "Queryfier - At least one Entity";
        String header = String.format("There are no entities to %s!", action);
        String content = String.format("There is no content to %s. Please create at least one entity to %s the file.",
                action, action);
        this.showAlert(AlertType.WARNING, title, header, content);
    }

    public void showAtLeastAttributeAlert(String action) {
        String title = "Queryfier - At least one Attribute";
        String header = String.format("There are no attributes to %s!", action);
        String content = String.format("Please create at least one attribute in each entity to %s the file.", action);
        this.showAlert(AlertType.WARNING, title, header, content);
    }

    public boolean showDeleteEntityAlert() {
        String entityName = this.getSelectedEntity();
        String title = "Queryfier - Delete Entity";
        String header = String.format("Would you like to delete %s?", entityName);
        String content = String.format("Deleting %s will also delete all of associated attributes and relations.",
                entityName);
        return this.showDeletePromptAlert(AlertType.WARNING, title, header, content);
    }

    public void showFeedback(boolean success, String successTitle, String successHeader, String successContent,
            String errorTitle, String errorHeader, String errorContent) {

        if (success) {
            this.showAlert(AlertType.INFORMATION, successTitle, successHeader, successContent);
        } else {
            this.showAlert(AlertType.ERROR, errorTitle, errorHeader, errorContent);
        }

    }

    public void showFileSaveFeedback(boolean success) {
        showFeedback(success, "File Save Success", "Success Saving File", "The file was saved successfully",
                "File Save Error", "Error Saving File", "An error occurred while saving the file");
    }

    public void showFileExportFeedback(boolean success) {
        showFeedback(success, "File Successfully Exported", "File Export Success", "The file was exported successfully",
                "Error Exporting File", "File Export Error", "An issue occurred while exporting the file");
    }

    /**
     * Displays a confirmation dialog to the user, asking if they want to save
     * unsaved changes.
     * <p>
     * This method shows a confirmation dialog to the user, asking whether they want
     * to save unsaved changes. The dialog includes "Yes" and "No" buttons for the
     * user to choose from.
     * <p>
     * The result of the dialog is returned as a boolean value, where true indicates
     * that the user chose to save the changes, and false indicates that the user
     * chose not to save them.
     * 
     * @return {@code true} to indicate to save changes or {@code false} to indicate
     *         discarding the changes
     */
    public ButtonType showUnsavedChanges() {
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Unsaved changes - Save?");
        alert.setHeaderText("Unsaved changes");
        alert.setContentText("There are unsaved changes, save them?");

        // Styling
        this.getPromptAlertDialogPane(alert);

        // Set the alert's initial owner to be the current (active) stage
        alert.initOwner(this.stage);
        alert.showAndWait();

        return alert.getResult();
    }

    /**
     * Displays an error message to the user, indicating that there has been an
     * issue with the program
     *
     * @param content The content of the error message to be shown to the user
     */
    public void showError(String content) {
        String title = "Error occurred";
        String header = "An error occurred with the program";
        this.showAlert(AlertType.ERROR, title, header, content);
    }

    /**
     * Displays an alert dialog with the specified parameters
     *
     * @param alertType The type of alert to be displayed (e.g., INFORMATION,
     *                  WARNING, ERROR)
     * @param title     The title of the alert dialog
     * @param header    The header text of the alert dialog
     * @param content   The content text of the alert dialog
     */
    public void showAlert(AlertType alertType, String title, String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            this.styleAlertDialogPane(alert.getDialogPane());
            // Set the alert's initial owner to be the current (active) stage
            alert.initOwner(this.stage);
            alert.showAndWait();
        });
    }

    private boolean showPromptAlert(AlertType alertType, String title, String header, String content) {

        final Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        // Styling
        this.getPromptAlertDialogPane(alert);

        alert.initOwner(this.stage);
        alert.showAndWait();

        return alert.getResult() == ButtonType.YES;
    }

    private boolean showDeletePromptAlert(AlertType alertType, String title, String header, String content) {

        final Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);

        DialogPane dialogPane = this.getPromptAlertDialogPane(alert);

        final String OS = System.getProperty("os.name").toLowerCase();

        String cmd = OS.indexOf("win") >= 0 ? "CTRL" : "CMD";
        String hint = String.format("Hint: Use %s to bypass this confirmation.", cmd);

        VBox vBox = new VBox(2);
        Label contentLabel = new Label(content);
        Label hintLabel = new Label(hint);
        hintLabel.setFont(Font.font(QueryfierStyle.FREDOKA, FontPosture.ITALIC, 10));
        vBox.getChildren().addAll(contentLabel, hintLabel);
        dialogPane.setContent(vBox);

        alert.initOwner(this.stage);
        alert.showAndWait();

        return alert.getResult() == ButtonType.YES;
    }

    /**
     * Styles a JavaFX DialogPane to customize its appearance.
     * <p>
     * This method applies custom styling to a DialogPane,
     * including setting a unique ID, adding stylesheets, and customizing the
     * appearance of the OK button.
     * <ul>
     * <li>The DialogPane's ID is set to "customAlert"</li>
     * <li>The CSS stylesheet is added to the dialogPane</li>
     * <li>The appearance of the OK button is customized with white
     * text, a shadow, and specific styles</li>
     * </ul>
     *
     * @param dialogPane The DialogPane to style.
     */
    private void styleAlertDialogPane(DialogPane dialogPane) {
        dialogPane.setId(QueryfierStyle.ID_CUSTOM_ALERT_DIALOG);
        dialogPane.getStylesheets().add(QueryfierStyle.CSS_PATH);

        Node okBtn = dialogPane.lookupButton(ButtonType.OK);
        this.styleAlertButtonBlack(okBtn);
    }

    private DialogPane styleAlertDialogPane(Alert alert) {
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setId(QueryfierStyle.ID_CUSTOM_ALERT_DIALOG);
        dialogPane.getStylesheets().add(QueryfierStyle.CSS_PATH);
        return dialogPane;
    }

    private DialogPane getPromptAlertDialogPane(Alert alert) {

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);

        // Styling
        DialogPane dialogPane = this.styleAlertDialogPane(alert);

        Node yesBtn = dialogPane.lookupButton(ButtonType.YES);
        this.styleAlertButtonBlue(yesBtn);

        Node noBtn = dialogPane.lookupButton(ButtonType.NO);
        this.styleAlertButtonBlack(noBtn);

        Node cancelBtn = dialogPane.lookupButton(ButtonType.CANCEL);
        this.styleAlertButtonBlack(cancelBtn);

        return dialogPane;
    }

    private void styleAlertButtonBlue(Node btn) {
        btn.getStyleClass().addAll(QueryfierStyle.CLASS_BLUE_BTN, QueryfierStyle.CLASS_SHADOW);
        btn.setStyle(QueryfierStyle.WHITE_TEXT_DIALOG_BTN +
                QueryfierStyle.FREDOKA_12PX + QueryfierStyle.ALERT_BTN_STYLE);
    }

    private void styleAlertButtonBlack(Node btn) {
        btn.getStyleClass().addAll(QueryfierStyle.CLASS_BLACK_BTN, QueryfierStyle.CLASS_SHADOW);
        btn.setStyle(QueryfierStyle.WHITE_TEXT_DIALOG_BTN +
                QueryfierStyle.FREDOKA_12PX + QueryfierStyle.ALERT_BTN_STYLE);
    }

    /**
     * Highlights the length TextField with a red border if the given value is true,
     * or removes the red border if the value is false
     *
     * @param value True to show an invalid length indication (red border), false to
     *              remove it
     */
    public void showInvalidLength(boolean value) {
        final String defaultStyle = "-fx-background-color: #1B1B1B; -fx-border-style: hidden hidden solid hidden; -fx-border-color: ";
        if (value) {
            this.lengthTextField.setStyle(defaultStyle + "#750000;");
        } else {
            this.lengthTextField.setStyle(defaultStyle + "white");
        }
    }

}