package edu.rit.croatia.iste422.g6.qf.model.db;

// Java imports
import java.util.List;

/**
 * Represents a database schema containing a list of entities and entity
 * relationships.
 * <p>
 * A database schema defines how data is organized within a relational database;
 * this is inclusive of logical constraints such as, table names, fields, data
 * types, and the relationships between these entities.
 * <p>
 * It is considered the "Blueprint" of a database which describes how the data
 * may relate to other tables or other data models. However, the schema does not
 * actually contain data.
 * <p>
 * Definition can be found at
 * <a href="https://www.ibm.com/topics/database-schema">IBM</a>.
 *
 * @param entities            A list of {@link Entity} objects representing the
 *                            tables/entities in the schema
 * @param entityRelationships A list of {@link EntityRelationship} objects
 *                            representing the relationships between entities
 * 
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Michel Brassard
 * @author Petra Cesar
 * @author Divna Mijic
 * 
 * @see Entity
 * @see EntityRelationship
 */
// The class has been created by "The Sixth Sense" Group.
public record DatabaseSchema(List<Entity> entities, List<EntityRelationship> entityRelationships) {
}
