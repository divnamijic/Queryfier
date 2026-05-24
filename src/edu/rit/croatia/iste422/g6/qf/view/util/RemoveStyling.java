package edu.rit.croatia.iste422.g6.qf.view.util;

import java.util.*;

import javafx.scene.*;
import javafx.scene.control.*;

/**
 * The RemoveStyling class provides utility methods to remove specific styling
 * classes from JavaFX nodes.
 * <p>
 * It allows removing styling classes for nodes, making them visually unstyled.
 */
public class RemoveStyling {

   /**
    * Removes specific styling classes from nodes in the JavaFX scene graph rooted
    * at the given parent node.
    * <p>
    * The styling classes removed are the following:
    * 
    * <ul>
    * 
    * <li>
    * <h3>TextField</h3>
    * <ul>
    * <li>{@code "text-input"}</li>
    * <li>{@code "text-field"}</li>
    * </ul>
    * </li>
    * 
    * <li>
    * <h3>Button</h3>
    * <ul>
    * <li>{@code "button"}</li>
    * </ul>
    * </li>
    * 
    * </ul>
    *
    * @param parent The parent node of the JavaFX scene graph from which styling
    *               classes will be removed
    */
   public static void of(Parent parent) {
      for (Node node : RemoveStyling.getAllNodes(parent)) {
         if (node instanceof TextField) {
            node.getStyleClass().removeAll("text-input", "text-field");
         } else if (node instanceof Button) {
            node.getStyleClass().remove("button");
         }
      }
   }

   /**
    * Retrieves a list of all nodes present in the JavaFX scene graph rooted at the
    * given parent node, including the parent node itself.
    *
    * @param parent The parent node of the JavaFX scene graph
    * @return A list of all nodes in the scene graph
    */
   public static List<Node> getAllNodes(Parent parent) {
      List<Node> nodes = new ArrayList<>();
      RemoveStyling.addAllDescendants(parent, nodes);
      return nodes;
   }

   /**
    * Recursive method to get all the children of a parent node and add them to a
    * list.
    * <p>
    * This method traverses the JavaFX scene graph rooted at the {@code parent}
    * node and populates the provided {@code nodes} list with all the nodes found.
    *
    * @param parent The parent node whose descendants are to be collected
    * @param nodes  The list to which nodes are added
    */
   private static void addAllDescendants(Parent parent, List<Node> nodes) {
      for (Node node : parent.getChildrenUnmodifiable()) {
         nodes.add(node);
         if (node instanceof Parent) {
            RemoveStyling.addAllDescendants((Parent) node, nodes);
         }
      }
   }

   /**
    * Private constructor to prevent the instantiation
    */
   private RemoveStyling() {
   }

}