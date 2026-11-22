# Sistema de inventario para una farmacia (POO en Java)
 
Proyecto de Programación II (ISWZ1103).
Escenario: la farmacia de barrio *Salud Vital* necesita controlar su inventario de
medicamentos e insumos médicos respetando un presupuesto mensual y una capacidad de almacenamiento.
 
Aplica **encapsulamiento, herencia y polimorfismo**: la clase abstracta `Producto` tiene dos
subclases, `ProductoPerecedero` y `ProductoNoPerecedero`.
 
## Requisitos
- JDK 11 o superior. No usa librerías externas.
 
## Cómo ejecutar
Abrir el proyecto en un IDE de Java (IntelliJ IDEA, Eclipse, NetBeans o VS Code), marcar la
carpeta `src` como raíz de fuentes y ejecutar la clase `interfaz.Main`.
 
## Estructura (paquetes)
```
src/
  modelo/    Producto (abstracta), ProductoPerecedero, ProductoNoPerecedero
  negocio/   Inventario, DatosIniciales
  interfaz/  MenuConsola, Main
docs/        Diagrama UML de clases (PNG, fuente .dot y uml_clases.drawio editable en Draw.io)
evidencias/  Capturas de los casos de prueba
```
 
## Función de cada clase
| Clase | Paquete | Función |
|---|---|---|
| `Producto` | modelo | Clase abstracta con los datos comunes (código, nombre, cantidad, costo) y las operaciones de stock. Define los métodos que cada tipo de producto implementa a su manera. |
| `ProductoPerecedero` | modelo | Producto que vence. Agrega la fecha de vencimiento, calcula si está vencido o por vencer y aplica descuento si vence en 30 días o menos. |
| `ProductoNoPerecedero` | modelo | Producto que no vence. Nunca está vencido y tiene su propio margen de ganancia. |
| `Inventario` | negocio | Guarda la lista de productos y aplica las reglas: validaciones, código único, espacio, presupuesto, ventas y reabastecimiento. |
| `DatosIniciales` | negocio | Carga 6 productos de ejemplo (con fechas calculadas a partir de hoy). |
| `MenuConsola` | interfaz | Muestra el menú, pide los datos al usuario y llama a `Inventario`. |
| `Main` | interfaz | Punto de entrada del programa. |
 
## Funcionalidades
| RF | Descripción |
|----|-------------|
| RF1 | Registrar nuevo producto (valida campos obligatorios, números, código único, vencimiento, espacio y presupuesto) |
| RF2 | Listar inventario y buscar por código |
| RF3 | Registrar venta (descuenta stock; no vende vencidos ni más de lo que hay) |
| RF4 | Reabastecer producto (respeta espacio y presupuesto) |
| RF5 | Alertas: por vencer (30 días o menos), vencidos y stock bajo (10 unidades o menos) |
| RF6 | Retirar productos vencidos y registrar la pérdida |
| RF7 | Reporte: unidades, valor del inventario, presupuesto disponible, ventas y pérdidas |
 
## Pruebas
Los casos de prueba se ejecutan desde el menú del programa. Las capturas de cada caso
están en la carpeta `evidencias/`.
 
## Reglas de negocio principales
- Capacidad máxima: 1000 unidades. Presupuesto mensual de compras: USD 1000.00.
- Precio de venta = costo × (1 + margen). Margen: 25 % perecederos, 40 % no perecederos.
  Descuento de liquidación del 20 % si al perecedero le faltan 30 días o menos para vencer.
 
## Diagrama UML
Ver `docs/uml_clases.png` (editable en `docs/uml_clases.drawio`).
 
## Integrantes
Michelle, Sol, Carlos Lima, Miguel Centeno y Thomas Cifuentes Pava.
