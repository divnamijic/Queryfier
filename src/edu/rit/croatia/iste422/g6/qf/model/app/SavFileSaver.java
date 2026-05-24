package edu.rit.croatia.iste422.g6.qf.model.app;

import edu.rit.croatia.iste422.g6.qf.model.db.Attribute;
import edu.rit.croatia.iste422.g6.qf.model.db.Entity;
import edu.rit.croatia.iste422.g6.qf.model.db.EntityRelationship;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Helper class for converting objects from the model into a file
 */
public class SavFileSaver {

    private static final Logger LOG = LogManager.getLogger(SavFileSaver.class);
    private static HashMap<Entity, Integer> entityIDMap = new HashMap<>();
    private static HashMap<Attribute, Integer> attributeIDMap = new HashMap<>();

    private SavFileSaver() {
        //used to hide the implicit public constructor
    }
    public static boolean save(File file, List<Entity> entities, List<EntityRelationship> entityRelationships) {
        entityIDMap = new HashMap<>();
        attributeIDMap = new HashMap<>();
        StringBuilder text = new StringBuilder();

        populateMaps(entities);

        text.append("EdgeConvert Save File\n");
        text.append(turnEntitiesToString(entityRelationships));
        text.append(turnAttributesToString());

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(text.toString());
        }
        catch (IOException ioe) {
            LOG.error("Error saving the file", ioe);
            return false;
        }

        return true;
    }

    private static void populateMaps(List<Entity> entities) {
        int idGenerator = 0;
        for (Entity entity : entities) {
            entityIDMap.put(entity, idGenerator);
            idGenerator++;
            for (Attribute attribute : entity.getAttributes()) {
                attributeIDMap.put(attribute, idGenerator);
                idGenerator++;
            }
        }
    }

    private static String turnEntitiesToString(List<EntityRelationship> entityRelationships) {
        StringBuilder text = new StringBuilder();
        text.append("#Tables#\n");
        entityIDMap.forEach((entityObject, id) -> {
            text.append("Table: ").append(id).append("\n");
            text.append("{\n");
            text.append("TableName: ").append(entityObject.getEntityName()).append("\n");
            text.append("NativeFields: ");

            //change to regular for loop?
            for (Attribute attribute : entityObject.getAttributes()) {
                text.append(attributeIDMap.get(attribute)).append("|");
            }
            text.append("\n");

            StringBuilder relatedTables = new StringBuilder();
            StringBuilder relatedFields = new StringBuilder();
            for (EntityRelationship relationship : entityRelationships) {

                if (entityObject.equals(relationship.entity()) && relationship.otherEntity() != null) {
                    relatedTables.append(entityIDMap.get(relationship.otherEntity())).append("|");

                    if (attributeIDMap.containsKey(relationship.otherAttribute())) {
                        relatedFields.append(attributeIDMap.get(relationship.otherAttribute())).append("|");
                    }
                }
            }

            text.append("RelatedTables: ").append(relatedTables).append("\n");
            text.append("RelatedFields: ").append(relatedFields).append("\n");
            text.append("}\n\n");
        });
        return text.toString();
    }

    private static String turnAttributesToString() {
        StringBuilder text = new StringBuilder();
        text.append("#Fields#\n");
        attributeIDMap.forEach((attributeObject, id) -> {
            text.append(id).append("|");
            text.append(attributeObject.getAttributeName()).append("|");
            text.append(attributeObject.getDefaultValue()).append("|");
            text.append(attributeObject.getDataType()).append("|");
            text.append(attributeObject.getInputLength()).append("|");
            text.append(attributeObject.isSigned()).append("|");
            text.append(attributeObject.isAutoIncremented()).append("|");
            text.append(attributeObject.allowsNull()).append("|");
            text.append(attributeObject.isPrimaryKey()).append("|");
            text.append("\n");
        });
        return text.toString();
    }
}
