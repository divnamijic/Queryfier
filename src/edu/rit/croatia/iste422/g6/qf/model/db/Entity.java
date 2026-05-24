package edu.rit.croatia.iste422.g6.qf.model.db;

// Java imports
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a single unique real-world object or concept that is being
 * mastered and stored in a database, which is typically represented as a table
 * in a relational database.
 * <p>
 * Entities have attributes that describe their characteristics or traits.
 * Entities may also have relationships with other
 * entities, allowing for the modeling of complex data interactions.
 * <p>
 * The Entity class itself serves as a conceptual representation and
 * documentation of an entity within the database system, and it is not a direct
 * implementation of a database table or entity.
 * <p>
 * Definition compiled from
 * <a href=
 * "https://uk.indeed.com/career-advice/career-development/entity-in-database">Indeed</a>,
 * <a href=
 * "https://www.knowledgehut.com/blog/database/entity-in-dbms">KnowledgeHut</a>,
 * and <a href=
 * "https://www.ibm.com/docs/en/imdm/12.0?topic=concepts-key-entity-attribute-entity-type">IBM</a>.
 * 
 * @author Divna Mijic
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Michel Brassard
 * @author Petra Cesar
 * 
 * @see Attribute
 */
// The class has been created by "The Sixth Sense" Group.
public class Entity {

    private String entityName;
    private List<Attribute> attributes = new ArrayList<>();

    /**
     * Default constructor which sets uninitialized variables to their default
     * values
     */
    public Entity() {
    }

    /**
     * Constructs an entity with the specified name
     * 
     * @param entityName The name of the attribute
     */
    public Entity(String entityName) {
        this.entityName = entityName;
    }

    /**
     * Constructs an entity with the specified name and attributes
     * 
     * @param entityName The name of the attribute
     * @param attributes A list of entity attributes
     */
    public Entity(String entityName, List<Attribute> attributes) {
        this.entityName = entityName;
        this.attributes = attributes;
    }

    /**
     * Gets the name of the entity
     *
     * @return The name of the entity
     */
    public String getEntityName() {
        return this.entityName;
    }

    /**
     * Gets the list of attributes of this entity
     * 
     * @return The list of attributes
     */
    public List<Attribute> getAttributes() {
        return this.attributes;
    }

    /**
     * Sets the name of the entity
     *
     * @param entityName The name of the entity
     */
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    /**
     * Sets the list of attributes of this entity
     * 
     * @param attributes The list of attributes
     */
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * Retrieves an attribute based on the name of the attribute
     * 
     * @param attributeName The name of the attribute to search for
     * @return The {@link Attribute} object if it is in the entity,
     *         {@code null} otherwise
     */
    public Attribute getAttribute(String attributeName) {
        for (final Attribute attribute : this.attributes) {
            if (attribute.getAttributeName().equals(attributeName)) {
                return attribute;
            }
        }
        return null;
    }

    /**
     * Adds an attribute to the entity
     * 
     * @param attribute The {@link Attribute} to add
     * @return {@code true} if adding the attribute was successful,
     *         throws an exception otherwise
     */
    public boolean addAttribute(Attribute attribute) {
        return this.attributes.add(attribute);
    }

    /**
     * Removes an attribute from the entity
     * 
     * @param attribute The {@link Attribute} to remove
     * @return {@code true} if removal of the attribute was successful,
     *         throws an exception otherwise
     */
    public boolean removeAttribute(Attribute attribute) {
        return this.attributes.remove(attribute);
    }

    /**
     * Removes an attribute from the entity based on the index
     * 
     * @param index The position of the attribute to remove
     * @return Removed {@link Attribute}
     */
    public Attribute removeAttribute(int index) {
        return this.attributes.remove(index);
    }

}
