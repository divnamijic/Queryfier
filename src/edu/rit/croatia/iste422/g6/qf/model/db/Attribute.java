package edu.rit.croatia.iste422.g6.qf.model.db;

/**
 * Represents a describing characteristic or property that define an entity.
 * <p>
 * Attributes are also known as columns or fields in database tables. They
 * represent the specific pieces of information or data that can be stored for
 * each record or row in a table. They describe the various features of an
 * entity, which help to distinguish it from other entities.
 * <p>
 * Attributes are essential for querying, sorting, and retrieving data from a
 * database. They provide the structure and organization necessary for efficient
 * data storage and retrieval.
 * <p>
 * Definition compiled from
 * <a href="https://www.lifewire.com/attribute-definition-1019244">LifeWire</a>,
 * <a href="https://intellipaat.com/blog/attributes-in-dbms">Intellipaat</a>,
 * <a href=
 * "https://www.techopedia.com/definition/1164/attribute-database-systems">Techopedia</a>,
 * and <a href=
 * "https://www.ibm.com/docs/en/engineering-lifecycle-management-suite/doors/9.7.0?topic=modules-attributes">IBM</a>.
 * 
 * @author Petra Cesar
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Michel Brassard
 * @author Divna Mijic
 * 
 * @see Entity
 */
// The class has been created by "The Sixth Sense" Group.
public class Attribute {

    private String attributeName;
    private AttributeDataType dataType = AttributeDataType.VARCHAR;
    private boolean primaryKey = false;
    private boolean allowNull = true;
    private String defaultValue;
    private int inputLength = DEFAULT_INPUT_LENGTH;
    private boolean signed = true;
    private boolean autoIncrement = false;

    public static final int DEFAULT_INPUT_LENGTH = 50;

    private static final String INPUT_LENGTH_ERROR = "Input length can not be negative";

    /**
     * Default constructor which sets uninitialized variables to their default
     * values
     */
    public Attribute() {
    }

    /**
     * Constructs an attribute with the specified name
     * 
     * @param attributeName The name of the attribute
     */
    public Attribute(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * Constructs an attribute with the specified name and type
     * 
     * @param attributeName The name of the attribute
     * @param dataType      The data type of the attribute
     * 
     * @see AttributeDataType
     */
    public Attribute(String attributeName, AttributeDataType dataType) {
        this(attributeName, dataType, false);
    }

        // New constructor for the modification
        public Attribute(String attributeName, boolean primaryKey) {
            this.attributeName = attributeName;
            this.primaryKey = primaryKey;
            // Initialize other attributes as needed
        }


    /**
     * Creates an attribute with the specified name, data type, and primary key
     * status
     * 
     * @param attributeName The name of the attribute
     * @param dataType      The data type of the attribute
     * @param primaryKey    Indicates if the attribute is a primary key
     * 
     * @see AttributeDataType
     */
    public Attribute(String attributeName, AttributeDataType dataType, boolean primaryKey) {
        this(attributeName, dataType, primaryKey, true);
    }

    /**
     * Creates an attribute with the specified name, data type, primary key status,
     * and null permission
     * 
     * @param attributeName The name of the attribute
     * @param dataType      The data type of the attribute
     * @param primaryKey    Indicates if the attribute is a primary key
     * @param allowNull     Indicates whether null values are allowed of the
     *                      attribute
     * 
     * @see AttributeDataType
     */
    public Attribute(String attributeName, AttributeDataType dataType, boolean primaryKey, boolean allowNull) {
        this(attributeName, dataType, primaryKey, allowNull, null);
    }

    /**
     * Creates an attribute with the specified name, data type, primary key status,
     * null permission, and default value
     *
     * @param attributeName The name of the attribute
     * @param dataType      The data type of the attribute
     * @param primaryKey    Indicates if the attribute is a primary key
     * @param allowNull     Indicates whether null values are allowed of the
     *                      attribute
     * @param defaultValue  The default value of the attribute
     * 
     * @see AttributeDataType
     */
    public Attribute(String attributeName, AttributeDataType dataType,
            boolean primaryKey, boolean allowNull, String defaultValue) {
        this.attributeName = attributeName;
        this.dataType = dataType;
        this.primaryKey = primaryKey;
        this.allowNull = allowNull;
        this.defaultValue = defaultValue;
    }

    /**
     * Creates an attribute with the specified name, data type, primary key status,
     * null permission, default value, and input length
     *
     * @param attributeName The name of the attribute
     * @param dataType      The data type of the attribute
     * @param primaryKey    Indicates if the attribute is a primary key
     * @param allowNull     Indicates whether null values are allowed of the
     *                      attribute
     * @param defaultValue  The default value of the attribute
     * @param inputLength   The maximum input length of the attribute
     * 
     * @throws IllegalArgumentException if the input length is less than 0
     * @see AttributeDataType
     */
    public Attribute(String attributeName, AttributeDataType dataType, boolean primaryKey,
            boolean allowNull, String defaultValue, int inputLength) {
        if (inputLength < 0) {
            throw new IllegalArgumentException(INPUT_LENGTH_ERROR);
        }
        this.attributeName = attributeName;
        this.dataType = dataType;
        this.primaryKey = primaryKey;
        this.allowNull = allowNull;
        this.defaultValue = defaultValue;
        this.inputLength = inputLength;
    }

    /**
     * Gets the name of the attribute
     *
     * @return The name of the attribute
     */
    public String getAttributeName() {
        return this.attributeName;
    }

    /**
     * Gets the data type of the attribute
     *
     * @return The data type of the attribute
     */
    public AttributeDataType getDataType() {
        return this.dataType;
    }

    /**
     * Indicates if the attribute is a primary key
     *
     * @return {@code true} if the attribute is a primary key, {@code false}
     *         otherwise
     */
    public boolean isPrimaryKey() {
        return this.primaryKey;
    }

    /**
     * Indicates whether null values are allowed of the attribute
     *
     * @return {@code true} if null values are allowed, {@code false} otherwise
     */
    public boolean allowsNull() {
        return this.allowNull;
    }

    /**
     * Gets the default value of the attribute
     *
     * @return The default value of the attribute
     */
    public String getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * Gets the maximum input length of the attribute
     *
     * @return The maximum input length of the attribute
     */
    public int getInputLength() {
        if (dataType.allowsLength()) {
            return this.inputLength;
        }
        return 0;
    }

    /**
     * Indicates if the attribute is signed (for numeric data types).
     *
     * @return {@code true} if the attribute is signed, {@code false} otherwise
     */
    public boolean isSigned() {
        if (dataType.isSignable()) {
            return this.signed;
        }
        return false;
    }

    /**
     * Indicates if the attribute is auto-incremented (for numeric data types).
     *
     * @return {@code true} if the attribute is auto-incremented, {@code false}
     *         otherwise
     */
    public boolean isAutoIncremented() {
        if (dataType.allowsAutoIncrement()) {
            return this.autoIncrement;
        }
        return false;
    }

    /**
     * Sets the name of the attribute
     *
     * @param attributeName The name of the attribute
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * Sets the data type of the attribute
     *
     * @param dataType The data type of the attribute
     */
    public void setDataType(AttributeDataType dataType) {
        this.dataType = dataType;
    }

    /**
     * Sets whether the attribute is a primary key
     *
     * @param primaryKey {@code true} if the attribute is a primary key,
     *                   {@code false} otherwise
     */
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * Sets whether null values are allowed of the attribute
     *
     * @param allowNull {@code true} if null values are allowed, {@code false}
     *                  otherwise
     */
    public void setAllowNull(boolean allowNull) {
        this.allowNull = allowNull;
    }

    /**
     * Sets the default value of the attribute
     *
     * @param defaultValue The default value of the attribute
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Sets the maximum input length of the attribute
     *
     * @param inputLength The maximum input length of the attribute
     * @throws IllegalArgumentException if the input length is negative
     */
    public void setInputLength(int inputLength) {
        if (inputLength < 0) {
            throw new IllegalArgumentException(INPUT_LENGTH_ERROR);
        }
        if (dataType.allowsLength()) {
            this.inputLength = inputLength;
        }
    }

    /**
     * Sets whether the attribute is signed (for numeric data types).
     *
     * @param signed {@code true} if the attribute is signed, {@code false}
     *               otherwise
     */
    public void setSigned(boolean signed) {
        if (dataType.isSignable()) {
            this.signed = signed;
        }
    }

    /**
     * Sets whether the attribute is auto-incremented (for numeric or ID fields).
     *
     * @param autoIncrement {@code true} if the attribute is auto-incremented,
     *                      {@code false} otherwise
     */
    public void setAutoIncrement(boolean autoIncrement) {
        if (dataType.allowsAutoIncrement()) {
            this.autoIncrement = autoIncrement;
        }
    }

}
