package com.example.tienda_mejorada;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ProductosController implements Initializable {

    @FXML
    private TableView<Productos> tabla_Productos;
    @FXML
    private TableColumn<Productos, Integer> p_id;
    @FXML
    private TableColumn<Productos, String> p_nombre;
    @FXML
    private TableColumn<Productos, Double> p_precio;
    @FXML
    private TableColumn<Productos, String> p_descripcion;
    @FXML
    private TableColumn<Productos, String> p_categoria;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        p_id.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("id_producto"));
        p_nombre.setCellValueFactory(new PropertyValueFactory<Productos, String>("nombre"));
        p_precio.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precio"));
        p_descripcion.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcion"));
        p_categoria.setCellValueFactory(new PropertyValueFactory<Productos, String>("categoria"));

        tabla_Productos.setItems(getProductos());
    }

    private ObservableList<Productos> getProductos() {
        ObservableList<Productos> productos = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.connect();
        String query = "SELECT * FROM productos";
        Statement st;
        ResultSet rs;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Productos producto;
            while (rs.next()) {
                producto = new Productos(rs.getInt("id_producto"), rs.getString("nombre"), rs.getString("descripcion"), rs.getDouble("precio"), rs.getString("categoria"));
                productos.add(producto);
            }

            st.close();
            rs.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }

    public void descargar(ActionEvent event) {
        try {
            FileWriter fileWriter = new FileWriter("informacion_productos.txt");

            String encabezados = String.format("%-10s %-20s %-10s %-30s %-20s%n",
                    "ID_Producto", "Nombre", "Precio", "Descripción", "Categoría");
            fileWriter.write(encabezados);

            for (Productos producto : tabla_Productos.getItems()) {
                String linea = String.format("%-10d %-20s %-10.2f %-30s %-20s%n",
                        producto.getId_producto(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        producto.getDescripcion(),
                        producto.getCategoria());
                fileWriter.write(linea);
            }

            fileWriter.close();
            Stage stage = (Stage) tabla_Productos.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
