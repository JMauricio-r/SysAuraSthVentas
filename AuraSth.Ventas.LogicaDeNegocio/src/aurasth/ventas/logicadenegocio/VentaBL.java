
package aurasth.ventas.logicadenegocio;

import aurasth.ventas.accesoadatos.*;
import aurasth.ventas.entidadesdenegocio.*;
import java.util.ArrayList;

public class VentaBL {
    
    public int Agregar(Venta pVenta) throws Exception{      
        return VentaDAL.Crear(pVenta);
    }
    
    public int Modificar(Venta pVenta) throws Exception{
        return VentaDAL.Modificar(pVenta);
    }
    
    public int Eliminar (Venta pVenta)throws Exception{
        return VentaDAL.Eliminar(pVenta);
    }
    
    public Venta ObtenerPorId(Venta pVenta) throws Exception{
        return VentaDAL.ObtenerPorId(pVenta);
    }
    
    public ArrayList<Venta> ObtenerTodos() throws Exception{
        return VentaDAL.ObtenerTodos();
    }
    
    public ArrayList<Venta> Buscar(Venta pVenta) throws Exception{
        return VentaDAL.Buscar(pVenta);
    }
}
