
package aurasth.ventas.logicadenegocio;

import aurasth.ventas.accesoadatos.*;
import aurasth.ventas.entidadesdenegocio.*;
import java.util.ArrayList;

public class CategoriaBL {
    
    public int Agregar(Categoria pCategoria) throws Exception{      
        return CategoríaDAL.Crear(pCategoria);
    }
    
    public int Modificar(Categoria pCategoria) throws Exception{
        return CategoríaDAL.Modificar(pCategoria);
    }
    
    public int Eliminar (Categoria pCategoria)throws Exception{
        return CategoríaDAL.Eliminar(pCategoria);
    }
    
    public Categoria ObtenerPorId(Categoria pCategoria) throws Exception{
        return CategoríaDAL.ObtenerPorId(pCategoria);
    }
    
    public ArrayList<Categoria> ObtenerTodos() throws Exception{
        return CategoríaDAL.ObtenerTodos();
    }
    
    public ArrayList<Categoria> Buscar(Categoria pCategoria) throws Exception{
        return CategoríaDAL.Buscar(pCategoria);
    }
}
