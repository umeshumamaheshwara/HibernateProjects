package org.example;

import org.example.Service.IMP.EmployeeImplementation;
import org.example.Service.EmployeeService;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        EmployeeService service = new EmployeeImplementation();

        boolean f = true;

        while (f) {

            System.out.println("\n===== EMPLOYEE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Find The Employee");
            System.out.println("6. Exit");
            System.out.print("Enter Your Choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    service.add();
                    break;

                case 2:
                    service.read();
                    break;

                case 3:
                    service.update();
                    break;

                case 4:
                    service.delete();
                    break;

                case 5:
                    service.display();
                    break;
                case 6:


                    System.out.println("Exiting the System... Goodbye!");
                    f = false;
                    break;
                default:
                    System.out.println("Invalid Choice! Please Enter Between 1 to 5.");
            }
        }
        sc.close();
    }
}
