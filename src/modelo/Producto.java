package modelo;

import java.time.LocalDate;

public abstract class Producto {

    private String codigo;
    private String nombre;
    private int cantidad;
    private double costoUnitario;

    public Producto(String codigo, String nombre, int cantidad, double costoUnitario) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
    }

    public abstract String getTipo();
    public abstract double getPrecioVenta(LocalDate hoy);
    public abstract boolean estaVencido(LocalDate hoy);
    public abstract boolean estaPorVencer(LocalDate hoy);
    public abstract String getVigencia();

    public void aumentarStock(int unidades) {
        cantidad = cantidad + unidades;
    }

    public void disminuirStock(int unidades) {
        cantidad = cantidad - unidades;
    }

    public double getCostoTotal() {
        return cantidad * costoUnitario;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }
}