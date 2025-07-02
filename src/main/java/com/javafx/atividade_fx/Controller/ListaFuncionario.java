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

import java.time.format.DateTimeFormatter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.collections.ObservableList;

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
    @FXML private Button exportarPdfButton;

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
    @FXML
    private void handleAbrirRelatorioGrafico() {
        try {
            // Supondo que o nome do seu FXML seja "relatorio_grafico.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javafx/atividade_fx/relatorio_grafico.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Relatório Gráfico");
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show(); // Usamos show() para permitir interação com a janela principal
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExportarPdf() {
        // 1. Obter a lista de funcionários
        ObservableList<Funcionario> funcionarios = employeeTableView.getItems();
        if (funcionarios.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Não há dados para exportar.").showAndWait();
            return;
        }

        // 2. Abrir o diálogo para salvar o arquivo
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Relatório PDF");
        fileChooser.setInitialFileName("Relatorio dos Funcionarios.pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos PDF (*.pdf)", "*.pdf"));
        File file = fileChooser.showSaveDialog(employeeTableView.getScene().getWindow());

        if (file != null) {
            // 3. Gerar o PDF com o novo layout
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // --- Configurações Iniciais ---
                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float yPosition = yStart;
                float leading = 15f; // Espaçamento entre linhas
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                // --- Escrever o Cabeçalho do Documento ---
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Relatório Completo de Funcionários");
                contentStream.endText();
                yPosition -= 40; // Espaço maior após o título

                // --- Escrever os Dados de Cada Funcionário ---
                for (Funcionario f : funcionarios) {
                    // Checar se precisa de uma nova página ANTES de escrever o registro
                    // 90 é uma estimativa do espaço vertical que cada "ficha" ocupa
                    if (yPosition < margin + 90) {
                        contentStream.close();
                        page = new PDPage();
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        yPosition = yStart; // Resetar a posição Y
                    }

                    // Escreve os dados do funcionário em formato de ficha
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText(f.getId() + ": " + f.getNomeCompleto());
                    contentStream.endText();
                    yPosition -= leading * 1.5; // Espaço após o nome

                    contentStream.setFont(PDType1Font.HELVETICA, 10);

                    String dataAdmissaoFormatada = f.getDataAdmissao() != null ? formatter.format(f.getDataAdmissao()) : "N/A";
                    String dataNascimentoFormatada = f.getDataNascimento() != null ? formatter.format(f.getDataNascimento()) : "N/A";

                    // Linha 1: CPF e E-mail
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText("CPF: " + f.getCpf() + "  |  E-mail: " + f.getEmail());
                    contentStream.endText();
                    yPosition -= leading;

                    // Linha 2: Cargo e Departamento
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText("Cargo: " + f.getCargo() + "  |  Departamento: " + f.getDepartamento());
                    contentStream.endText();
                    yPosition -= leading;

                    // Linha 3: Datas
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText("Data de Admissão: " + dataAdmissaoFormatada + "  |  Data de Nascimento: " + dataNascimentoFormatada);
                    contentStream.endText();
                    yPosition -= leading;

                    // Linha separadora
                    contentStream.moveTo(margin, yPosition);
                    contentStream.lineTo(page.getMediaBox().getWidth() - margin, yPosition);
                    contentStream.stroke();
                    yPosition -= leading; // Espaço extra entre os funcionários
                }

                contentStream.close();
                document.save(file);

                new Alert(Alert.AlertType.INFORMATION, "Relatório PDF gerado com sucesso!").showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erro ao gerar o PDF.").showAndWait();
            }
        }
    }
}