package edu.rit.croatia.iste422.g6.qf.controller.handler.menu;

// Package imports
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;

// JavaFX imports
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;

public class ContactUsMenuHandler implements EventHandler<ActionEvent> {

    private final QueryfierDisplay display;

    public ContactUsMenuHandler(QueryfierDisplay display) {
        this.display = display;
    }

    @Override
    public void handle(ActionEvent event) {

        // Display the alert using the view
        display.showAlert(AlertType.INFORMATION,
                "Contact Us - The Queryfier Team",
                "How to Reach Us?",
                "\n\tEmail: sixthsense@rit.edu"
                        + "\n\tMobile phone: +01-30 0701 1102\n");
    }

}
