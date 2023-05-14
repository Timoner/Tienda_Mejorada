package com.example.tienda_mejorada;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String url = "jdbc:mysql://localhost:3306/tienda";
    private static final String username = "root";
    private static final String password = "R4inb0wGr455";

    public static Connection connect() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return connection;
    }

    public static void main(String[] args) {
        Connection connection = connect();
    }

    public static void tablaProductos(){
        // Aquí puedes implementar la lógica para trabajar con la tabla de productos
    }
}
