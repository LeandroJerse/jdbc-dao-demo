package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection connection = null;


    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                Properties props = loadedProperties();
                String url = props.getProperty("dburl");
                connection = DriverManager.getConnection(url, props);
            }
            catch (SQLException e) {
                throw new DbException("Erro ao conectar ao banco de dados: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection(){
        if (connection != null){
            try{
                connection.close();
            }
            catch (SQLException e){
                throw new DbException(e.getMessage());
            }

        }
    }

    private static Properties loadedProperties() {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(fis);

            return properties;
        }
        catch (IOException e) {
            throw new DbException("Erro ao carregar as propriedades do banco de dados: " + e.getMessage());
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
