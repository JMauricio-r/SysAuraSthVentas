
package aurasth.ventas.logicadenegocio;

import aurasth.ventas.accesoadatos.*;
import aurasth.ventas.entidadesdenegocio.*;

public class CarritoBL {
    
    public int Agregar(Carrito pCarrito) throws Exception{      
        return CarritoDAL.Crear(pCarrito);
    }
    
    public int Modificar(Carrito pCarrito) throws Exception{
        return CarritoDAL.Modificar(pCarrito);
    }
    
    public int Eliminar (Carrito pCarrito)throws Exception{
        return CarritoDAL.Eliminar(pCarrito);
    }
    
    public Carrito ObtenerPorId(Carrito pCarrito) throws Exception{
        return CarritoDAL.ObtenerPorId(pCarrito);
    }
 
}
