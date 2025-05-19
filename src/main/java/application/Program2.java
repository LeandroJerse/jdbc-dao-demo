package application;

import db.DB;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.Connection;
import java.util.Scanner;

public class Program2 {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("== Teste 1: findById ==");
            DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
            Department department = departmentDao.findById(1);
            System.out.println(department);

            /**
            System.out.println("== Teste 2: insert ==");
            System.out.println("Digite o nome do departamento:");
            String name = sc.nextLine();
            department = new Department(null, name);
            departmentDao.insert(department);
            System.out.println("Departamento armagenado no computador: "+department+"\n" +
                    "departamento no banco de dados: "+departmentDao.findById(department.getId())+"\n");
             **/

            System.out.println("== Teste 3: findALL ==");
            departmentDao.findAll().forEach(System.out::println);


            /**
            System.out.println("== Teste 4: Delete ==");
            System.out.println("Digite o id do departamento a ser deletado:");
            int id = sc.nextInt();
            departmentDao.deleteById(id);
             **/

            System.out.println("== Teste 5: update ==");
            System.out.println("Digite o id do departamento a ser atualizado:");
            int newId = sc.nextInt();
            System.out.println("Digite o novo nome do departamento:");
            String newName = sc.next();
            Department departmentUpdate = new Department(newId, newName);
            departmentDao.update(departmentUpdate);
            System.out.println("Atualizado: "+departmentDao.findById(newId));

            sc.close();
        }   catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            DB.closeConnection();
        }




    }
}
