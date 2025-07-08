package com.javafx.atividade_fx.Controller;

import com.javafx.atividade_fx.DAO.FuncionarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import java.sql.SQLException;
import java.util.Map;

public class RelatorioGrafico {

    @FXML
    private PieChart graficoDepartamentos;

    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    @FXML
    public void initialize() {
        carregarDadosDoGrafico();
    }

    private void carregarDadosDoGrafico() {
        try {
            // 1. Busca os dados agregados do DAO
            Map<String, Integer> dadosPorDepartamento = funcionarioDAO.countFuncionariosPorDepartamento();

            // 2. Cria uma lista de dados para o gráfico
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            // 3. Adiciona cada departamento como uma fatia do gráfico
            for (Map.Entry<String, Integer> entry : dadosPorDepartamento.entrySet()) {
                pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }

            // 4. Define os dados no gráfico e adiciona um título
            graficoDepartamentos.setData(pieChartData);
            graficoDepartamentos.setTitle("Distribuição de Funcionários");

        } catch (SQLException e) {
            e.printStackTrace();
            // Opcional: Mostrar um alerta de erro
        }
    }
}