package com.javafx.atividade_fx.DAO;

import com.javafx.atividade_fx.model.Ferias;
import com.javafx.atividade_fx.bancoDado.Conexão;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FeriasDAO {

    public void save(Ferias ferias) throws SQLException {
        String sql = "INSERT INTO ferias (data_inicio, data_fim, observacao, funcionario_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexão.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(ferias.getDataInicio()));
            pstmt.setDate(2, Date.valueOf(ferias.getDataFim()));
            pstmt.setString(3, ferias.getObservacao());
            pstmt.setInt(4, ferias.getFuncionarioId());
            pstmt.executeUpdate();
        }
    }

    public List<Ferias> findAll() throws SQLException {
        // Usamos JOIN para buscar o nome do funcionário junto com os dados das férias
        String sql = "SELECT fe.id, fe.data_inicio, fe.data_fim, fe.observacao, fu.nome_completo " +
                "FROM ferias fe " +
                "JOIN funcionarios fu ON fe.funcionario_id = fu.id";

        List<Ferias> listaFerias = new ArrayList<>();
        try (Connection conn = Conexão.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Ferias ferias = new Ferias();
                ferias.setId(rs.getInt("id"));
                ferias.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                ferias.setDataFim(rs.getDate("data_fim").toLocalDate());
                ferias.setObservacao(rs.getString("observacao"));
                ferias.setNomeFuncionario(rs.getString("nome_completo"));
                listaFerias.add(ferias);
            }
        }
        return listaFerias;
    }

    public Optional<Ferias> findProximasFerias(int funcionarioId) {
        // A query busca por férias cuja data final seja hoje ou no futuro,
        // ordenada pela data de início para pegarmos a mais próxima.
        String sql = "SELECT * FROM ferias " +
                "WHERE funcionario_id = ? AND data_fim >= CURDATE() " +
                "ORDER BY data_inicio ASC " +
                "LIMIT 1";

        try (Connection conn = Conexão.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, funcionarioId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Ferias ferias = new Ferias();
                    ferias.setId(rs.getInt("id"));
                    ferias.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                    ferias.setDataFim(rs.getDate("data_fim").toLocalDate());
                    ferias.setObservacao(rs.getString("observacao"));
                    ferias.setFuncionarioId(funcionarioId);
                    return Optional.of(ferias); // Retorna as férias encontradas
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); // Retorna vazio se não encontrar ou se ocorrer um erro
    }
}