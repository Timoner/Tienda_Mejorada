package com.example.tienda_mejorada;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

public class ordenesController implements Initializable {

    @FXML
    private TableView<Orden> tabla_Orden;
    @FXML
    private TableColumn<Orden, Integer> O_id;
    @FXML
    private TableColumn<Orden, Integer> ID_cliente;
    @FXML
    private TableColumn<Orden, Date> O_fecha;
    @FXML
    private TableColumn<Orden, Double> O_total;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        O_id.setCellValueFactory(new PropertyValueFactory<Orden, Integer>("id_orden"));
        ID_cliente.setCellValueFactory(new PropertyValueFactory<Orden, Integer>("id_cliente"));
        O_fecha.setCellValueFactory(new PropertyValueFactory<Orden, Date>("fecha"));
        O_total.setCellValueFactory(new PropertyValueFactory<Orden, Double>("total"));

        tabla_Orden.setItems(getOrden());
    }

    private ObservableList<Orden> getOrden() {
        ObservableList<Orden> ordenes = FXCollections.observableArrayList();
        //hacemos la conexion a la base de datos
        Connection connection = DatabaseConnection.connect();
        String consulta = "SELECT * FROM ordenes";
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(consulta);
            Orden orden;
            while (rs.next()){
                orden = new Orden(rs.getInt("id_orden"),rs.getInt("id_cliente"),rs.getDate("fecha"),rs.getDouble("total"));
                ordenes.add(orden);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordenes;
    }


    public void descargar(ActionEvent event) {
        try {
            FileWriter fileWriter = new FileWriter("informacion_ordenes.txt");

            String encabezados = String.format("%-10s %-12s %-12s %-10s%n",
                    "ID_Orden", "ID_Cliente", "Fecha", "Total");
            fileWriter.write(encabezados);

            for (Orden orden : tabla_Orden.getItems()) {
                String linea = String.format("%-10d %-12d %-12s %-10.2f%n",
                        orden.getId_orden(),
                        orden.getId_cliente(),
                        orden.getFecha().toString(),
                        orden.getTotal());
                fileWriter.write(linea);
            }

            fileWriter.close();
            System.out.println("La informacion se descargara despus de cerar la ventana principal.");
            Stage stage = (Stage) tabla_Orden.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
