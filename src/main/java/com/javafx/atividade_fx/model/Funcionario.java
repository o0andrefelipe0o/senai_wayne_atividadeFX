package com.javafx.atividade_fx.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import com.javafx.atividade_fx.bancoDado.Conexão;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Funcionario {

    private final IntegerProperty id;
    private final StringProperty nomeCompleto;
    private final StringProperty cpf;
    private final StringProperty email;
    private final StringProperty cargo;
    private final StringProperty departamento;
    private final ObjectProperty<LocalDate> dataAdmissao;
    private final ObjectProperty<LocalDate> dataNascimento;

    public Funcionario() {
        this.id = new SimpleIntegerProperty();
        this.nomeCompleto = new SimpleStringProperty();
        this.cpf = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.cargo = new SimpleStringProperty();
        this.departamento = new SimpleStringProperty();
        this.dataAdmissao = new SimpleObjectProperty<>();
        this.dataNascimento = new SimpleObjectProperty<>();
    }

    public static List<Funcionario> getTodos() {
        List<Funcionario> lista = new ArrayList<>();

        String sql = "SELECT * FROM funcionarios";

        try (Connection conn = Conexão.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setNomeCompleto(rs.getString("nome_completo"));
                f.setCpf(rs.getString("cpf"));
                f.setEmail(rs.getString("email"));
                f.setCargo(rs.getString("cargo"));
                f.setDepartamento(rs.getString("departamento"));
                f.setDataAdmissao(rs.getDate("data_admissao").toLocalDate());
                f.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());

                lista.add(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }


    // --- Getters e Setters para os valores ---
    public int getId() {
        return id.get();
    }
    public void setId(int id) {
        this.id.set(id);
    }
    public String getNomeCompleto() {
        return nomeCompleto.get();
    }
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto.set(nomeCompleto);
    }
    public String getCpf() {
        return cpf.get();
    }
    public void setCpf(String cpf) {
        this.cpf.set(cpf);
    }
    public String getEmail() {
        return email.get();
    }
    public void setEmail(String email) {
        this.email.set(email);
    }
    public String getCargo() {
        return cargo.get();
    }
    public void setCargo(String cargo) {
        this.cargo.set(cargo);
    }
    public String getDepartamento() {
        return departamento.get();
    }
    public void setDepartamento(String departamento) {
        this.departamento.set(departamento);
    }
    public LocalDate getDataAdmissao() {
        return dataAdmissao.get();
    }
    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao.set(dataAdmissao);
    }
    public LocalDate getDataNascimento() {
        return dataNascimento.get();
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento.set(dataNascimento);
    }


    // --- Métodos de Propriedade (Property Methods) ---
    public IntegerProperty idProperty() {
        return id;
    }
    public StringProperty nomeCompletoProperty() {
        return nomeCompleto;
    }
    public StringProperty cpfProperty() {
        return cpf;
    }
    public StringProperty emailProperty() {
        return email;
    }
    public StringProperty cargoProperty() {
        return cargo;
    }
    public StringProperty departamentoProperty() {
        return departamento;
    }
    public ObjectProperty<LocalDate> dataAdmissaoProperty() {
        return dataAdmissao;
    }
    public ObjectProperty<LocalDate> dataNascimentoProperty() {
        return dataNascimento;
    }


}