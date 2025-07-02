package com.javafx.atividade_fx.Controller;

import com.javafx.atividade_fx.DAO.FuncionarioDAO;
import com.javafx.atividade_fx.model.EventoFuncionario;
import com.javafx.atividade_fx.model.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class AgentaEvento {

    @FXML private TableView<EventoFuncionario> tabelaEventos;
    @FXML private TableColumn<EventoFuncionario, String> colNome;
    @FXML private TableColumn<EventoFuncionario, LocalDate> colAdmissao;
    @FXML private TableColumn<EventoFuncionario, LocalDate> colAniversario;
    @FXML private TableColumn<EventoFuncionario, String> colFerias;

    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    @FXML
    public void initialize() {
        // 1. Configurar as colunas para buscar os dados do modelo EventoFuncionario
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeFuncionario"));
        colAdmissao.setCellValueFactory(new PropertyValueFactory<>("dataAdmissao"));
        colAniversario.setCellValueFactory(new PropertyValueFactory<>("dataAniversario"));
        colFerias.setCellValueFactory(new PropertyValueFactory<>("statusFerias"));

        // 2. Carregar os dados na tabela
        carregarDadosEventos();
    }

    private void carregarDadosEventos() {
        // Busca todos os funcionários do banco
        List<Funcionario> funcionariosDoBanco = funcionarioDAO.findAll();

        // Cria uma lista observável para a tabela
        ObservableList<EventoFuncionario> eventos = FXCollections.observableArrayList();

        // Converte cada Funcionario para um EventoFuncionario e adiciona na lista
        for (Funcionario f : funcionariosDoBanco) {
            eventos.add(new EventoFuncionario(f));
        }

        // Define os itens na tabela
        tabelaEventos.setItems(eventos);
    }
}