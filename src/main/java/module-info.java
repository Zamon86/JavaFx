module com.example.foldermaker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;


    opens com.example.foldermaker to javafx.fxml;
    exports com.example.foldermaker;
}