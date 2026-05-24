package edu.rit.croatia.iste422.g6.qf.io.composer;

// Package imports
import edu.rit.croatia.iste422.g6.qf.model.db.DatabaseSchema;

// Java imports
import java.io.File;
import java.io.IOException;

/**
 * An interface for composing SQL statements and exporting them to a file.
 * <p>
 * A SQLComposer interface serves as a blueprint for classes responsible for
 * composing SQL files to specific Database Management Systems (DBMS).
 * Implementations of this interface are responsible for composing SQL
 * statements based on a given {@link DatabaseSchema} and writing those
 * statements to a specified file.
 * 
 * @author Petra Cesar
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Michel Brassard
 * @author Divna Mijic
 * 
 * @see DatabaseSchema
 */
// The class has been created by "The Sixth Sense" Group.
public interface SQLComposer {

    /**
     * Composes a SQL script based on the provided {@link DatabaseSchema} and writes
     * the SQL statements to the specified file.
     *
     * @param file     The file to which the SQL statements will be written
     * @param dbSchema The {@link DatabaseSchema} containing the structure of the database
     * @throws IOException If an I/O error occurs during script composition
     * 
     * @see DatabaseSchema
     */
    public void compose(File file, DatabaseSchema dbSchema) throws IOException;

}
