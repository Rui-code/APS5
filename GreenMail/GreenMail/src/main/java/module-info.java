module com.aps6.greenmail {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.aps6.greenmail to javafx.fxml;
    exports com.aps6.greenmail;
    requires java.mail;
    requires java.sql;
}
