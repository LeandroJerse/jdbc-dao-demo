package application;

import db.DB;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.Connection;

public class Program2 {
    public static void main(String[] args) {
        try {
            DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
            Department department = departmentDao.findById(1);
            System.out.println(department);


        }   catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            DB.closeConnection();
        }




    }
}
