package com.javafx.atividade_fx.Controller;

import com.javafx.atividade_fx.DAO.FuncionarioDAO;
import com.javafx.atividade_fx.model.Funcionario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Formulario {

    @FXML private TextField nomeField;
    @FXML private TextField cpfField;
    @FXML private TextField emailField;
    @FXML private TextField cargoField;
    @FXML private TextField departamentoField;
    @FXML private DatePicker dataAdmissaoPicker;
    @FXML private DatePicker dataNascimentoPicker; // ADICIONADO: Campo para data de nascimento
    @FXML private Button saveButton;

    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private Funcionario funcionario;

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        if (funcionario != null) {
            nomeField.setText(funcionario.getNomeCompleto());
            cpfField.setText(funcionario.getCpf());
            emailField.setText(funcionario.getEmail());
            cargoField.setText(funcionario.getCargo());
            departamentoField.setText(funcionario.getDepartamento());
            dataAdmissaoPicker.setValue(funcionario.getDataAdmissao());
            dataNascimentoPicker.setValue(funcionario.getDataNascimento()); // ADICIONADO: Popula a data de nascimento
        }
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            if (this.funcionario == null) {
                this.funcionario = new Funcionario();
            }

            this.funcionario.setNomeCompleto(nomeField.getText());
            this.funcionario.setCpf(cpfField.getText());
            this.funcionario.setEmail(emailField.getText());
            this.funcionario.setCargo(cargoField.getText());
            this.funcionario.setDepartamento(departamentoField.getText());
            this.funcionario.setDataAdmissao(dataAdmissaoPicker.getValue());
            this.funcionario.setDataNascimento(dataNascimentoPicker.getValue()); // ADICIONADO: Pega o valor da data de nascimento

            if (this.funcionario.getId() == 0) {
                funcionarioDAO.save(this.funcionario);
            } else {
                funcionarioDAO.update(this.funcionario);
            }

            closeWindow();
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nomeField.getText() == null || nomeField.getText().trim().isEmpty()) {
            errorMessage += "Nome completo inválido!\n";
        }
        if (cpfField.getText() == null || cpfField.getText().trim().isEmpty()) {
            errorMessage += "CPF inválido!\n";
        }
        if (emailField.getText() == null || !emailField.getText().contains("@")) {
            errorMessage += "E-mail inválido!\n";
        }
        if (cargoField.getText() == null || cargoField.getText().trim().isEmpty()) {
            errorMessage += "Cargo inválido!\n";
        }
        if (departamentoField.getText() == null || departamentoField.getText().trim().isEmpty()) {
            errorMessage += "Departamento inválido!\n";
        }
        if (dataAdmissaoPicker.getValue() == null) {
            errorMessage += "Data de admissão inválida!\n";
        }

        // ADICIONADO: Validação para a data de nascimento
        if (dataNascimentoPicker.getValue() == null) {
            errorMessage += "Data de nascimento inválida!\n";
        } else if (dataNascimentoPicker.getValue().isAfter(LocalDate.now())) {
            errorMessage += "Data de nascimento não pode ser no futuro!\n";
        }


        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos Inválidos");
            alert.setHeaderText("Por favor, corrija os campos inválidos.");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}