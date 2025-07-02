package com.javafx.atividade_fx.DAO;

import com.javafx.atividade_fx.model.Funcionario;
import com.javafx.atividade_fx.bancoDado.Conexão;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public void save(Funcionario funcionario) {
        // 1. SQL ALTERADO para incluir data_nascimento
        String sql = "INSERT INTO funcionarios (nome_completo, cpf, email, cargo, departamento, data_admissao, data_nascimento) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexão.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, funcionario.getNomeCompleto());
            pstmt.setString(2, funcionario.getCpf());
            pstmt.setString(3, funcionario.getEmail());
            pstmt.setString(4, funcionario.getCargo());
            pstmt.setString(5, funcionario.getDepartamento());
            pstmt.setDate(6, Date.valueOf(funcionario.getDataAdmissao()));
            // 2. ADICIONADO o parâmetro para data_nascimento
            pstmt.setDate(7, Date.valueOf(funcionario.getDataNascimento()));

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Funcionario funcionario) {
        // 1. SQL ALTERADO para incluir data_nascimento
        String sql = "UPDATE funcionarios SET nome_completo = ?, cpf = ?, email = ?, cargo = ?, departamento = ?, data_admissao = ?, data_nascimento = ? WHERE id = ?";

        try (Connection conn = Conexão.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, funcionario.getNomeCompleto());
            pstmt.setString(2, funcionario.getCpf());
            pstmt.setString(3, funcionario.getEmail());
            pstmt.setString(4, funcionario.getCargo());
            pstmt.setString(5, funcionario.getDepartamento());
            pstmt.setDate(6, Date.valueOf(funcionario.getDataAdmissao()));
            // 2. ADICIONADO o parâmetro para data_nascimento
            pstmt.setDate(7, Date.valueOf(funcionario.getDataNascimento()));
            pstmt.setInt(8, funcionario.getId()); // O ID agora é o 8º parâmetro

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

                // Pega a data de admissão e converte para LocalDate
                funcionario.setDataAdmissao(rs.getDate("data_admissao").toLocalDate());

                // 1. ADICIONADO: Pega a data de nascimento e converte
                Date dataNascimentoSql = rs.getDate("data_nascimento");
                if (dataNascimentoSql != null) {
                    funcionario.setDataNascimento(dataNascimentoSql.toLocalDate());
                }

                funcionarios.add(funcionario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funcionarios;
    }
}