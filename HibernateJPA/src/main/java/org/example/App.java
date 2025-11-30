package org.example;

import jakarta.persistence.*;
import org.example.entity.Employee;

import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");


        boolean f =  true;
        while(f){

            System.out.println("1. ADD \n 2.Fetch \n 3.Update \n 4.Delete \n 5.Exit ");
            Scanner sc  = new Scanner(System.in);
            int option =  sc.nextInt();
            switch (option){
                case 1: {
                    EntityManager entityManager = entityManagerFactory.createEntityManager();
                    EntityTransaction transaction = entityManager.getTransaction();
                    transaction.begin();
                    System.out.println("Enter the Employee Name:");
                    String name =  sc.next();
                    Employee employee  = new Employee(name);
                    entityManager.persist(employee);
                    transaction.commit();
                    entityManager.close();

                }
                break;
                case 2:  {
                    EntityManager entityManager = entityManagerFactory.createEntityManager();
                    System.out.println("Enter the Emp Id to Search:");
                    Employee employee  = entityManager.find(Employee.class,sc.nextInt());
                    System.out.println("Employee Name is: "+ employee.getEmpName());
                }
                break;
                case 3:{
                    EntityManager entityManager = entityManagerFactory.createEntityManager();
                    EntityTransaction transaction = entityManager.getTransaction();
                    transaction.begin();
                    System.out.println("Enter the Emp Id to Update There Name:");
                    Employee employee  = entityManager.find(Employee.class,sc.next());
                    System.out.println("Employee Name is:"+ employee.getEmpName());
                    employee.setEmpName(sc.next());
                    entityManager.merge(employee);
                    System.out.println("Updated Name Is:"+ employee.getEmpName());
                    transaction.commit();
                    entityManager.close();
                }
                break;
                case 4:{

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
                break;
                case 5:{
                    System.out.println("Exiting the system.....");
                    entityManagerFactory.close();
                    System.exit(0);
                }
                break;
                default:{
                    System.out.println("Invalid Choice....");
                }
                break;

            }


        }
    }
}
