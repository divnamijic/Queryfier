package edu.rit.croatia.iste422.g6.qf.io.composer.util;

// Package imports
import edu.rit.croatia.iste422.g6.qf.io.composer.MySQLComposer;
import edu.rit.croatia.iste422.g6.qf.io.composer.PostgreSQLComposer;
import edu.rit.croatia.iste422.g6.qf.io.composer.SQLComposer;

/**
 * A factory class for creating SQLComposer objects based on the specified
 * DBSystem.
 * 
 * @author Doroteja Krtalic
 * @author Swen Grgicevic
 * @author Michel Brassard
 * @author Petra Cesar
 * @author Divna Mijic
 */
// The class has been created by "The Sixth Sense" Group.
public class SQLComposerFactory {

    /**
     * The class creates and returns an SQLComposer object based on the specified
     * DBSystem.
     *
     * @param system The DBSystem for which to create an SQLComposer.
     * @return An SQLComposer object corresponding to the specified DBSystem.
     * @throws IllegalArgumentException If the specified DBSystem is unsupported.
     */
    public static SQLComposer get(DBSystem system) {
        return switch (system) {
            // a case for MySQLComposer
            case MYSQL -> new MySQLComposer();
            // a case for PostgreSQLComposer
            case POSTGRESQL -> new PostgreSQLComposer();
            // a default case for error
            default -> throw new IllegalArgumentException("Unsupported DBSystem: " + system);
        };
    }

    /**
     * Private constructor to prevent the instantiation of SQLComposerFactory
     * objects.
     * <p>
     * This class should only be used in a static context.
     */
    private SQLComposerFactory() {
    }
}
