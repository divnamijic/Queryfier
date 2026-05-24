package edu.rit.croatia.iste422.g6.qf.view.util;

// JavaFX imports
import javafx.scene.paint.Color;

/**
 * The QueryfierStyle class provides constants and utility methods for managing
 * CSS styles, font sizes, and other styling-related properties for Queryfier
 * application.
 * 
 * @author Divna Mijic
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Michel Brassard
 * @author Petra Cesar
 */
// The class has been created by "The Sixth Sense" Group.
public class QueryfierStyle {

    // Css
    public static final String CSS = "/assets/css/style.css";
    public static final String CSS_PATH = QueryfierStyle.class.getResource(CSS).toExternalForm();

    // Fonts
    public static final String FREDOKA = "Fredoka";

    // Font sizes
    public static final String FREDOKA_12PX = "-fx-font: normal 12px 'Fredoka';";
    public static final String FREDOKA_14PX = "-fx-font: normal 14px 'Fredoka';";
    public static final String FREDOKA_16PX = "-fx-font: normal 16px 'Fredoka';";
    public static final String FREDOKA_18PX = "-fx-font: normal 18px 'Fredoka';";
    public static final String FREDOKA_20PX = "-fx-font: normal 20px 'Fredoka';";
    public static final String FREDOKA_22PX = "-fx-font: normal 22px 'Fredoka';";
    public static final String FREDOKA_24PX = "-fx-font: normal 24px 'Fredoka';";
    public static final String FREDOKA_26PX = "-fx-font: normal 26px 'Fredoka';";
    public static final String FREDOKA_28PX = "-fx-font: normal 28px 'Fredoka';";

    // Font Fill
    public static final String TEXT_FILL_BLACK = "-fx-text-fill: black;";

    // Classes
    public static final String CLASS_FREDOKA = "fredoka-text";
    public static final String CLASS_BLACK_BTN = "qf-black-btn";
    public static final String CLASS_BLUE_BTN = "qf-blue-btn";
    public static final String CLASS_WELCOME_BTN = "qf-welcome-btn";
    public static final String CLASS_SHADOW = "shadow";
    public static final String CLASS_TEXT_FIELD = "tf-user-input";
    public static final String CLASS_EXPORT_TEXT_FIELD = "tf-file-export-input";

    // IDs
    public static final String ID_CUSTOM_ALERT_DIALOG = "customAlert";

    // Styles
    public static final String HEADER_STYLE = "-fx-background-color: #1B1B1B; -fx-border-style: hidden hidden solid hidden; -fx-border-width: 1; -fx-border-color: #6EADF7;";
    public static final String FOOTER_STYLE = "-fx-background-color: #1B1B1B; -fx-border-style: solid hidden hidden hidden; -fx-border-width: 1; -fx-border-color: #6EADF7;";
    
    // Dialog
    public static final String WHITE_TEXT_DIALOG_BTN = "-fx-text-fill: white; -fx-font: normal 16px 'Fredoka';";
    public static final String DIALOG_STYLE = "-fx-background-color: #222222; -fx-font: normal 20px 'Fredoka';";
    public static final String ALERT_BTN_STYLE = "-fx-min-width: 0; -fx-padding: 2px 0 2px 0;";

    // Color
    public static final String QF_COLOR_HEX = "#6EADF7";
    public static final String QF_COLOR_BACKGROUND_HEX = "#222222";
    public static final String QF_COLOR_HEADER_HEX = "#1B1B1B";
    public static final String QF_COLOR_GRAY_HEX = "#5E5E5E";
    public static final String QF_COLOR_TEXT_HEX = "#CCCCCC";
    public static final String QF_COLOR_TEXT_DARKER_HEX = "#9D9D9D";
    public static final Color QF_COLOR = Color.web(QF_COLOR_HEX);
    public static final Color QF_COLOR_BACKGROUND = Color.web(QF_COLOR_HEADER_HEX);
    public static final Color QF_COLOR_HEADER = Color.web(QF_COLOR_HEADER_HEX);
    public static final Color QF_COLOR_TEXT_DARKER = Color.web(QF_COLOR_TEXT_DARKER_HEX);
    public static final Color QF_COLOR_TEXT = Color.web(QF_COLOR_TEXT_HEX);
    public static final Color QF_COLOR_GRAY = Color.web(QF_COLOR_GRAY_HEX);

    // Icons
    public static final String DIRECTORY_ICON = "M1.8 15.5C1.305 15.5 0.88125 15.3164 0.52875 14.9492C0.17625 14.582 0 14.1406 0 13.625V2.375C0 1.85937 0.17625 1.41797 0.52875 1.05078C0.88125 0.683594 1.305 0.5 1.8 0.5H7.2L9 2.375H16.2C16.695 2.375 17.1188 2.55859 17.4713 2.92578C17.8238 3.29297 18 3.73437 18 4.25V13.625C18 14.1406 17.8238 14.582 17.4713 14.9492C17.1188 15.3164 16.695 15.5 16.2 15.5H1.8ZM1.8 13.625H16.2V4.25H8.2575L6.4575 2.375H1.8V13.625Z";
    public static final String ENTITY_ICON = "M3.38006 20.5147V33.3743V4.30047V20.5147ZM26.7789 40.0836C27.543 40.0836 28.1953 39.804 28.7358 39.2449C29.2762 38.6858 29.5465 38.0242 29.5465 37.2601C29.5465 36.496 29.2786 35.8437 28.7428 35.3032C28.2069 34.7627 27.543 34.4925 26.7509 34.4925C26.0054 34.4925 25.3531 34.7604 24.794 35.2962C24.2349 35.832 23.9554 36.496 23.9554 37.288C23.9554 38.0335 24.2349 38.6858 24.794 39.2449C25.3531 39.804 26.0148 40.0836 26.7789 40.0836ZM41.3158 16.6009C42.0799 16.6009 42.7322 16.3214 43.2727 15.7623C43.8131 15.2031 44.0834 14.5415 44.0834 13.7774C44.0834 13.0133 43.8155 12.361 43.2797 11.8205C42.7438 11.28 42.0799 11.0098 41.2878 11.0098C40.5423 11.0098 39.89 11.2777 39.3309 11.8135C38.7718 12.3493 38.4923 13.0133 38.4923 13.8054C38.4923 14.5508 38.7718 15.2031 39.3309 15.7623C39.89 16.3214 40.5517 16.6009 41.3158 16.6009ZM9.41846 15.4827H20.6007V12.128H9.41846V15.4827ZM9.41846 25.5467H20.6007V22.192H9.41846V25.5467ZM3.38006 36.7289C2.48548 36.7289 1.70273 36.3935 1.03179 35.7225C0.360857 35.0516 0.0253906 34.2688 0.0253906 33.3743V4.30047C0.0253906 3.40589 0.360857 2.62313 1.03179 1.9522C1.70273 1.28127 2.48548 0.945801 3.38006 0.945801H42.2942C43.1888 0.945801 43.9715 1.28127 44.6425 1.9522C45.3134 2.62313 45.6489 3.40589 45.6489 4.30047H3.38006V33.3743H17.246V36.7289H3.38006ZM26.7443 43.4383C25.0341 43.4383 23.5826 42.8403 22.3898 41.6445C21.1971 40.4486 20.6007 38.9964 20.6007 37.288C20.6007 35.8716 21.02 34.6229 21.8587 33.542C22.6974 32.461 23.769 31.7342 25.0736 31.3615V23.8694H39.6105V19.7319C38.3059 19.3592 37.2343 18.6324 36.3956 17.5514C35.5569 16.4705 35.1376 15.2218 35.1376 13.8054C35.1376 12.097 35.7362 10.6448 36.9333 9.44893C38.1305 8.25307 39.5842 7.65514 41.2944 7.65514C43.0046 7.65514 44.4561 8.25307 45.6489 9.44893C46.8417 10.6448 47.438 12.097 47.438 13.8054C47.438 15.2218 47.0187 16.4705 46.18 17.5514C45.3414 18.6324 44.2697 19.3592 42.9651 19.7319V27.224H28.4283V31.3615C29.7328 31.7342 30.8045 32.461 31.6431 33.542C32.4818 34.6229 32.9011 35.8716 32.9011 37.288C32.9011 38.9964 32.3026 40.4486 31.1054 41.6445C29.9083 42.8403 28.4546 43.4383 26.7443 43.4383Z";
    public static final String TABLE_ICON = "M59 4C60.3333 4 61.5 4.5 62.5 5.5C63.5 6.5 64 7.66666 64 9V59C64 60.3333 63.5 61.5 62.5 62.5C61.5 63.5 60.3333 64 59 64L9 64C7.66666 64 6.5 63.5 5.5 62.5C4.5 61.5 4 60.3333 4 59L4 9C4 7.66666 4.5 6.5 5.5 5.5C6.5 4.5 7.66666 4 9 4L59 4ZM59 32.3333L9 32.3333L9 59L59 59V32.3333ZM59 27.3333V9L9 9V27.3333L59 27.3333Z";

    // Exception
    private static final String FONT_SIZE_ERROR = "Font size must not be less than or equal to 0.";

    /**
     * Generates a CSS font style string for the fredoka font with the specified
     * font size in form of a {@code "-fx-font: normal (size)px 'Fredoka';"}
     *
     * @param size The font size in pixels, must be greater than 0
     * @return A CSS font style string representing the fredoka font with the
     *         specified font size
     * @throws IllegalArgumentException If the specified font size is not greater
     *                                  than 0
     */
    public static String getFredokaFont(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException(FONT_SIZE_ERROR);
        }
        return "-fx-font: normal " + size + "px '" + FREDOKA + "';";
    }

    /**
     * Private constructor to prevent the instantiation
     */
    private QueryfierStyle() {
    }

}