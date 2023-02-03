package com.example.kyrsach;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class WinZContr {
    @FXML
    private Button btnAuto;
    @FXML
    private Button BtnAdd;
    @FXML
    private Button btnCls;
    @FXML
    private Button btnOtpr;
    @FXML
    private Pane ZayvPane;
    @FXML
    private TextField tfDaZ;
    @FXML
    private TextField tfFIO;
    @FXML
    private TextField tfKontD;
    @FXML
    private TextField tfAdrs;
    @FXML
    private TextField tfVidN;
    @FXML
    private TextField tfSt;
    @FXML private Label wtext;
    @FXML
    private TableView<DBtableZ> tableEvent;
    @FXML
    private TableColumn<DBtableZ, Integer> CodeID;
    @FXML
    private TableColumn<DBtableZ, String> DateZayv;
    @FXML
    private TableColumn<DBtableZ, String> CodeFIO;
    @FXML
    private TableColumn<DBtableZ, String> CodeContDat;
    @FXML
    private TableColumn<DBtableZ, String> CodeAdr;
    @FXML
    private TableColumn<DBtableZ, String> CodeNeispr;
    @FXML
    private TableColumn<DBtableZ, String> CodeStatus;
    private final ObservableList<DBtableZ> tableDataEvent = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        CodeID.setCellValueFactory(new PropertyValueFactory<>("CodeID"));
        DateZayv.setCellValueFactory(new PropertyValueFactory<>("DateZayv"));
        CodeFIO.setCellValueFactory(new PropertyValueFactory<>("CodeFIO"));
        CodeContDat.setCellValueFactory(new PropertyValueFactory<>("CodeContDat"));
        CodeAdr.setCellValueFactory(new PropertyValueFactory<>("CodeAdr"));
        CodeNeispr.setCellValueFactory(new PropertyValueFactory<>("CodeNeispr"));
        CodeStatus.setCellValueFactory(new PropertyValueFactory<>("CodeStatus"));

        tablerefresh();

        btnAuto.setOnAction(event -> {
            btnAuto.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("authorization.fxml"));

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

        BtnAdd.setOnAction(event -> {
            ZayvPane.setVisible(true);
        });

        btnCls.setOnAction(event -> {
            ZayvPane.setVisible(false);
        });

        btnOtpr.setOnAction(event -> {
            if (!(tfDaZ.getText().isEmpty() || tfFIO.getText().isEmpty()
                    || tfKontD.getText().isEmpty() || tfAdrs.getText().isEmpty()
                    || tfVidN.getText().isEmpty() || tfSt.getText().isEmpty())) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gorvod",
                            "root", "MySQL")) {
                        PreparedStatement statement = conn.prepareStatement
                                ("INSERT into zayavki(ДатаЗаявления, ФИО, КонтактныеДанные, Адрес, ВидНеисправности, Статус) VALUES(?,?,?,?,?,?)");
                        statement.setString(1, tfDaZ.getText());
                        statement.setString(2, tfFIO.getText());
                        statement.setString(3, tfKontD.getText());
                        statement.setString(4, tfAdrs.getText());
                        statement.setString(5, tfVidN.getText());
                        statement.setString(6, tfSt.getText());
                        statement.executeUpdate();
                        tablerefresh();

                    }
                } catch (Exception e) {
                    System.out.println("Ошибка с заполнением");
                }
            } else wtext.setText("Данные в полях некорректны");
        });
    }

    private void tablerefresh() {
        tableDataEvent.clear();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gorvod",
                    "root", "MySQL")) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM zayavki");
                while (resultSet.next()) {
                    tableDataEvent.add(new DBtableZ(
                            resultSet.getInt("id_zayavki"),
                            resultSet.getString("ДатаЗаявления"),
                            resultSet.getString("ФИО"),
                            resultSet.getString("КонтактныеДанные"),
                            resultSet.getString("Адрес"),
                            resultSet.getString("ВидНеисправности"),
                            resultSet.getString("Статус")
                    ));
                }
            }
            if (!tableDataEvent.isEmpty()) {
                tableEvent.setItems(tableDataEvent);
            }
        } catch (Exception e) {
            System.out.println("Ошибка с таблицей");
        }
    }
}