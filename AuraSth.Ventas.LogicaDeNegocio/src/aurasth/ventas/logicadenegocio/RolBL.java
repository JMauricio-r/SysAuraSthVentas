
package aurasth.ventas.logicadenegocio;

import aurasth.ventas.accesoadatos.*;
import aurasth.ventas.entidadesdenegocio.*;
import java.util.ArrayList;

public class RolBL {
        
    public int Agregar(Rol pRol) throws Exception{      
        return RolDAL.Crear(pRol);
    }
    
    public int Modificar(Rol pRol) throws Exception{
        return RolDAL.Modificar(pRol);
    }
    
    public int Eliminar (Rol pRol)throws Exception{
        return RolDAL.Eliminar(pRol);
    }
    
    public Rol ObtenerPorId(Rol pRol) throws Exception{
        return RolDAL.ObtenerPorId(pRol);
    }
    
    public ArrayList<Rol> ObtenerTodos() throws Exception{
        return RolDAL.ObtenerTodos();
    }
    
    public ArrayList<Rol> Buscar(Rol pRol) throws Exception{
        return RolDAL.Buscar(pRol);
    }
}
