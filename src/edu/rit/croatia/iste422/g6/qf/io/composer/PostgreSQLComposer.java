package edu.rit.croatia.iste422.g6.qf.io.composer;

// Package imports
import edu.rit.croatia.iste422.g6.qf.model.db.DatabaseSchema;

// Java imports
import java.io.File;
import java.io.IOException;

/**
 * PostgreSQLComposer implementation of the {@link SQLComposer} interface for
 * composing the PostgreSQL-specific SQL statements based on a given
 * {@link DatabaseSchema} for the PostgreSQL Database Management System.
 * <p>
 * The class provides an implementation for composing SQL statements to a
 * specified file. The specific implementation is tailored for PostgreSQL
 * databases.
 * 
 * @author Michel Brassard
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Petra Cesar
 * @author Divna Mijic
 * 
 * @see SQLComposer
 * @see DatabaseSchema
 */
// The class has been created by "The Sixth Sense" Group.
public class PostgreSQLComposer implements SQLComposer {

    /**
     * Composes a PostgreSQL script based on the provided {@link DatabaseSchema} and
     * writes the PostgreSQL statements to the specified file.
     * 
     * @param {@inheritDoc}
     */
    @Override
    public void compose(File file, DatabaseSchema dbSchema) throws IOException {
        // Unimplemented for now...
    }

}
