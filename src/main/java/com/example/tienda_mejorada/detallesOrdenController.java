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
import java.util.ResourceBundle;
public class detallesOrdenController implements Initializable {
    @FXML
    private TableView<detallesOrden> tabla_detalleOrden;
    @FXML
    private TableColumn<detallesOrden, Integer> DO_id;
    @FXML
    private TableColumn<detallesOrden, Integer> ID_producto;
    @FXML
    private TableColumn<detallesOrden, Integer> OD_Cantidad;
    @FXML
    private TableColumn<detallesOrden, Double> OD_precio_Unidad;
    @FXML
    private TableColumn<detallesOrden, Double> OD_total;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DO_id.setCellValueFactory(new PropertyValueFactory<detallesOrden, Integer>("id_orden"));
        ID_producto.setCellValueFactory(new PropertyValueFactory<detallesOrden, Integer>("id_producto"));
        OD_Cantidad.setCellValueFactory(new PropertyValueFactory<detallesOrden, Integer>("cantidad"));
        OD_precio_Unidad.setCellValueFactory(new PropertyValueFactory<detallesOrden, Double>("precio_und"));
        OD_total.setCellValueFactory(new PropertyValueFactory<detallesOrden, Double>("total"));

        tabla_detalleOrden.setItems(getDetalleOrden());

    }

    private ObservableList<detallesOrden> getDetalleOrden() {
        ObservableList<detallesOrden> detallesOrdeness = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.connect();
        String query = "SELECT * FROM detalles_ordenes";
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            detallesOrden detallesOrdens;
            while (rs.next()){
                detallesOrdens = new detallesOrden(rs.getInt("id_orden"),rs.getInt("id_producto"),rs.getInt("cantidad"),rs.getDouble("precio_und"),rs.getDouble("total"))   ;
                detallesOrdeness.add(detallesOrdens);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detallesOrdeness;
    }

    //Este metodo lo que
    @FXML
    private void descargar(ActionEvent event) {
        try {
            FileWriter fileWriter = new FileWriter("informacion_tabladetallesOrden.txt");

            /*
            * Este apartado lo que hace es poner unas cabezeras para que cuando se escriba lo de las tablas
            * tengan cada uno su apartado. (%)>> aqui se definen como de grandes va cada cosa
            * lo he ajustado para que se vea bien)
            * */
            String encabezados = String.format("%-10s %-12s %-9s %-14s %-8s%n",
                    "ID_Orden", "ID_Producto", "Cantidad", "Precio_Unidad", "Total");
            fileWriter.write(encabezados);

            for (detallesOrden detalles : tabla_detalleOrden.getItems()) {
                String linea = String.format("%-10d %-12d %-9d %-14.2f %-8.2f%n",
                        detalles.getId_orden(),
                        detalles.getId_producto(),
                        detalles.getCantidad(),
                        detalles.getPrecio_und(),
                        detalles.getTotal());
                fileWriter.write(linea);
            }

            fileWriter.close();
            System.out.println("La informacion se descargara despus de cerar la ventana principal.");
            Stage stage = (Stage) tabla_detalleOrden.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
