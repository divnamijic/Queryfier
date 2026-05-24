package edu.rit.croatia.iste422.g6.qf.model.db;

/**
 * Represents a relationship between two entities in a database schema, along
 * with the associated attributes.
 * <p>
 * An {@code EntityRelationship} object encapsulates the relationship between
 * two entities and their associated attributes, allowing for modeling the
 * connections between database entities.
 *
 * @param entity         The first entity in the relationship
 * @param attribute      The attribute associated with the first entity
 * @param otherEntity    The second entity in the relationship
 * @param otherAttribute The attribute associated with the second entity
 * 
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Michel Brassard
 * @author Petra Cesar
 * @author Divna Mijic
 * 
 * @see Entity
 * @see Attribute
 */
// The class has been created by "The Sixth Sense" Group.
public record EntityRelationship(Entity entity, Attribute attribute, Entity otherEntity, Attribute otherAttribute) {
}
