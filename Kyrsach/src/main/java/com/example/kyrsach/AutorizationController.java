package com.example.kyrsach;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AutorizationController {

    @FXML private TextField codefield;
    @FXML private Label codepic;
    @FXML private Label wtext;
    @FXML private Button glavnoeMenuButton;
    @FXML private TextField login_field;
    @FXML private TextField password_field;
    @FXML private Button signUpButton;

    @FXML
    void initialize() {

        glavnoeMenuButton.setOnAction(event -> {
            glavnoeMenuButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("WinZ.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent room = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(room));
            stage.show();
        });

        FXMLLoader loader = new FXMLLoader();

        password_field.textProperty().addListener((observableValue, s, t1)->
                codefield.setEditable(true));

        codeRefresh();

        signUpButton.setOnAction(event -> {
            try {
                String login = login_field.getText();
                String password = password_field.getText();
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gorvod",
                        "root", "MySQL")) {
                    System.out.println("Подключение к бд");
                    wtext.setText("Неверный логин или пароль!");
                    Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");
                    while (resultSet.next()) {
                        if (resultSet.getString("Логин").equals(login) &&
                                resultSet.getString("Пароль").equals(password) &&
                                (codefield.getText().equals(codepic.getText()))) {
                            System.out.println("Complete connect employee");
                            signUpButton.getScene().getWindow().hide();
                            loader.setLocation(getClass().getResource("hello-view.fxml"));
                            Parent root = null;
                            try {
                                root = loader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Stage stage = new Stage();
                            assert root != null;
                            stage.setScene(new Scene(root));
                            stage.show();
                            break;
                        }
                    }
                }
            } catch (Exception ex) {
                System.out.println("Ошибка доступа к БД");
            }
            codeRefresh();
        });
    }

    private void codeRefresh(){

    String simCode = "qwertyuiopasdfghjkzxcvbnmQWERTYUOASDFGHJKLZXCVBNM1234567890";
    Random random = new Random();
    char sim;
    String code = "";
    int index;
        for (int i = 0; i < 4; i++) {
        index = random.nextInt(simCode.length());
        sim = simCode.charAt(index);
        code += sim;
    }
        codepic.setText(code);}
}

