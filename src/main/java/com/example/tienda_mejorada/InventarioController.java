package com.example.tienda_mejorada;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class InventarioController implements Initializable {

    @FXML
    private TableView<Inventario> tabla_Inventario;
    @FXML
    private TableColumn<Inventario, Integer> p_id;
    @FXML
    private TableColumn<Inventario, Integer> p_nombre;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        p_id.setCellValueFactory(new PropertyValueFactory<Inventario, Integer>("id_producto"));
        p_nombre.setCellValueFactory(new PropertyValueFactory<Inventario, Integer>("cantidad_stock"));

        tabla_Inventario.setItems(getDetalleOrden());

    }

    private ObservableList<Inventario> getDetalleOrden() {
        ObservableList<Inventario> inventarios = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.connect();
        String query = "SELECT * FROM inventario";
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Inventario inventario;
            while (rs.next()){
                inventario = new Inventario(rs.getInt("id_producto"),rs.getInt("cantidad_stock"))   ;
                inventarios.add(inventario);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventarios;
    }

    public void descargar(ActionEvent event) {
        try {
            FileWriter fileWriter = new FileWriter("informacion_inventario.txt");

            /*
             * Este apartado lo que hace es poner unas cabezeras para que cuando se escriba lo de las tablas
             * tengan cada uno su apartado. (%)>> aqui se definen como de grandes va cada cosa
             * lo he ajustado para que se vea bien)
             * */
            String encabezados = String.format("%-10s %-10s%n", "ID_Producto", "Cantidad_Stock");
            fileWriter.write(encabezados);

            for (Inventario inventario : tabla_Inventario.getItems()) {
                String linea = String.format("%-10d %-10d%n",
                        inventario.getId_producto(),
                        inventario.getCantidad_stock());
                fileWriter.write(linea);
            }

            fileWriter.close();
            System.out.println("La informacion se descargara despus de cerar la ventana principal.");
            Stage stage = (Stage) tabla_Inventario.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
