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

    @Override
    public List<Department> findAll() {
        return null;

    }
}
