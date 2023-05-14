package com.example.tienda_mejorada;
import java.util.Date;

public class Orden {
    private int id_orden;
    private int id_cliente;
    private Date fecha;
    private double total;

    public Orden(int id_orden, int id_cliente, Date fecha, double total) {
        this.id_orden = id_orden;
        this.id_cliente = id_cliente;
        this.fecha = fecha;
        this.total = total;
    }

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
