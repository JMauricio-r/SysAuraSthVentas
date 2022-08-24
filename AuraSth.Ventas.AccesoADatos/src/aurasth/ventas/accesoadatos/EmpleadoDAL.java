package aurasth.ventas.accesoadatos;

import aurasth.ventas.entidadesdenegocio.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class EmpleadoDAL {
    
    static String ObtenerCampos() {
        return "e.Id, e.IdRol, e.Nombre, e.Apellido, e.Contacto, e.numeroDocumento, e.Apellido, e.Estado, e.Fecha";
    }
    
    private static String ObtenerSelect(Empleado pEmpleado) {
        String sql;
        sql = "SELECT ";
        if (pEmpleado.getTop_aux() > 0) {
            sql += "TOP " + pEmpleado.getTop_aux() + " ";
        }
        sql += (ObtenerCampos() + " FROM Empleados e");
        return sql;
    }   
    
    private static String AgregarOrderBy(Empleado pEmpleado) {
        String sql = " ORDER BY e.Id DESC";
        return sql;
    }
    
    private static boolean ExisteEmpleado(Empleado pEmpleado) throws Exception {
        boolean existe = false;
        ArrayList<Empleado> empleados = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(pEmpleado);  
            sql += " WHERE e.Id<>? AND e.numeroDocumento=?";
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                ps.setInt(1, pEmpleado.getId());  
                ps.setString(2, pEmpleado.getNumeroDocumento());  
                ObtenerDatos(ps, empleados); 
                ps.close();
            } catch (SQLException ex) {
                throw ex;  
            }
            conn.close(); 
        }
        catch (SQLException ex) {
            throw ex; 
        }
        if (!empleados.isEmpty()) {
            Empleado empleado;
            empleado = empleados.get(0);  
            if (empleado.getId() > 0 && empleado.getNumeroDocumento().equals(pEmpleado.getNumeroDocumento())) {
                existe = true;
            }
        }
        return existe;
    }
   
    public static int Crear(Empleado pEmpleado) throws Exception {
        int result;
        String sql;
        boolean existe = ExisteEmpleado(pEmpleado); 
        if (existe == false) {
            try (Connection conn = ComunDB.ObtenerConexion();) { 
                sql = "INSERT INTO Empleados(IdRol,Nombre,Apellido,Contacto,numeroDocumento,Estado,Fecha) VALUES(?,?,?,?,?,?,?)";
                try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                    ps.setInt(1, pEmpleado.getIdRol());  
                    ps.setString(2, pEmpleado.getNombre());
                    ps.setString(3, pEmpleado.getApellido());  
                    ps.setString(4, pEmpleado.getContacto());
                    ps.setString(5, pEmpleado.getNumeroDocumento());   
                    ps.setByte(6, pEmpleado.getEstado());  
                    ps.setDate(7, java.sql.Date.valueOf(LocalDate.now())); 
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
            throw new RuntimeException("Numero Documento ya ha sido registrado anteriormente");
        }
        return result; 
    }
  
    public static int Modificar(Empleado pEmpleado) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            sql = "UPDATE Empleado SET IdRol=?, Nombre=?, Apellido=?, Contacto=?, Estado=? WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                    ps.setInt(1, pEmpleado.getIdRol());  
                    ps.setString(2, pEmpleado.getNombre());
                    ps.setString(3, pEmpleado.getApellido());  
                    ps.setString(4, pEmpleado.getContacto());
                    ps.setByte(5, pEmpleado.getEstado()); 
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

    public static int Eliminar(Empleado pEmpleado) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            sql = "DELETE FROM Empleados WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {  
                ps.setInt(1, pEmpleado.getId()); 
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
    
    static int AsignarDatosResultSet(Empleado pEmpleado, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pEmpleado.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pEmpleado.setIdRol(pResultSet.getInt(pIndex)); 
        pIndex++;
        pEmpleado.setNombre(pResultSet.getString(pIndex)); 
        pIndex++;
        pEmpleado.setApellido(pResultSet.getString(pIndex));
        pIndex++;
        pEmpleado.setContacto(pResultSet.getString(pIndex));
        pIndex++;
        pEmpleado.setNumeroDocumento(pResultSet.getString(pIndex)); 
        pIndex++;
        pEmpleado.setEstado(pResultSet.getByte(pIndex)); 
        pIndex++;
        pEmpleado.setFecha(pResultSet.getDate(pIndex).toLocalDate()); 
        return pIndex;
    }
    
    private static void ObtenerDatos(PreparedStatement pPS, ArrayList<Empleado> pEmpleado) throws Exception {
        try (ResultSet resultSet = ComunDB.ObtenerResultSet(pPS);) { 
            while (resultSet.next()) { 
                Empleado empleado = new Empleado();
                AsignarDatosResultSet(empleado, resultSet, 0);
                pEmpleado.add(empleado); 
            }
            resultSet.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    private static void ObtenerDatosIncluirRol(PreparedStatement pPS, ArrayList<Empleado> pEmpleado) throws Exception {
        try (ResultSet resultSet = ComunDB.ObtenerResultSet(pPS);) {
            HashMap<Integer, Rol> rolMap = new HashMap(); 
            while (resultSet.next()) { 
                Empleado empleado = new Empleado();
                int index = AsignarDatosResultSet(empleado, resultSet, 0);
                if (rolMap.containsKey(empleado.getIdRol()) == false) { 
                    Rol rol = new Rol();
                    RolDAL.AsignarDatosResultSet(rol, resultSet, index);
                    rolMap.put(rol.getId(), rol); 
                    empleado.setRol(rol);
                } else {
                    empleado.setRol(rolMap.get(empleado.getIdRol())); 
                }
                pEmpleado.add(empleado); 
            }
            resultSet.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static Empleado ObtenerPorId(Empleado pEmpleado) throws Exception {
        Empleado empleado = new Empleado();
        ArrayList<Empleado> empleados = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(pEmpleado);
            sql += " WHERE u.Id=?";
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                ps.setInt(1, pEmpleado.getId()); 
                ObtenerDatos(ps, empleados);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (!empleados.isEmpty()) { 
            empleado = empleados.get(0);
        }
        return empleado; 
    }

    public static ArrayList<Empleado> ObtenerTodos() throws Exception {
        ArrayList<Empleado> empleados;
        empleados = new ArrayList<>();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(new Empleado());
            sql += AgregarOrderBy(new Empleado()); 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                ObtenerDatos(ps, empleados); 
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex; 
        }
        return empleados;
    }
    
    static void QuerySelect(Empleado pEmpleado, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); 
        if (pEmpleado.getId() > 0) {
            pUtilQuery.AgregarWhereAnd(" e.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pEmpleado.getId());
            }
        }
        if (pEmpleado.getIdRol() > 0) {
            pUtilQuery.AgregarWhereAnd(" e.IdRol=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pEmpleado.getIdRol());
            }
        }
        if (pEmpleado.getNombre() != null && pEmpleado.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" e.Nombre LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEmpleado.getNombre() + "%");
            }
        }
        if (pEmpleado.getApellido() != null && pEmpleado.getApellido().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" e.Apellido LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEmpleado.getApellido() + "%");
            }
        }
        if (pEmpleado.getContacto()!= null && pEmpleado.getContacto().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" e.Contacto LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEmpleado.getContacto()+ "%");
            }
        }
        if (pEmpleado.getNumeroDocumento()!= null && pEmpleado.getNumeroDocumento().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" e.numeroDocumento=? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), pEmpleado.getNumeroDocumento());
            }
        }
        if (pEmpleado.getEstado() > 0) {
            pUtilQuery.AgregarWhereAnd(" u.Estado=? "); 
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pEmpleado.getEstado());
            }
        }
    }
 
    public static ArrayList<Empleado> Buscar(Empleado pEmpleado) throws Exception {
        ArrayList<Empleado> empleados = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(pEmpleado);
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            QuerySelect(pEmpleado, utilQuery); 
            sql = utilQuery.getSQL();
            sql += AgregarOrderBy(pEmpleado); 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                QuerySelect(pEmpleado, utilQuery); 
                ObtenerDatos(ps, empleados); 
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close(); 
        } 
        catch (SQLException ex) {
            throw ex; 
        }
        return empleados;
    }
}
