
package aurasth.ventas.logicadenegocio;

import aurasth.ventas.accesoadatos.*;
import aurasth.ventas.entidadesdenegocio.*;
import java.util.ArrayList;

public class DetalleVentaBL {
    
    public int Agregar(DetalleVenta pDetalleVenta) throws Exception{      
        return DetalleVentaDAL.Crear(pDetalleVenta);
    }
    
    public int Modificar(DetalleVenta pDetalleVenta) throws Exception{
        return DetalleVentaDAL.Modificar(pDetalleVenta);
    }
    
    public int Eliminar (DetalleVenta pDetalleVenta)throws Exception{
        return DetalleVentaDAL.Eliminar(pDetalleVenta);
    }
    
    public DetalleVenta ObtenerPorId(DetalleVenta pDetalleVenta) throws Exception{
        return DetalleVentaDAL.ObtenerPorId(pDetalleVenta);
    }
    
    public ArrayList<DetalleVenta> ObtenerTodos() throws Exception{
        return DetalleVentaDAL.ObtenerTodos();
    }
    
    public ArrayList<DetalleVenta> Buscar(DetalleVenta pDetalleVenta) throws Exception{
        return DetalleVentaDAL.Buscar(pDetalleVenta);
    }
}
