
package aurasth.ventas.logicadenegocio;

import aurasth.ventas.accesoadatos.*;
import aurasth.ventas.entidadesdenegocio.*;
import java.util.ArrayList;


public class ProductoBL {
    
    public int Agregar(Producto pProducto) throws Exception{      
        return ProductoDAL.Crear(pProducto);
    }
    
    public int Modificar(Producto pProducto) throws Exception{
        return ProductoDAL.Modificar(pProducto);
    }
    
    public int Eliminar (Producto pProducto)throws Exception{
        return ProductoDAL.Eliminar(pProducto);
    }
    
    public Producto ObtenerPorId(Producto pProducto) throws Exception{
        return ProductoDAL.ObtenerPorId(pProducto);
    }
    
    public ArrayList<Producto> ObtenerTodos() throws Exception{
        return ProductoDAL.ObtenerTodos();
    }
    
    public ArrayList<Producto> Buscar(Producto pProducto) throws Exception{
        return ProductoDAL.Buscar(pProducto);
    }
    
}
