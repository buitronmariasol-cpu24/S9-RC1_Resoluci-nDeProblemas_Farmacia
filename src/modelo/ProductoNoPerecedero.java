package modelo;
 
import java.time.LocalDate;
 
/** Producto que no vence (jeringas, mascarillas, termómetros...). */
public class ProductoNoPerecedero extends Producto {
 
    public ProductoNoPerecedero(String codigo, String nombre, int cantidad, double costoUnitario) {
        super(codigo, nombre, cantidad, costoUnitario);
    }
 
    @Override
    public String getTipo() {
        return "No perecedero";
    }
 
    @Override
    public boolean estaVencido(LocalDate hoy) {
        return false;
    }
 
    @Override
    public boolean estaPorVencer(LocalDate hoy) {
        return false;
    }
 
    /** Precio = costo + 40 %. */
    @Override
    public double getPrecioVenta(LocalDate hoy) {
        return getCostoUnitario() * 1.40;
    }
 
    @Override
    public String getVigencia() {
        return "No caduca";
    }
}

