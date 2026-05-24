package edu.rit.croatia.iste422.g6.qf.view.util;

// Java imports
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

// JavaFX imports
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

/**
 * The DialogCreator class provides methods for creating various types of
 * dialogs.
 * 
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Michel Brassard
 * @author Petra Cesar
 * @author Divna Mijic
 */
// The class has been created by "The Sixth Sense" Group.
public class DialogCreator {

    /**
     * An enumeration representing the possible results of a dialog box
     */
    public enum DialogResult {
        OK, EXPORT, SAVE, CANCEL;
    }

    /**
     * Displays an export dialog to the user, allowing them to specify export
     * settings
     *
     * @param stage                  The JavaFX stage on which to display the dialog
     * @param previouslyExportedFile The previously exported file (if any) for
     *                               reference
     * @param projectName            The name of the project to be exported
     * @param dbSystems              A list of available database systems for export
     * @return A list of export settings chosen by the user or an empty list if the
     *         user cancels the operation
     */
    public static FileExportDefinition exportDialog(Stage stage, File previouslyExportedFile,
            String projectName, List<String> dbSystems) {

        final Dialog<DialogResult> fileExportDialog = new Dialog<>();
        fileExportDialog.setTitle("Queryfier - Export file");

        final DialogPane fileExportDialogPane = DialogCreator.createDialogPane();

        ButtonType exportButton = DialogCreator.addBlueButton(fileExportDialogPane, "Export");
        DialogCreator.addCancelButton(fileExportDialogPane);

        final VBox exportVBox = new VBox(0);
        exportVBox.setPadding(new Insets(0, 20, 0, 20));
        exportVBox.setMinWidth(500);
        exportVBox.setMaxWidth(500);

        final HBox headerBox = DialogCreator.createHeader("Export", projectName);
        exportVBox.getChildren().add(headerBox);

        Text databaseNameText = DialogCreator.createDialogText("Database Name");
        VBox.setMargin(databaseNameText, new Insets(20, 20, 5, 20));
        exportVBox.getChildren().add(databaseNameText);

        String databaseName = projectName.substring(0, projectName.lastIndexOf("."));
        TextField databaseNameTextField = DialogCreator.createTextField(databaseName);
        VBox.setMargin(databaseNameTextField, new Insets(5, 20, 5, 20));
        exportVBox.getChildren().add(databaseNameTextField);

        Text databaseFileLocation = DialogCreator.createDialogText("Location");
        VBox.setMargin(databaseFileLocation, new Insets(5, 20, 0, 20));
        exportVBox.getChildren().add(databaseFileLocation);

        String sqlFileExtension = ".sql";

        String defaultPath = DialogCreator.constructDefaultFilePath(previouslyExportedFile);
        String location = defaultPath + File.separator + databaseName + sqlFileExtension;

        Label databaseFileLocationInfoLabel = DialogCreator.createLocationLabel(location, 460);
        VBox.setMargin(databaseFileLocationInfoLabel, new Insets(0, 20, 5, 20));
        exportVBox.getChildren().add(databaseFileLocationInfoLabel);

        databaseNameTextField.setOnKeyTyped(event -> DialogCreator.changeLocationOnKeyTyped(
                databaseNameTextField, sqlFileExtension, databaseFileLocationInfoLabel));

        HBox chooseDirHBox = new HBox();
        HBox.setHgrow(chooseDirHBox, Priority.NEVER);
        chooseDirHBox.setPadding(new Insets(5, 20, 5, 20));

        HBox chooseDirHBoxButton = DialogCreator.createChooseDirectoryButton();

        chooseDirHBox.getChildren().add(chooseDirHBoxButton);

        DirectoryChooser databaseFileLocationChooser = new DirectoryChooser();
        String fileName = databaseNameTextField.getText();
        databaseFileLocationChooser.setTitle("Choose where to export " + fileName + sqlFileExtension);

        chooseDirHBoxButton.setOnMouseClicked(event -> DialogCreator.openDirectoryChooser(databaseFileLocationChooser,
                stage, fileName, sqlFileExtension, databaseFileLocationInfoLabel));

        exportVBox.getChildren().add(chooseDirHBox);

        final HBox databaseHBoxWrapper = new HBox();
        databaseHBoxWrapper.setAlignment(Pos.CENTER);

        final VBox databaseChooserVBox = new VBox(5);
        VBox.setMargin(databaseChooserVBox, new Insets(10, 0, 10, 0));
        databaseChooserVBox.setPrefWidth(230);

        Text databaseSystemText = DialogCreator.createDialogText("Database System");
        databaseSystemText.setStyle(QueryfierStyle.FREDOKA_16PX);

        databaseChooserVBox.getChildren().add(databaseSystemText);

        ComboBox<String> databaseSystemComboBox = new ComboBox<>(FXCollections.observableArrayList(dbSystems));
        databaseSystemComboBox.getSelectionModel().select(0);
        databaseSystemComboBox.setPrefWidth(230);
        VBox.setMargin(databaseSystemComboBox, new Insets(0, 0, 30, 0));

        databaseChooserVBox.getChildren().add(databaseSystemComboBox);

        databaseHBoxWrapper.getChildren().add(databaseChooserVBox);

        exportVBox.getChildren().add(databaseHBoxWrapper);

        fileExportDialogPane.setContent(exportVBox);

        fileExportDialog.setResultConverter(btn -> btn == exportButton ? DialogResult.EXPORT : DialogResult.CANCEL);

        fileExportDialog.setDialogPane(fileExportDialogPane);

        // Set the dialog's initial owner to be the current (active) stage
        fileExportDialog.initOwner(stage);

        Window window = fileExportDialogPane.getScene().getWindow();
        window.setOnCloseRequest(evt -> window.hide());

        Optional<DialogResult> result = fileExportDialog.showAndWait();

        if (result.isPresent() && result.get() == DialogResult.EXPORT) {
            String databaseSystem = databaseSystemComboBox.getSelectionModel().getSelectedItem();
            String exportFileLocation = databaseFileLocationInfoLabel.getText();
            return new FileExportDefinition(databaseSystem, new File(exportFileLocation));
        }

        return null;
    }

    /**
     * Displays an save dialog to the user, allowing them to save the current
     * project into a file
     * 
     * @param stage               The JavaFX stage on which to display the dialog
     * @param previouslySavedFile The previously saved file (if any) for reference
     * @param projectName         The name of the project to be saved
     * @param saveFileExtension   The extension of the save file
     * @return A file to where to save the project or {@code null} if the user
     *         cancels the operation
     */
    public static File saveDialog(Stage stage, File previouslySavedFile, String projectName, String saveFileExtension) {

        // Dialog with title and content text
        final Dialog<DialogResult> fileSaveDialog = new Dialog<>();
        fileSaveDialog.setTitle("Queryfier - Save file");

        // configuring the basics of the dialog pane
        final DialogPane fileSaveDialogPane = DialogCreator.createDialogPane();

        ButtonType saveButton = DialogCreator.addBlueButton(fileSaveDialogPane, "Save");
        DialogCreator.addCancelButton(fileSaveDialogPane);

        // configuring the VBox, which holds as the ultimate holder for the dialogue
        // content
        final VBox saveVBox = new VBox(0);
        saveVBox.setPadding(new Insets(0, 20, 0, 20));
        saveVBox.setMinWidth(500);
        saveVBox.setMaxWidth(500);

        // configuring the headerBox VBox which will serve as the header
        final HBox headerBox = DialogCreator.createHeader("Save", projectName);
        saveVBox.getChildren().add(headerBox);

        // creating the text inside the dialogue
        Text saveFileNameText = DialogCreator.createDialogText("Name");
        VBox.setMargin(saveFileNameText, new Insets(20, 20, 5, 20));
        saveVBox.getChildren().add(saveFileNameText);

        // getting the name of the database
        String saveFileName = projectName.substring(0, projectName.lastIndexOf("."));
        // adding the text of the database to the TextField
        TextField saveFileNameTextField = DialogCreator.createTextField(saveFileName);
        // adjusting margins in the VBox
        VBox.setMargin(saveFileNameTextField, new Insets(5, 20, 5, 20));

        // adding children nodes ot the saveVBox
        saveVBox.getChildren().add(saveFileNameTextField);

        // "Location" text under which is a button for choosing the place where we wish
        // to save our file
        Text databaseFileLocation = DialogCreator.createDialogText("Location");
        // stylizing the text
        VBox.setMargin(databaseFileLocation, new Insets(5, 20, 0, 20));
        // adding the text to the Vbox
        saveVBox.getChildren().add(databaseFileLocation);

        // initializing a default path holder
        String defaultPath = DialogCreator.constructDefaultFilePath(previouslySavedFile);
        String location = defaultPath + File.separator + saveFileName + saveFileExtension;

        // initializing a new label that holds the path to the place where we wish to
        // save the file to and updates as the user chooses different locations
        // stylizing the label for the location info and adjusting its margins
        Label saveFileLocation = DialogCreator.createLocationLabel(location, 460);
        VBox.setMargin(saveFileLocation, new Insets(0, 20, 5, 20));

        // adding the label that holds the path to place where we want to save the file
        // to, to the VBox
        saveVBox.getChildren().add(saveFileLocation);

        // changing the name based on what is typed in the saveFileNameTextField
        saveFileNameTextField.setOnKeyTyped(event -> DialogCreator.changeLocationOnKeyTyped(
                saveFileNameTextField, saveFileExtension, saveFileLocation));

        // an HBox that serves as a holder for a button, which upon clicking, displays
        // the directories we can save our file to
        HBox chooseDirHBox = new HBox();
        HBox.setHgrow(chooseDirHBox, Priority.NEVER);
        chooseDirHBox.setPadding(new Insets(5, 20, 20, 20));

        // an HBox that serves as a button, which upon clicking, displays the
        // directories we can save our file to
        HBox chooseDirHBoxButton = DialogCreator.createChooseDirectoryButton();

        // "choose directory" button placed inside its holder
        chooseDirHBox.getChildren().add(chooseDirHBoxButton);

        DirectoryChooser saveFileLocationChooser = new DirectoryChooser();
        String fileName = saveFileNameTextField.getText();
        saveFileLocationChooser.setTitle("Choose where to save " + fileName + saveFileExtension);

        // prompting a directory chooser upon clicking on the "choose directory" button
        chooseDirHBoxButton.setOnMouseClicked(event -> DialogCreator.openDirectoryChooser(saveFileLocationChooser,
                stage, fileName, saveFileExtension, saveFileLocation));

        // putting the holder for the "choose directory" button in the VBox
        saveVBox.getChildren().add(chooseDirHBox);

        // setting the content of the pane to the VBox
        fileSaveDialogPane.setContent(saveVBox);

        // result converter for a file save dialog so that it correctly interprets the
        // user's button-click actions and maps them to the appropriate dialog result:
        // "Save" or "Cancel".
        fileSaveDialog.setResultConverter(btn -> btn == saveButton ? DialogResult.SAVE : DialogResult.CANCEL);

        // setting the pane
        fileSaveDialog.setDialogPane(fileSaveDialogPane);

        // Set the dialog's initial owner to be the current (active) stage
        fileSaveDialog.initOwner(stage);

        // setting the style of the dialogPane
        fileSaveDialogPane.setStyle(QueryfierStyle.DIALOG_STYLE);

        // after getting the scene, the code retrieves the window associated with that
        // scene
        Window window = fileSaveDialogPane.getScene().getWindow();

        // handler to hide (close) the file save dialog window when the user requests to
        // close
        window.setOnCloseRequest(evt -> window.hide());

        Optional<DialogResult> result = fileSaveDialog.showAndWait();

        if (result.isPresent() && result.get() == DialogResult.SAVE) {
            return new File(saveFileLocation.getText());
        }

        return null;
    }

    /**
     * Displays a dialog to prompt the user for a name.
     * 
     * @param stage          The JavaFX stage on which to display the dialog
     * @param title          The title of the window, prepends a "Queryfier - "
     *                       before the title
     * @param grayHeaderText Text to put in the header in gray color
     * @param blueHeaderText Text to put in the header in blue color
     * @param tfPromptText   Text to put as prompt text in the text field
     * @return A valid name or {@code null} if the user cancels the operation
     */
    public static String nameDialog(Stage stage, String title,
            String grayHeaderText, String blueHeaderText, String tfPromptText) {

        final Dialog<DialogResult> nameDialog = new Dialog<>();
        nameDialog.setTitle("Queryfier - " + title);

        final DialogPane nameDialogPane = DialogCreator.createDialogPane();

        ButtonType okButton = DialogCreator.addBlueButton(nameDialogPane, "Ok");
        DialogCreator.addCancelButton(nameDialogPane);
        ((Button) nameDialogPane.lookupButton(okButton)).setDefaultButton(true);

        final VBox nameVBox = new VBox(0);
        nameVBox.setPadding(new Insets(0, 20, 0, 20));
        nameVBox.setMinWidth(250);
        nameVBox.setMaxWidth(250);

        final HBox headerBox = DialogCreator.createHeader(grayHeaderText, blueHeaderText);
        nameVBox.getChildren().add(headerBox);

        Text nameText = DialogCreator.createDialogText("Please enter a name");
        VBox.setMargin(nameText, new Insets(20, 20, 5, 20));
        nameVBox.getChildren().add(nameText);

        final TextField nameTextField = new TextField();
        nameTextField.setPromptText(tfPromptText);
        nameTextField.setStyle(QueryfierStyle.FREDOKA_16PX);
        nameTextField.getStyleClass().addAll(QueryfierStyle.CLASS_FREDOKA, QueryfierStyle.CLASS_SHADOW,
                QueryfierStyle.CLASS_EXPORT_TEXT_FIELD);
        VBox.setMargin(nameTextField, new Insets(5, 20, 0, 20));
        nameVBox.getChildren().add(nameTextField);

        Text nameInfoText = DialogCreator.createDialogText("");
        nameInfoText.setStyle(QueryfierStyle.FREDOKA_12PX);
        nameInfoText.setFill(QueryfierStyle.QF_COLOR_TEXT);
        VBox.setMargin(nameInfoText, new Insets(5, 20, 20, 20));
        nameVBox.getChildren().add(nameInfoText);

        nameDialogPane.setContent(nameVBox);

        nameDialog.setResultConverter(btn -> btn == okButton ? DialogResult.OK : DialogResult.CANCEL);

        nameDialog.setDialogPane(nameDialogPane);

        // Set the dialog's initial owner to be the current (active) stage
        nameDialog.initOwner(stage);

        Window window = nameDialogPane.getScene().getWindow();
        window.setOnCloseRequest(evt -> window.hide());

        String validName = "Please enter a valid name";

        Node okayButton = nameDialogPane.lookupButton(okButton);

        okayButton.addEventFilter(ActionEvent.ACTION, okEvent -> {
            String name = nameTextField.getText();

            if (!(name == null || name.isBlank())) {
                return;
            }

            nameInfoText.setText(validName);

            // Make the text field shake
            Timeline timeline = new Timeline();
            Duration duration = Duration.millis(100);

            KeyValue keyValue1 = new KeyValue(nameTextField.translateXProperty(), 5);
            KeyValue keyValue2 = new KeyValue(nameTextField.translateXProperty(), -5);
            KeyValue keyValue3 = new KeyValue(nameTextField.translateXProperty(), 0);
            KeyFrame keyFrame1 = new KeyFrame(duration, keyValue1);
            KeyFrame keyFrame2 = new KeyFrame(duration.multiply(2), keyValue2);
            KeyFrame keyFrame3 = new KeyFrame(duration.multiply(3), keyValue1);
            KeyFrame keyFrame4 = new KeyFrame(duration.multiply(4), keyValue2);
            KeyFrame keyFrame5 = new KeyFrame(duration.multiply(5), keyValue3);

            timeline.getKeyFrames().addAll(keyFrame1, keyFrame2, keyFrame3, keyFrame4, keyFrame5);

            if (!timeline.getStatus().equals(Animation.Status.RUNNING)) {
                timeline.playFromStart();
            }

            okEvent.consume();
        });

        nameTextField.setOnKeyTyped(event -> {
            String name = nameTextField.getText();

            if (name == null || name.isBlank()) {
                nameInfoText.setText(validName);
            } else {
                nameInfoText.setText("");
            }

        });

        Optional<DialogResult> result = nameDialog.showAndWait();

        if (result.isPresent() && result.get() == DialogResult.OK) {
            return nameTextField.getText();
        }

        return null;
    }

    /**
     * Creates a Text element with the specified text content and styling
     * for use in a dialog
     *
     * @param textContent The text content to be displayed
     * @return Text element with the specified text content and styling
     */
    private static Text createDialogText(String textContent) {
        final Text text = new Text(textContent);
        text.setFill(Color.WHITE);
        text.setStyle(QueryfierStyle.FREDOKA_18PX);
        text.getStyleClass().add(QueryfierStyle.CLASS_FREDOKA);
        return text;
    }

    /**
     * Creates a Queryfier DialogPane with a specific button bar layout and styles
     * 
     * @return Styled {@code DialogPane} with bottom bar
     */
    private static DialogPane createDialogPane() {
        final DialogPane dialogPane = new DialogPane() {
            @Override
            public Node createButtonBar() {
                VBox vbox = new VBox(20);
                vbox.setAlignment(Pos.CENTER);
                vbox.getChildren().add(super.createButtonBar());
                HBox hBox = new HBox();
                hBox.setMinSize(350, 20);
                hBox.setStyle(QueryfierStyle.FOOTER_STYLE);
                vbox.getChildren().add(hBox);
                return vbox;
            }
        };
        dialogPane.getStylesheets().add(QueryfierStyle.CSS_PATH);
        dialogPane.setStyle(QueryfierStyle.DIALOG_STYLE);
        return dialogPane;
    }

    /**
     * Adds a blue styled button type to the DialogPane ButtonTypes
     * 
     * @param dialogPane DialogPane to add the button to
     * @param text       text of the blue button
     * @return ButtonType that was added
     */
    private static ButtonType addBlueButton(DialogPane dialogPane, String text) {
        final ButtonType button = new ButtonType(text, ButtonData.LEFT);
        DialogCreator.addButtonToDialogPane(dialogPane, button,
                QueryfierStyle.WHITE_TEXT_DIALOG_BTN,
                QueryfierStyle.CLASS_BLUE_BTN, QueryfierStyle.CLASS_SHADOW);
        return button;
    }

    /**
     * Adds a blue styled button type to the DialogPane ButtonTypes
     * 
     * @param dialogPane DialogPane to add the button to
     * @return ButtonType that was added
     */
    private static ButtonType addCancelButton(DialogPane dialogPane) {
        final ButtonType cancelButton = new ButtonType("Cancel", ButtonData.LEFT);
        DialogCreator.addButtonToDialogPane(dialogPane, cancelButton,
                QueryfierStyle.WHITE_TEXT_DIALOG_BTN,
                QueryfierStyle.CLASS_BLACK_BTN, QueryfierStyle.CLASS_SHADOW);
        return cancelButton;
    }

    /**
     * Adds a ButtonType to the DialogPane's ButtonTypes with the given css style
     * and style classes
     * 
     * @param dialogPane   DialogPane to add the button to
     * @param buttonType   Button to add and style
     * @param cssStyle     Style to add to the button
     * @param styleClasses Style classes to add to the button
     */
    private static void addButtonToDialogPane(DialogPane dialogPane, ButtonType buttonType,
            String cssStyle, String... styleClasses) {
        dialogPane.getButtonTypes().add(buttonType);
        Node button = dialogPane.lookupButton(buttonType);
        button.setStyle(cssStyle);
        button.getStyleClass().addAll(styleClasses);
    }

    /**
     * Creates a header to the dialog with gray and blue text.
     * <p>
     * In case that any of the given text is empty, the text is not shown in the
     * header
     * 
     * @param grayText Content of the gray text
     * @param blueText Content of the blue text
     * @return {@code HBox} styled in form of a header with given title
     */
    private static HBox createHeader(String grayText, String blueText) {

        final HBox headerBox = new HBox(5);
        headerBox.setMinHeight(50);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setStyle(QueryfierStyle.HEADER_STYLE);

        if (grayText != null && !grayText.isBlank()) {
            Text headerExportText = new Text(grayText);
            headerExportText.setFill(QueryfierStyle.QF_COLOR_TEXT);
            headerExportText.setStyle(QueryfierStyle.FREDOKA_20PX);
            headerBox.getChildren().add(headerExportText);
        }

        if (blueText != null && !blueText.isBlank()) {
            Text headerProjectText = new Text(blueText);
            headerProjectText.setFill(QueryfierStyle.QF_COLOR);
            headerProjectText.setStyle(QueryfierStyle.FREDOKA_20PX);
            headerBox.getChildren().add(headerProjectText);
        }

        return headerBox;
    }

    /**
     * Creates a TextField in style of Queryfier
     * 
     * @param text given text for initial text in the text field and prompt text
     * @return styled {@code TextField} element
     */
    private static TextField createTextField(String text) {
        final TextField textField = new TextField(text);
        textField.setPromptText(text);
        textField.setStyle(QueryfierStyle.FREDOKA_16PX);
        textField.getStyleClass().addAll(QueryfierStyle.CLASS_FREDOKA, QueryfierStyle.CLASS_SHADOW,
                QueryfierStyle.CLASS_EXPORT_TEXT_FIELD);
        return textField;
    }

    /**
     * Creates a location label for showing location
     * 
     * @param location initial location to set
     * @param maxWidth maximum width of the label
     * @return styled {@code Label} element
     */
    private static Label createLocationLabel(String location, int maxWidth) {
        final Label label = new Label(location);
        label.setMaxWidth(maxWidth);
        label.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
        label.setTextFill(QueryfierStyle.QF_COLOR_TEXT_DARKER);
        label.setStyle(QueryfierStyle.FREDOKA_12PX);
        label.getStyleClass().add(QueryfierStyle.CLASS_FREDOKA);
        return label;
    }

    /**
     * Updates the file location displayed in a label based on the user's input in a
     * text field
     *
     * @param textField     The text field where the user enters the file name
     * @param fileExtension The file extension to be added to the file name
     * @param fileLocation  The label where the updated file location is displayed
     */
    private static void changeLocationOnKeyTyped(TextField textField, String fileExtension, Label fileLocation) {
        Platform.runLater(() -> {
            String fullPath = fileLocation.getText();
            String fileName = textField.getText();
            if (fileName.contains(File.separator)) {
                fileName = fileName.replace(File.separator, "");
            }
            if (fileName.isEmpty()) {
                fileName = textField.getPromptText();
            }
            String path = fullPath.substring(0, fullPath.lastIndexOf(File.separator));
            String newFilePath = path + File.separator + fileName + fileExtension;
            fileLocation.setText(newFilePath);
        });
    }

    /**
     * Creates a JavaFX HBox acting as a "Choose Directory" button
     *
     * @return The HBox being the "Choose Directory" button
     */
    private static HBox createChooseDirectoryButton() {
        final HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(5, 20, 5, 20));
        hBox.setMinHeight(25);
        hBox.getStyleClass().addAll(QueryfierStyle.CLASS_BLACK_BTN, QueryfierStyle.CLASS_SHADOW);

        SVGPath directoryIcon = new SVGPath();
        directoryIcon.setContent(QueryfierStyle.DIRECTORY_ICON);
        directoryIcon.setFill(QueryfierStyle.QF_COLOR);
        HBox.setHgrow(directoryIcon, Priority.NEVER);

        Text chooseDirectoryText = DialogCreator.createDialogText("Choose");
        chooseDirectoryText.setStyle(QueryfierStyle.FREDOKA_12PX);
        HBox.setHgrow(chooseDirectoryText, Priority.NEVER);
        hBox.getChildren().addAll(directoryIcon, chooseDirectoryText);
        return hBox;
    }

    /**
     * Constructs the default file path based on the previous file location or the
     * current working directory
     *
     * @param previousFileLocation The previous file location, which can be null
     * @return The default file path as a String
     */
    private static String constructDefaultFilePath(File previousFileLocation) {
        String defaultPath;
        if (previousFileLocation != null) {
            defaultPath = previousFileLocation.getParent();
        } else {
            try {
                defaultPath = new File(".").getCanonicalPath();
            } catch (IOException e) {
                defaultPath = System.getProperty("user.home");
            }
        }
        return defaultPath;
    }

    /**
     * Opens up the given DirectoryChooser on the given file location, which upon
     * selection updates the label file location
     * 
     * @param dc            DirectoryChooser to open
     * @param stage         Stage on which to open the DirectoryCHooser
     * @param fileName      Name of the file
     * @param fileExtension Extension of the file
     * @param fileLocation  initial location of the file
     */
    private static void openDirectoryChooser(DirectoryChooser dc, Stage stage, String fileName,
            String fileExtension, Label fileLocation) {
        String fullPath = fileLocation.getText();
        String path = fileLocation.getText().substring(0, fullPath.lastIndexOf(File.separator));
        dc.setInitialDirectory(new File(path));
        File chosenFileLocation = dc.showDialog(stage);

        if (chosenFileLocation == null) {
            return;
        }

        Platform.runLater(() -> {
            String sqlPath = chosenFileLocation + File.separator + fileName + fileExtension;
            fileLocation.setText(sqlPath);
        });
    }

    /**
     * Private constructor to prevent the instantiation of the DialogCreator
     * class.
     * <p>
     * This class is intended to be used as a utility class with static methods.
     */
    private DialogCreator() {
    }

}
