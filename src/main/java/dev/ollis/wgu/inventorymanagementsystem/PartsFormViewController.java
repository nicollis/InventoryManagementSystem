package dev.ollis.wgu.inventorymanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PartsFormViewController implements Initializable {
    public TextField dynamic_textbox;
    public Text dynamic_field;
    public Text title_text;

    public TextField id;
    public TextField name;
    public TextField inventory;
    public TextField price;
    public TextField max;
    public TextField min;
    public ToggleGroup attainment;

    private boolean is_add = true;
    private Part part;
    private boolean is_inhouse = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void inhouse_selected(MouseEvent mouseEvent) {
        dynamic_field.setText("Machine ID");
        dynamic_textbox.setText("");
        is_inhouse = true;
    }

    public void outsourced_selected(MouseEvent mouseEvent) {
        dynamic_field.setText("Company Name");
        dynamic_textbox.setText("");
        is_inhouse = false;
    }

    public void on_save(MouseEvent mouseEvent) {
        if (is_add) {
            saveNewPart();
        } else {
            saveModifiedPart();
        }
    }

    @FXML
    public void on_cancel(MouseEvent mouseEvent) {
        close();
    }

    public void set_is_add(boolean is_add) {
        this.is_add = is_add;
        title_text.setText(is_add ? "Add a Part" : "Modify a Part");
    }

    public void set_part(Part part) {
        this.part = part;
        id.setText(Integer.toString(part.getId()));
        name.setText(part.getName());
        inventory.setText(Integer.toString(part.getStock()));
        price.setText(Double.toString(part.getPrice()));
        max.setText(Integer.toString(part.getMax()));
        min.setText(Integer.toString(part.getMin()));

        if (part instanceof InHouse) {
            inhouse_selected(null);
            attainment.selectToggle(attainment.getToggles().get(0));
            dynamic_textbox.setText(Integer.toString(((InHouse) part).getMachineId()));
        } else {
            outsourced_selected(null);
            attainment.selectToggle(attainment.getToggles().get(1));
            dynamic_textbox.setText(((Outsourced) part).getCompanyName());
        }
    }

    private void saveNewPart() {
        int id = Inventory.getAllParts().get(Inventory.getAllParts().size() - 1).getId() + 1;
        Part part = createPart(id);

        Inventory.addPart(part);
        close();
    }

    private void saveModifiedPart() {
        int index = Inventory.getAllParts().indexOf(this.part);
        Part part = createPart(this.part.getId());

        Inventory.updatePart(index, part);
        close();
    }

    private Part createPart(int id) {
        String name = this.name.getText();
        int stock = Integer.parseInt(inventory.getText());
        double price = Double.parseDouble(this.price.getText());
        int max = Integer.parseInt(this.max.getText());
        int min = Integer.parseInt(this.min.getText());

        return is_inhouse ? new InHouse(
                id,
                name,
                price,
                stock,
                min,
                max,
                Integer.parseInt(dynamic_textbox.getText())
        ) : new Outsourced(
                id,
                name,
                price,
                stock,
                min,
                max,
                dynamic_textbox.getText()
        );
    }

    private void close() {
        Stage stage = (Stage) title_text.getScene().getWindow();
        stage.close();
    }
}
