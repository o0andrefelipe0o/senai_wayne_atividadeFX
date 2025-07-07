package com.javafx.atividade_fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Home.fxml"));

            Scene scene = new Scene(loader.load());

            primaryStage.setTitle(" Gerenciamento de Funcionários");

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}

