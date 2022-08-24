
package aurasth.ventas.accesoadatos;

import aurasth.ventas.entidadesdenegocio.*;
import java.sql.*;
import java.util.ArrayList;

public class DetalleVentaDAL {
    
    static String ObtenerCampos(){
        return "d.Id, d.idProducto, d.idVenta, d.Cantidad, d.total";
    }
    
    private static String ObtenerSelect(DetalleVenta pDetalleVenta){
        String sql;
        sql = "SELECT ";
        if (pDetalleVenta.getTop_aux() > 0 ) {
            sql += " TOP "+pDetalleVenta.getTop_aux() + " ";
        } 
        sql += (ObtenerCampos()+ " FROM DetalleVentas d");
        return sql;
    }
    
    private static String AgregarOrderBy(DetalleVenta pDetalleVenta) {
        String sql = " ORDER BY d.Id DESC";
        return sql;
    }
    
    private static boolean ExisteDetalleVenta(DetalleVenta pDetalleVenta) throws Exception {
        boolean existe = false;
        ArrayList<DetalleVenta> detalleVentas = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(pDetalleVenta);  
            sql += " WHERE d.Id<>? AND d.idProducto<>? AND d.idVenta<>? d.Cantidad<>? AND d.total=?";
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn,sql);){
                ps.setInt(1, pDetalleVenta.getId()); 
                ps.setInt(2, pDetalleVenta.getIdProducto()); 
                ps.setInt(3, pDetalleVenta.getIdVenta());
                ps.setInt(4, pDetalleVenta.getCantidad());
                ps.setDouble(5, pDetalleVenta.getTotal());
                ObtenerDatos(ps, detalleVentas);
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close(); 
        }
        catch (SQLException ex) {
            throw ex; 
        }
        if (!detalleVentas.isEmpty()) { 
            DetalleVenta detalleVenta;
            detalleVenta = detalleVentas.get(0); 
            if (detalleVenta.getId() > 0 && detalleVenta.getIdProducto()>0 && detalleVenta.getIdVenta() >0 && detalleVenta.getCantidad() >0 && detalleVenta.getTotal() > 0) {
                existe = true;
            }
        }
        return existe;
    }
   
    public static int crear(DetalleVenta pDetalleVenta) throws Exception {
        int result;
        String sql;
        boolean existe = ExisteDetalleVenta(pDetalleVenta);
        if (existe == false) {
            try (Connection conn = ComunDB.ObtenerConexion();) { 
                sql = "INSERT INTO DetalleVentas(IdProducto, IdVenta, Cantidad, Total) VALUES(?,?,?,?)";
                try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn,sql);) {
                    ps.setInt(1, pDetalleVenta.getIdProducto());
                    ps.setInt(2, pDetalleVenta.getIdVenta());
                    ps.setInt(3, pDetalleVenta.getCantidad());
                    ps.setDouble(4, pDetalleVenta.getTotal());
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
            throw new RuntimeException("DetalleVenta ya existe");
        }
        return result; 
    }

    public static int Modificar(DetalleVenta pDetalleVenta) throws Exception {
        int result;
        String sql;
        boolean existe = ExisteDetalleVenta(pDetalleVenta);
        if (existe == false) {
            try (Connection conn = ComunDB.ObtenerConexion();) { 
                sql = "UPDATE DetalleVentas SET IdProducto, IdVenta, Cantidad, Total? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                    ps.setInt(1, pDetalleVenta.getIdProducto());
                    ps.setInt(2, pDetalleVenta.getIdVenta());
                    ps.setInt(3, pDetalleVenta.getCantidad());
                    ps.setDouble(4, pDetalleVenta.getTotal());
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
            throw new RuntimeException("DetalleVenta ya existe"); 
        }
        return result; 
    }
    
    public static int Eliminar(DetalleVenta pDetalleVenta) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            sql = "DELETE FROM DetalleVentas WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn,sql);) { 
                ps.setInt(1, pDetalleVenta.getId());
//                ps.setInt(2, pDetalleVenta.getIdProducto());
//                ps.setInt(3, pDetalleVenta.getIdVenta());
//                ps.setInt(4, pDetalleVenta.getCantidad());
//                ps.setDouble(5, pDetalleVenta.getTotal());
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
    
    static int AsignarDatosResultSet(DetalleVenta pDetalleVenta, ResultSet pResultSet, int pIndex) 
            throws Exception {
        pIndex++;
        pDetalleVenta.setId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pDetalleVenta.setIdProducto(pResultSet.getInt(pIndex));
        pIndex++;
        pDetalleVenta.setIdVenta((pResultSet.getInt(pIndex)));
        pIndex++;
        pDetalleVenta.setCantidad(pResultSet.getInt(pIndex));
        pIndex++;
        pDetalleVenta.setTotal(pResultSet.getDouble(pIndex));
        pIndex++;
        return pIndex;
    }
     
    private static void ObtenerDatos(PreparedStatement pPS, ArrayList<DetalleVenta> pDetalleVenta) throws Exception {
        try (ResultSet resultSet = ComunDB.ObtenerResultSet(pPS);) {  
            while (resultSet.next()) { 
                DetalleVenta detalleVenta = new DetalleVenta();               
                AsignarDatosResultSet(detalleVenta, resultSet, 0);
                pDetalleVenta.add(detalleVenta); 
            }
            resultSet.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static DetalleVenta ObtenerPorId(DetalleVenta pDetalleVenta) throws Exception {
        DetalleVenta detalleVenta = new DetalleVenta();
        ArrayList<DetalleVenta> detalleVentas = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) {
            String sql = ObtenerSelect(pDetalleVenta);
            sql += " WHERE d.Id=?";
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                ps.setInt(1, pDetalleVenta.getId()); 
                ObtenerDatos(ps, detalleVentas); 
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close(); 
        }
        catch (SQLException ex) {
            throw ex; 
        }
        if (!detalleVentas.isEmpty()) { 
             detalleVenta = detalleVentas.get(0); 
        }
        return detalleVenta; 
    }
    
    public static ArrayList<DetalleVenta> ObtenerTodos() throws Exception {
        ArrayList<DetalleVenta> detalleVentas;
        detalleVentas = new ArrayList<>();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(new DetalleVenta()); 
            sql += AgregarOrderBy(new DetalleVenta());
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                ObtenerDatos(ps, detalleVentas); 
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close(); 
        }
        catch (SQLException ex) {
            throw ex; 
        }
        return detalleVentas; 
    }

    static void QuerySelect(DetalleVenta pDetalleVenta, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pDetalleVenta.getId() > 0) { 
            pUtilQuery.AgregarWhereAnd(" d.Id=? "); 
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pDetalleVenta.getId());
            }
        }
        if (pDetalleVenta.getIdProducto() > 0  && pDetalleVenta.getIdVenta() >0 && pDetalleVenta.getCantidad() >0 && pDetalleVenta.getTotal() >0 == false) {
            pUtilQuery.AgregarWhereAnd(" d.IdProducto LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pDetalleVenta.getIdProducto()+ "%");
            }
        }  
    }
    
    public static ArrayList<DetalleVenta> Buscar(DetalleVenta pDetalleVenta) throws Exception {
        ArrayList<DetalleVenta> detalleVentas = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) {
            String sql = ObtenerSelect(pDetalleVenta); 
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            QuerySelect(pDetalleVenta, utilQuery); 
            sql = utilQuery.getSQL();
            sql += AgregarOrderBy(pDetalleVenta); 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                QuerySelect(pDetalleVenta, utilQuery); 
                ObtenerDatos(ps, detalleVentas); 
                ps.close(); 
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close(); 
        } 
        catch (SQLException ex) {
            throw ex; 
        }
        return detalleVentas; 
    }

}