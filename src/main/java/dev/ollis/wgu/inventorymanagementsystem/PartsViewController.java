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

public class PartsViewController implements Initializable {
    public TextField search_field;
    public TableView<Part> table_view;
    public TableColumn<Part, Integer> idColumn;
    public TableColumn<Part, String> nameColumn;
    public TableColumn<Part, Integer> inventoryColumn;
    public TableColumn<Part, Double> priceColumn;

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        ObservableList<Part> allParts = Inventory.getAllParts();
        table_view.setItems(allParts);
    }

    public void on_add(MouseEvent mouseEvent) throws IOException {
        open_parts_form(true, null);
    }

    public void on_modify(MouseEvent mouseEvent) throws IOException {
        Part part = table_view.getSelectionModel().getSelectedItem();
        open_parts_form(false, part);
    }

    public void on_delete(MouseEvent mouseEvent) {
        Part part = table_view.getSelectionModel().getSelectedItem();
        Inventory.deletePart(part);
    }

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

    public void on_search(KeyEvent inputMethodEvent) {
        String search_term = search_field.getText();
        if (search_term.isEmpty()) {
            table_view.setItems(Inventory.getAllParts());
            return;
        }

        try {
            ObservableList<Part> parts = FXCollections.observableArrayList(Inventory.lookupPart(Integer.parseInt(search_term)));
            table_view.setItems(parts);
        } catch (NumberFormatException e) {
            ObservableList<Part> parts = Inventory.lookupPart(search_term);
            table_view.setItems(parts);
        }
    }
}
