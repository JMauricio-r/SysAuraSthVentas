
package aurasth.ventas.entidadesdenegocio;

public class Carrito {

   private int Id;
   private int IdCliente;
   private int IdProducto;
   private Cliente clientes;
   private Producto productos;

    public Carrito() {
    }

    public Carrito(int Id, int IdCliente, int IdProducto) {
        this.Id = Id;
        this.IdCliente = IdCliente;
        this.IdProducto = IdProducto;
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