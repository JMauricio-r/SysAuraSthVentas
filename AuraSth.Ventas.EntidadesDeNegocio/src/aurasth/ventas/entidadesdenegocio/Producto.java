
package aurasth.ventas.entidadesdenegocio;
import java.time.LocalDate;
import java.util.ArrayList;

public class Producto {
private int id ;
private int idCategoria;
private int idMarca;
private String nombre;
private String  descripcion;
private double precio;
private int stock;
private String rutaImagen;
private int estado;
private LocalDate fecha;
private int top_aux;
private ArrayList<DetalleVenta>detalleventas;
private ArrayList<Carrito>carritos;
private Categoria categorias;
private Marca marcas;

    public Producto() {
    }

    public Producto(int id, int idCategoria, int idMarca, String nombre, String descripcion, double precio, int stock, String rutaImagen, int estado, LocalDate fecha) {
        this.id = id;
        this.idCategoria = idCategoria;
        this.idMarca = idMarca;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.rutaImagen = rutaImagen;
        this.estado = estado;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public ArrayList<DetalleVenta> getDetalleventas() {
        return detalleventas;
    }

    public void setDetalleventas(ArrayList<DetalleVenta> detalleventas) {
        this.detalleventas = detalleventas;
    }

    public ArrayList<Carrito> getCarritos() {
        return carritos;
    }

    public void setCarritos(ArrayList<Carrito> carritos) {
        this.carritos = carritos;
    }

    public Categoria getCategorias() {
        return categorias;
    }

    public void setCategorias(Categoria categorias) {
        this.categorias = categorias;
    }

    public Marca getMarcas() {
        return marcas;
    }

    public void setMarcas(Marca marcas) {
        this.marcas = marcas;
    }



}
