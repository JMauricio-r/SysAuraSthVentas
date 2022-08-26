
package aurasth.ventas.accesoadatos;
import static aurasth.ventas.accesoadatos.VentaDAL.AsignarDatosResultSet;
import aurasth.ventas.entidadesdenegocio.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class VentaDAL {
    
    static String ObtenerCampos(){
        return "v.Id, v.idCliente, v.idProducto, v.Contacto, v.TotalProducto, v.MontoTotal, v.IdTransacion, v.Direccion, v.Fecha";
    }
    
    private static String ObtenerSelect(Venta pVenta){
        String sql;
        sql = "SELECT ";
        if (pVenta.getTop_aux() > 0 ) {
            sql += " TOP "+pVenta.getTop_aux() + " ";
        } 
        sql += (ObtenerCampos()+ " FROM Ventas v");
        return sql;
    }
    
    private static String AgregarOrderBy(Venta pVenta) {
        String sql = " ORDER BY v.Id DESC";
        return sql;
    }
    
   
   
    public static int crear(Venta pVenta) throws Exception {
        int result;
        String sql;
        boolean existe = ExisteVenta(pVenta);
        if (existe == false) {
            try (Connection conn = ComunDB.ObtenerConexion();) { 
                sql = "INSERT INTO Ventas(IdCliente,IdProducto, Contacto, TotalProducto, MontoTotal, IdTransacion,Direccion, Fecha) VALUES(?,?,?,?,?,?,?)";
                try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn,sql);) {
                    ps.setInt(1, pVenta.getIdCliente());
                    ps.setInt(2, pVenta.getIdProducto());
                    ps.setString(3, pVenta.getContacto());
                    ps.setDouble(4, pVenta.getTotalProducto());
                    ps.setDouble(5, pVenta.getMontoTotal());
                    ps.setDouble(6, pVenta.getIdTransacion());
                    ps.setString(7, pVenta.getDireccion());
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
            throw new RuntimeException("Venta ya existe");
        }
        return result; 
    }

    public static int Modificar(Venta pVenta) throws Exception {
        int result;
        String sql;
        boolean existe = ExisteVenta(pVenta);
        if (existe == false) {
            try (Connection conn = ComunDB.ObtenerConexion();) { 
                sql = "UPDATE Ventas SET IdCliente, IdProducto, Contacto, TotalProducto, MontoTotal, IdTransacion, Direccion, Fecha? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                    ps.setInt(1, pVenta.getIdCliente());
                    ps.setInt(2, pVenta.getIdProducto());
                    ps.setString(3, pVenta.getContacto());
                    ps.setDouble(4, pVenta.getTotalProducto());
                    ps.setDouble(5, pVenta.getMontoTotal());
                    ps.setDouble(6, pVenta.getIdTransacion());
                    ps.setString(7, pVenta.getDireccion());
                    ps.setDate(8, java.sql.Date.valueOf(LocalDate.now())); 
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
            throw new RuntimeException("Venta ya existe"); 
        }
        return result; 
    }
    
    public static int Eliminar(Venta pVenta) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            sql = "DELETE FROM Ventas WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn,sql);) { 
                ps.setInt(1, pVenta.getId());
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
    
    static int AsignarDatosResultSet(Venta pVenta, ResultSet pResultSet, int pIndex) 
            throws Exception {
        pIndex++;
        pVenta.setId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pVenta.setIdCliente(pResultSet.getInt(pIndex));
        pIndex++;
        pVenta.setIdProducto((pResultSet.getInt(pIndex)));
        pIndex++;
        pVenta.setContacto(pResultSet.getString(pIndex));
        pIndex++;
        pVenta.setTotalProducto(pResultSet.getDouble(pIndex));
        pIndex++;
        pVenta.setMontoTotal(pResultSet.getDouble(pIndex));
        pIndex++;
        pVenta.setIdTransacion(pResultSet.getDouble(pIndex));
        pIndex++;
        pVenta.setDireccion(pResultSet.getString(pIndex));
        pIndex++;
        pVenta.setFecha(pResultSet.getDate(pIndex).toLocalDate());
        return pIndex;
    }
     
    private static void ObtenerDatos(PreparedStatement pPS, ArrayList<Venta> pVenta) throws Exception {
        try (ResultSet resultSet = ComunDB.ObtenerResultSet(pPS);) {  
            while (resultSet.next()) { 
                Venta venta = new Venta();               
                AsignarDatosResultSet(venta, resultSet, 0);
                pVenta.add(venta); 
            }
            resultSet.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static Venta ObtenerPorId(Venta pVenta) throws Exception {
        Venta venta = new Venta();
        ArrayList<Venta> ventas = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) {
            String sql = ObtenerSelect(pVenta);
            sql += " WHERE v.Id=?";
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                ps.setInt(1, pVenta.getId()); 
                ObtenerDatos(ps, ventas); 
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close(); 
        }
        catch (SQLException ex) {
            throw ex; 
        }
        if (!ventas.isEmpty()) { 
             venta = ventas.get(0); 
        }
        return venta; 
    }
    
    public static ArrayList<Venta> ObtenerTodos() throws Exception {
        ArrayList<Venta> ventas;
        ventas = new ArrayList<>();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(new Venta()); 
            sql += AgregarOrderBy(new Venta());
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                ObtenerDatos(ps, ventas); 
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close(); 
        }
        catch (SQLException ex) {
            throw ex; 
        }
        return ventas; 
    }

    static void QuerySelect(Venta pVenta, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pVenta.getId() > 0) { 
            pUtilQuery.AgregarWhereAnd(" v.Id=? "); 
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pVenta.getId());
            }
        }
        if (pVenta.getIdCliente()>0 &&  pVenta.getIdProducto() > 0 && pVenta.getContacto().equals(pVenta.getContacto()) && pVenta.getTotalProducto()>0 && pVenta.getMontoTotal()>0 && pVenta.getIdTransacion()>0 && pVenta.getDireccion().equals(pVenta.getDireccion())){
                if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pVenta.getIdCliente()+ "%");
            }
        }  
    }
    
    public static ArrayList<Venta> Buscar(Venta pVenta) throws Exception {
        ArrayList<Venta> ventas = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) {
            String sql = ObtenerSelect(pVenta); 
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            QuerySelect(pVenta, utilQuery); 
            sql = utilQuery.getSQL();
            sql += AgregarOrderBy(pVenta); 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                QuerySelect(pVenta, utilQuery); 
                ObtenerDatos(ps, ventas); 
                ps.close(); 
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close(); 
        } 
        catch (SQLException ex) {
            throw ex; 
        }
        return ventas; 
    }

 private static void obtenerDatosIncluirCliente(PreparedStatement pPS, ArrayList<Venta> pVentas) throws Exception {
        try (ResultSet resultSet = ComunDB.ObtenerResultSet(pPS);) {
            HashMap<Integer, Cliente> clienteMap = new HashMap(); 
            while (resultSet.next()) { 
                Venta venta = new Venta();
                int index = AsignarDatosResultSet(venta, resultSet, 0);
                if (clienteMap.containsKey(venta.getIdCliente()) == false) { 
                    Cliente cliente = new Cliente();
                    ClienteDAL.AsignarDatosResultSet(cliente, resultSet, index);
                    clienteMap.put(cliente.getId(), cliente); 
                    venta.setClientes(cliente);
                } else {
                    venta.setClientes(clienteMap.get(venta.getIdCliente())); 
                }
                pVentas.add(venta); 
            }
            resultSet.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
    }


 public static ArrayList<Venta> BuscarIncluirCliente(Venta pVenta) throws Exception {
        ArrayList<Venta> ventas = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = "SELECT ";
            if (pVenta.getTop_aux() > 0 ) {
                sql += "TOP " + pVenta.getTop_aux() + " "; 
            }
            sql += ObtenerCampos(); 
            sql += ",";
            sql += ClienteDAL.ObtenerCampos();
            sql += " FROM Ventas v";
            sql += " JOIN Clientes c on (u.IdCliente=c.Id)"; 
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            QuerySelect(pVenta, utilQuery);
            sql = utilQuery.getSQL();
            sql += AgregarOrderBy(pVenta);
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                QuerySelect(pVenta, utilQuery);
                obtenerDatosIncluirCliente(ps, ventas);
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
        return ventas; 
    }
}
