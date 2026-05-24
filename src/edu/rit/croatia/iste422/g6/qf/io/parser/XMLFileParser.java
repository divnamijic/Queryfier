package edu.rit.croatia.iste422.g6.qf.io.parser;

// Package imports
import edu.rit.croatia.iste422.g6.qf.io.parser.util.DatabaseSchemaGenerator;
import edu.rit.croatia.iste422.g6.qf.model.db.DatabaseSchema;

// Java imports
import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.*;

/**
 * XMLFileParser implementation of the {@code FileParser} interface. Implements
 * the parse method for parsing XML files.
 * 
 * @author Michel Brassard
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Petra Cesar
 * @author Divna Mijic
 */
// The class has been created by "The Sixth Sense" Group.
public class XMLFileParser implements FileParser {

    private static final Logger LOG = LogManager.getLogger();

    /**
     * Parses the given XML file to extract information to a DatabaseSchema.
     * 
     * @param {@inheritDoc}
     */
    @Override
    public DatabaseSchema parse(File file) throws IOException {
        LOG.info("XML File Parser has been selected!");
        return DatabaseSchemaGenerator.generateRandomDatabase();
    }

}
