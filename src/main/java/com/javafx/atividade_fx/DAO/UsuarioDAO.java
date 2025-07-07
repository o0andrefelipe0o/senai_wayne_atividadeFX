package com.javafx.atividade_fx.DAO;

import com.javafx.atividade_fx.model.Usuario;
import com.javafx.atividade_fx.bancoDado.Conexão;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

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
                return u;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
