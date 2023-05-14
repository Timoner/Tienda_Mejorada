package com.example.tienda_mejorada;

public class detallesOrden {
    private int id_orden;
    private int id_producto;
    private int cantidad;
    private double precio_und;
    private double total;

    public detallesOrden(int id_orden, int id_producto, int cantidad, double precio_und, double total) {
        this.id_orden = id_orden;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.precio_und = precio_und;
        this.total = total;
    }

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_und() {
        return precio_und;
    }

    public void setPrecio_und(double precio_und) {
        this.precio_und = precio_und;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
