package modelo;

import java.time.LocalDate;
public class ProductoPerecedero extends Producto {

    private LocalDate fechaVencimiento;

    public ProductoPerecedero(String codigo, String nombre, int cantidad,
                              double costoUnitario, LocalDate fechaVencimiento) {
        super(codigo, nombre, cantidad, costoUnitario);
        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    @Override
    public String getTipo() {
        return "Perecedero";
    }

    @Override
    public boolean estaVencido(LocalDate hoy) {
        return hoy.isAfter(fechaVencimiento);
    }

    @Override
    public boolean estaPorVencer(LocalDate hoy) {
        return !estaVencido(hoy) && !fechaVencimiento.isAfter(hoy.plusDays(30));
    }

    @Override
    public double getPrecioVenta(LocalDate hoy) {
        double precio = getCostoUnitario() * 1.25;
        if (estaPorVencer(hoy)) {
            precio = precio * 0.80;
        }
        return precio;
    }

    @Override
    public String getVigencia() {
        return "Vence " + fechaVencimiento;
    }
}