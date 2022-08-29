
package aurasth.ventas.logicadenegocio;

import aurasth.ventas.accesoadatos.*;
import aurasth.ventas.entidadesdenegocio.Usuario;
import java.util.*;

public class UsuarioBL {
    
    public int Agregar(Usuario pUsuario) throws Exception{      
        return UsuarioDAL.Crear(pUsuario);
    }
    
    public int Modificar(Usuario pUsuario) throws Exception{
        return UsuarioDAL.Modificar(pUsuario);
    }
    
    public int Eliminar (Usuario pUsuario)throws Exception{
        return UsuarioDAL.Eliminar(pUsuario);
    }
    
    public Usuario ObtenerPorId(Usuario pUsuario) throws Exception{
        return UsuarioDAL.ObtenerPorId(pUsuario);
    }
    
    public ArrayList<Usuario> ObtenerTodos() throws Exception{
        return UsuarioDAL.ObtenerTodos();
    }
    
    public ArrayList<Usuario> Buscar(Usuario pUsuario) throws Exception{
        return UsuarioDAL.Buscar(pUsuario);
    }
    
    public Usuario Login(Usuario pUsuario)throws Exception{
        return UsuarioDAL.Login(pUsuario);
    }
    
    public int CambiarContraseña(Usuario usuario,String pContraseña)throws Exception{
        return UsuarioDAL.CambiarPassword(usuario, pContraseña);
    }
}
