package edu.rit.croatia.iste422.g6.qf.view.util;

// Java imports
import java.io.File;

/**
 * The FileExportDefinition record represents a definition for exporting data
 * from the application to a file. It specifies the target database system and
 * the file where the data will be exported.
 *
 * @param databaseSystem The name of the database system to which data is to be
 *                       exported
 * @param exportFile     The file to which the data will be exported
 * 
 * @author Petra Cesar
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Michel Brassard
 * @author Divna Mijic
 */
// The class has been created by "The Sixth Sense" Group.
public record FileExportDefinition(String databaseSystem, File exportFile) {
}
