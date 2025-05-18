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
import java.util.List;

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
                Department department = new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
                Seller seller = new Seller(rs.getInt("Id"),rs.getString("Name"),
                        rs.getString("Email"),rs.getDate("BirthDate"),
                        rs.getDouble("BaseSalary"),department);
                return seller;

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

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}
