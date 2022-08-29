
package aurasth.ventas.logicadenegocio;

import aurasth.ventas.accesoadatos.*;
import aurasth.ventas.entidadesdenegocio.*;
import java.util.ArrayList;


public class ClienteBL {
    
    public int Agregar(Cliente pCliente) throws Exception{      
        return ClienteDAL.Crear(pCliente);
    }
    
    public int Modificar(Cliente pCliente) throws Exception{
        return ClienteDAL.Modificar(pCliente);
    }
    
    public int Eliminar (Cliente pCliente)throws Exception{
        return ClienteDAL.Eliminar(pCliente);
    }
    
    public Cliente ObtenerPorId(Cliente pCliente) throws Exception{
        return ClienteDAL.ObtenerPorId(pCliente);
    }
    
    public ArrayList<Cliente> ObtenerTodos() throws Exception{
        return ClienteDAL.ObtenerTodos();
    }
    
    public ArrayList<Cliente> Buscar(Cliente pCliente) throws Exception{
        return ClienteDAL.Buscar(pCliente);
    }
    
    public Cliente Login(Cliente pCliente)throws Exception{
        return ClienteDAL.Login(pCliente);
    }
    
    public int CambiarContraseña(Cliente cliente,String pContraseña)throws Exception{
        return ClienteDAL.CambiarPassword(cliente, pContraseña);
    }
}
    