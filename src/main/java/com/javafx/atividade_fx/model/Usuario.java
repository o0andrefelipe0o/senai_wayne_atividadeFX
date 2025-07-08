package com.javafx.atividade_fx.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Usuario {

    private final IntegerProperty id;
    private final StringProperty usuario;
    private final StringProperty senha;
    private final StringProperty tipo;

    public Usuario() {
        this.id = new SimpleIntegerProperty();
        this.usuario = new SimpleStringProperty();
        this.senha = new SimpleStringProperty();
        this.tipo = new SimpleStringProperty();
    }

    // Getters e Setters
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getUsuario() {
        return usuario.get();
    }

    public void setUsuario(String usuario) {
        this.usuario.set(usuario);
    }

    public String getSenha() {
        return senha.get();
    }

    public void setSenha(String senha) {
        this.senha.set(senha);
    }

    public String getTipo() {
        return tipo.get();
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }

    // Property methods
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty usuarioProperty() {
        return usuario;
    }

    public StringProperty senhaProperty() {
        return senha;
    }

    public StringProperty tipoProperty() {
        return tipo;
    }
}
