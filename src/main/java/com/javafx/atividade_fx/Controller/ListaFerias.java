package com.javafx.atividade_fx.Controller;

import com.javafx.atividade_fx.DAO.FeriasDAO;
import com.javafx.atividade_fx.model.Ferias;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ListaFerias {

    @FXML private TableView<Ferias> tabelaFerias;
    @FXML private TableColumn<Ferias, String> colNome;
    @FXML private TableColumn<Ferias, LocalDate> colInicio;
    @FXML private TableColumn<Ferias, LocalDate> colFim;
    @FXML private TableColumn<Ferias, String> colObs;

    private final FeriasDAO feriasDAO = new FeriasDAO();

    @FXML
    public void initialize() {
        // Configura as colunas da tabela
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeFuncionario"));
        colInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
        colObs.setCellValueFactory(new PropertyValueFactory<>("observacao"));

        // Carrega os dados
        carregarDados();
    }

    private void carregarDados() {
        try {
            List<Ferias> feriasList = feriasDAO.findAll();
            tabelaFerias.setItems(FXCollections.observableArrayList(feriasList));
        } catch (SQLException e) {
            e.printStackTrace();
            // Opcional: mostrar alerta de erro
        }
    }
}