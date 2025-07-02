package com.javafx.atividade_fx.Controller;

import com.javafx.atividade_fx.DAO.FuncionarioDAO;
import com.javafx.atividade_fx.model.Funcionario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListaFuncionario {

    @FXML private TableView<Funcionario> employeeTableView;
    @FXML private TableColumn<Funcionario, Integer> idColumn;
    @FXML private TableColumn<Funcionario, String> nomeColumn;
    @FXML private TableColumn<Funcionario, String> cpfColumn;
    @FXML private TableColumn<Funcionario, String> emailColumn;
    @FXML private TableColumn<Funcionario, String> departamentoColumn;
    @FXML private TableColumn<Funcionario, String> cargoColumn;
    @FXML private TableColumn<Funcionario, LocalDate> dataAdmissaoColumn;
    @FXML private TextField searchField;

    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private List<Funcionario> todosOsFuncionarios;

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        departamentoColumn.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        cargoColumn.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        dataAdmissaoColumn.setCellValueFactory(new PropertyValueFactory<>("dataAdmissao"));

        loadEmployeeData();
    }

    private void loadEmployeeData() {
        this.todosOsFuncionarios = funcionarioDAO.findAll();
        employeeTableView.setItems(FXCollections.observableArrayList(this.todosOsFuncionarios));
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase();

        if (searchText.isEmpty()) {
            employeeTableView.setItems(FXCollections.observableArrayList(this.todosOsFuncionarios));
            return;
        }

        List<Funcionario> filteredList = this.todosOsFuncionarios.stream()
                .filter(f -> f.getNomeCompleto().toLowerCase().contains(searchText) || f.getCpf().contains(searchText))
                .collect(Collectors.toList());

        employeeTableView.setItems(FXCollections.observableArrayList(filteredList));
    }

    @FXML
    private void handleNewEmployee() {
        showFormularioDialog(null);
    }

    @FXML
    private void handleEditEmployee() {
        Funcionario selected = employeeTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showFormularioDialog(selected);
        }
    }

    @FXML
    private void handleDeleteEmployee() {
        Funcionario selectedFuncionario = employeeTableView.getSelectionModel().getSelectedItem();
        if (selectedFuncionario != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText("Tem certeza que deseja excluir o funcionário?");
            alert.setContentText(selectedFuncionario.getNomeCompleto());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                funcionarioDAO.deleteById(selectedFuncionario.getId());
                loadEmployeeData();
            }
        }
    }

    @FXML
    private void handleAbrirCadastroFerias() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javafx/atividade_fx/Cadastro_ferias.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Cadastrar Novas Férias");
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAbrirListaFerias() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javafx/atividade_fx/Lista_ferias.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Histórico de Férias");
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAbrirAgenda() {
        try {
            // Carrega o arquivo FXML da agenda
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javafx/atividade_fx/Agenta_evento.fxml"));

            // Cria um novo palco (janela)
            Stage stage = new Stage();
            stage.setTitle("Agenda de Eventos");
            stage.setScene(new Scene(loader.load()));

            // Define a janela como modal (bloqueia a janela principal)
            stage.initModality(Modality.APPLICATION_MODAL);

            // Mostra a nova janela
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Não foi possível abrir a agenda.");
            alert.setContentText("Verifique se o arquivo 'agenda_evento.fxml' está no local correto.");
            alert.showAndWait();
        }
    }
    private void showFormularioDialog(Funcionario funcionario) {
        try {
            URL fxmlLocation = getClass().getResource("/com/javafx/atividade_fx/FormularioView.fxml");

            if (fxmlLocation == null) {
                System.err.println("ERRO: Não foi possível encontrar 'FormularioView.fxml'.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(loader.load());

            Formulario controller = loader.getController();
            controller.setFuncionario(funcionario);

            Stage dialogStage = new Stage();
            dialogStage.setTitle(funcionario == null ? "Novo Funcionário" : "Editar Funcionário");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);

            dialogStage.setOnHidden(e -> loadEmployeeData());

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}