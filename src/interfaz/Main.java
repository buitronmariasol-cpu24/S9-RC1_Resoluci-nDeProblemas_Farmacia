package interfaz;
 
import java.time.LocalDate;
import java.util.Locale;
 
import negocio.DatosIniciales;
import negocio.Inventario;
 
public class Main {
 
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Inventario inventario = new Inventario(LocalDate.now());
        DatosIniciales.cargar(inventario);
        MenuConsola menu = new MenuConsola(inventario);
        menu.iniciar();
    }
}
