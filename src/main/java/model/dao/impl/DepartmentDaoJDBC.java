package model.dao.impl;

import db.DB;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection con;
    public DepartmentDaoJDBC(Connection connection) {
        this.con = connection;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement stmt = null;
        try{
            stmt = con.prepareStatement("INSERT INTO department (Name) VALUES (?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, department.getName());
            int rowsAffected =  stmt.executeUpdate();
            if (rowsAffected > 0){
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()){
                    department.setId(generatedKeys.getInt(1));
                    DB.closeResultSet(generatedKeys);
                }
                else{
                    throw new RuntimeException("Erro ao inserir o departamento no banco de dados");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {}

    }

    @Override
    public void update(Department department) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.prepareStatement("SELECT * FROM department WHERE department.Id = ?");
            stmt.setInt(1,id);
            rs = stmt.executeQuery();
            if (rs.next()){
                return new Department(rs.getInt("Id"),rs.getString("Name"));
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DB.closeStatement(stmt);
        }

    }

}
