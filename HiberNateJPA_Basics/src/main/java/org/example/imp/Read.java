package org.example.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.entity.Employee;

import java.util.Scanner;

public class Read {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();



        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the Emp Id to Search:");
        Employee employee  = entityManager.find(Employee.class,sc.nextInt());


        if (employee != null) {
            System.out.println("Employee Name: " + employee.getEmpName());
        } else {
            System.out.println("Employee Not Found!");

            entityManager.close();
            entityManagerFactory.close();

        }
    }
}
