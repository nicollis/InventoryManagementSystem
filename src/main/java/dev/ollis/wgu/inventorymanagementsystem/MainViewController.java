package dev.ollis.wgu.inventorymanagementsystem;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    public VBox parts_view;

    @FXML
    public VBox products_view;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("MainViewController initialized!");
    }

    public void on_exit(MouseEvent mouseEvent) {
        System.out.println("Exiting application...");
        Platform.exit();
    }
}
