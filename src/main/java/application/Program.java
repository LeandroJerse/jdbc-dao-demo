package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {


        try{
            SellerDao sellerDao = DaoFactory.createSellerDao();
            Seller seller = sellerDao.findById(3);
            System.out.println("=== Teste 1: Seller findById ===");
            System.out.println(seller);
            System.out.println("=== Teste 2: Seller findByDepartment ===");
            Department department = new Department(1, null);
            List<Seller> sellers = sellerDao.findAllByDepartment(department);
            sellers.forEach(System.out::println);


            System.out.println("=== Teste 3: Seller findAll ===");
            sellers.clear();
            sellers = sellerDao.findAll();
            sellers.forEach(System.out::println);

            System.out.println("=== Teste 4: Seller insert ===");
            Seller newseller = new Seller(null,"Greg", "greg@greg", new Date(),4000.00,department);
            sellerDao.insert(newseller);
            System.out.println("Inserted new Id = " + newseller.getId());

            System.out.println("=== Teste 5: Seller update ===");

            seller = sellerDao.findById(1);
            seller.setName("Marta Jhones");
            sellerDao.update(seller);
            System.out.println("Updated seller = " + seller + "\nUtilizando Dao\t" + sellerDao.findById(1));

        }
        catch (Exception e){
            e.printStackTrace();
        }



    }
}
