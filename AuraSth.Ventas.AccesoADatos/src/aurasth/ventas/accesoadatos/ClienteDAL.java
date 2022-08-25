
package aurasth.ventas.accesoadatos;

import aurasth.ventas.entidadesdenegocio.*;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;

public class ClienteDAL {
   
    public static String EncriptarMD5(String txt) throws Exception {
        try {
            StringBuffer sb;
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(txt.getBytes());
            sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException ex) {
            throw ex;
        }
    }

    static String ObtenerCampos() {
        return "c.Id, c.Nombre, c.Apellido, c.Email, c.Login,  c.Fecha";
    }
    
    private static String ObtenerSelect(Cliente pCliente) {
        String sql;
        sql = "SELECT ";
        if (pCliente.getTop_aux() > 0) {
            sql += "TOP " + pCliente.getTop_aux() + " ";
        }
        sql += (ObtenerCampos() + " FROM Clientes c");
        return sql;
    }   
    
    private static String AgregarOrderBy(Cliente pCliente) {
        String sql = " ORDER BY c.Id DESC";
        return sql;
    }

    private static boolean ExisteLogin(Cliente pCliente) throws Exception {
        boolean existe = false;
        ArrayList<Cliente> clientes = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(pCliente);  
            sql += " WHERE c.Id<>? AND c.Login=?";
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                ps.setInt(1, pCliente.getId());  
                ps.setString(2, pCliente.getLogin());  
                ObtenerDatos(ps, clientes); 
                ps.close();
            } catch (SQLException ex) {
                throw ex;  
            }
            conn.close(); 
        }
        catch (SQLException ex) {
            throw ex; 
        }
        if (!clientes.isEmpty()) {
            Cliente cliente;
            cliente = clientes.get(0);  
            if (cliente.getId() > 0 && cliente.getLogin().equals(pCliente.getLogin())) {
                existe = true;
            }
        }
        return existe;

    }

    public static int Crear(Cliente pCliente) throws Exception {
        int result;
        String sql;
        boolean existe = ExisteLogin(pCliente); 
        if (existe == false) {
            try (Connection conn = ComunDB.ObtenerConexion();) { 
                sql = "INSERT INTO Clientes(Nombre,Apellido,Email,Login,Contrasenia,Fecha) VALUES(?,?,?,?,?,?)";
                try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                    ps.setString(1, pCliente.getNombre());
                    ps.setString(2, pCliente.getApellido());  
                    ps.setString(3, pCliente.getEmail());
                    ps.setString(4, pCliente.getLogin()); 
                    ps.setString(5, EncriptarMD5(pCliente.getContrasenia()));  
                    ps.setDate(6, java.sql.Date.valueOf(LocalDate.now())); 
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
            throw new RuntimeException("Login ya existe");
        }
        return result; 
    }

    public static int Modificar(Cliente pCliente) throws Exception {
        int result;
        String sql;
        boolean existe = ExisteLogin(pCliente); 
        if (existe == false) {
            try (Connection conn = ComunDB.ObtenerConexion();) { 
                sql = "UPDATE Usuarios SET  Nombre=?, Apellido=?, Email=?, Login=?, Fecha=? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                    ps.setString(1, pCliente.getNombre()); 
                    ps.setString(2, pCliente.getApellido());
                    ps.setString(3, pCliente.getEmail());                   
                    ps.setString(4, pCliente.getLogin());   
                    ps.setDate(5, java.sql.Date.valueOf(LocalDate.now()));  
                    ps.setInt(6, pCliente.getId());
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
            throw new RuntimeException("Login ya existe"); 
        }
        return result;
    }

    public static int Eliminar(Cliente pCliente) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            sql = "DELETE FROM Clientes WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {  
                ps.setInt(1, pCliente.getId()); 
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

    static int AsignarDatosResultSet(Cliente pCliente, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pCliente.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pCliente.setNombre(pResultSet.getString(pIndex)); 
        pIndex++;
        pCliente.setApellido(pResultSet.getString(pIndex));
        pIndex++;
        pCliente.setEmail(pResultSet.getString(pIndex));
        pIndex++;
        pCliente.setLogin(pResultSet.getString(pIndex)); 
        pIndex++;
        pCliente.setFecha(pResultSet.getDate(pIndex).toLocalDate()); 
        return pIndex;
    }

    private static void ObtenerDatos(PreparedStatement pPS, ArrayList<Cliente> pClientes) throws Exception {
        try (ResultSet resultSet = ComunDB.ObtenerResultSet(pPS);) { 
            while (resultSet.next()) { 
                Cliente cliente = new Cliente();
                AsignarDatosResultSet(cliente, resultSet, 0);
                pClientes.add(cliente); 
            }
            resultSet.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
    }

   

    public static Cliente ObtenerPorId(Cliente pCliente) throws Exception {
        Cliente cliente = new Cliente();
        ArrayList<Cliente> clientes = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(pCliente);
            sql += " WHERE c.Id=?";
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                ps.setInt(1, pCliente.getId()); 
                ObtenerDatos(ps, clientes);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (!clientes.isEmpty()) { 
            cliente = clientes.get(0);
        }
        return cliente; 
    }

    public static ArrayList<Cliente> ObtenerTodos() throws Exception {
        ArrayList<Cliente> clientes;
        clientes = new ArrayList<>();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(new Cliente());
            sql += AgregarOrderBy(new Cliente()); 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                ObtenerDatos(ps, clientes); 
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex; 
        }
        return clientes;
    }

    static void QuerySelect(Cliente pCliente, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); 
        if (pCliente.getId() > 0) {
            pUtilQuery.AgregarWhereAnd(" c.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pCliente.getId());
            }
        }
      
        if (pCliente.getNombre() != null && pCliente.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" c.Nombre LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pCliente.getNombre() + "%");
            }
        }
        if (pCliente.getApellido() != null && pCliente.getApellido().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" c.Apellido LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pCliente.getApellido() + "%");
            }
        }
        if (pCliente.getEmail() != null && pCliente.getEmail().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" c.Email LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pCliente.getApellido() + "%");
            }
        }
        if (pCliente.getLogin() != null && pCliente.getLogin().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" c.Login=? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), pCliente.getLogin());
            }
        }
//        if (pCliente.getFecha() != null) {
//            pUtilQuery.AgregarWhereAnd(" c.Cliente=? "); 
//            if (statement != null) {
//                statement.setDate(pUtilQuery.getNumWhere(), pCliente.getFecha());
//            }
//        }
    }
 
    public static ArrayList<Cliente> Buscar(Cliente pCliente) throws Exception {
        ArrayList<Cliente> clientes = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(pCliente);
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            QuerySelect(pCliente, utilQuery); 
            sql = utilQuery.getSQL();
            sql += AgregarOrderBy(pCliente); 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                QuerySelect(pCliente, utilQuery); 
                ObtenerDatos(ps, clientes); 
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close(); 
        } 
        catch (SQLException ex) {
            throw ex; 
        }
        return clientes;
    }

    public static Cliente Login(Cliente pCliente) throws Exception {
        Cliente cliente = new Cliente();
        ArrayList<Cliente> clientes = new ArrayList();
        String contrasenia = EncriptarMD5(pCliente.getContrasenia());
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(pCliente);
            sql += " WHERE c.Id=? AND c.Login=?";
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                ps.setString(1, pCliente.getLogin());
                ps.setString(2, contrasenia);
                ObtenerDatos(ps, clientes);
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close(); 
        } 
        catch (SQLException ex) {
            throw ex; 
        }
        if (!clientes.isEmpty()) { 
            cliente = clientes.get(0); 
        }
        return cliente; 
    }

    public static int CambiarPassword(Usuario pCliente, String pPasswordAnt) throws Exception {
        int result;
        String sql;
        Cliente clienteAnt = new Cliente();
        clienteAnt.setLogin(pCliente.getLogin());
        clienteAnt.setContrasenia(pPasswordAnt);
        Cliente clienteAut = Login(clienteAnt); 
        if (clienteAut.getId() > 0 && clienteAut.getLogin().equals(pCliente.getLogin())) {
            try (Connection conn = ComunDB.ObtenerConexion();) {
                sql = "UPDATE Cliente SET Password=? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                    ps.setString(1, EncriptarMD5(pCliente.getContrasenia())); 
                    ps.setInt(2, pCliente.getId()); 
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
            throw new RuntimeException("La contraseña actual es incorrecta");
        }
        return result; 
    }
}
