package interfaz;
 
import java.util.Scanner;
 
import modelo.Producto;
import negocio.Inventario;
 
public class MenuConsola {
 
    private Inventario inventario;
    private Scanner teclado = new Scanner(System.in);
 
    public MenuConsola(Inventario inventario) {
        this.inventario = inventario;
    }
 
    public void iniciar() {
        String opcion;
        do {
            mostrarMenu();
            opcion = leer("Seleccione una opción: ");
            switch (opcion) {
                case "1": registrar(); break;
                case "2": listar(); break;
                case "3": buscar(); break;
                case "4": vender(); break;
                case "5": reabastecer(); break;
                case "6": alertas(); break;
                case "7": retirarVencidos(); break;
                case "8": reporte(); break;
                case "0": System.out.println("Gracias por usar el sistema."); break;
                default: System.out.println("Opción no válida.");
            }
        } while (!opcion.equals("0"));
    }
 
    private void mostrarMenu() {
        System.out.println();
        System.out.println("=== FARMACIA SALUD VITAL ===  Fecha: " + inventario.getHoy());
        System.out.println("1. Registrar nuevo producto");
        System.out.println("2. Listar inventario");
        System.out.println("3. Buscar producto");
        System.out.println("4. Registrar venta");
        System.out.println("5. Reabastecer producto");
        System.out.println("6. Ver alertas");
        System.out.println("7. Retirar productos vencidos");
        System.out.println("8. Reporte del inventario");
        System.out.println("0. Salir");
    }
 
    private String leer(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine().trim();
    }
 
    private void mostrarResultado(String error, String mensajeExito) {
        if (error.isEmpty()) {
            System.out.println(mensajeExito);
        } else {
            System.out.println("ERROR: " + error);
        }
    }
 
    private void imprimirProducto(Producto p) {
        System.out.printf("%-8s %-22s %-13s %5d  $%7.2f  %s%n",
                p.getCodigo(), p.getNombre(), p.getTipo(), p.getCantidad(),
                p.getPrecioVenta(inventario.getHoy()), p.getVigencia());
    }
 
    private void registrar() {
        System.out.println("--- Registrar nuevo producto ---");
        String tipo = leer("Tipo (1 = Perecedero, 2 = No perecedero): ");
        String codigo = leer("Código (ej. MED-010): ");
        String nombre = leer("Nombre: ");
        String cantidad = leer("Cantidad: ");
        String costo = leer("Costo unitario (USD): ");
        String vencimiento = "";
        if (tipo.equals("1")) {
            vencimiento = leer("Fecha de vencimiento (AAAA-MM-DD): ");
        }
        String error = inventario.registrarProducto(tipo, codigo, nombre, cantidad, costo, vencimiento);
        mostrarResultado(error, "Producto registrado correctamente.");
    }
 
    private void listar() {
        System.out.println("--- Inventario (" + inventario.totalUnidades() + " unidades) ---");
        System.out.printf("%-8s %-22s %-13s %5s  %8s  %s%n",
                "Código", "Nombre", "Tipo", "Cant.", "PVP", "Vigencia");
        for (Producto p : inventario.getProductos()) {
            imprimirProducto(p);
        }
    }
 
    private void buscar() {
        String codigo = leer("Código a buscar: ");
        Producto p = inventario.buscarPorCodigo(codigo);
        if (p == null) {
            System.out.println("ERROR: No existe un producto con ese código.");
        } else {
            imprimirProducto(p);
        }
    }
 
    private void vender() {
        String codigo = leer("Código del producto: ");
        String cantidad = leer("Cantidad a vender: ");
        String error = inventario.vender(codigo, cantidad);
        mostrarResultado(error, "Venta registrada correctamente.");
    }
 
    private void reabastecer() {
        String codigo = leer("Código del producto: ");
        String cantidad = leer("Cantidad a comprar: ");
        String error = inventario.reabastecer(codigo, cantidad);
        mostrarResultado(error, "Producto reabastecido correctamente.");
    }
 
    private void alertas() {
        System.out.println("--- Productos por vencer (30 días o menos) ---");
        for (Producto p : inventario.getProductos()) {
            if (p.estaPorVencer(inventario.getHoy())) {
                imprimirProducto(p);
            }
        }
        System.out.println("--- Productos vencidos ---");
        for (Producto p : inventario.getProductos()) {
            if (p.estaVencido(inventario.getHoy())) {
                imprimirProducto(p);
            }
        }
        System.out.println("--- Stock bajo (" + Inventario.STOCK_BAJO + " unidades o menos) ---");
        for (Producto p : inventario.getProductos()) {
            if (p.getCantidad() <= Inventario.STOCK_BAJO) {
                imprimirProducto(p);
            }
        }
    }
 
    private void retirarVencidos() {
        int retirados = inventario.retirarVencidos();
        System.out.println("Productos vencidos retirados: " + retirados);
    }
 
    private void reporte() {
        System.out.println("--- Reporte del inventario ---");
        System.out.printf("Productos registrados: %d%n", inventario.getProductos().size());
        System.out.printf("Unidades en almacén: %d de %d%n", inventario.totalUnidades(), Inventario.CAPACIDAD_MAXIMA);
        System.out.printf("Valor del inventario (al costo): $%.2f%n", inventario.valorInventario());
        System.out.printf("Presupuesto disponible: $%.2f de $%.2f%n",
                inventario.getPresupuestoDisponible(), Inventario.PRESUPUESTO_MENSUAL);
        System.out.printf("Ingresos por ventas: $%.2f%n", inventario.getIngresosPorVentas());
        System.out.printf("Pérdidas por vencimiento: $%.2f%n", inventario.getPerdidasPorVencimiento());
    }
}
