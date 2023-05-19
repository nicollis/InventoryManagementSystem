package dev.ollis.wgu.inventorymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the parts view in the main view.
 * @author Nicholas Ollis
 */
public class PartsViewController implements Initializable {
    public TextField search_field;
    public TableView<Part> table_view;
    public TableColumn<Part, Integer> idColumn;
    public TableColumn<Part, String> nameColumn;
    public TableColumn<Part, Integer> inventoryColumn;
    public TableColumn<Part, Double> priceColumn;

    /**
     * Called when the view is initialized.
     * Sets up the table view and populates it with all parts.
     * @param url Ignored
     * @param resourceBundle Ignored
     */
    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        ObservableList<Part> allParts = Inventory.getAllParts();
        table_view.setItems(allParts);
    }

    /**
     * Called when the add button is clicked.
     * Opens the parts form view with the is_add flag set to true.
     * @param mouseEvent Ignored
     * @throws IOException If the parts form view cannot be loaded
     */
    public void on_add(MouseEvent mouseEvent) throws IOException {
        open_parts_form(true, null);
    }

    /**
     * Called when the modify button is clicked.
     * Opens the parts form view with the is_add flag set to false.
     * @param mouseEvent Ignored
     * @throws IOException If the parts form view cannot be loaded
     */
    public void on_modify(MouseEvent mouseEvent) throws IOException {
        Part part = table_view.getSelectionModel().getSelectedItem();
        if (part == null) {
            Popup.error("Error", "Please select a part to modify.");
            return;
        }
        open_parts_form(false, part);
    }

    /**
     * Called when the delete button is clicked.
     * Deletes the selected part from the inventory.
     * @param mouseEvent Ignored
     * FUTURE ENHANCEMENT: Don't allow deletion of parts that are associated with products
     */
    public void on_delete(MouseEvent mouseEvent) {
        Part part = table_view.getSelectionModel().getSelectedItem();
        if (part == null) {
            Popup.error("Error", "Please select a part to delete.");
            return;
        }
        if (Popup.showConfirmationDialog("Delete Part", "Are you sure you want to delete this part?")) {
            if (!Inventory.deletePart(part)) {
                Popup.error("Error", "Part is associated with a product and cannot be deleted.");
            }
        }
    }

    /**
     * Opens the parts form view.
     * @param is_add Whether the form should be in add mode or modify mode
     * @param selected_part The part to modify, or null if adding a part
     * @throws IOException If the parts form view cannot be loaded
     */
    private void open_parts_form(boolean is_add, Part selected_part) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("parts-form.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);

        PartsFormViewController controller = loader.getController();
        controller.set_is_add(is_add);
        if (selected_part != null) {
            controller.set_part(selected_part);
        }

        Stage stage = new Stage();
        stage.setTitle(is_add ? "Add a Part" : "Modify a Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Called when the search field is updated.
     * Searches the inventory for parts matching the search term.
     * @param inputMethodEvent Ignored
     */
    public void on_search(KeyEvent inputMethodEvent) {
        String search_term = search_field.getText();
        if (search_term.isEmpty()) {
            table_view.setItems(Inventory.getAllParts());
            return;
        }

        try {
            ObservableList<Part> parts = FXCollections.observableArrayList(Inventory.lookupPart(Integer.parseInt(search_term)));
            if (parts.isEmpty()) {
                parts = on_search_error(search_term);
            }
            table_view.setItems(parts);
        } catch (NumberFormatException e) {
            ObservableList<Part> parts = Inventory.lookupPart(search_term);
            if (parts.isEmpty()) {
                parts = on_search_error(search_term);
            }
            table_view.setItems(parts);
        }
    }

    /**
     * Generates an error popup when a search returns no results.
     * @param search_term The search term
     */
    private ObservableList<Part> on_search_error(String search_term) {
        Popup.error("No results", "No parts found for search term: " + search_term);
        search_field.setText("");
        return Inventory.getAllParts();
    }
}
