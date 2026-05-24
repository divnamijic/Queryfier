package edu.rit.croatia.iste422.g6.qf.model.app;

// Java imports
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.LinkedHashMap;

// JavaX imports
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

// Library imports
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Org imports
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The QueryfierConfiguration class is responsible for managing and persisting
 * configuration settings.
 * <p>
 * This class provides methods to save and load configuration data, as well as
 * access various configuration parameters such as recent files, file locations,
 * and the last opened project file.
 * 
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Michel Brassard
 * @author Petra Cesar
 * @author Divna Mijic
 */
// The class has been created by "The Sixth Sense" Group.
public class QueryfierConfiguration {

    private static final Logger LOG = LogManager.getLogger(QueryfierConfiguration.class);

    private static final String CONFIG_FILE = "qfConfig.xml";

    private final Map<File, Integer> recentFilesMap = new LinkedHashMap<>();
    private final Map<String, File> previousFileLocationsMap = new HashMap<>();

    private File lastOpenProjectFile;

    private static final String CONFIGURATION_ROOT = "Queryfier-Configuration";
    private static final String RECENT_FILES = "Recent-Files";
    private static final String RECENT_FILE = "Recent-File";
    private static final String RECENT_FILE_PATH = "Recent-File-Path";
    private static final String ENTITY_COUNT = "Entity-Count";

    private static final String PREVIOUS_FILE_LOCATIONS = "Previous-File-Locations";
    public static final String PREVIOUS_OPEN_LOCATION = "Open-Location";
    public static final String PREVIOUS_SAVE_LOCATION = "Save-Location";
    public static final String PREVIOUS_EXPORT_LOCATION = "Export-Location";

    private static final String LAST_OPEN_PROJECT = "Last-Open-Project-File";

    /**
     * Saves the current configuration to the {@value #CONFIG_FILE} file, in which
     * it writes the configuration of the application.
     */
    public void saveConfiguration() {

        if (this.recentFilesMap.isEmpty() && this.previousFileLocationsMap.isEmpty() && lastOpenProjectFile == null) {
            return;
        }

        LOG.info("Configuration saving initiated");

        try (FileOutputStream dos = new FileOutputStream(new File(CONFIG_FILE))) {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            final Document doc = dBuilder.newDocument();

            // Creating the root element
            Element rootElement = doc.createElement(CONFIGURATION_ROOT);
            doc.appendChild(rootElement);

            if (!this.recentFilesMap.isEmpty()) {
                // Array of recent files
                Element recentFilesElement = doc.createElement(RECENT_FILES);
                rootElement.appendChild(recentFilesElement);

                // Appending all the recent files to the recent files element
                this.appendRecentFiles(doc, recentFilesElement);
            }

            if (!this.previousFileLocationsMap.isEmpty()) {
                // Locations of all the previous files
                Element previousFileLocationsElement = doc.createElement(PREVIOUS_FILE_LOCATIONS);
                rootElement.appendChild(previousFileLocationsElement);

                // Appending all the previous files to the previous files element
                this.appendPreviousFileLocations(doc, previousFileLocationsElement);
            }

            // Append the last open project
            if (lastOpenProjectFile != null) {
                Element lastOpenProject = doc.createElement(LAST_OPEN_PROJECT);
                lastOpenProject.appendChild(doc.createTextNode(lastOpenProjectFile.getAbsolutePath()));
                rootElement.appendChild(lastOpenProject);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(dos);
            transformer.transform(source, result);

            LOG.info("Configuration saving finalized");

        } catch (ParserConfigurationException | IOException | TransformerException e) {
            String message = "Error while trying to save the file.";
            LOG.warn(message, e);
        }
    }

    /**
     * Appends recent files and their entity counts
     *
     * @param doc                The XML document to which recent files will be
     *                           appended
     * @param recentFilesElement The XML element that represents recent files
     */
    private void appendRecentFiles(Document doc, Element recentFilesElement) {
        for (final Entry<File, Integer> entry : this.recentFilesMap.entrySet()) {

            // Getting the values from the map
            final String absolutePath = entry.getKey().getAbsolutePath();
            final String entityCount = String.valueOf(entry.getValue());

            // Recent File Instance
            final Element recentFileElement = doc.createElement(RECENT_FILE);

            // Path for the recent file, text node for the absolute path and append it to
            // the recent file instance
            Element recentFilePathElement = doc.createElement(RECENT_FILE_PATH);
            recentFilePathElement.appendChild(doc.createTextNode(absolutePath));
            recentFileElement.appendChild(recentFilePathElement);

            // Entity count for the recent file, text node for the entity count and append
            // it to the recent file instance
            Element recentFileEntityCount = doc.createElement(ENTITY_COUNT);
            recentFileEntityCount.appendChild(doc.createTextNode(entityCount));
            recentFileElement.appendChild(recentFileEntityCount);

            // Append the recent file to the recent files
            recentFilesElement.appendChild(recentFileElement);
        }
    }

    /**
     * Appends previous file locations
     *
     * @param doc                   The XML document to which previous file
     *                              locations will be appended
     * @param previousFileLocations The XML element that represents previous file
     *                              locations
     */
    private void appendPreviousFileLocations(Document doc, Element previousFileLocations) {
        for (final Entry<String, File> entry : this.previousFileLocationsMap.entrySet()) {

            // get the location key and the absolute path
            final String location = entry.getKey();
            final String absolutePath = entry.getValue().getAbsolutePath();

            // Element for the location, to append the absolute path
            final Element locationElement = doc.createElement(location);
            locationElement.appendChild(doc.createTextNode(absolutePath));

            // Append the location element to the previous file locations
            previousFileLocations.appendChild(locationElement);
        }
    }

    /**
     * Loads the configuration from the {@value #CONFIG_FILE} file.
     * <p>
     * While it is reading the file, it sets the configuration for the application.
     */
    public void loadConfiguration() {

        if (!(new File(CONFIG_FILE).exists())) {
            LOG.info("No configuration to be loaded");
            return;
        }

        try {

            LOG.info("Configuration loading initiated");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();

            Document doc = documentBuilder.parse(new File(CONFIG_FILE));

            doc.getDocumentElement().normalize();

            // For recent files
            final NodeList recentFilesList = doc.getElementsByTagName(RECENT_FILE);

            for (int i = 0; i < recentFilesList.getLength(); i++) {

                Node recentFileNode = recentFilesList.item(i);

                if (recentFileNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element recentFileElement = (Element) recentFileNode;

                    final String recentFilePath = this.getConfiguration(recentFileElement, RECENT_FILE_PATH, 0);
                    final int entityCount = Integer.parseInt(this.getConfiguration(recentFileElement, ENTITY_COUNT, 0));

                    this.recentFilesMap.put(new File(recentFilePath), entityCount);
                }

            }

            final Node previousFileNode = doc.getElementsByTagName(PREVIOUS_FILE_LOCATIONS).item(0);

            if (previousFileNode != null) {
                this.addPreviousFileLocation(doc, PREVIOUS_OPEN_LOCATION);
                this.addPreviousFileLocation(doc, PREVIOUS_SAVE_LOCATION);
                this.addPreviousFileLocation(doc, PREVIOUS_EXPORT_LOCATION);
            }

            final Node lastOpenProject = doc.getElementsByTagName(LAST_OPEN_PROJECT).item(0);

            if (lastOpenProject != null) {
                lastOpenProjectFile = new File(lastOpenProject.getTextContent());
            }

            LOG.info("Configuration loading finalized");

        } catch (ParserConfigurationException | SAXException | IOException | NullPointerException e) {
            String message = "Error reading file; wrong values.";
            LOG.warn(message, e);
        }

    }

    /**
     * This function takes in an element, a configuration, and an index, and
     * returns the text content of the element's configuration at the given index
     * 
     * @param element       The element that contains the configuration.
     * @param configuration The name of the configuration you want to get.
     * @param index         The index of the configuration you want to get.
     * @return The text content of the element.
     */
    private String getConfiguration(Element element, String configuration, int index) {
        return element.getElementsByTagName(configuration).item(index).getTextContent();
    }

    /**
     * Adds a previous file location to the map of previous file locations.
     *
     * @param doc     The XML document containing the previous file location.
     * @param tagName The name of the XML element representing the location.
     */
    private void addPreviousFileLocation(Document doc, String tagName) {
        final Node previousFileNode = doc.getElementsByTagName(tagName).item(0);
        if (previousFileNode != null) {
            String previousOpenFileLocation = previousFileNode.getTextContent();
            this.previousFileLocationsMap.put(tagName, new File(previousOpenFileLocation));
        }
    }

    /**
     * Retrieves the map of recent files and their associated entity counts
     *
     * @return The map of recent files
     */
    public Map<File, Integer> getRecentFilesMap() {
        return this.recentFilesMap;
    }

    /**
     * Retrieves the map of previous file locations
     *
     * @return The map of previous file locations
     */
    public Map<String, File> getPreviousFileLocationsMap() {
        return this.previousFileLocationsMap;
    }

    /**
     * Retrieves the file representing the previous open location
     *
     * @return The file representing the previous open location
     */
    public File getPreviousOpenLocation() {
        return this.previousFileLocationsMap.get(PREVIOUS_OPEN_LOCATION);
    }

    /**
     * Retrieves the file representing the previous save location
     *
     * @return The file representing the previous save location
     */
    public File getPreviousSaveLocation() {
        return this.previousFileLocationsMap.get(PREVIOUS_SAVE_LOCATION);
    }

    /**
     * Retrieves the file representing the previous export location
     *
     * @return The file representing the previous export location
     */
    public File getPreviousExportLocation() {
        return this.previousFileLocationsMap.get(PREVIOUS_EXPORT_LOCATION);
    }

    /**
     * Retrieves the file representing the last opened project file
     *
     * @return The file representing the last opened project file
     */
    public File getLastOpenProjectFile() {
        return this.lastOpenProjectFile;
    }

    /**
     * Sets the file representing the previous open location.
     *
     * @param previousOpenLocation The file representing the previous open location
     */
    public void setPreviousOpenLocation(File previousOpenLocation) {
        this.previousFileLocationsMap.put(PREVIOUS_OPEN_LOCATION, previousOpenLocation);
    }

    /**
     * Sets the file representing the previous save location.
     *
     * @param previousSaveLocation The file representing the previous save location
     */
    public void setPreviousSaveLocation(File previousSaveLocation) {
        this.previousFileLocationsMap.put(PREVIOUS_SAVE_LOCATION, previousSaveLocation);
    }

    /**
     * Sets the file representing the previous export location.
     *
     * @param previousExportLocation The file representing the previous export
     *                               location
     */
    public void setPreviousExportLocation(File previousExportLocation) {
        this.previousFileLocationsMap.put(PREVIOUS_EXPORT_LOCATION, previousExportLocation);
    }

    /**
     * Sets the file representing the last opened project file.
     *
     * @param lastOpenProjectFile The file representing the last opened project file
     */
    public void setLastOpenProjectFile(File lastOpenProjectFile) {
        this.lastOpenProjectFile = lastOpenProjectFile;
    }

}
