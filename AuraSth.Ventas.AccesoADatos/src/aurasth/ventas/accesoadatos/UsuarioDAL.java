
package aurasth.ventas.accesoadatos;

import aurasth.ventas.entidadesdenegocio.*;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;

public class UsuarioDAL {
   
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
        return "u.Id, u.IdRol, u.Nombre, u.Apellido, u.Email, u.Login, u.Estado, u.Fecha";
    }
    
    private static String ObtenerSelect(Usuario pUsuario) {
        String sql;
        sql = "SELECT ";
        if (pUsuario.getTop_aux() > 0) {
            sql += "TOP " + pUsuario.getTop_aux() + " ";
        }
        sql += (ObtenerCampos() + " FROM Usuarios u");
        return sql;
    }   
    
    private static String AgregarOrderBy(Usuario pUsuario) {
        String sql = " ORDER BY u.Id DESC";
        return sql;
    }

    private static boolean ExisteLogin(Usuario pUsuario) throws Exception {
        boolean existe = false;
        ArrayList<Usuario> usuarios = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(pUsuario);  
            sql += " WHERE u.Id<>? AND u.Login=?";
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                ps.setInt(1, pUsuario.getId());  
                ps.setString(2, pUsuario.getLogin());  
                ObtenerDatos(ps, usuarios); 
                ps.close();
            } catch (SQLException ex) {
                throw ex;  
            }
            conn.close(); 
        }
        catch (SQLException ex) {
            throw ex; 
        }
        if (!usuarios.isEmpty()) {
            Usuario usuario;
            usuario = usuarios.get(0);  
            if (usuario.getId() > 0 && usuario.getLogin().equals(pUsuario.getLogin())) {
                existe = true;
            }
        }
        return existe;

    }

    public static int Crear(Usuario pUsuario) throws Exception {
        int result;
        String sql;
        boolean existe = ExisteLogin(pUsuario); 
        if (existe == false) {
            try (Connection conn = ComunDB.ObtenerConexion();) { 
                sql = "INSERT INTO Usuarios(IdRol,Nombre,Apellido,Email,Login,Contrasenia,Estado,Fecha) VALUES(?,?,?,?,?,?,?,?)";
                try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                    ps.setInt(1, pUsuario.getIdRol());  
                    ps.setString(2, pUsuario.getNombre());
                    ps.setString(3, pUsuario.getApellido());  
                    ps.setString(4, pUsuario.getEmail());
                    ps.setString(5, pUsuario.getLogin()); 
                    ps.setString(6, EncriptarMD5(pUsuario.getContrasenia()));  
                    ps.setByte(7, pUsuario.getEstado());  
                    ps.setDate(9, java.sql.Date.valueOf(LocalDate.now())); 
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

    public static int Modificar(Usuario pUsuario) throws Exception {
        int result;
        String sql;
        boolean existe = ExisteLogin(pUsuario); 
        if (existe == false) {
            try (Connection conn = ComunDB.ObtenerConexion();) { 
                sql = "UPDATE Usuarios SET IdRol=?, Nombre=?, Apellido=?, Email=?, Login=?, Estado=? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                    ps.setInt(1, pUsuario.getIdRol());
                    ps.setString(2, pUsuario.getNombre()); 
                    ps.setString(3, pUsuario.getApellido());
                    ps.setString(4, pUsuario.getEmail());                   
                    ps.setString(5, pUsuario.getLogin());   
                    ps.setByte(6, pUsuario.getEstado());  
                    ps.setInt(7, pUsuario.getId());
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

    public static int Eliminar(Usuario pUsuario) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            sql = "DELETE FROM Usuarios WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {  
                ps.setInt(1, pUsuario.getId()); 
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

    static int AsignarDatosResultSet(Usuario pUsuario, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pUsuario.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pUsuario.setIdRol(pResultSet.getInt(pIndex)); 
        pIndex++;
        pUsuario.setNombre(pResultSet.getString(pIndex)); 
        pIndex++;
        pUsuario.setApellido(pResultSet.getString(pIndex));
        pIndex++;
        pUsuario.setEmail(pResultSet.getString(pIndex));
        pIndex++;
        pUsuario.setLogin(pResultSet.getString(pIndex)); 
        pIndex++;
        pUsuario.setEstado(pResultSet.getByte(pIndex)); 
        pIndex++;
        pUsuario.setFecha(pResultSet.getDate(pIndex).toLocalDate()); 
        return pIndex;
    }

    private static void ObtenerDatos(PreparedStatement pPS, ArrayList<Usuario> pUsuarios) throws Exception {
        try (ResultSet resultSet = ComunDB.ObtenerResultSet(pPS);) { 
            while (resultSet.next()) { 
                Usuario usuario = new Usuario();
                AsignarDatosResultSet(usuario, resultSet, 0);
                pUsuarios.add(usuario); 
            }
            resultSet.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
    }

    private static void obtenerDatosIncluirRol(PreparedStatement pPS, ArrayList<Usuario> pUsuarios) throws Exception {
        try (ResultSet resultSet = ComunDB.ObtenerResultSet(pPS);) {
            HashMap<Integer, Rol> rolMap = new HashMap(); 
            while (resultSet.next()) { 
                Usuario usuario = new Usuario();
                int index = AsignarDatosResultSet(usuario, resultSet, 0);
                if (rolMap.containsKey(usuario.getIdRol()) == false) { 
                    Rol rol = new Rol();
                    RolDAL.AsignarDatosResultSet(rol, resultSet, index);
                    rolMap.put(rol.getId(), rol); 
                    usuario.setRol(rol);
                } else {
                    usuario.setRol(rolMap.get(usuario.getIdRol())); 
                }
                pUsuarios.add(usuario); 
            }
            resultSet.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public static Usuario ObtenerPorId(Usuario pUsuario) throws Exception {
        Usuario usuario = new Usuario();
        ArrayList<Usuario> usuarios = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(pUsuario);
            sql += " WHERE u.Id=?";
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                ps.setInt(1, pUsuario.getId()); 
                ObtenerDatos(ps, usuarios);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (!usuarios.isEmpty()) { 
            usuario = usuarios.get(0);
        }
        return usuario; 
    }

    public static ArrayList<Usuario> ObtenerTodos() throws Exception {
        ArrayList<Usuario> usuarios;
        usuarios = new ArrayList<>();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(new Usuario());
            sql += AgregarOrderBy(new Usuario()); 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                ObtenerDatos(ps, usuarios); 
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex; 
        }
        return usuarios;
    }

    static void QuerySelect(Usuario pUsuario, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); 
        if (pUsuario.getId() > 0) {
            pUtilQuery.AgregarWhereAnd(" u.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pUsuario.getId());
            }
        }
        if (pUsuario.getIdRol() > 0) {
            pUtilQuery.AgregarWhereAnd(" u.IdRol=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pUsuario.getIdRol());
            }
        }
        if (pUsuario.getNombre() != null && pUsuario.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" u.Nombre LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pUsuario.getNombre() + "%");
            }
        }
        if (pUsuario.getApellido() != null && pUsuario.getApellido().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" u.Apellido LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pUsuario.getApellido() + "%");
            }
        }
        if (pUsuario.getEmail() != null && pUsuario.getEmail().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" u.Email LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pUsuario.getApellido() + "%");
            }
        }
        if (pUsuario.getLogin() != null && pUsuario.getLogin().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" u.Login=? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), pUsuario.getLogin());
            }
        }
        if (pUsuario.getEstado() > 0) {
            pUtilQuery.AgregarWhereAnd(" u.Estado=? "); 
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pUsuario.getEstado());
            }
        }
    }
 
    public static ArrayList<Usuario> Buscar(Usuario pUsuario) throws Exception {
        ArrayList<Usuario> usuarios = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(pUsuario);
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            QuerySelect(pUsuario, utilQuery); 
            sql = utilQuery.getSQL();
            sql += AgregarOrderBy(pUsuario); 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                QuerySelect(pUsuario, utilQuery); 
                ObtenerDatos(ps, usuarios); 
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close(); 
        } 
        catch (SQLException ex) {
            throw ex; 
        }
        return usuarios;
    }

    public static Usuario Login(Usuario pUsuario) throws Exception {
        Usuario usuario = new Usuario();
        ArrayList<Usuario> usuarios = new ArrayList();
        String contrasenia = EncriptarMD5(pUsuario.getContrasenia());
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(pUsuario);
            sql += " WHERE u.Login=? AND u.Contrasenia=? AND u.Estado=?";
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                ps.setString(1, pUsuario.getLogin());
                ps.setString(2, contrasenia); 
                ps.setByte(3, Estado.ACTIVO); 
                ObtenerDatos(ps, usuarios);
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close(); 
        } 
        catch (SQLException ex) {
            throw ex; 
        }
        if (!usuarios.isEmpty()) { 
            usuario = usuarios.get(0); 
        }
        return usuario; 
    }

    public static int CambiarPassword(Usuario pUsuario, String pPasswordAnt) throws Exception {
        int result;
        String sql;
        Usuario usuarioAnt = new Usuario();
        usuarioAnt.setLogin(pUsuario.getLogin());
        usuarioAnt.setContrasenia(pPasswordAnt);
        Usuario usuarioAut = Login(usuarioAnt); 
        if (usuarioAut.getId() > 0 && usuarioAut.getLogin().equals(pUsuario.getLogin())) {
            try (Connection conn = ComunDB.ObtenerConexion();) {
                sql = "UPDATE Usuario SET Password=? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                    ps.setString(1, EncriptarMD5(pUsuario.getContrasenia())); 
                    ps.setInt(2, pUsuario.getId()); 
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
            throw new RuntimeException("El password actual es incorrecto");
        }
        return result; 
    }

    public static ArrayList<Usuario> BuscarIncluirRol(Usuario pUsuario) throws Exception {
        ArrayList<Usuario> usuarios = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = "SELECT ";
            if (pUsuario.getTop_aux() > 0 ) {
                sql += "TOP " + pUsuario.getTop_aux() + " "; 
            }
            sql += ObtenerCampos(); 
            sql += ",";
            sql += RolDAL.ObtenerCampos();
            sql += " FROM Usuarios u";
            sql += " JOIN Roles r on (u.IdRol=r.Id)"; 
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            QuerySelect(pUsuario, utilQuery);
            sql = utilQuery.getSQL();
            sql += AgregarOrderBy(pUsuario);
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                QuerySelect(pUsuario, utilQuery);
                obtenerDatosIncluirRol(ps, usuarios);
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
        return usuarios; 
    }
}
