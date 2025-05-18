package application;

import model.entities.Department;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        Department department = new Department(1, "Books");
        Seller seller = new Seller(21,"bob", "bob@bob", new Date(), 1000.0, department);
        System.out.println(department);
        System.out.println(seller);
    }
}
