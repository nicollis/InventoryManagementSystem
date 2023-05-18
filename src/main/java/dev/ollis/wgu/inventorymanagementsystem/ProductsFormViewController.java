package dev.ollis.wgu.inventorymanagementsystem;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductsFormViewController implements Initializable {
    public Text title_text;
    public TextField search_field;
    public TableView<Part> all_parts_list;
    public TableView<Part> included_parts_list;

    public TableColumn<Part, Integer> allIdColumn;
    public TableColumn<Part, String> allNameColumn;
    public TableColumn<Part, Integer> allInventoryColumn;
    public TableColumn<Part, Double> allPriceColumn;

    public TableColumn<Part, Integer> incIdColumn;
    public TableColumn<Part, String> incNameColumn;
    public TableColumn<Part, Integer> incInventoryColumn;
    public TableColumn<Part, Double> incPriceColumn;
    public TextField name;
    public TextField inventory;
    public TextField price;
    public TextField max;
    public TextField min;

    private boolean is_add;

    private final Product product = new Product();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        incIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        incNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        incInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        incPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        all_parts_list.setItems(Inventory.getAllParts());
        included_parts_list.setItems(product.getAllAssociatedParts());
    }

    public void on_add(MouseEvent mouseEvent) {
        Part part = all_parts_list.getSelectionModel().getSelectedItem();
        product.addAssociatedPart(part);
    }

    public void on_remove(MouseEvent mouseEvent) {
        Part part = included_parts_list.getSelectionModel().getSelectedItem();
        product.deleteAssociatedPart(part);
    }

    public void on_cancel(MouseEvent mouseEvent) {
        close();
    }

    public void on_save(MouseEvent mouseEvent) {
        product.setId(Inventory.getAllProducts().get(Inventory.getAllProducts().size() - 1).getId() + 1);
        product.setName(name.getText());
        product.setPrice(Double.parseDouble(price.getText()));
        product.setStock(Integer.parseInt(inventory.getText()));
        product.setMin(Integer.parseInt(min.getText()));
        product.setMax(Integer.parseInt(max.getText()));

        Inventory.addProduct(product);
        close();
    }

    public void set_is_add(boolean is_add) {
        this.is_add = is_add;
        title_text.setText(is_add ? "Add a Product" : "Modify a Product");
    }

    public void close() {
        Stage stage = (Stage) title_text.getScene().getWindow();
        stage.close();
    }
}