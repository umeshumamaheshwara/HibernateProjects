package org.example.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.entity.Employee;

import java.util.List;
import java.util.Scanner;

public class Update {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Scanner sc = new Scanner(System.in);


        List<Employee> employees = entityManager
                .createQuery("from Employee", Employee.class)
                .getResultList();

        System.out.println("\n===== EMPLOYEE LIST =====");
        for (Employee emp : employees) {
            System.out.println(emp.getEmpId() + "  →  " + emp.getEmpName());
        }





        System.out.println("Enter the Emp Id to Update There Name:");
        Employee employee  = entityManager.find(Employee.class,sc.next());

        System.out.println("Employee Name is:"+ employee.getEmpName());
        employee.setEmpName(sc.next());
        entityManager.merge(employee);
        System.out.println("Updated Name Is:"+ employee.getEmpName());





        transaction.commit();
        entityManager.close();
    }
}
