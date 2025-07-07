module com.javafx.atividade_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.javafx.atividade_fx.model to javafx.base, javafx.fxml;
    opens com.javafx.atividade_fx.Controller to javafx.fxml;

    exports com.javafx.atividade_fx;
    exports com.javafx.atividade_fx.Controller;
    exports com.javafx.atividade_fx.model;
    requires org.apache.pdfbox;
}
