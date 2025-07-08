package com.javafx.atividade_fx.Controller;

import com.javafx.atividade_fx.DAO.UsuarioDAO;
import com.javafx.atividade_fx.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usuarioField;

    @FXML
    private PasswordField senhaField;

    public void voltarParaHome(ActionEvent event) {
        try {
            Parent homeRoot = FXMLLoader.load(getClass().getResource("/com/javafx/atividade_fx/Home.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(homeRoot));
            stage.setTitle("Home - Wayne Enterprises");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void autenticarUsuario(ActionEvent event) {
        String login = usuarioField.getText();
        String senha = senhaField.getText();

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.buscarPorUsuarioESenha(login, senha);

        if (usuario != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javafx/atividade_fx/Lista.fxml"));
                Parent root = loader.load();

                com.javafx.atividade_fx.Controller.ListaFuncionario controller = loader.getController();

                controller.setUsuarioLogado(usuario);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Lista de Funcionários");
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Erro", "Não foi possível abrir a tela Lista.fxml", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Erro", "Usuário ou senha inválidos!", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
