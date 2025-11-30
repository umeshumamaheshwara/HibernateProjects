package org.example.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.entity.Employee;

import java.util.Scanner;

public class Insert {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();


        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the Employee ID:");
        Integer id =  sc.nextInt();


        System.out.println("Enter the Employee Name:");
        String name =  sc.next();
        Employee employee  = new Employee(id,name);


        entityManager.persist(employee);
        transaction.commit();
        entityManager.close();





    }
}
