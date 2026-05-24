package edu.rit.croatia.iste422.g6.qf.io.parser;

// Package imports
import edu.rit.croatia.iste422.g6.qf.model.db.Attribute;
import edu.rit.croatia.iste422.g6.qf.model.db.DatabaseSchema;
import edu.rit.croatia.iste422.g6.qf.model.db.Entity;
import edu.rit.croatia.iste422.g6.qf.model.db.EntityRelationship;

// Library imports
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Java imports
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * EdgeFileParser implementation of the {@code FileParser} interface. Implements
 * the parse method for parsing Edge files.
 * 
 * @author Michel Brassard
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Petra Cesar
 * @author Divna Mijic
 */
// The class has been created by "The Sixth Sense" Group.
public class EdgeFileParser implements FileParser {

    private static final Logger LOG = LogManager.getLogger(EdgeFileParser.class);
    private final HashMap<Integer, Attribute> attributes = new HashMap<>();
    private final HashMap<Integer, Entity> entities = new HashMap<>();

    private final List<EntityRelationship> listOfEntityRelationships = new ArrayList<>();

    BufferedReader reader;
    String currentLine;

    /**
     * Parses the given Edge file to extract information to a DatabaseSchema.
     * 
     * @param file file chosen for parsing
     */
    @Override
    public DatabaseSchema parse(File file) throws IOException {
        try {
            reader = new BufferedReader(new FileReader(file));

            LOG.info("Reading started.");

            while (((currentLine = reader.readLine()) != null)) {
                if (currentLine.startsWith("Figure ")) {
                    readFigure();
                }
                if (currentLine.startsWith("Connector ")) {
                    readConnector();
                }
            }
            LOG.info("Reading finished.");

            List<Entity> listOfEntitiesWithAttributes = new ArrayList<>(entities.values());

            return new DatabaseSchema(listOfEntitiesWithAttributes, listOfEntityRelationships);
        } catch (NumberFormatException e) {
            LOG.warn("NumberFormatException, parsing of a line failed.");
        }

        return null;
    }

    public void rearrangeConnectors(int endPoint1, int endPoint2) {
        if (entities.containsKey(endPoint1)) {
            Entity entity = entities.get(endPoint1);

            if (attributes.containsKey(endPoint2)) {
                Attribute attribute = attributes.get(endPoint2);
                entity.addAttribute(attribute);
                return;
            }

            Entity otherEntity = entities.get(endPoint2);
            EntityRelationship entityRelationship = new EntityRelationship(entity, null, otherEntity, null);
            listOfEntityRelationships.add(entityRelationship);
            return;
        }
        if (attributes.containsKey(endPoint1)) {
            Attribute attribute = attributes.get(endPoint1);
            Entity otherEntity = entities.get(endPoint2);
            otherEntity.addAttribute(attribute);
        }
    }

    public String getFigureName() throws IOException {
        currentLine = reader.readLine().trim();
        String figureText = currentLine
                .substring(currentLine.indexOf("\"") + 1, currentLine.lastIndexOf("\""))
                .replace(" ", "");
        if (figureText.isEmpty()) {
            // show alert
            LOG.debug("A name for the figure needs to be provided.");
        }

        int escape = figureText.indexOf("\\");
        if (escape > 0) {
            figureText = figureText.substring(0, escape);
        }
        return figureText;
    }

    public void readFigure() throws IOException {
        int figureNumber = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1));

        currentLine = reader.readLine();
        currentLine = reader.readLine();

        currentLine = reader.readLine().trim();
        if (!currentLine.startsWith("Style")) {
            return;
        }

        String figureStyle = currentLine.substring(currentLine.indexOf("\"") + 1,
                currentLine.lastIndexOf("\""));

        if (figureStyle.startsWith("Relation")) {
            // show alert because it contains relations which means it is not normalized
            // logging the alert ?
            LOG.debug("There is a lack of normalization!");
        }
        if (figureStyle.startsWith("Entity")) {
            Entity entity = new Entity(getFigureName());
            LOG.info("Adding Entity {} to Hashmap.", entity.getEntityName());
            entities.put(figureNumber, entity);
        }
        if (figureStyle.startsWith("Attribute")) {
            Attribute attribute = new Attribute(getFigureName());
            do {
                currentLine = reader.readLine().trim();
                if (currentLine.startsWith("TypeUnderl")) {
                    LOG.info("Setting primary key.");
                    attribute.setPrimaryKey(true);
                }
            } while (!currentLine.equals("}"));

            LOG.info("Setting {} attribute.", attribute.getAttributeName());
            attributes.put(figureNumber, attribute);
        }
    }

    public void readConnector() throws IOException {
        do {
            currentLine = reader.readLine().trim();
            if (currentLine.startsWith("Figure")) {
                int endPoint1 = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1));

                currentLine = reader.readLine().trim();
                int endPoint2 = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1));

                // REARRANGE connections between tables and attributes
                rearrangeConnectors(endPoint1, endPoint2);
                break;
            }
        } while (!currentLine.equals("}"));
    }
}
