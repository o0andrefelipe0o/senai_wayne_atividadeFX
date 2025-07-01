package com.javafx.atividade_fx.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Funcionario {

    private final IntegerProperty id;
    private final StringProperty nomeCompleto;
    private final StringProperty cpf;
    private final StringProperty email;
    private final StringProperty cargo;
    private final StringProperty departamento;
    private final ObjectProperty<LocalDate> dataAdmissao;


    public Funcionario() {
        this.id = new SimpleIntegerProperty();
        this.nomeCompleto = new SimpleStringProperty();
        this.cpf = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.cargo = new SimpleStringProperty();
        this.departamento = new SimpleStringProperty();
        this.dataAdmissao = new SimpleObjectProperty<>();
    }

    // --- Getters e Setters para os valores ---
    // (Usados pelo seu código e pelo DAO para manipular os dados)

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
}
