package edu.rit.croatia.iste422.g6.qf.model.db;

/**
 * Enumeration of SQL data types used to represent attribute types in a
 * database.
 * <p>
 * It defines the type of data that a column in a database table can hold. It
 * specifies the kind of values that can be stored in a particular column, as
 * well as the operations that can be performed on those values.
 * 
 * @author Petra Cesar
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Michel Brassard
 * @author Divna Mijic
 * 
 * @see Attribute
 */
// The class has been created by "The Sixth Sense" Group.
public enum AttributeDataType {

    // Numeric SQL Data Types
    BIT(false, false, false),
    TINYINT(false, true, true),
    BOOLEAN(false, false, false),
    SMALLINT(false, true, true),
    INT(false, true, true),
    BIGINT(false, true, true),
    FLOAT(false, true, false),
    DOUBLE(false, true, false),
    DECIMAL(false, true, false),

    // String SQL Data Types
    CHAR(true, false, false),
    VARCHAR(true, false, false),
    BINARY(true, false, false),
    VARBINARY(true, false, false),
    TEXT(false, false, false),
    BLOB(false, false, false),
    LONGTEXT(false, false, false),

    // Date and Time SQL Data Types
    DATE(false, false, false),
    DATETIME(false, false, false),
    TIMESTAMP(false, false, false),
    TIME(false, false, false),
    YEAR(false, false, false);

    private final boolean allowsLength;
    private final boolean signable;
    private final boolean allowsAutoIncrement;

    AttributeDataType(boolean allowsLength, boolean signable, boolean allowsAutoIncrement) {
        this.allowsLength = allowsLength;
        this.signable = signable;
        this.allowsAutoIncrement = allowsAutoIncrement;
    }

    /**
     * Indicates if the attribute data type includes length
     *
     * @return {@code true} if the attribute data type includes length,
     *         {@code false} otherwise
     */
    public boolean allowsLength() {
        return this.allowsLength;
    }

    /**
     * Indicates if the attribute data type is of numeric type
     *
     * @return {@code true} if the attribute data type is numeric,
     *         {@code false} otherwise
     */
    public boolean isSignable() {
        return this.signable;
    }

    public boolean allowsAutoIncrement() {
        return this.allowsAutoIncrement;
    }

}

