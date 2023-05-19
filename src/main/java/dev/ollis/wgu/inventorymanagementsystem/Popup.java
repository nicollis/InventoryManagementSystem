package dev.ollis.wgu.inventorymanagementsystem;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * A class to handle popup dialogs
 * @author Nicholas Ollis
 */
public class Popup {

    /**
     * Shows an error dialog
     * @param title The title of the dialog
     * @param message The message of the dialog
     */
    public static void error(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Shows a conformation dialog
     * @param title The title of the dialog
     * @param message The message of the dialog
     * @return True if the user clicked OK, false otherwise
     */
    public static boolean showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
