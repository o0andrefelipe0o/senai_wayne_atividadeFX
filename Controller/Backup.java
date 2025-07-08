package com.javafx.atividade_fx.Controller;

import com.javafx.atividade_fx.bancoDado.Conexão;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.time.LocalDate;

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
        fileChooser.setInitialFileName("backup_" + Conexão.getNomeBanco() + "_" + LocalDate.now() + ".sql");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo SQL (*.sql)", "*.sql"));
        File file = fileChooser.showSaveDialog(getStage());

        if (file != null) {
            ProgressIndicator progressIndicator = showProgressDialog("Realizando Backup...");

            Task<String> backupTask = new Task<>() {
                @Override
                protected String call() throws Exception {
                    // Validação do caminho configurado
                    if (mysqlBinPath == null || mysqlBinPath.isBlank()) {
                        throw new IOException("O caminho para a pasta 'bin' do MySQL não está configurado no arquivo config.properties.");
                    }

                    String dumpExecutable = mysqlBinPath + File.separator + "mysqldump.exe";
                    File dumpFile = new File(dumpExecutable);
                    if (!dumpFile.exists()) {
                        throw new IOException("O executável 'mysqldump.exe' não foi encontrado no caminho especificado: " + dumpExecutable);
                    }

                    ProcessBuilder processBuilder = new ProcessBuilder(
                            dumpExecutable,
                            "-u" + Conexão.getUsuario(),
                            "--password=" + Conexão.getSenha(), // CORRETO
                            "--databases",
                            Conexão.getNomeBanco(),
                            "--result-file", file.getAbsolutePath()
                    );


                    // --- CORREÇÃO PRINCIPAL: Redirecionar o stream de erro ---
                    processBuilder.redirectErrorStream(true);

                    Process process = processBuilder.start();

                    // --- Lógica para consumir a saída do processo ---
                    StringBuilder output = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            output.append(line).append("\n");
                        }
                    }

                    int exitCode = process.waitFor();
                    if (exitCode == 0) {
                        return "Backup realizado com sucesso!";
                    } else {
                        // Se falhou, lança uma exceção com a mensagem de erro do mysqldump
                        throw new IOException("O backup falhou (código de saída: " + exitCode + ").\nSaída do mysqldump:\n" + output);
                    }
                }

            };

            backupTask.setOnSucceeded(e -> {
                closeProgressDialog(progressIndicator);
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", backupTask.getValue());
            });

            backupTask.setOnFailed(e -> {
                closeProgressDialog(progressIndicator);
                // Mostra o erro exato para o usuário
                showAlert(Alert.AlertType.ERROR, "Erro no Backup", backupTask.getException().getMessage());
                backupTask.getException().printStackTrace();
            });

            executorService.submit(backupTask);
        }
    }

    @FXML
    private void restaurarBackup() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Backup para Restaurar");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo SQL (*.sql)", "*.sql"));
        File file = fileChooser.showOpenDialog(getStage());

        if (file == null) {
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "ATENÇÃO: Esta operação substituirá os dados atuais do banco de dados '" + Conexão.getNomeBanco() + "'. Deseja continuar?",
                ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirmar Restauração");

        if (confirm.showAndWait().filter(b -> b == ButtonType.YES).isEmpty()) {
            return;
        }

        ProgressIndicator progressIndicator = showProgressDialog("Restaurando Backup...");

        Task<String> restoreTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                if (mysqlBinPath == null || mysqlBinPath.isBlank()) {
                    throw new IOException("Caminho do MySQL não configurado.");
                }

                String mysqlExecutable = mysqlBinPath + File.separator + "mysql.exe";
                File mysqlFile = new File(mysqlExecutable);
                if (!mysqlFile.exists()) {
                    throw new IOException("Executável 'mysql.exe' não encontrado: " + mysqlExecutable);
                }

                ProcessBuilder processBuilder = new ProcessBuilder(
                        mysqlExecutable,
                        "-u" + Conexão.getUsuario(),
                        "--password=" + Conexão.getSenha(),
                        Conexão.getNomeBanco()
                );

                processBuilder.redirectErrorStream(true);

                Process process = processBuilder.start();

                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                     BufferedReader reader = new BufferedReader(new FileReader(file))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                    writer.flush();
                }

                StringBuilder output = new StringBuilder();
                try (BufferedReader outReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = outReader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                }

                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    return "Backup restaurado com sucesso!";
                } else {
                    throw new IOException("Erro ao restaurar backup. Código de saída: " + exitCode + "\nSaída:\n" + output);
                }
            }
        };

        restoreTask.setOnSucceeded(e -> {
            closeProgressDialog(progressIndicator);
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", restoreTask.getValue());
        });

        restoreTask.setOnFailed(e -> {
            closeProgressDialog(progressIndicator);
            showAlert(Alert.AlertType.ERROR, "Erro na Restauração", restoreTask.getException().getMessage());
            restoreTask.getException().printStackTrace();
        });

        executorService.submit(restoreTask);
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