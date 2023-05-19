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
 * Product View Controller
 * @author Nicholas Ollis
 */
public class ProductsViewController implements Initializable {

    public TableView<Product> table_view;
    public TableColumn<Product, Integer> idColumn;
    public TableColumn<Product, String> nameColumn;
    public TableColumn<Product, Integer> inventoryColumn;
    public TableColumn<Product, Double> priceColumn;

    protected TextField search_field;

    /**
     * Initializes the controller class.
     * @param url Ignored
     * @param resourceBundle Ignored
     */
    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        ObservableList<Product> allParts = Inventory.getAllProducts();
        table_view.setItems(allParts);
    }

    /**
     * Opens the add product form
     * @param mouseEvent Ignored
     * @throws IOException Ignored
     */
    public void on_add(MouseEvent mouseEvent) throws IOException {
        open_products_form(true, null);
    }

    /**
     * Opens the modify product form
     * @param mouseEvent Ignored
     * @throws IOException Ignored
     */
    public void on_modify(MouseEvent mouseEvent) throws IOException {
        Product product = table_view.getSelectionModel().getSelectedItem();
        if (product == null) {
            Popup.error("Error", "Please select a product to modify.");
            return;
        }
        open_products_form(false, product);
    }

    /**
     * Deletes the selected product
     * @param mouseEvent Ignored
     */
    public void on_delete(MouseEvent mouseEvent) {
        Product product = table_view.getSelectionModel().getSelectedItem();
        if (product == null) {
            Popup.error("Error", "Please select a product to delete.");
            return;
        }
        if (product.getAllAssociatedParts().size() > 0) {
            Popup.error("Error", "Product has associated parts and cannot be deleted.");
            return;
        }
        if (Popup.showConfirmationDialog("Delete Product", "Are you sure you want to delete this product?")) {
            if (!Inventory.deleteProduct(product)) {
                Popup.error("Error", "Product could not be deleted.");
            }
        }
    }

    /**
     * Opens the products form
     * @param is_add Whether the form is for adding a product
     * @param product The product to modify, or null if adding
     * @throws IOException Ignored
     */
    private void open_products_form(boolean is_add, Product product) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("products-form.fxml"));
        Scene scene = new Scene(loader.load(), 950, 580);

        ProductsFormViewController controller = loader.getController();
        controller.set_is_add(is_add);
        if (product != null) {
            controller.set_product(product);
        }

        Stage stage = new Stage();
        stage.setTitle(is_add ? "Add a Product" : "Modify a Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Searches for products
     * @param keyEvent Ignored
     */
    public void on_search(KeyEvent keyEvent) {
        String search_term = search_field.getText();
        if (search_term.isEmpty()) {
            table_view.setItems(Inventory.getAllProducts());
            return;
        }

        try {
            ObservableList<Product> products = FXCollections.observableArrayList(Inventory.lookupProduct(Integer.parseInt(search_term)));
            if (products.isEmpty()) {
                products = on_search_error(search_term);
            }
            table_view.setItems(products);
        } catch (NumberFormatException e) {
            ObservableList<Product> products = Inventory.lookupProduct(search_term);
            if (products.isEmpty()) {
                products = on_search_error(search_term);
            }
            table_view.setItems(products);
        }
    }

    /**
     * Handles search errors
     * @param search_term The search term
     * @return The list of products
     */
    private ObservableList<Product> on_search_error(String search_term) {
        Popup.error("No results", "No products found for search term: " + search_term);
        search_field.setText("");
        return Inventory.getAllProducts();
    }
}
