package negocio;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import modelo.Producto;
import modelo.ProductoNoPerecedero;
import modelo.ProductoPerecedero;


public class Inventario {

    public static final int CAPACIDAD_MAXIMA = 1000;
    public static final double PRESUPUESTO_MENSUAL = 1000.00;
    public static final int STOCK_BAJO = 10;

    private ArrayList<Producto> productos = new ArrayList<>();
    private LocalDate hoy;
    private double presupuestoDisponible = PRESUPUESTO_MENSUAL;
    private double ingresosPorVentas = 0;
    private double perdidasPorVencimiento = 0;

    public Inventario(LocalDate hoy) {
        this.hoy = hoy;
    }

    
    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public LocalDate getHoy() {
        return hoy;
    }

    public double getPresupuestoDisponible() {
        return presupuestoDisponible;
    }

    public double getIngresosPorVentas() {
        return ingresosPorVentas;
    }

    public double getPerdidasPorVencimiento() {
        return perdidasPorVencimiento;
    }

   
    public int totalUnidades() {
        int total = 0;
        for (Producto p : productos) {
            total = total + p.getCantidad();
        }
        return total;
    }

    public double valorInventario() {
        double total = 0;
        for (Producto p : productos) {
            total = total + p.getCostoTotal();
        }
        return total;
    }

    
    private int aEntero(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    
    public Producto buscarPorCodigo(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return p;
            }
        }
        return null;
    }

    
    public void agregarInicial(Producto p) {
        productos.add(p);
    }

    
    public String registrarProducto(String tipo, String codigo, String nombre,
                                    String cantidadTxt, String costoTxt, String vencimientoTxt) {
        // 1. Campos obligatorios
        if (!tipo.equals("1") && !tipo.equals("2")) {
            return "El tipo debe ser 1 (perecedero) o 2 (no perecedero).";
        }
        if (codigo.isEmpty()) {
            return "El código es obligatorio.";
        }
        if (nombre.isEmpty()) {
            return "El nombre es obligatorio.";
        }
        if (cantidadTxt.isEmpty()) {
            return "La cantidad es obligatoria.";
        }
        if (costoTxt.isEmpty()) {
            return "El costo es obligatorio.";
        }

        
        int cantidad = aEntero(cantidadTxt);
        if (cantidad <= 0) {
            return "La cantidad debe ser un número entero mayor que cero.";
        }
        double costo;
        try {
            costo = Double.parseDouble(costoTxt);
        } catch (NumberFormatException e) {
            return "El costo debe ser un número (use punto decimal, ej. 0.50).";
        }
        if (costo <= 0) {
            return "El costo debe ser mayor que cero.";
        }

      
        if (buscarPorCodigo(codigo) != null) {
            return "Ya existe un producto con ese código.";
        }

      
        Producto nuevo;
        if (tipo.equals("1")) {
            if (vencimientoTxt.isEmpty()) {
                return "La fecha de vencimiento es obligatoria.";
            }
            LocalDate vence;
            try {
                vence = LocalDate.parse(vencimientoTxt);
            } catch (DateTimeParseException e) {
                return "La fecha debe tener el formato AAAA-MM-DD.";
            }
            if (vence.isBefore(hoy)) {
                return "No se puede registrar un producto vencido.";
            }
            nuevo = new ProductoPerecedero(codigo.toUpperCase(), nombre, cantidad, costo, vence);
        } else {
            nuevo = new ProductoNoPerecedero(codigo.toUpperCase(), nombre, cantidad, costo);
        }

     
        if (totalUnidades() + cantidad > CAPACIDAD_MAXIMA) {
            return "No hay espacio: la capacidad máxima es " + CAPACIDAD_MAXIMA + " unidades.";
        }
        if (nuevo.getCostoTotal() > presupuestoDisponible) {
            return "Presupuesto insuficiente: la compra cuesta " + nuevo.getCostoTotal()
                    + " y solo quedan " + presupuestoDisponible + ".";
        }

        presupuestoDisponible = presupuestoDisponible - nuevo.getCostoTotal();
        productos.add(nuevo);
        return "";
    }

  
    public String vender(String codigo, String cantidadTxt) {
        Producto p = buscarPorCodigo(codigo);
        if (p == null) {
            return "No existe un producto con ese código.";
        }
        int cantidad = aEntero(cantidadTxt);
        if (cantidad <= 0) {
            return "La cantidad debe ser un número entero mayor que cero.";
        }
        if (p.estaVencido(hoy)) {
            return "No se puede vender un producto vencido.";
        }
        if (cantidad > p.getCantidad()) {
            return "Solo hay " + p.getCantidad() + " unidades en existencia.";
        }
        ingresosPorVentas = ingresosPorVentas + cantidad * p.getPrecioVenta(hoy);
        p.disminuirStock(cantidad);
        return "";
    }

   
    public String reabastecer(String codigo, String cantidadTxt) {
        Producto p = buscarPorCodigo(codigo);
        if (p == null) {
            return "No existe un producto con ese código.";
        }
        int cantidad = aEntero(cantidadTxt);
        if (cantidad <= 0) {
            return "La cantidad debe ser un número entero mayor que cero.";
        }
        if (p.estaVencido(hoy)) {
            return "No se puede reabastecer un producto vencido.";
        }
        double costo = cantidad * p.getCostoUnitario();
        if (totalUnidades() + cantidad > CAPACIDAD_MAXIMA) {
            return "No hay espacio suficiente en el almacén.";
        }
        if (costo > presupuestoDisponible) {
            return "Presupuesto insuficiente para esta compra.";
        }
        presupuestoDisponible = presupuestoDisponible - costo;
        p.aumentarStock(cantidad);
        return "";
    }

    
    public int retirarVencidos() {
        int retirados = 0;
        for (int i = productos.size() - 1; i >= 0; i--) {
            Producto p = productos.get(i);
            if (p.estaVencido(hoy)) {
                perdidasPorVencimiento = perdidasPorVencimiento + p.getCostoTotal();
                productos.remove(i);
                retirados++;
            }
        }
        return retirados;
    }
}
