package edu.rit.croatia.iste422.g6.qf.io.composer;

// Package imports
import edu.rit.croatia.iste422.g6.qf.model.db.Attribute;
import edu.rit.croatia.iste422.g6.qf.model.db.DatabaseSchema;
import edu.rit.croatia.iste422.g6.qf.model.db.Entity;
import edu.rit.croatia.iste422.g6.qf.model.db.EntityRelationship;
import edu.rit.croatia.iste422.g6.qf.util.FileUtil;

// Java imports
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

// Library imports
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * MySQLComposer implementation of the {@link SQLComposer} interface for
 * composing the MySQL-specific SQL statements based on a given
 * {@link DatabaseSchema} for the MySQL Database Management System.
 * <p>
 * The class provides an implementation for composing SQL statements to a
 * specified file. The specific implementation is tailored for MySQL databases.
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
public class MySQLComposer implements SQLComposer {

    private static final Logger LOG = LogManager.getLogger(MySQLComposer.class);

    /**
     * Composes a MySQL script based on the provided {@link DatabaseSchema} and
     * writes the MySQL statements to the specified file.
     * 
     * @param file     The file to which the MySQL SQL statements will be written
     * @param dbSchema The {@link DatabaseSchema} containing the structure of the
     *                 database
     * @throws IOException If an I/O error occurs during script composition
     * 
     * @see SQLComposer#compose(File, DatabaseSchema)
     * @see DatabaseSchema
     */
    @Override
    public void compose(File file, DatabaseSchema dbSchema) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            StringBuilder sqlText = new StringBuilder();
            List<Entity> entities = dbSchema.entities();
            List<EntityRelationship> entityRelationships = dbSchema.entityRelationships();

            String databaseName = FileUtil.removeExtension(file.getName());

            String database = "CREATE DATABASE " + databaseName + ";\nUSE " + databaseName + ";\n\n";
            sqlText.append(database);

            for (Entity entity : entities) {
                sqlText.append(convertEntityToQuery(entity));
            }
            if (entityRelationships != null && !entityRelationships.isEmpty()) {
                for (EntityRelationship relationship : entityRelationships) {
                    sqlText.append(convertRelationshipToUpdateQuery(relationship));
                }
            }
            writer.write(sqlText.toString());
        } catch (IOException ioe) {
            LOG.error("An I/O error occurred during script composition", ioe);
            throw new IOException(ioe);
        }
    }

    public String convertEntityToQuery(Entity entity) {
        StringBuilder primaryKeyConstraint = new StringBuilder();
        StringBuilder query = new StringBuilder("CREATE TABLE `" + entity.getEntityName() + "` (\n");

        List<Attribute> attributes = entity.getAttributes();
        for (int i = 0; i < attributes.size(); i++) {
            if (attributes.get(i).isPrimaryKey()) {
                primaryKeyConstraint
                        .append(String.format("   PRIMARY KEY (%s),", attributes.get(i).getAttributeName()));
            }

            String column = convertAttributeToColumn(attributes.get(i));
            query.append(column);

            if (attributes.size() - 1 != i) {
                query.append(",\n");
            }
        }
        query.append(primaryKeyConstraint);
        query.append("\n);\n");
        return query.toString();
    }

    public String convertAttributeToColumn(Attribute attribute) {
        String column = "  `" + attribute.getAttributeName() + "` " + attribute.getDataType();
        if (attribute.getDataType().allowsLength()) {
            column += "(" + attribute.getInputLength() + ")";
        }
        if (!attribute.isSigned() && attribute.getDataType().isSignable()) {
            column += " UNSIGNED";
        }
        if (!attribute.allowsNull()) {
            column += " NOT NULL";
        }
        String defaultValue = attribute.getDefaultValue();
        if (defaultValue != null) {
            column += defaultValue;
        }
        if (attribute.isAutoIncremented()) {
            column += " AUTO_INCREMENT";
        }
        return column;
    }

    public String convertRelationshipToUpdateQuery(EntityRelationship relationship) {
        if (relationship.attribute() != null && relationship.otherAttribute() != null) {
            return "ALTER TABLE " + "`" + relationship.entity().getEntityName() + "`" + " ADD CONSTRAINT FK_"
                    + removeEmptySpace(relationship.attribute().getAttributeName())
                    + " FOREIGN KEY (`" + relationship.attribute().getAttributeName() + "`) REFERENCES "
                    + "`" + relationship.otherEntity().getEntityName() + "`" + "(`"
                    + relationship.otherAttribute().getAttributeName() + "`);\n";
        }
        return "";
    }

    public String removeEmptySpace(String name) {
        return name.replace(" ", "_");
    }

}
