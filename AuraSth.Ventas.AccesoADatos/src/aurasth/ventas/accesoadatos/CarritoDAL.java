
package aurasth.ventas.accesoadatos;

import aurasth.ventas.entidadesdenegocio.*;
import java.sql.*;

public class CarritoDAL {
    static String ObtenerCampos() {
        return "c.Id, c.IdCliente, c.IdProducto";
    }
    
    public static int Crear(Carrito pCarrito) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            sql = "INSERT INTO Carrito(IdCliente,IdProducto) VALUES(?,?)"; 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                ps.setInt(1, pCarrito.getIdCliente()); 
                ps.setInt(2, pCarrito.getIdProducto()); 
                result = ps.executeUpdate(); 
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex; 
        }
        return result; 
    }
}
