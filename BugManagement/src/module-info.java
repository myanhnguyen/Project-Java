module BugManagement {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.j;
    requires java.base;

    opens app;
}
