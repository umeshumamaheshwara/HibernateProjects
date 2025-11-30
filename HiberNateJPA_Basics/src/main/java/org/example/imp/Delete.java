package org.example.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.entity.Employee;

import java.util.List;
import java.util.Scanner;

public class Delete {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();



        List<Employee> employees = entityManager
                .createQuery("from Employee", Employee.class)
                .getResultList();

        System.out.println("\n===== EMPLOYEE LIST =====");
        for (Employee emp : employees) {
            System.out.println(emp.getEmpId() + "  →  " + emp.getEmpName());
        }


        Scanner sc = new Scanner(System.in);


        System.out.println("Enter Employee ID to Delete:");
        int id = sc.nextInt();

        Employee employee = entityManager.find(Employee.class, id);

        if (employee != null) {
            entityManager.remove(employee);
            System.out.println("Employee Deleted Successfully!");
        } else {
            System.out.println("Employee Not Found!");
        }

        transaction.commit();
        entityManager.close();
    }
}
