package com.example.tienda_mejorada;

public class Inventario {
    private int id_producto;
    private int cantidad_stock;

    public Inventario(int id_producto, int cantidad_stock) {
        this.id_producto = id_producto;
        this.cantidad_stock = cantidad_stock;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad_stock() {
        return cantidad_stock;
    }

    public void setCantidad_stock(int cantidad_stock) {
        this.cantidad_stock = cantidad_stock;
    }
}
