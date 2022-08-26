
package aurasth.ventas.accesoadatos;


import aurasth.ventas.entidadesdenegocio.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductoDAL {
    
    static String ObtenerCampos() {
        return "p.Id, p.IdCategoria, p.IdMarca, p.Nombre, p.Descripcion, p.Precio,"
                + " p.Stock, p.RutaImagen, p.Estado, p.Fecha";
    }
     
    private static String ObtenerSelect(Producto pProducto) {
        String sql;
        sql = "SELECT ";
        if (pProducto.getTop_aux() > 0) {
            sql += "TOP " + pProducto.getTop_aux() + " ";
        }
        sql += (ObtenerCampos() + " FROM Productos p");
        return sql;
    }   
    
    private static String AgregarOrderBy(Producto pProducto) {
        String sql = " ORDER BY p.Id DESC";
        return sql;
    }
    
    private static boolean ExisteProducto(Producto pProducto) throws Exception {
        boolean existe = false;
        ArrayList<Producto> productos = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(pProducto);  
            sql += " WHERE p.Id<>? AND p.Nombre=?";
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                ps.setInt(1, pProducto.getId());  
                ps.setString(2, pProducto.getNombre());  
                ObtenerDatos(ps, productos); 
                ps.close();
            } catch (SQLException ex) {
                throw ex;  
            }
            conn.close(); 
        }
        catch (SQLException ex) {
            throw ex; 
        }
        if (!productos.isEmpty()) {
            Producto producto;
            producto = productos.get(0);  
            if (producto.getId() > 0 && producto.getNombre().equals(pProducto.getNombre())) {
                existe = true;
            }
        }
        return existe;
    }
    
        public static int Crear(Producto pProducto) throws Exception {
        int result;
        String sql;
        boolean existe = ExisteProducto(pProducto); 
        if (existe == false) {
            try (Connection conn = ComunDB.ObtenerConexion();) { 
                sql = "INSERT INTO Productos(IdRol,Nombre,Apellido,Contacto,numeroDocumento,Estado,Fecha) VALUES(?,?,?,?,?,?,?)";
                try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                    ps.setInt(1, pProducto.getIdCategoria());  
                    ps.setInt(2, pProducto.getIdMarca());
                    ps.setString(3, pProducto.getNombre());  
                    ps.setString(4, pProducto.getDescripcion());
                    ps.setDouble(5, pProducto.getPrecio());   
                    ps.setInt(6, pProducto.getStock());
                    ps.setString(7, pProducto.getRutaImagen());
                    ps.setByte(7, (byte) pProducto.getEstado());  
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
            throw new RuntimeException("Producto ya existe");
        }
        return result; 
    }

    public static int Modificar(Producto pProducto) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            sql = "UPDATE Productos SET IdCategoria=?, IdMarca=?, Nombre=?, Descripcion=?, "
                    + "Precio=?, Stock=?, RutaImagen=?, Estado=? WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                    ps.setInt(1, pProducto.getIdCategoria());  
                    ps.setInt(2, pProducto.getIdMarca());
                    ps.setString(3, pProducto.getNombre());  
                    ps.setString(4, pProducto.getDescripcion());
                    ps.setDouble(5, pProducto.getPrecio());   
                    ps.setInt(6, pProducto.getStock());
                    ps.setString(7, pProducto.getRutaImagen());
                    ps.setByte(7, (byte) pProducto.getEstado());
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
    
    public static int Eliminar(Producto pProducto) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            sql = "DELETE FROM Productos WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {  
                ps.setInt(1, pProducto.getId()); 
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
    
    static int AsignarDatosResultSet(Producto pProducto, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pProducto.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pProducto.setIdCategoria(pResultSet.getInt(pIndex)); 
        pIndex++;
        pProducto.setIdMarca(pResultSet.getInt(pIndex)); 
        pIndex++;
        pProducto.setNombre(pResultSet.getString(pIndex)); 
        pIndex++;
        pProducto.setDescripcion(pResultSet.getString(pIndex));
        pIndex++;
        pProducto.setPrecio(pResultSet.getDouble(pIndex));
        pIndex++;
        pProducto.setStock(pResultSet.getInt(pIndex)); 
        pIndex++;
        pProducto.setRutaImagen(pResultSet.getString(pIndex)); 
        pIndex++;
        pProducto.setEstado(pResultSet.getByte(pIndex)); 
        pIndex++;
        pProducto.setFecha(pResultSet.getDate(pIndex).toLocalDate()); 
        return pIndex;
    }
    
    private static void ObtenerDatos(PreparedStatement pPS, ArrayList<Producto> pProducto) throws Exception {
        try (ResultSet resultSet = ComunDB.ObtenerResultSet(pPS);) { 
            while (resultSet.next()) { 
                Producto producto = new Producto();
                AsignarDatosResultSet(producto, resultSet, 0);
                pProducto.add(producto); 
            }
            resultSet.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    private static void ObtenerDatosIncluirMarca(PreparedStatement pPS, ArrayList<Producto> pProducto) throws Exception {
        try (ResultSet resultSet = ComunDB.ObtenerResultSet(pPS);) {
            HashMap<Integer, Marca> marcaMap = new HashMap(); 
            while (resultSet.next()) { 
                Producto producto = new Producto();
                int index = AsignarDatosResultSet(producto, resultSet, 0);
                if (marcaMap.containsKey(producto.getIdMarca()) == false) { 
                    Marca marca = new Marca();
                    MarcaDAL.AsignarDatosResultSet(marca, resultSet, index);
                    marcaMap.put(marca.getId(), marca); 
                    producto.setMarca(marca);
                } else {
                    producto.setMarca(marcaMap.get(producto.getIdMarca())); 
                }
                pProducto.add(producto); 
            }
            resultSet.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
    }
    private static void ObtenerDatosIncluirCategoria(PreparedStatement pPS, ArrayList<Producto> pProducto) throws Exception {
        try (ResultSet resultSet = ComunDB.ObtenerResultSet(pPS);) {
            HashMap<Integer, Categoria> categoriaMap = new HashMap(); 
            while (resultSet.next()) { 
                Producto producto = new Producto();
                int index = AsignarDatosResultSet(producto, resultSet, 0);
                if (categoriaMap.containsKey(producto.getIdCategoria()) == false) { 
                    Categoria categoria = new Categoria();
                    CategoríaDAL.AsignarDatosResultSet(categoria, resultSet, index);
                    categoriaMap.put(categoria.getId(), categoria); 
                    producto.setCategoria(categoria);
                } else {
                    producto.setCategoria(categoriaMap.get(producto.getIdCategoria())); 
                }
                pProducto.add(producto); 
            }
            resultSet.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static Producto ObtenerPorId(Producto pProducto) throws Exception {
        Producto producto = new Producto();
        ArrayList<Producto> productos = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(pProducto);
            sql += " WHERE u.Id=?";
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                ps.setInt(1, pProducto.getId()); 
                ObtenerDatos(ps, productos);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (!productos.isEmpty()) { 
            producto = productos.get(0);
        }
        return producto; 
    }
    
    public static ArrayList<Producto> ObtenerTodos() throws Exception {
        ArrayList<Producto> productos;
        productos = new ArrayList<>();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(new Producto());
            sql += AgregarOrderBy(new Producto()); 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) {
                ObtenerDatos(ps, productos); 
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex; 
        }
        return productos;
    }
    
    static void QuerySelect(Producto pProducto, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); 
        if (pProducto.getId() > 0) {
            pUtilQuery.AgregarWhereAnd(" p.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pProducto.getId());
            }
        }
        if (pProducto.getIdCategoria()> 0) {
            pUtilQuery.AgregarWhereAnd(" p.IdCategoria=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pProducto.getIdCategoria());
            }
        }
        if (pProducto.getIdMarca()> 0) {
            pUtilQuery.AgregarWhereAnd(" p.IdMarca=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pProducto.getIdMarca());
            }
        }
        if (pProducto.getNombre() != null && pProducto.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" p.Nombre LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pProducto.getNombre() + "%");
            }
        }
        if (pProducto.getDescripcion()!= null && pProducto.getDescripcion().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" p.Descripcion LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pProducto.getDescripcion()+ "%");
            }
        }
        if (pProducto.getPrecio()> 0) {
            pUtilQuery.AgregarWhereAnd(" p.Precio=? ");
            if (statement != null) {
                statement.setDouble(pUtilQuery.getNumWhere(), pProducto.getPrecio());
            }
        }
        if (pProducto.getStock()> 0) {
            pUtilQuery.AgregarWhereAnd(" p.Stock=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pProducto.getStock());
            }
        }
        if (pProducto.getEstado() > 0) {
            pUtilQuery.AgregarWhereAnd(" u.Estado=? "); 
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pProducto.getEstado());
            }
        }
    }
 
    public static ArrayList<Producto> Buscar(Producto pProducto) throws Exception {
        ArrayList<Producto> productos = new ArrayList();
        try (Connection conn = ComunDB.ObtenerConexion();) { 
            String sql = ObtenerSelect(pProducto);
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            QuerySelect(pProducto, utilQuery); 
            sql = utilQuery.getSQL();
            sql += AgregarOrderBy(pProducto); 
            try (PreparedStatement ps = ComunDB.CreatePreparedStatement(conn, sql);) { 
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                QuerySelect(pProducto, utilQuery); 
                ObtenerDatos(ps, productos); 
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close(); 
        } 
        catch (SQLException ex) {
            throw ex; 
        }
        return productos;
    }
}
