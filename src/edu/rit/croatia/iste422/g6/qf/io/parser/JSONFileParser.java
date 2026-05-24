package edu.rit.croatia.iste422.g6.qf.io.parser;

// Package imports
import edu.rit.croatia.iste422.g6.qf.io.parser.util.DatabaseSchemaGenerator;
import edu.rit.croatia.iste422.g6.qf.model.db.DatabaseSchema;

// Java imports
import java.io.File;
import java.io.IOException;

// Library imports
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * JSONFileParser implementation of the {@code FileParser} interface. Implements
 * the parse method for parsing JSON files.
 * 
 * @author Doroteja Krtalic
 * @author Swen Grgicevic
 * @author Michel Brassard
 * @author Petra Cesar
 * @author Divna Mijic
 */
// The class has been created by "The Sixth Sense" Group.
public class JSONFileParser implements FileParser {

    private static final Logger LOG = LogManager.getLogger(JSONFileParser.class);

    /**
     * Parses the given JSON file to extract information to a DatabaseSchema.
     * 
     * @param {@inheritDoc}
     */
    @Override
    public DatabaseSchema parse(File file) throws IOException {
        LOG.info("JSON FileParser has been selected!");
        return DatabaseSchemaGenerator.generateRandomDatabase();
    }

}
