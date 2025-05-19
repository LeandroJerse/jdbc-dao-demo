package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT seller.*,department.Name as DepName FROM seller "+
                    "INNER JOIN department ON seller.DepartmentId = department.Id WHERE seller.Id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()){
                Department department = instanciateDepartment(rs);
                return instanceSeller(rs, department);

            }
            return null;
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(rs);

        }
    }

    public List<Seller> findAllByDepartment(Department department) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT seller.*,department.Name as DepName FROM seller "+
                    "INNER JOIN department ON seller.DepartmentId = department.Id WHERE DepartmentId = ? "+
                    "ORDER BY Name");
            stmt.setInt(1, department.getId());
            rs = stmt.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> departments = new HashMap<>();

            while (rs.next()){


                Department dep = departments.get(rs.getInt("DepartmentId"));
                if (dep == null){
                    dep = instanciateDepartment(rs);
                    departments.put(rs.getInt("DepartmentId"), dep);
                }


                Seller seller = instanceSeller(rs,dep);
                sellers.add(seller);

            }
            return sellers;
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(rs);
        }
    }

    private Seller instanceSeller(ResultSet rs, Department department) throws SQLException {
        return new Seller(rs.getInt("Id"),rs.getString("Name"),
                rs.getString("Email"),rs.getDate("BirthDate"),
                rs.getDouble("BaseSalary"),department);
    }

    private Department instanciateDepartment(ResultSet rs) throws SQLException {
        Department department = new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
    return department;
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}
