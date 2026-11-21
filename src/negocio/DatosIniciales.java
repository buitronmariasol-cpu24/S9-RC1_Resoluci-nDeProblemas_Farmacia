package negocio;

import java.time.LocalDate;

import modelo.ProductoNoPerecedero;
import modelo.ProductoPerecedero;


public class DatosIniciales {

    public static void cargar(Inventario inv) {
        LocalDate hoy = inv.getHoy();
        
        inv.agregarInicial(new ProductoPerecedero("MED-001", "Paracetamol 500 mg", 120, 0.10, hoy.plusDays(400)));
        inv.agregarInicial(new ProductoPerecedero("MED-002", "Ibuprofeno 400 mg", 80, 0.15, hoy.plusDays(250)));
        inv.agregarInicial(new ProductoPerecedero("MED-003", "Jarabe para la tos", 8, 2.50, hoy.plusDays(15)));   // por vencer y stock bajo
        inv.agregarInicial(new ProductoPerecedero("MED-004", "Amoxicilina 500 mg", 40, 0.30, hoy.minusDays(10))); // vencido
        inv.agregarInicial(new ProductoNoPerecedero("INS-001", "Mascarillas (caja)", 60, 3.00));
        inv.agregarInicial(new ProductoNoPerecedero("INS-002", "Termómetro digital", 5, 4.50));                   // stock bajo
    }
}
