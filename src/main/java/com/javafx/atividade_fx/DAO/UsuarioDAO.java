package com.javafx.atividade_fx.DAO;

import com.javafx.atividade_fx.model.Usuario;
import com.javafx.atividade_fx.bancoDado.Conexão;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public static boolean jaExisteAdmin() {
        String sql = "SELECT COUNT(*) FROM usuario WHERE tipo = 'admin'";

        try (Connection conn = Conexão.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean salvar(String usuario, String senha, String tipo) {
        String sql = "INSERT INTO usuario (usuario, senha, tipo) VALUES (?, ?, ?)";

        try (Connection conn = Conexão.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, senha); // ideal: aplicar hash
            stmt.setString(3, tipo);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean existeUsuario(String nomeUsuario) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE usuario = ?";

        try (Connection conn = Conexão.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean excluirUsuarioPorNome(String nomeUsuario) {
        String sql = "DELETE FROM usuario WHERE usuario = ?";
        try (Connection conn = Conexão.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeUsuario);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario buscarPorUsuarioESenha(String usuario, String senha) {
        String sql = "SELECT * FROM usuario WHERE usuario = ? AND senha = ?";

        try (Connection conn = Conexão.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setUsuario(rs.getString("usuario"));
                u.setSenha(rs.getString("senha"));
                u.setTipo(rs.getString("tipo"));
                return u;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
