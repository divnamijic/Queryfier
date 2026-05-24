package edu.rit.croatia.iste422.g6.qf.io.parser.util;

// Package imports
import edu.rit.croatia.iste422.g6.qf.io.parser.EdgeFileParser;
import edu.rit.croatia.iste422.g6.qf.io.parser.FileParser;
import edu.rit.croatia.iste422.g6.qf.io.parser.JSONFileParser;
import edu.rit.croatia.iste422.g6.qf.io.parser.SaveFileParser;
import edu.rit.croatia.iste422.g6.qf.io.parser.XMLFileParser;

/**
 * Factory class for creating FileParser objects based on file extensions.
 * 
 * @author Doroteja Krtalic
 * @author Swen Grgicevic
 * @author Michel Brassard
 * @author Petra Cesar
 * @author Divna Mijic
 */
// The class has been created by "The Sixth Sense" Group.
public class FileParserFactory {

    /**
     * The class creates a FileParser object based on the provided file extension.
     *
     * @param extension The file extension to determine the type of parser.
     * @return A FileParser object corresponding to the provided extension.
     * @throws IllegalArgumentException If the extension is not supported.
     */
    public static FileParser get(FileExtension extension) {
        return switch (extension) {
            // a case for edge files
            case EDGE -> new EdgeFileParser();
            // a case for save files
            case SAVE -> new SaveFileParser();
            // a case for xml files
            case XML -> new XMLFileParser();
            // a case for JSON files
            case JSON -> new JSONFileParser();
            // a default case for error
            default -> throw new IllegalArgumentException("Unsupported file extension: " + extension);
        };
    }

    /**
     * Private constructor to prevent the instantiation of FileParserFactory
     * objects.
     * <p>
     * This class should only be used in a static context.
     */
    private FileParserFactory() {
    }
}
