package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
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

        PreparedStatement stmt = null;
        try{
            stmt = connection.prepareStatement("INSERT INTO seller (Name,Email, BirthDate, BaseSalary," +
                    "DepartmentId) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, seller.getName());
            stmt.setString(2, seller.getEmail());
            stmt.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            stmt.setDouble(4, seller.getBaseSalary());
            stmt.setInt(5, seller.getDepartment().getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0){
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()){
                    seller.setId(generatedKeys.getInt(1));
                    DB.closeResultSet(generatedKeys);
                }
                else{
                    throw new DbException("Erro ao inserir o vendedor no banco de dados");
                }
            }
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(stmt);
        }

    }

    @Override
    public void update(Seller seller) {
        PreparedStatement stmt = null;
        try{
            stmt = connection.prepareStatement("UPDATE seller SET Name = ?,Email=? ,BirthDate = ?, BaseSalary=?" +
                    ",DepartmentId=? WHERE Id=?");

            stmt.setString(1, seller.getName());
            stmt.setString(2, seller.getEmail());
            stmt.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            stmt.setDouble(4, seller.getBaseSalary());
            stmt.setInt(5, seller.getDepartment().getId());
            stmt.setInt(6, seller.getId());

            stmt.executeUpdate();

        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(stmt);
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement stmt = null;
        try{
            stmt = connection.prepareStatement("DELETE FROM seller WHERE Id = ?");
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows == 0){
                System.out.println("Nenhum vendedor foi deletado");
            }

        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(stmt);
        }

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

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT seller.*,department.Name as DepName FROM seller "+
                    "INNER JOIN department ON seller.DepartmentId = department.Id "+
                    "ORDER BY Name");
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
}
