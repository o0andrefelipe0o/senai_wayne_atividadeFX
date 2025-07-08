package com.javafx.atividade_fx.bancoDado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexão {
    private static final String URL = "jdbc:mysql://localhost:3306/wayne_enterprises_db";
    private static final String USER = "root";
    private static final String PASSWORD = "1234567";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {

            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public static String getUsuario() {
        return "root";
    }

    public static String getSenha() {
        return "";
    }

    public static String getNomeBanco() {
        return "wayne_enterprises_db";
    }
}