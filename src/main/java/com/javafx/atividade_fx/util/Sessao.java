package com.javafx.atividade_fx.util;

import com.javafx.atividade_fx.model.Usuario;

public class Sessao {
    private static Usuario usuarioLogado;

    public static void setUsuarioLogado(Usuario usuario) {
        Sessao.usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void limpar() {
        usuarioLogado = null;
    }
}
