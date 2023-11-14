module com.example.myissuesinterface {
    requires javafx.controls;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    requires javafx.fxml;

    opens com.example.myissuesinterface to javafx.fxml;
    exports com.example.myissuesinterface;
}