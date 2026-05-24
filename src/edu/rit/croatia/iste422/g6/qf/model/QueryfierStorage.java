package edu.rit.croatia.iste422.g6.qf.model;

// Package imports
import edu.rit.croatia.iste422.g6.qf.io.composer.SQLComposer;
import edu.rit.croatia.iste422.g6.qf.io.composer.util.DBSystem;
import edu.rit.croatia.iste422.g6.qf.io.composer.util.SQLComposerFactory;
import edu.rit.croatia.iste422.g6.qf.io.parser.FileParser;
import edu.rit.croatia.iste422.g6.qf.io.parser.util.FileExtension;
import edu.rit.croatia.iste422.g6.qf.io.parser.util.FileParserFactory;
import edu.rit.croatia.iste422.g6.qf.model.app.QueryfierConfiguration;
import edu.rit.croatia.iste422.g6.qf.model.app.SavFileSaver;
import edu.rit.croatia.iste422.g6.qf.model.db.Attribute;
import edu.rit.croatia.iste422.g6.qf.model.db.AttributeDataType;
import edu.rit.croatia.iste422.g6.qf.model.db.DatabaseSchema;
import edu.rit.croatia.iste422.g6.qf.model.db.Entity;
import edu.rit.croatia.iste422.g6.qf.model.db.EntityRelationship;

// Library imports
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Java imports
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * <h3>Main Storage of Queryfier</h3>
 * 
 * It is in charge of storage and manipulation of database schema-related data.
 * It provides methods for loading and saving database schemas, exporting
 * schemas to different database systems, managing entities and attributes, and
 * handling configuration settings of the application.
 * 
 * @author Michel Brassard
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Petra Cesar
 * @author Divna Mijic
 * 
 * @see QueryfierConfiguration
 * @see Entity
 * @see EntityRelationship
 */
// The class has been created by "The Sixth Sense" Group.
public class QueryfierStorage {

    private static final Logger LOG = LogManager.getLogger(QueryfierStorage.class);
    private final QueryfierConfiguration configuration = new QueryfierConfiguration();
    private final List<Entity> entities = new ArrayList<>();
    private final List<EntityRelationship> entityRelationships = new ArrayList<>();

    /**
     * Loads a database schema from a file.
     * <p>
     * This method attempts to load a database schema from a specified file. It
     * determines the file type based on the file extension, parses the file, and
     * populates the application's entity and relationship
     * collections with the parsed data.
     *
     * @param file The file from which to load the database schema.
     * @return {@code true} if the file was successfully loaded and parsed,
     *         {@code false} if an error occurred or if the file is unsupported.
     */
    public boolean loadFile(final File file) {
        if (file == null) {
            LOG.warn("File not found");
            return false;
        }
        LOG.info("File loading started");

        // Determine what file it is going to be read from based on extension
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1); // only get the extension
        final FileExtension fileExtension = FileExtension.convert(extension);

        if (fileExtension == null) {
            return false; // Unsupported file type... yet
        }

        final FileParser fileParser = FileParserFactory.get(fileExtension);

        // Clear the lists before parsing
        this.entities.clear();
        this.entityRelationships.clear();

        // Parse the file
        try {
            LOG.info("File parsing initialized");

            final DatabaseSchema dbSchema = fileParser.parse(file);

            if (dbSchema == null) {
                return false;
            }

            this.entities.addAll(dbSchema.entities());
            this.entityRelationships.addAll(dbSchema.entityRelationships());

            LOG.info("File parsing successful");
        } catch (IOException e) {
            LOG.error("Unable to parse file", e);
            return false;
        }

        return !this.entities.isEmpty();
    }

    public boolean saveFile(final File file) {
        return SavFileSaver.save(file, entities, entityRelationships);
    }

    /**
     * Exports a file using the specified database system.
     *
     * @param file   The file to be exported.
     * @param system The name of the database system to use for exporting.
     * @return {@code true} if the export was successful, {@code false} otherwise.
     */
    public boolean exportFile(File file, String system) {

        LOG.info("Export file initialized");
        // Convert the sys parameter to uppercase and get the corresponding DBSystem
        // enum value.
        final DBSystem dbSystem = this.getDBSystem(system);

        // Check if the provided DBSystem value is null
        // If it is, exporting can't be completed.
        if (dbSystem == null) {
            return false;
        }

        // Get an SQLComposer instance based on the database system.
        final SQLComposer composer = SQLComposerFactory.get(dbSystem);

        try {
            // Compose SQL statements for the given file and DatabaseSchema.
            composer.compose(file, new DatabaseSchema(this.entities, this.entityRelationships));
            LOG.info("Export file successful");
        } catch (IOException e) {
            // Handle any IOException that may occur during the export process.
            LOG.error("Unable to export file", e);
            return false;
        }

        // Return true to indicate a successful export.
        return true;
    }

    /**
     * Retrieves a DBSystem enum constant that matches the provided system name.
     * 
     * @param system The name of the DBSystem to retrieve
     * @return The matching {@link DBSystem} enum constant, or {@code null} if no
     *         match is found
     */
    private DBSystem getDBSystem(String system) {
        return Stream.of(DBSystem.values())
                .filter(sys -> sys.name().equalsIgnoreCase(system))
                .findFirst().orElse(null);
    }

    /**
     * Saves the current application configuration
     * <p>
     * This method is responsible for persisting the current configuration
     * to a file
     * 
     * @throws IOException If an error occurs while saving the configuration
     */
    public void saveConfiguration() {
        this.configuration.saveConfiguration();
    }

    /**
     * Loads application configuration from a file
     * <p>
     * This method retrieves and loads configuration from a storage
     * location into the application
     * 
     * @throws IOException If an error occurs while loading the configuration
     */
    public void loadConfiguration() {
        this.configuration.loadConfiguration();
    }

    /**
     * Retrieves a map of recent files and their associated usage counts
     * <p>
     * 
     * @return A map containing recent files as keys and their usage counts as
     *         values
     */
    public Map<File, Integer> getRecentFiles() {
        return this.configuration.getRecentFilesMap();
    }

    /**
     * Retrieves a list of available database systems the application is able to
     * work with
     *
     * @return A list of database system names
     */
    public List<String> getAvailableDBSystems() {
        return Stream.of(DBSystem.values()).map(DBSystem::getName).toList();
    }

    /**
     * Gets a list of available file extensions
     *
     * @return A list of file extensions
     */
    public List<String> getFileExtensionList() {
        return Stream.of(FileExtension.values()).map(FileExtension::getExtension).toList();
    }

    /**
     * Gets a list of available attribute data types
     *
     * @return A list of attribute data type names
     */
    public List<String> getAttributeDataTypes() {
        return Stream.of(AttributeDataType.values()).map(AttributeDataType::name).toList();
    }

    /**
     * Gets an attribute by name within a specified entity
     *
     * @param entityName    The name of the entity containing the attribute
     * @param attributeName The name of the attribute to retrieve
     * @return The attribute with the specified name or {@code null} if not found
     */
    public Attribute getAttributeByEntity(String entityName, String attributeName) {
        final Entity entity = this.findEntity(entityName);
        return (entity == null) ? null : this.getAttributeByEntity(entity, attributeName);
    }

    /**
     * Gets an attribute by name within a specified entity
     *
     * @param entity        The entity containing the attribute
     * @param attributeName The name of the attribute to retrieve
     * @return The attribute with the specified name or {@code null} if not found
     */
    public Attribute getAttributeByEntity(Entity entity, String attributeName) {
        for (final Attribute attribute : entity.getAttributes()) {
            if (attribute.getAttributeName().equals(attributeName)) {
                return attribute;
            }
        }
        return null;
    }

    /**
     * Binds two entities in a database schema together through their attributes,
     * creating a relationship.
     * <p>
     * This method establishes a relationship between two entities by specifying the
     * names of the entities, their attributes, and the names of the attributes of
     * the other entity involved in the relationship.
     * <p>
     * If any of the specified entities or attributes cannot be found, the binding
     * operation fails, and the method returns {@code false}.
     *
     * @param entityName         The name of the first entity in the relationship.
     * @param attributeName      The name of the attribute associated with the first
     *                           entity.
     * @param otherEntityName    The name of the second entity in the relationship.
     * @param otherAttributeName The name of the attribute associated with the
     *                           second entity.
     * @return {@code True} if the binding operation was successful; {@code False}
     *         if any of the specified entities or attributes could not be found or
     *         if the relationship already exists.
     */
    public boolean bindEntities(String entityName, String attributeName,
            String otherEntityName, String otherAttributeName) {

        // Find the first entity and attribute
        final Entity entity = this.findEntity(entityName);
        final Attribute attribute = this.getAttributeByEntity(entity, attributeName);
        if (entity == null || attribute == null) {
            return false;
        }

        // Find the second entity and attribute
        final Entity otherEntity = this.findEntity(otherEntityName);
        final Attribute otherAttribute = this.getAttributeByEntity(otherEntity, otherAttributeName);
        if (otherEntity == null || otherAttribute == null) {
            return false;
        }

        return this.entityRelationships.add(new EntityRelationship(entity, attribute, otherEntity, otherAttribute));
    }

    /**
     * Finds an entity based on the given entity name
     * 
     * @param entityName The name of the entity to search for
     * @return The Entity object with the specified name, or {@code null} if the
     *         entity was not found
     */
    public Entity findEntity(String entityName) {
        for (final Entity entity : this.entities) {
            if (entity.getEntityName().equals(entityName)) {
                return entity;
            }
        }
        return null;
    }

    /**
     * Adds a new entity to the collection
     *
     * @param entityName The name of the entity to add
     * @return {@code True} if the entity was added successfully; {@code False} if
     *         an entity with
     *         the same name already exists
     */
    public boolean addEntity(String entityName) {
        final Entity entity = this.findEntity(entityName);
        if (entity != null) {
            return false;
        }
        return this.entities.add(new Entity(entityName));
    }

    /**
     * Removes an entity from the collection
     *
     * @param entityName The name of the entity to remove
     * @return {@code True} if the entity was removed successfully; {@code False} if
     *         the entity was
     *         not found in the collection
     */
    public boolean removeEntity(String entityName) {
        final Entity entity = this.findEntity(entityName);
        if (entity == null) {
            return false;
        }
        // Removing all the relationships tied to the Entity object
        this.entityRelationships.removeIf(relationship -> relationship.entity().equals(entity)
                || relationship.otherEntity().equals(entity));
        return this.entities.remove(entity);
    }

    /**
     * Retrieves the list of entities
     * 
     * @return List of {@link Entity} objects
     */
    public List<Entity> getEntityList() {
        return this.entities;
    }

    /**
     * Retrieves the list of entity relationships
     * 
     * @return List of {@link EntityRelationship} objects
     */
    public List<EntityRelationship> getEntityRelationships() {
        return this.entityRelationships;
    }

    /**
     * Retrieves the map of previous file locations
     *
     * @return The map of previous file locations
     */
    public Map<String, File> getPreviousFileLocations() {
        return this.configuration.getPreviousFileLocationsMap();
    }

    /**
     * Retrieves the file representing the previous open location
     *
     * @return The file representing the previous open location
     */
    public File getPreviousOpenLocation() {
        return this.configuration.getPreviousOpenLocation();
    }

    /**
     * Retrieves the file representing the previous save location
     *
     * @return The file representing the previous save location
     */
    public File getPreviousSaveLocation() {
        return this.configuration.getPreviousSaveLocation();
    }

    /**
     * Retrieves the file representing the previous export location
     *
     * @return The file representing the previous export location
     */
    public File getPreviousExportLocation() {
        return this.configuration.getPreviousExportLocation();
    }

    /**
     * Retrieves the file representing the last opened project file
     *
     * @return The file representing the last opened project file
     */
    public File getLastOpenProjectFile() {
        return this.configuration.getLastOpenProjectFile();
    }

    /**
     * Sets the file representing the previous open location.
     *
     * @param previousOpenLocation The file representing the previous open location
     */
    public void setPreviousOpenLocation(File previousOpenLocation) {
        this.configuration.setPreviousOpenLocation(previousOpenLocation);
    }

    /**
     * Sets the file representing the previous save location.
     *
     * @param previousSaveLocation The file representing the previous save location
     */
    public void setPreviousSaveLocation(File previousSaveLocation) {
        this.configuration.setPreviousSaveLocation(previousSaveLocation);
    }

    /**
     * Sets the file representing the previous export location.
     *
     * @param previousExportLocation The file representing the previous export
     *                               location
     */
    public void setPreviousExportLocation(File previousExportLocation) {
        this.configuration.setPreviousExportLocation(previousExportLocation);
    }

    /**
     * Sets the file representing the last opened project file.
     *
     * @param lastOpenProjectFile The file representing the last opened project file
     */
    public void setLastOpenProjectFile(File lastOpenProjectFile) {
        this.configuration.setLastOpenProjectFile(lastOpenProjectFile);
    }

}
