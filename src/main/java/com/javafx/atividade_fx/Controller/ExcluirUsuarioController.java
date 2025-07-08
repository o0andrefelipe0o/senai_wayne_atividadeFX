package com.javafx.atividade_fx.Controller;

import com.javafx.atividade_fx.DAO.UsuarioDAO;
import com.javafx.atividade_fx.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ExcluirUsuarioController {

    @FXML
    private TextField usuarioField;

    private Usuario usuarioLogado;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    @FXML
    public void handleExcluir() {
        String nomeUsuario = usuarioField.getText().trim();

        if (nomeUsuario.isEmpty()) {
            mostrarAlerta("Aviso", "Digite o nome do usuário a ser excluído.", Alert.AlertType.WARNING);
            return;
        }

        // Impede excluir a si mesmo
        if (usuarioLogado != null && nomeUsuario.equalsIgnoreCase(usuarioLogado.getUsuario())) {
            mostrarAlerta("Erro", "Você não pode excluir a si mesmo.", Alert.AlertType.ERROR);
            return;
        }

        boolean sucesso = UsuarioDAO.excluirUsuarioPorNome(nomeUsuario);

        if (sucesso) {
            mostrarAlerta("Sucesso", "Usuário excluído com sucesso!", Alert.AlertType.INFORMATION);
            fecharJanela();
        } else {
            mostrarAlerta("Erro", "Usuário não encontrado ou erro ao excluir.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleCancelar() {
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) usuarioField.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
