package edu.rit.croatia.iste422.g6.qf.util;

// Java imports
import java.io.PrintWriter;
import java.io.StringWriter;

// JavaFX imports
import javafx.fxml.LoadException;

/**
 * Responsible for formatting exceptions by capturing their stack traces and
 * providing a formatted string representation.
 * 
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Michel Brassard
 * @author Petra Cesar
 * @author Divna Mijic
 */
// The class has been created by "The Sixth Sense" Group.
public class ExceptionFormatter {

    /**
     * Formats a LoadException by capturing its stack trace and converting it into
     * a string representation where line breaks are replaced with the "|"
     * character
     *
     * @param loadException The LoadException to be formatted
     * @return A string representation of the formatted exception stack trace
     */
    public static String format(LoadException loadException) {
        StringWriter sw = new StringWriter();
        loadException.printStackTrace(new PrintWriter(sw));
        return sw.toString().replaceAll("\\R", "|");
    }

    /**
     * Private constructor to prevent the instantiation of ExceptionFormatter.
     * <p>
     * It provides only static utility methods.
     */
    private ExceptionFormatter() {
    }

}
