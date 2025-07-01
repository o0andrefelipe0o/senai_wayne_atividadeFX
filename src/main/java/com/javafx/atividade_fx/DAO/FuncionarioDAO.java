package com.javafx.atividade_fx.DAO;

import com.javafx.atividade_fx.model.Funcionario;
import com.javafx.atividade_fx.bancoDado.Conexão;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {


    public void save(Funcionario funcionario) {
        String sql = "INSERT INTO funcionarios (nome_completo, cpf, email, cargo, departamento, data_admissao) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexão.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, funcionario.getNomeCompleto());
            pstmt.setString(2, funcionario.getCpf());
            pstmt.setString(3, funcionario.getEmail());
            pstmt.setString(4, funcionario.getCargo());
            pstmt.setString(5, funcionario.getDepartamento());
            pstmt.setDate(6, Date.valueOf(funcionario.getDataAdmissao()));

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Funcionario funcionario) {
        String sql = "UPDATE funcionarios SET nome_completo = ?, cpf = ?, email = ?, cargo = ?, departamento = ?, data_admissao = ? WHERE id = ?";

        try (Connection conn = Conexão.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, funcionario.getNomeCompleto());
            pstmt.setString(2, funcionario.getCpf());
            pstmt.setString(3, funcionario.getEmail());
            pstmt.setString(4, funcionario.getCargo());
            pstmt.setString(5, funcionario.getDepartamento());
            pstmt.setDate(6, Date.valueOf(funcionario.getDataAdmissao()));
            pstmt.setInt(7, funcionario.getId()); // O ID é usado na cláusula WHERE

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM funcionarios WHERE id = ?";

        try (Connection conn = Conexão.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Funcionario> findAll() {
        String sql = "SELECT * FROM funcionarios";
        List<Funcionario> funcionarios = new ArrayList<>();

        try (Connection conn = Conexão.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id"));
                funcionario.setNomeCompleto(rs.getString("nome_completo"));
                funcionario.setCpf(rs.getString("cpf"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setDepartamento(rs.getString("departamento"));
                funcionario.setDataAdmissao(rs.getDate("data_admissao").toLocalDate()); // Converte java.sql.Date para LocalDate

                funcionarios.add(funcionario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funcionarios;
    }
}