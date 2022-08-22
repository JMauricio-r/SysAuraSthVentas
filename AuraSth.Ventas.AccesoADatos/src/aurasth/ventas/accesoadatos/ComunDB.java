
package aurasth.ventas.accesoadatos;
import java.sql.*;
public class ComunDB {
    
    
    static String connectionUrl = "jdbc:sqlserver://localhost"
            + "database=AuraSt_db"
            + "user=VinnyJava"
            + "password=mauricio17"
            + "trustServerCertificate=true";
    
    public static Connection ObtenerConexion() throws SQLException{
        DriverManager.deregisterDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        Connection connection = DriverManager.getConnection(connectionUrl);
        return connection;
    }
    
    public static Statement CreateStatement(Connection pConn) throws SQLException{
        Statement statement = pConn.createStatement();
        return statement;
    }
    
    public static PreparedStatement CreatePreparedStatement(Connection pConn,String pSql)
            throws SQLException {
        PreparedStatement statement = pConn.prepareStatement(pSql);
        return statement;
    }
    
    public static ResultSet ObtenerResulSet(Statement pStatement,String pSql)
            throws SQLException{
        ResultSet resultSet = pStatement.executeQuery(pSql);
        return resultSet;
    }
    
    public static ResultSet ObtenerResultSet(PreparedStatement pPreparedStatement)
            throws SQLException{
        ResultSet resultSet = pPreparedStatement.executeQuery();
        return resultSet;
    }
    
    public static int EjecutarSQL(String pSql) throws SQLException {
        int result;
        try (Connection connection = ObtenerConexion();) {  
            Statement statement = connection.createStatement();
            result = statement.executeUpdate(pSql); 
        } catch (SQLException ex) {
            throw ex; 
        }
        return result; 
    }
    
    class UtilQuery{
     
        private String SQL; 
        private PreparedStatement statement;
        private int numWhere; 

        public UtilQuery() {
        }

        public UtilQuery(String SQL, PreparedStatement statement, int numWhere){
            this.SQL = SQL;
            this.statement = statement;
            this.numWhere = numWhere;
        }

        public String getSQL() {
            return SQL;
        }

        public void setSQL(String SQL) {
            this.SQL = SQL;
        }

        public PreparedStatement getStatement() {
            return statement;
        }

        public void setStatement(PreparedStatement statement) {
            this.statement = statement;
        }

        public int getNumWhere() {
            return numWhere;
        }

        public void setNumWhere(int numWhere) {
            this.numWhere = numWhere;
        }
        
        public void AgregarWhereAnd(String pSql) {
            if (this.SQL != null) {
                if (this.numWhere == 0) { 
                    this.SQL += " WHERE ";
                } else {
                    this.SQL += " AND ";
                }
                this.SQL += pSql; 
            }
            this.numWhere++;
        }
    }
}
