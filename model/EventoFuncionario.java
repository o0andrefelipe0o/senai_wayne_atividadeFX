package com.javafx.atividade_fx.model;

import com.javafx.atividade_fx.DAO.FeriasDAO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class EventoFuncionario {

    private final StringProperty nomeFuncionario;
    private final ObjectProperty<LocalDate> dataAdmissao;
    private final ObjectProperty<LocalDate> dataAniversario;
    private final StringProperty statusFerias; // Alteramos o nome da propriedade

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final FeriasDAO feriasDAO = new FeriasDAO(); // Instancia o DAO

    public EventoFuncionario(Funcionario funcionario) {
        this.nomeFuncionario = new SimpleStringProperty(funcionario.getNomeCompleto());
        this.dataAdmissao = new SimpleObjectProperty<>(funcionario.getDataAdmissao());
        this.dataAniversario = new SimpleObjectProperty<>(funcionario.getDataNascimento());

        // LÓGICA ATUALIZADA PARA BUSCAR AS FÉRIAS
        this.statusFerias = new SimpleStringProperty(buscarStatusFerias(funcionario.getId()));
    }

    private String buscarStatusFerias(int funcionarioId) {
        // Usa o novo método do DAO
        Optional<Ferias> proximasFerias = feriasDAO.findProximasFerias(funcionarioId);

        // Verifica se encontrou um registro de férias
        if (proximasFerias.isPresent()) {
            Ferias ferias = proximasFerias.get();
            // Formata a string de saída
            return String.format("%s a %s",
                    ferias.getDataInicio().format(dtf),
                    ferias.getDataFim().format(dtf));
        } else {
            // Se não encontrou, retorna "Em branco"
            return "Férias não marcada";
        }
    }

    // --- Getters e Métodos de Propriedade  ---

    public String getNomeFuncionario() { return nomeFuncionario.get(); }
    public StringProperty nomeFuncionarioProperty() { return nomeFuncionario; }

    public LocalDate getDataAdmissao() { return dataAdmissao.get(); }
    public ObjectProperty<LocalDate> dataAdmissaoProperty() { return dataAdmissao; }

    public LocalDate getDataAniversario() { return dataAniversario.get(); }
    public ObjectProperty<LocalDate> dataAniversarioProperty() { return dataAniversario; }

    // Métodos para a nova propriedade de férias
    public String getStatusFerias() { return statusFerias.get(); }
    public StringProperty statusFeriasProperty() { return statusFerias; }
}