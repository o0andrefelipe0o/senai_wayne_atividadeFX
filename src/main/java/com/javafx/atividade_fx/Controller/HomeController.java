package com.javafx.atividade_fx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private void abrirLogin(ActionEvent event) {
        try {
            // Carrega o novo FXML
            Parent root = FXMLLoader.load(getClass().getResource("/com/javafx/atividade_fx/Login.fxml"));

            // Obtém o Stage atual a partir do evento
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Define a nova cena com a interface de login
            stage.setScene(new Scene(root));
            stage.setTitle("Login - Wayne Enterprises");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void mostrarSobre(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sobre a Empresa");
        alert.setHeaderText("Wayne Enterprises");
        alert.setContentText("A Wayne Enterprises é uma corporação global focada em tecnologia, inovação e segurança. "
                + "Com sede em Gotham City, é conhecida por seus avanços em engenharia, ciência aplicada e desenvolvimento sustentável.");
        alert.showAndWait();
    }
}
