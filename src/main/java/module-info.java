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
    opens appli.accueil to javafx.fxml;
    exports database;
    opens database to javafx.fxml;
    exports repository;
    opens repository to javafx.fxml;
    exports model;
    opens model to javafx.fxml;
}