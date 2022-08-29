
package aurasth.ventas.logicadenegocio;

import aurasth.ventas.accesoadatos.*;
import aurasth.ventas.entidadesdenegocio.*;
import java.util.ArrayList;


public class MarcaBL {
    
    public int Agregar(Marca pMarca) throws Exception{      
        return MarcaDAL.Crear(pMarca);
    }
    
    public int Modificar(Marca pMarca) throws Exception{
        return MarcaDAL.Modificar(pMarca);
    }
    
    public int Eliminar (Marca pMarca)throws Exception{
        return MarcaDAL.Eliminar(pMarca);
    }
    
    public Marca ObtenerPorId(Marca pMarca) throws Exception{
        return MarcaDAL.ObtenerPorId(pMarca);
    }
    
    public ArrayList<Marca> ObtenerTodos() throws Exception{
        return MarcaDAL.ObtenerTodos();
    }
    
    public ArrayList<Marca> Buscar(Marca pMarca) throws Exception{
        return MarcaDAL.Buscar(pMarca);
    }
    
}
