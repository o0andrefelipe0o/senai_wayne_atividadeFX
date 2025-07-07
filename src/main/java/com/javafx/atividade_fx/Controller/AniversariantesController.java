package com.javafx.atividade_fx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        departamentoColumn.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        dataNascimentoColumn.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));

        carregarAniversariantes();

        // 🟦 Coloração personalizada das linhas
        tableAniversariantes.setRowFactory(tv -> new TableRow<Funcionario>() {
            @Override
            protected void updateItem(Funcionario funcionario, boolean empty) {
                super.updateItem(funcionario, empty);
                setStyle(""); // Reseta o estilo

                if (funcionario == null || empty || funcionario.getDataNascimento() == null) return;

                LocalDate hoje = LocalDate.now();
                LocalDate nascimento = funcionario.getDataNascimento();
                LocalDate aniversarioEsteAno = nascimento.withYear(hoje.getYear());

                if (aniversarioEsteAno.equals(hoje)) {
                    setStyle("-fx-background-color: #A8D5BA; -fx-text-fill: #333333;"); // Verde pastel (hoje)
                } else if (aniversarioEsteAno.isBefore(hoje)) {
                    setStyle("-fx-background-color: #F8B4B4; -fx-text-fill: #333333;"); // Vermelho claro (já passou)
                } else if (!aniversarioEsteAno.isAfter(hoje.plusDays(2))) {
                    setStyle("-fx-background-color: #A7C7E7; -fx-text-fill: #333333;"); // Azul pastel (próximos 2 dias)
                } else {
                    setStyle(""); // Preto (padrão)
                }
            }
        });
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
