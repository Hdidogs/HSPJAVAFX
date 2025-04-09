module appli.todolistfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires spring.security.crypto;

    opens appli to javafx.fxml;
    exports appli;
    opens appli.GestionChambresPatient to javafx.fxml;
    opens appli.dashboard to javafx.fxml;
    opens appli.dossierMedic to javafx.fxml;
    opens appli.patients to javafx.fxml;
    opens appli.produits to javafx.fxml;
    opens appli.user to javafx.fxml;
    opens appli.chambres to javafx.fxml;
    exports database;
    opens database to javafx.fxml;
    exports repository;
    opens repository to javafx.fxml;
    exports model;
    opens model to javafx.fxml;
}