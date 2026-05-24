package edu.rit.croatia.iste422.g6.qf.io.parser;

// Package imports
import edu.rit.croatia.iste422.g6.qf.model.db.DatabaseSchema;

// Java imports 
import java.io.File;
import java.io.IOException;

/**
 * A FileParser interface serves as a blueprint for classes responsible for
 * parsing specific files and extracting information to create a DatabaseSchema.
 * 
 * @author Petra Cesar
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Michel Brassard
 * @author Divna Mijic
 */
// The class has been created by "The Sixth Sense" Group.
public interface FileParser {

    /**
     * Parses the given file to extract information to a DatabaseSchema.
     * 
     * @param file The file to be parsed
     * @return A {@link DatabaseSchema} object representing the parsed information
     * @throws IOException If an I/O error occurs during file parsing
     * 
     * @see DatabaseSchema
     */
    public DatabaseSchema parse(File file) throws IOException;

}
