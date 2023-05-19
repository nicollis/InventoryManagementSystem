package dev.ollis.wgu.inventorymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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
    public TextField id;
    public TextField name;
    public TextField inventory;
    public TextField price;
    public TextField max;
    public TextField min;

    private boolean is_add;

    private Product product = new Product();

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

        if (Popup.showConfirmationDialog("Remove Part", "Are you sure you want to remove this part?")) {
            product.deleteAssociatedPart(part);
        }
    }

    public void on_cancel(MouseEvent mouseEvent) {
        close();
    }

    public void on_save(MouseEvent mouseEvent) {
        if (is_add) {
            saveNewProduct();
        } else {
            saveModifiedProduct();
        }
    }

    public void on_search(KeyEvent inputMethodEvent) {
        String search_term = search_field.getText();
        if (search_term.isEmpty()) {
            all_parts_list.setItems(Inventory.getAllParts());
            return;
        }

        try {
            ObservableList<Part> parts = FXCollections.observableArrayList(Inventory.lookupPart(Integer.parseInt(search_term)));
            if (parts.isEmpty()) {
                parts = on_search_error(search_term);
            }
            all_parts_list.setItems(parts);
        } catch (NumberFormatException e) {
            ObservableList<Part> parts = Inventory.lookupPart(search_term);
            if (parts.isEmpty()) {
                parts = on_search_error(search_term);
            }
            all_parts_list.setItems(parts);
        }
    }

    private ObservableList<Part> on_search_error(String search_term) {
        Popup.error("No results", "No parts found for search term: " + search_term);
        search_field.setText("");
        return Inventory.getAllParts();
    }

    public void set_is_add(boolean is_add) {
        this.is_add = is_add;
        title_text.setText(is_add ? "Add a Product" : "Modify a Product");
    }

    public void set_product(Product product) {
        this.product = product;
        id.setText(String.valueOf(product.getId()));
        name.setText(product.getName());
        price.setText(String.valueOf(product.getPrice()));
        inventory.setText(String.valueOf(product.getStock()));
        min.setText(String.valueOf(product.getMin()));
        max.setText(String.valueOf(product.getMax()));
        included_parts_list.setItems(product.getAllAssociatedParts());
    }

    private void saveNewProduct() {
        int id = Inventory.getAllProducts().get(Inventory.getAllProducts().size() - 1).getId() + 1;
        Product product = createProduct(id);
        if (product == null) {
            return;
        }

        Inventory.addProduct(product);
        close();
    }

    private void saveModifiedProduct() {
        int index = Inventory.getAllProducts().indexOf(product);
        Product product = createProduct(this.product.getId());
        if (product == null) {
            return;
        }

        Inventory.updateProduct(index, product);
        close();
    }

    private Product createProduct(int id) {
        int stock, min, max;
        double price;

        try {
            stock = Integer.parseInt(inventory.getText());
        } catch (NumberFormatException e) {
            Popup.error("Invalid Inventory", "Inventory must be a number");
            return null;
        }
        try {
            price = Double.parseDouble(this.price.getText());
        } catch (NumberFormatException e) {
            Popup.error("Invalid Price", "Price must be a number");
            return null;
        }
        try {
            min = Integer.parseInt(this.min.getText());
        } catch (NumberFormatException e) {
            Popup.error("Invalid Min", "Min must be a number");
            return null;
        }
        try {
            max = Integer.parseInt(this.max.getText());
        } catch (NumberFormatException e) {
            Popup.error("Invalid Max", "Max must be a number");
            return null;
        }

        this.product.setId(id);
        this.product.setName(name.getText());
        this.product.setPrice(price);
        this.product.setStock(stock);
        this.product.setMin(min);
        this.product.setMax(max);

        try {
            this.product.validateProduct();
            return this.product;
        } catch (IllegalArgumentException e) {
            Popup.error("Invalid Product", e.getMessage());
            return null;
        }
    }

    private void close() {
        Stage stage = (Stage) title_text.getScene().getWindow();
        stage.close();
    }
}