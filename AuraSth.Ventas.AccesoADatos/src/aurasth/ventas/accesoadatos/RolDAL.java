
package aurasth.ventas.accesoadatos;
import aurasth.ventas.entidadesdenegocio.*;
import java.sql.*;
import java.util.ArrayList;

public class RolDAL {
    
    static String ObtenerCampos(){
        return "r.Id,r.Nombre";
    }
    
    private static String ObtenerSelect(Rol pRol){
        String sql;
        sql = "SELECT ";
        if (pRol.getTop_aux() > 0 ) {
            sql += " TOP "+pRol.getTop_aux() + " ";
        } 
        sql += (ObtenerCampos()+ " FROM Roles r");
        return sql;
    }
    
    private static String AgregarOrderBy(Rol pRol) {
        String sql = " ORDER BY u.Id DESC";
        return sql;
    }
    
    private static boolean ExisteRol(Rol pRol) throws Exception {
        boolean existe = false;
        ArrayList<Rol> roles = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(pRol);  
            sql += " WHERE r.Id<>? AND r.Nombre=?";
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn,sql);){
                ps.setInt(1, pRol.getId()); 
                ps.setString(2, pRol.getNombre());  
                ObtenerDatos(ps, roles);
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close(); 
        }
        catch (SQLException ex) {
            throw ex; 
        }
        if (!roles.isEmpty()) { 
            Rol rol;
            rol = roles.get(0); 
            if (rol.getId() > 0 && rol.getNombre().equals(pRol.getNombre())) {
                existe = true;
            }
        }
        return existe;
    }
   
    public static int Crear(Rol pRol) throws Exception {
        int result;
        String sql;
        boolean existe = ExisteRol(pRol);
        if (existe == false) {
            try (Connection conn = ComunDB.ObtenerConexion();) { 
                sql = "INSERT INTO Roles(Nombre) VALUES(?)";
                try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn,sql);) {
                    ps.setString(1, pRol.getNombre());
                    result = ps.executeUpdate(); 
                    ps.close(); 
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            } 
            catch (SQLException ex) {
                throw ex; 
            }
        } else {
            result = 0;
            throw new RuntimeException("Rol ya existe");
        }
        return result; 
    }

    public static int Modificar(Rol pRol) throws Exception {
        int result;
        String sql;
        boolean existe = ExisteRol(pRol);
        if (existe == false) {
            try (Connection conn = ComunDB.ObtenerConexion();) { 
                sql = "UPDATE Roles SET Nombre=? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                    ps.setString(1, pRol.getNombre()); 
                    result = ps.executeUpdate(); 
                    ps.close(); 
                } catch (SQLException ex) {
                    throw ex; 
                }
                conn.close(); 
            } 
            catch (SQLException ex) {
                throw ex; 
            }
        } else {
            result = 0;
            throw new RuntimeException("Rol ya existe"); 
        }
        return result; 
    }
    
    public static int Eliminar(Rol pRol) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            sql = "DELETE FROM Roles WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn,sql);) { 
                ps.setInt(1, pRol.getId());
                result = ps.executeUpdate(); 
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex; 
        }
        return result;
    }
    
    static int AsignarDatosResultSet(Rol pRol, ResultSet pResultSet, int pIndex) 
            throws Exception {
        pIndex++;
        pRol.setId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pRol.setNombre(pResultSet.getString(pIndex));
        pIndex++;
        return pIndex;
    }
     
    private static void ObtenerDatos(PreparedStatement pPS, ArrayList<Rol> pRol) throws Exception {
        try (ResultSet resultSet = ComunDB.ObtenerResultSet(pPS);) {  
            while (resultSet.next()) { 
                Rol rol = new Rol();               
                AsignarDatosResultSet(rol, resultSet, 0);
                pRol.add(rol); 
            }
            resultSet.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static Rol ObtenerPorId(Rol pRol) throws Exception {
        Rol rol = new Rol();
        ArrayList<Rol> roles = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) {
            String sql = ObtenerSelect(pRol);
            sql += " WHERE r.Id=?";
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                ps.setInt(1, pRol.getId()); 
                ObtenerDatos(ps, roles); 
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close(); 
        }
        catch (SQLException ex) {
            throw ex; 
        }
        if (!roles.isEmpty()) { 
            rol = roles.get(0); 
        }
        return rol; 
    }
    
    public static ArrayList<Rol> ObtenerTodos() throws Exception {
        ArrayList<Rol> roles;
        roles = new ArrayList<>();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(new Rol()); 
            sql += AgregarOrderBy(new Rol());
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                ObtenerDatos(ps, roles); 
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close(); 
        }
        catch (SQLException ex) {
            throw ex; 
        }
        return roles; 
    }

    static void QuerySelect(Rol pRol, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pRol.getId() > 0) { 
            pUtilQuery.AgregarWhereAnd(" r.Id=? "); 
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pRol.getId());
            }
        }
        if (pRol.getNombre() != null && pRol.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" r.Nombre LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pRol.getNombre() + "%");
            }
        }  
    }
    
    public static ArrayList<Rol> Buscar(Rol pRol) throws Exception {
        ArrayList<Rol> roles = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) {
            String sql = ObtenerSelect(pRol); 
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            QuerySelect(pRol, utilQuery); 
            sql = utilQuery.getSQL();
            sql += AgregarOrderBy(pRol); 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                QuerySelect(pRol, utilQuery); 
                ObtenerDatos(ps, roles); 
                ps.close(); 
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close(); 
        } 
        catch (SQLException ex) {
            throw ex; 
        }
        return roles; 
    }

}
