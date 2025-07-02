package com.javafx.atividade_fx.model;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ferias {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final ObjectProperty<LocalDate> dataInicio = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> dataFim = new SimpleObjectProperty<>();
    private final StringProperty observacao = new SimpleStringProperty();
    private final IntegerProperty funcionarioId = new SimpleIntegerProperty();
    private final StringProperty nomeFuncionario = new SimpleStringProperty(); // Para exibição na tabela


    // Getters e Setters
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public LocalDate getDataInicio() { return dataInicio.get(); }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio.set(dataInicio); }
    public ObjectProperty<LocalDate> dataInicioProperty() { return dataInicio; }

    public LocalDate getDataFim() { return dataFim.get(); }
    public void setDataFim(LocalDate dataFim) { this.dataFim.set(dataFim); }
    public ObjectProperty<LocalDate> dataFimProperty() { return dataFim; }

    public String getObservacao() { return observacao.get(); }
    public void setObservacao(String observacao) { this.observacao.set(observacao); }
    public StringProperty observacaoProperty() { return observacao; }

    public int getFuncionarioId() { return funcionarioId.get(); }
    public void setFuncionarioId(int funcionarioId) { this.funcionarioId.set(funcionarioId); }
    public IntegerProperty funcionarioIdProperty() { return funcionarioId; }

    public String getNomeFuncionario() { return nomeFuncionario.get(); }
    public void setNomeFuncionario(String nome) { this.nomeFuncionario.set(nome); }
    public StringProperty nomeFuncionarioProperty() { return nomeFuncionario; }
}