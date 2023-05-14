package com.example.tienda_mejorada;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ClienteController implements Initializable {
    @FXML
    private TableView <Clientes> tabla_Cliente;
    @FXML
    private TableColumn<Clientes, Integer> ID_cliente;
    @FXML
    private TableColumn <Clientes, String>C_nombre;
    @FXML
    private TableColumn <Clientes, String>C_apellido;
    @FXML
    private TableColumn <Clientes, String>C_direccion;
    @FXML
    private TableColumn<Clientes,Integer>C_telefono;
    @FXML
    private TableColumn<Clientes,String>C_correo;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidoField;
    @FXML
    private TextField direccionField;
    @FXML
    private TextField telefonoField;
    @FXML
    private TextField correoField;
    @FXML
    private TextField idField;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ID_cliente.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("id_cliente"));
        C_nombre.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombre"));
        C_apellido.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellido"));
        C_direccion.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccion"));
        C_telefono.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("telefono"));
        C_correo.setCellValueFactory(new  PropertyValueFactory<Clientes, String>("correo"));

        tabla_Cliente.setItems(getClientes());

    }

    private ObservableList<Clientes> getClientes() {
        ObservableList<Clientes> clientes = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.connect();
        String query = "SELECT * FROM clientes";
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Clientes cliente;
            while (rs.next()){
                cliente = new Clientes(rs.getInt("id_cliente"),rs.getString("nombre"),rs.getString("apellido"),rs.getString("direccion"),rs.getInt("telefono"),rs.getString("correo"));
                clientes.add(cliente);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    @FXML
    private void agregarPersona(ActionEvent event) {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String direccion = direccionField.getText();
        int telefono = Integer.parseInt(telefonoField.getText());
        String correo = correoField.getText();

        Connection connection = DatabaseConnection.connect();
        String query = "INSERT INTO clientes (nombre, apellido, direccion, telefono, correo) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement(query);
            ((PreparedStatement) pst).setString(1, nombre);
            pst.setString(2, apellido);
            pst.setString(3, direccion);
            pst.setInt(4, telefono);
            pst.setString(5, correo);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Limpia los campos de entrada después de agregar a la persona
        nombreField.clear();
        apellidoField.clear();
        direccionField.clear();
        telefonoField.clear();
        correoField.clear();

        // Actualiza la tabla
        tabla_Cliente.setItems(getClientes());
    }

    @FXML
    public void borrarPersona(ActionEvent event) {
        int id = Integer.parseInt(idField.getText());

        Connection connection = DatabaseConnection.connect();
        String query = "DELETE FROM clientes WHERE id_cliente = ?";
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Limpia el campo de entrada después de borrar la persona
        idField.clear();

        // Actualiza la tabla
        tabla_Cliente.setItems(getClientes());
    }

    public void descargar(ActionEvent event) {

        try {
            FileWriter fileWriter = new FileWriter("informacion_tablaCliente.txt");

            /*
             * Este apartado lo que hace es poner unas cabezeras para que cuando se escriba lo de las tablas
             * tengan cada uno su apartado. (%)>> aqui se definen como de grandes va cada cosa
             * lo he ajustado para que se vea bien)
             * */
            String encabezados = String.format("%-10s %-12s %-9s %-14s %-12s%n",
                    "ID_Cliente", "Nombre", "Apellido", "Dirección", "Teléfono", "Correo");
            fileWriter.write((encabezados));

            for (Clientes cliente : tabla_Cliente.getItems()) {
                String linea = String.format("%-10d %-12s %-9s %-14s %-12d %s%n",
                        cliente.getId_cliente(),
                        cliente.getNombre(),
                        cliente.getApellido(),
                        cliente.getDireccion(),
                        cliente.getTelefono(),
                        cliente.getCorreo());

                fileWriter.write(linea);
            }
            fileWriter.close();
            System.out.println("La informacion se descargara despus de cerar la ventana principal.");
            Stage stage = (Stage) tabla_Cliente.getScene().getWindow();
            stage.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
