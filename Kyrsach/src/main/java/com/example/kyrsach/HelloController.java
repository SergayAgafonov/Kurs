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

public class HelloController {
    @FXML private Button btnAuto;
    @FXML private Button BtnAdd;
    @FXML private Button btnCls;
    @FXML private Button btnOtpr;
    @FXML private Button BtnDell;
    @FXML private Button BtnIzm;
    @FXML private Button btnCls1;
    @FXML private Button btnOtpr1;
    @FXML private Pane ZayvPane;
    @FXML private Pane IzmPane;
    @FXML private Label wtext;
    @FXML private Label wtext1;
    @FXML private TextField tfDaZ;
    @FXML private TextField tfFIO;
    @FXML private TextField tfKontD;
    @FXML private TextField tfAdrs;
    @FXML private TextField tfVidN;
    @FXML private TextField tfSt;
    @FXML private TextField tfDaZ1;
    @FXML private TextField tfFIO1;
    @FXML private TextField tfKontD1;
    @FXML private TextField tfAdrs1;
    @FXML private TextField tfVidN1;
    @FXML private TextField tfSt1;
    private DBtableZ selectedTable1 = new DBtableZ();
    @FXML private TableView<DBtableZ> tableEvent;
    @FXML private TableColumn<DBtableZ, Integer> CodeID;
    @FXML private TableColumn<DBtableZ, String> DateZayv;
    @FXML private TableColumn<DBtableZ, String> CodeFIO;
    @FXML private TableColumn<DBtableZ, String> CodeContDat;
    @FXML private TableColumn<DBtableZ, String> CodeAdr;
    @FXML private TableColumn<DBtableZ, String> CodeNeispr;
    @FXML private TableColumn<DBtableZ, String> CodeStatus;
    private final ObservableList<DBtableZ> tableDataEvent = FXCollections.observableArrayList();
    public int CodeZay;

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

        BtnDell.setOnAction(event ->{
            selectedTable1 = tableEvent.getSelectionModel().getSelectedItem();
            selectedTable1.getIDCode();
            deleteTable(selectedTable1.getIDCode());
            tableEvent.getItems().remove(selectedTable1);
        });

        BtnIzm.setOnAction(event -> {
            IzmPane.setVisible(true);
            selectedTable1 = tableEvent.getSelectionModel().getSelectedItem();
            izmenTable(selectedTable1.getIDCode());
            CodeZay = selectedTable1.getIDCode();
        });

        btnCls1.setOnAction(event -> {
            IzmPane.setVisible(false);
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
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gorvod", "root", "MySQL")) {
                        PreparedStatement statement = conn.prepareStatement
                                ("INSERT into statement(ДатаЗаявления, ФИО, КонтактныеДанные, Адрес, ВидНеисправности, Статус) VALUES(?,?,?,?,?,?)");
                        statement.setString(1, tfDaZ.getText());
                        statement.setString(2, tfFIO.getText());
                        statement.setString(3, tfKontD.getText());
                        statement.setString(4, tfAdrs.getText());
                        statement.setString(5, tfVidN.getText());
                        statement.setString(6, tfSt.getText());
                        statement.executeUpdate();
                        tablerefresh();
                    }}
                catch (Exception e){
                    System.out.println("Ошибка с заполнением");
                }}else wtext1.setText("Данные в полях некорректны");});

        btnOtpr1.setOnAction(event -> {
            if (!(tfDaZ1.getText().isEmpty() || tfFIO1.getText().isEmpty()
                    || tfKontD1.getText().isEmpty() || tfAdrs1.getText().isEmpty()
                    || tfVidN1.getText().isEmpty() || tfSt1.getText().isEmpty())) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gorvod",
                            "root", "MySQL")) {
                        PreparedStatement statement = conn.prepareStatement
                                ("UPDATE statement SET ДатаЗаявления = '" + tfDaZ1.getText() +"', ФИО = '"+ tfFIO1.getText() +"', КонтактныеДанные = '"+
                                        tfKontD1.getText()+"', Адрес = '"+tfAdrs1.getText()+"', ВидНеисправности = '"+tfVidN1.getText()+"', Статус = '"+
                                        tfSt1.getText()+"' WHERE id_zayavki = " + CodeZay);
                        statement.executeUpdate();
                        tablerefresh();
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else wtext.setText("Данные в полях некорректны");
        });
    }

    private void tablerefresh(){
        tableDataEvent.clear();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gorvod",
                        "root", "MySQL")) {
                    Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM statement");
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

    public static void deleteTable(int Code) {
        String zayav = "DELETE FROM statement WHERE 'id_zayavki' = " + Code;
        try {Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gorvod",
                "root", "MySQL");
            System.out.println ("Подключение к бд");
            PreparedStatement preparedStatement = conn.prepareStatement(zayav);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Ошибка с удалением");
        }
    }

    public void izmenTable(int Code) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gorvod",
                "root", "MySQL")) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM statement WHERE id_zayavki = " + Code );
            while (resultSet.next()) {
                tfDaZ1.setText(resultSet.getString("ДатаЗаявления"));
                tfFIO1.setText(resultSet.getString("ФИО"));
                tfKontD1.setText(resultSet.getString("КонтактныеДанные"));
                tfAdrs1.setText(resultSet.getString("Адрес"));
                tfVidN1.setText(resultSet.getString("ВидНеисправности"));
                tfSt1.setText(resultSet.getString("Статус"));
            }
        } catch (Exception e) {
        System.out.println("Ошибка с добавление в текстфилд");
    }
}
}