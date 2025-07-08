package com.javafx.atividade_fx.Controller;

import com.javafx.atividade_fx.DAO.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CadastroUsuarioController {

    @FXML private TextField usuarioField;
    @FXML private PasswordField senhaField;
    @FXML private ChoiceBox<String> tipoChoiceBox;

    @FXML
    public void initialize() {
        tipoChoiceBox.getItems().addAll("normal", "admin");
        tipoChoiceBox.setValue("normal");
    }

    @FXML
    public void handleCadastrar() {
        String usuario = usuarioField.getText();
        String senha = senhaField.getText();
        String tipo = tipoChoiceBox.getValue();

        if (usuario.isBlank() || senha.isBlank()) {
            new Alert(Alert.AlertType.WARNING, "Preencha todos os campos.").showAndWait();
            return;
        }

        if (UsuarioDAO.existeUsuario(usuario)) {
            new Alert(Alert.AlertType.ERROR, "Nome de usuário já existe!").showAndWait();
            return;
        }

        boolean sucesso = UsuarioDAO.salvar(usuario, senha, tipo);
        if (sucesso) {
            new Alert(Alert.AlertType.INFORMATION, "Usuário cadastrado com sucesso!").showAndWait();
            Stage stage = (Stage) usuarioField.getScene().getWindow();
            stage.close();
        } else {
            new Alert(Alert.AlertType.ERROR, "Erro ao cadastrar usuário.").showAndWait();
        }
    }

}
