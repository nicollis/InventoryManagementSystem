package dev.ollis.wgu.inventorymanagementsystem;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Main view controller for the application.
 * @author Nicholas Ollis
 */
public class MainViewController {
    @FXML
    public VBox parts_view;

    @FXML
    public VBox products_view;

    /**
     * Exits the application.
     * @param mouseEvent Ignored.
     */
    public void on_exit(MouseEvent mouseEvent) {
        Platform.exit();
    }
}
