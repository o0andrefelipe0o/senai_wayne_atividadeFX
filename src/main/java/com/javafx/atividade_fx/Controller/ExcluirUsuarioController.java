package com.javafx.atividade_fx.Controller;

import com.javafx.atividade_fx.DAO.UsuarioDAO;
import com.javafx.atividade_fx.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
            voltarParaLista();
        } else {
            mostrarAlerta("Erro", "Usuário não encontrado ou erro ao excluir.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleCancelar() {
        voltarParaLista();
    }

    private void voltarParaLista() {
        try {
            Parent listaRoot = FXMLLoader.load(getClass().getResource("/com/javafx/atividade_fx/lista.fxml"));
            Stage stage = (Stage) usuarioField.getScene().getWindow();
            stage.setScene(new Scene(listaRoot));
            stage.setTitle("Lista de Funcionários");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível carregar a lista de funcionários.", Alert.AlertType.ERROR);
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
