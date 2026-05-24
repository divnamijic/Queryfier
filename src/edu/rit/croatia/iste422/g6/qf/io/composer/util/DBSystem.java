package edu.rit.croatia.iste422.g6.qf.io.composer.util;

/**
 * The DBSystem enum represents various database management systems (DBMS)
 * that can be used to export the file to.
 * 
 * @author Doroteja Krtalic
 * @author Swen Grgicevic
 * @author Michel Brassard
 * @author Petra Cesar
 * @author Divna Mijic
 */
// The class has been created by "The Sixth Sense" Group.
public enum DBSystem {
    MYSQL("MySQL"),
    POSTGRESQL("PostgreSQL");

    final String name;

    private DBSystem(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}