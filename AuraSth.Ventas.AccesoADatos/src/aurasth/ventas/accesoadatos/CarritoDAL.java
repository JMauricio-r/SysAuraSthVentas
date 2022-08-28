
package aurasth.ventas.accesoadatos;

import aurasth.ventas.entidadesdenegocio.*;
import java.sql.*;
import java.util.ArrayList;

public class CarritoDAL {
    
    static String ObtenerCampos() {
        return "c.Id, c.IdCliente, c.IdProducto";
    }
    
    private static String ObtenerSelect(Carrito pCarrito) {
        String sql;
        sql = "SELECT ";
        if (pCarrito.getTop_aux() > 0 ) {
            sql += "TOP " + pCarrito.getTop_aux() + " ";
        }
        sql += (ObtenerCampos() + " FROM Carrito c");
        return sql;
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

    public static int Modificar(Carrito pCarrito) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            sql = "UPDATE Carrito SET IdCliente=?, IdProducto WHERE Id=?"; 
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

    public static int Eliminar(Carrito pCarrito) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            sql = "DELETE FROM Carrito WHERE Id=?";  
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                ps.setInt(1, pCarrito.getId());
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
    
    static int AsignarDatosResultSet(Carrito pCarrito, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pCarrito.setId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pCarrito.setIdCliente(pResultSet.getInt(pIndex)); 
        pIndex++;
        pCarrito.setIdProducto(pResultSet.getInt(pIndex)); 
        return pIndex;
    }
    
    private static void ObtenerDatos(PreparedStatement pPS, ArrayList<Carrito> pCarrito) throws Exception {
        try (ResultSet resultSet = ComunDB.ObtenerResultSet(pPS);) { 
            while (resultSet.next()) { 
                Carrito carrito = new Carrito(); 
                AsignarDatosResultSet(carrito, resultSet, 0); 
                pCarrito.add(carrito); 
            }
            resultSet.close(); 
        } catch (SQLException ex) {
            throw ex; 
        }
    }
    
    public static Carrito ObtenerPorId(Carrito pCarrito) throws Exception {
        Carrito carrito = new Carrito();
        ArrayList<Carrito> carritos = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) {
            String sql = ObtenerSelect(pCarrito); 
            sql += " WHERE c.Id=?"; 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                ps.setInt(1, pCarrito.getId());
                ObtenerDatos(ps, carritos);
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;  
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex; 
        }
        if (!carritos.isEmpty()) {
            carrito = carritos.get(0); 
        }
        return carrito;
    }

}
