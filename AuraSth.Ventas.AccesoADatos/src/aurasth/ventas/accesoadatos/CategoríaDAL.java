
package aurasth.ventas.accesoadatos;

import java.util.ArrayList;
import java.sql.*;
import aurasth.ventas.entidadesdenegocio.*;

public class CategoríaDAL {
    
       static String ObtenerCampos(){
        return "c.Id,c.Nombre";
    }
    
    private static String ObtenerSelect(Categoria pCategoria){
        String sql;
        sql = "SELECT ";
        if (pCategoria.getTop_aux() > 0 ) {
            sql += " TOP "+pCategoria.getTop_aux() + " ";
        } 
        sql += (ObtenerCampos()+ " FROM categorias c");
        return sql;
    }
    
    private static String AgregarOrderBy(Categoria pCategoria) {
        String sql = " ORDER BY c.Id DESC";
        return sql;
    }
    
    private static boolean ExisteCategoria(Categoria pCategoria) throws Exception {
        boolean existe = false;
        ArrayList<Categoria> categorias = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(pCategoria);  
            sql += " WHERE c.Id<>? AND c.Nombre=?";
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn,sql);){
                ps.setInt(1, pCategoria.getId()); 
                ps.setString(2, pCategoria.getNombre());  
                ObtenerDatos(ps, categorias);
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close(); 
        }
        catch (SQLException ex) {
            throw ex; 
        }
        if (!categorias.isEmpty()) { 
            Categoria categoria;
            categoria = categorias.get(0); 
            if (categoria.getId() > 0 && categoria.getNombre().equals(pCategoria.getNombre())) {
                existe = true;
            }
        }
        return existe;
    }
   
    public static int Crear(Categoria pCategoria) throws Exception {
        int result;
        String sql;
        boolean existe = ExisteCategoria(pCategoria);
        if (existe == false) {
            try (Connection conn = ComunDB.ObtenerConexion();) { 
                sql = "INSERT INTO categorias(Nombre) VALUES(?)";
                try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn,sql);) {
                    ps.setString(1, pCategoria.getNombre());
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
            throw new RuntimeException("Categoria ya existe");
        }
        return result; 
    }

    public static int Modificar(Categoria pCategoria) throws Exception {
        int result;
        String sql;
        boolean existe = ExisteCategoria(pCategoria);
        if (existe == false) {
            try (Connection conn = ComunDB.ObtenerConexion();) { 
                sql = "UPDATE Categorias SET Nombre=? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                    ps.setString(1, pCategoria.getNombre()); 
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
            throw new RuntimeException("Categoria ya existe"); 
        }
        return result; 
    }
    
    public static int Eliminar(Categoria pCategoria) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            sql = "DELETE FROM Categorias WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn,sql);) { 
                ps.setInt(1, pCategoria.getId());
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
    
    static int AsignarDatosResultSet(Categoria pCategoria, ResultSet pResultSet, int pIndex) 
            throws Exception {
        pIndex++;
        pCategoria.setId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pCategoria.setNombre(pResultSet.getString(pIndex));
        pIndex++;
        return pIndex;
    }
     
    private static void ObtenerDatos(PreparedStatement pPS, ArrayList<Categoria> pMarca) throws Exception {
        try (ResultSet resultSet = ComunDB.ObtenerResultSet(pPS);) {  
            while (resultSet.next()) { 
                Categoria categoria = new Categoria();               
                AsignarDatosResultSet(categoria, resultSet, 0);
                pMarca.add(categoria); 
            }
            resultSet.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static Categoria ObtenerPorId(Categoria pCategoria) throws Exception {
        Categoria categoria = new Categoria();
        ArrayList<Categoria> categorias = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) {
            String sql = ObtenerSelect(pCategoria);
            sql += " WHERE c.Id=?";
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                ps.setInt(1, pCategoria.getId()); 
                ObtenerDatos(ps, categorias); 
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close(); 
        }
        catch (SQLException ex) {
            throw ex; 
        }
        if (!categorias.isEmpty()) { 
            categoria = categorias.get(0); 
        }
        return categoria; 
    }
    
    public static ArrayList<Categoria> ObtenerTodos() throws Exception {
        ArrayList<Categoria> categorias;
        categorias = new ArrayList<>();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(new Categoria()); 
            sql += AgregarOrderBy(new Categoria());
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                ObtenerDatos(ps, categorias); 
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close(); 
        }
        catch (SQLException ex) {
            throw ex; 
        }
        return categorias; 
    }

    static void QuerySelect(Categoria pCategoria, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pCategoria.getId() > 0) { 
            pUtilQuery.AgregarWhereAnd(" c.Id=? "); 
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pCategoria.getId());
            }
        }
        if (pCategoria.getNombre() != null && pCategoria.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" c.Nombre LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pCategoria.getNombre() + "%");
            }
        }  
    }
    
    public static ArrayList<Categoria> Buscar(Categoria pCategoria) throws Exception {
        ArrayList<Categoria> categorias = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) {
            String sql = ObtenerSelect(pCategoria); 
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            QuerySelect(pCategoria, utilQuery); 
            sql = utilQuery.getSQL();
            sql += AgregarOrderBy(pCategoria); 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                QuerySelect(pCategoria, utilQuery); 
                ObtenerDatos(ps, categorias); 
                ps.close(); 
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close(); 
        } 
        catch (SQLException ex) {
            throw ex; 
        }
        return categorias; 
    }
    
}
