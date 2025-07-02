package com.javafx.atividade_fx.Controller;

import com.javafx.atividade_fx.DAO.FeriasDAO;
import com.javafx.atividade_fx.model.Ferias;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CadastroFerias {

    @FXML private TextField funcionarioIdField;
    @FXML private DatePicker dataInicioPicker;
    @FXML private DatePicker dataFimPicker;
    @FXML private TextArea observacaoArea;

    private final FeriasDAO feriasDAO = new FeriasDAO();

    @FXML
    private void salvarFerias() {
        // Validação simples
        if (funcionarioIdField.getText().isEmpty() || dataInicioPicker.getValue() == null || dataFimPicker.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Erro de Validação", "ID do Funcionário e datas são obrigatórios.");
            return;
        }

        if (dataFimPicker.getValue().isBefore(dataInicioPicker.getValue())) {
            showAlert(Alert.AlertType.ERROR, "Erro de Validação", "A data final não pode ser anterior à data inicial.");
            return;
        }

        try {
            Ferias ferias = new Ferias();
            ferias.setFuncionarioId(Integer.parseInt(funcionarioIdField.getText()));
            ferias.setDataInicio(dataInicioPicker.getValue());
            ferias.setDataFim(dataFimPicker.getValue());
            ferias.setObservacao(observacaoArea.getText());

            feriasDAO.save(ferias);

            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Férias cadastradas com sucesso!");
            closeWindow();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Formato", "ID do Funcionário deve ser um número.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Banco de Dados", "Não foi possível salvar as férias.");
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) funcionarioIdField.getScene().getWindow();
        stage.close();
    }
}