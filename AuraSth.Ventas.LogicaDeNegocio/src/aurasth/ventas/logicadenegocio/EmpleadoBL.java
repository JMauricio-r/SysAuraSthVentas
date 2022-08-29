
package aurasth.ventas.logicadenegocio;

import aurasth.ventas.accesoadatos.*;
import aurasth.ventas.entidadesdenegocio.*;
import java.util.ArrayList;

public class EmpleadoBL {
    
    public int Agregar(Empleado pEmpleado) throws Exception{      
        return EmpleadoDAL.Crear(pEmpleado);
    }
    
    public int Modificar(Empleado pEmpleado) throws Exception{
        return EmpleadoDAL.Modificar(pEmpleado);
    }
    
    public int Eliminar (Empleado pEmpleado)throws Exception{
        return EmpleadoDAL.Eliminar(pEmpleado);
    }
    
    public Empleado ObtenerPorId(Empleado pEmpleado) throws Exception{
        return EmpleadoDAL.ObtenerPorId(pEmpleado);
    }
    
    public ArrayList<Empleado> ObtenerTodos() throws Exception{
        return EmpleadoDAL.ObtenerTodos();
    }
    
    public ArrayList<Empleado> Buscar(Empleado pEmpleado) throws Exception{
        return EmpleadoDAL.Buscar(pEmpleado);
    }

}
