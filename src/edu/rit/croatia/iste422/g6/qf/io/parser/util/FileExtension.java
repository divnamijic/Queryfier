package edu.rit.croatia.iste422.g6.qf.io.parser.util;

/**
 * <h3>Enum representing different file extensions.</h3>
 * <p>
 * This enum provides constants for common file extensions along with methods to
 * retrieve the extension string and convert from a string to an enum value.
 * 
 * @author Doroteja Krtalic
 * @author Swen Grgicevic
 * @author Michel Brassard
 * @author Petra Cesar
 * @author Divna Mijic
 */
// The class has been created by "The Sixth Sense" Group.
public enum FileExtension {
    EDGE("edg"),
    SAVE("sav"),
    JSON("json"),
    XML("xml");

    private final String extension;

    /**
     * Constructs a FileExtension enum constant with the specified extension
     *
     * @param extension The file extension string associated with this enum
     *                  constant
     */
    private FileExtension(String extension) {
        this.extension = extension;
    }

    /**
     * Gets the file extension string associated with this enum constant
     *
     * @return The file extension string
     */
    public String getExtension() {
        return this.extension;
    }

    /**
     * Converts a file extension string to the corresponding FileExtension enum
     * value
     *
     * @param extension The file extension string to convert
     * @return The FileExtension enum constant corresponding to the provided
     *         extension, or null if no matching enum constant is found.
     */
    public static FileExtension convert(String extension) {
        for (final FileExtension fex : FileExtension.values()) {
            if (fex.getExtension().equals(extension)) {
                return fex;
            }
        }
        return null;
    }

}
