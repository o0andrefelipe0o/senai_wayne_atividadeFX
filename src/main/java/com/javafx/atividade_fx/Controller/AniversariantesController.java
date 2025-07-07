package com.javafx.atividade_fx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.javafx.atividade_fx.model.Funcionario;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AniversariantesController {

    @FXML
    private TableView<Funcionario> tableAniversariantes;

    @FXML
    private TableColumn<Funcionario, String> nomeColumn;

    @FXML
    private TableColumn<Funcionario, String> departamentoColumn;

    @FXML
    private TableColumn<Funcionario, LocalDate> dataNascimentoColumn;

    @FXML
    public void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        departamentoColumn.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        dataNascimentoColumn.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));

        carregarAniversariantes();
    }

    private void carregarAniversariantes() {
        List<Funcionario> todosFuncionarios = Funcionario.getTodos();
        int mesAtual = LocalDate.now().getMonthValue();

        List<Funcionario> aniversariantes = todosFuncionarios.stream()
                .filter(f -> f.getDataNascimento() != null && f.getDataNascimento().getMonthValue() == mesAtual)
                .collect(Collectors.toList());

        tableAniversariantes.getItems().setAll(aniversariantes);
    }
}
