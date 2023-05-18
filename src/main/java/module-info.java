module dev.ollis.wgu.inventorymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.ollis.wgu.inventorymanagementsystem to javafx.fxml;
    exports dev.ollis.wgu.inventorymanagementsystem;
}