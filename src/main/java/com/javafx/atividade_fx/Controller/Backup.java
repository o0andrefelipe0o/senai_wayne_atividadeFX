package com.javafx.atividade_fx.Controller;

import com.javafx.atividade_fx.bancoDado.Conexão;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Backup {

    @FXML private Button btnBackup;
    @FXML private Button btnRestaurar;
    @FXML private PasswordField campoSenha; // Este campo é ilustrativo, a senha virá do Conexao.java

    private String mysqlBinPath;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @FXML
    public void initialize() {
        loadConfig();
    }

    private void loadConfig() {
        Properties props = new Properties();
        try (InputStream input = getClass().getResourceAsStream("/config.properties")) {
            if (input == null) {
                showAlert(Alert.AlertType.ERROR, "Erro de Configuração", "Arquivo config.properties não encontrado.");
                return;
            }
            props.load(input);
            mysqlBinPath = props.getProperty("mysql.bin.path");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void fazerBackup() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Backup do Banco de Dados");
        fileChooser.setInitialFileName("backup_wayne_enterprises.sql");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo SQL (*.sql)", "*.sql"));
        File file = fileChooser.showSaveDialog(getStage());

        if (file != null) {
            ProgressIndicator progressIndicator = showProgressDialog("Realizando Backup...");

            Task<Boolean> backupTask = new Task<>() {
                @Override
                protected Boolean call() throws Exception {
                    String dumpExecutable = mysqlBinPath + File.separator + "mysqldump.exe";
                    String command = String.format("%s -u%s -p%s --databases %s --result-file=\"%s\"",
                            dumpExecutable,
                            Conexão.getUsuario(),
                            Conexão.getSenha(),
                            Conexão.getNomeBanco(),
                            file.getAbsolutePath());

                    ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
                    Process process = processBuilder.start();
                    return process.waitFor() == 0;
                }
            };

            backupTask.setOnSucceeded(e -> {
                closeProgressDialog(progressIndicator);
                if (backupTask.getValue()) {
                    showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Backup realizado com sucesso!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Falha", "O backup falhou. Verifique o console.");
                }
            });
            backupTask.setOnFailed(e -> {
                closeProgressDialog(progressIndicator);
                showAlert(Alert.AlertType.ERROR, "Erro", "Ocorreu um erro inesperado durante o backup.");
                backupTask.getException().printStackTrace();
            });

            executorService.submit(backupTask);
        }
    }

    @FXML
    private void restaurarBackup() {
        // Implementação da restauração (ainda mais perigosa, requer confirmação)
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "ATENÇÃO: A restauração irá apagar todos os dados atuais e substituí-los pelos dados do backup. Deseja continuar?",
                ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirmar Restauração");

        if (confirm.showAndWait().filter(b -> b == ButtonType.YES).isEmpty()) {
            return;
        }

        // Lógica de restauração...
        showAlert(Alert.AlertType.WARNING, "Não Implementado", "A função de restauração deve ser implementada com extremo cuidado.");
    }

    @FXML
    private void cancelar() {
        getStage().close();
    }

    private Stage getStage() {
        return (Stage) btnBackup.getScene().getWindow();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private ProgressIndicator showProgressDialog(String text) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Processando");
        dialog.setHeaderText(text);
        ProgressIndicator pi = new ProgressIndicator();
        dialog.getDialogPane().setContent(pi);
        dialog.show();
        return pi;
    }

    private void closeProgressDialog(ProgressIndicator pi) {
        if(pi != null && pi.getScene() != null) {
            ((Stage) pi.getScene().getWindow()).close();
        }
    }
}