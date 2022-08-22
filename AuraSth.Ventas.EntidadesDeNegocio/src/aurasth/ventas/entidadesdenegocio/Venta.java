
package aurasth.ventas.entidadesdenegocio;

import java.time.LocalDate;
import java.util.ArrayList;

public class Venta {

   private int Id;
   private int IdCliente;
   private int IdProducto;
   private String Contacto;
   private double TotalProducto;
   private double MontoTotal;
   private double IdTransacion;
   private String Direccion;
   private LocalDate Fecha;
   private int top_aux;
   private ArrayList<DetalleVenta>detalleVentas;
   private Cliente clientes;
   private Producto productos;
   
    public Venta() {
    }

    public Venta(int Id, int IdCliente, int IdProducto, String Contacto, double TotalProducto, double MontoTotal, double IdTransacion, String Direccion, LocalDate Fecha) {
        this.Id = Id;
        this.IdCliente = IdCliente;
        this.IdProducto = IdProducto;
        this.Contacto = Contacto;
        this.TotalProducto = TotalProducto;
        this.MontoTotal = MontoTotal;
        this.IdTransacion = IdTransacion;
        this.Direccion = Direccion;
        this.Fecha = Fecha;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int IdCliente) {
        this.IdCliente = IdCliente;
    }

    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int IdProducto) {
        this.IdProducto = IdProducto;
    }

    public String getContacto() {
        return Contacto;
    }

    public void setContacto(String Contacto) {
        this.Contacto = Contacto;
    }

    public double getTotalProducto() {
        return TotalProducto;
    }

    public void setTotalProducto(double TotalProducto) {
        this.TotalProducto = TotalProducto;
    }

    public double getMontoTotal() {
        return MontoTotal;
    }

    public void setMontoTotal(double MontoTotal) {
        this.MontoTotal = MontoTotal;
    }

    public double getIdTransacion() {
        return IdTransacion;
    }

    public void setIdTransacion(double IdTransacion) {
        this.IdTransacion = IdTransacion;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public LocalDate getFecha() {
        return Fecha;
    }

    public void setFecha(LocalDate Fecha) {
        this.Fecha = Fecha;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public ArrayList<DetalleVenta> getDetalleVentas() {
        return detalleVentas;
    }

    public void setDetalleVentas(ArrayList<DetalleVenta> detalleVentas) {
        this.detalleVentas = detalleVentas;
    }

    public Cliente getClientes() {
        return clientes;
    }

    public void setClientes(Cliente clientes) {
        this.clientes = clientes;
    }

    public Producto getProductos() {
        return productos;
    }

    public void setProductos(Producto productos) {
        this.productos = productos;
    }
}