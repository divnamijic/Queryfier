package edu.rit.croatia.iste422.g6.qf.controller.handler.menu;

// Package imports
import edu.rit.croatia.iste422.g6.qf.view.QueryfierDisplay;

// JavaFX imports
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;

public class AboutUsMenuHandler implements EventHandler<ActionEvent> {

    private final QueryfierDisplay display;

    public AboutUsMenuHandler(QueryfierDisplay display) {
        this.display = display;
    }

    @Override
    public void handle(ActionEvent event) {

        // Display the alert using the view
        display.showAlert(AlertType.INFORMATION,
                "About Us - The Queryfier Team",
                "Developed by: The Sixth Sense",
                "Members: \n\tSwen Grgicevic\n\tMichel Brassard\n\tDoroteja Krtalic\n\tPetra Cesar\n\tDivna Mijic\n\n"
                        + "Release Date: 2023-10-22\n"
                        + "App Version: 1.1");
    }

}
