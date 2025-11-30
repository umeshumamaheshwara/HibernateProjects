package org.example.Service.IMP;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.Service.EmployeeService;
import org.example.entity.Employee;
import org.example.excptions.EmployeeNotFoundException;
import org.example.excptions.InvalidInputException;
import org.example.excptions.PhoneNumberNotValidException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class EmployeeImplementation implements EmployeeService {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
    Scanner sc = new Scanner(System.in);

    @Override
    public void add() {

        EntityManager entityManager  = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try{
            System.out.println("Enter the Employee Name:");
            String name =  sc.next();

            System.out.println("Enter the Employee Phone No:");
            String phoneNumber =  sc.next();

            if (!phoneNumber.matches("\\d{10}")) {
                throw new PhoneNumberNotValidException("Phone number must be exactly 10 digits!");
            }

            System.out.println("Enter the Employee Salary:");
            Double sal =  sc.nextDouble();
            sc.nextLine();

            System.out.println("Enter the Employee Dept:");
            String deptname =  sc.next();

            Employee employee = new Employee(name, phoneNumber, sal, deptname);
            entityManager.persist(employee);

            transaction.commit();
            entityManager.close();

        }
        catch (PhoneNumberNotValidException e) {
            System.out.println("Validation Error: " + e.getMessage());
            transaction.rollback();

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
            transaction.rollback();

        } finally {
            entityManager.close();
        }
    }

//    @Override
//    public void read() {
//
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//
//        try {
//            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
//
//            Root<Employee> root = cq.from(Employee.class);
//            cq.select(root);
//
//            List<Employee> employeeList =
//                    entityManager.createQuery(cq)
//                            .setHint("org.hibernate.cacheable", true)   // <-- REQUIRED!
//                            .getResultList();
//
//            for (Employee e : employeeList) {
//                System.out.println(e.getEmpID() + " → " + e.getEmpName());
//            }
//
//        } finally {
//            entityManager.close();
//        }
//    }


    @Override
    public void read() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> root = cq.from(Employee.class);
            cq.select(root);

            // IMPORTANT: Enable Query Cache
            List<Employee> employeeList = entityManager
                    .createQuery(cq)
                    .setHint("org.hibernate.cacheable", true)
                    .setHint("org.hibernate.cacheRegion", "query.employeeList")
                    .getResultList();

            if (employeeList.isEmpty()) {
                System.out.println("No employees found in database.");
            } else {
                System.out.println("\n===== ALL EMPLOYEES =====");
                for (Employee e : employeeList) {
                    System.out.println(e.getEmpID() + " → " + e.getEmpName() + "->" + e.getEmpPhone()+ "->" +e.getEmpSal()+ "->" + e.getDname());
                }
            }

        } catch (Exception e) {
            System.out.println("Error while fetching employees: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }


    @Override
    public void update() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {

            System.out.println("Enter Employee ID to Update:");
            int id = sc.nextInt();

            Employee e1 = entityManager.find(Employee.class, id);

            // If employee not found → throw custom exception
            if (e1 == null) {
                throw new EmployeeNotFoundException("Employee ID " + id + " does not exist!");
            }

            // Show current details
            System.out.println("Employee Name: " + e1.getEmpName());
            System.out.println("Employee Salary: " + e1.getEmpSal());
            System.out.println("Employee PhoneNo: " + e1.getEmpPhone());
            System.out.println("Employee DeptName: " + e1.getDname());

            System.out.println("\nWhat do you want to update?");
            System.out.println("1. Name\n2. Salary\n3. Phone\n4. Department\n5. Exit");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("Enter New Name:");
                    e1.setEmpName(sc.next());
                    break;

                case 2:
                    System.out.println("Enter New Salary:");
                    e1.setEmpSal(sc.nextDouble());
                    break;

                case 3:
                    System.out.println("Enter New Phone:");
                    String newPhone = sc.next();

                    // Phone validation
                    if (!newPhone.matches("\\d{10}")) {
                        throw new PhoneNumberNotValidException("Phone number must be 10 digits!");
                    }

                    e1.setEmpPhone(newPhone);
                    break;

                case 4:
                    System.out.println("Enter New Department Name:");
                    e1.setDname(sc.next());
                    break;

                case 5:
                    System.out.println("Exited Update Menu...");
                    transaction.rollback();
                    entityManager.close();
                    return;

                default:
                    System.out.println("Invalid Choice!");
                    transaction.rollback();
                    entityManager.close();
                    return;
            }

            entityManager.merge(e1);
            transaction.commit();
            System.out.println("Employee Updated Successfully!");

        } catch (EmployeeNotFoundException | PhoneNumberNotValidException ex) {
            System.out.println("Error: " + ex.getMessage());
            transaction.rollback();

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
            transaction.rollback();

        } finally {
            entityManager.close();
        }
    }


    @Override
    public void delete() {

        EntityManager entityManager  = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try{
            Employee e1 =  entityManager.find(Employee.class,sc.nextInt());

            if(e1==null){
                throw new EmployeeNotFoundException("Given Id Is Not Found" + e1 );
            }
            entityManager.remove(e1);
        } catch (EmployeeNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void display() {

//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//
//            System.out.println("\nEnter the Employee ID to Search:");
//            int id = sc.nextInt();
//
//
//            Employee e1 = entityManager.find(Employee.class, id);
//        try {
//            if (e1 == null) {
//                throw new EmployeeNotFoundException("Employee ID " + id + " does not exist!");
//            }
//
//            System.out.println("\n=== EMPLOYEE DETAILS ===");
//            System.out.println("Name      : " + e1.getEmpName());
//            System.out.println("Salary    : " + e1.getEmpSal());
//            System.out.println("Phone No  : " + e1.getEmpPhone());
//            System.out.println("Department: " + e1.getDname());
//
//        }catch (EmployeeNotFoundException ex) {
//            System.out.println("Error: " + ex.getMessage());
//
//        } catch (Exception e) {
//            System.out.println("Not a valid ID format: " + e.getMessage());
//
//        } finally {
//            entityManager.close();
//        }

        EntityManager em = entityManagerFactory.createEntityManager();

        try {
            while (true) {
                System.out.println("\n===== SEARCH EMPLOYEE =====");
                System.out.println("1. Search By ID");
                System.out.println("2. Search By Name");
                System.out.println("3. Search By Phone");
                System.out.println("4. Search By Department");
                System.out.println("5. Search By Salary Range");
                System.out.println("6. Exit Search Menu");
                System.out.print("Enter Your Choice: ");

                int choice = sc.nextInt();

                try {
                    switch (choice) {

                        // ====================== 1. SEARCH BY ID (uses L2 Cache automatically) ======================
                        case 1:
                            System.out.print("Enter Employee ID: ");
                            int id = sc.nextInt();

                            Employee emp = em.find(Employee.class, id);  // L2 cache hit

                            if (emp != null) {
                                System.out.println(emp);
                            } else {
                                throw new EmployeeNotFoundException("Employee ID " + id + " not found");
                            }
                            break;

                        // ====================== 2. SEARCH BY NAME (Enable Query Cache) ======================
                        case 2:
                            System.out.print("Enter Name: ");
                            String name = sc.next();

                            if (!name.matches("^[A-Za-z]+$")) {
                                throw new InvalidInputException("Invalid name! Name must contain only alphabets.");
                            }

                            List<Employee> list1 = em.createQuery(
                                            "SELECT e FROM Employee e WHERE e.empName = :name", Employee.class)
                                    .setParameter("name", name)
                                    .setHint("org.hibernate.cacheable", true)  // QUERY CACHE
                                    .getResultList();



                            if (!list1.isEmpty()) {
                                System.out.println(list1);
                            } else {
                                throw new EmployeeNotFoundException("No employee found with name: " + name);
                            }
                            break;

                        // ====================== 3. SEARCH BY PHONE ======================
                        case 3:
                            System.out.print("Enter Phone: ");
                            String phone = sc.next();

                            if (!phone.matches("\\d{10}")){
                                throw new PhoneNumberNotValidException("Phone must be 10 digits");}

                            List<Employee> list2 = em.createQuery(
                                            "SELECT e FROM Employee e WHERE e.empPhone = :phone", Employee.class)
                                    .setParameter("phone", phone)
                                    .setHint("org.hibernate.cacheable", true)
                                    .getResultList();

                            if (!list2.isEmpty()) {
                                System.out.println(list2);
                            } else {
                                throw new EmployeeNotFoundException("No employee found with phone: " + phone);
                            }
                            break;

                        // ====================== 4. SEARCH BY DEPT ======================
                        case 4:
                            System.out.print("Enter Department: ");
                            String dept = sc.next();

                            if (!dept.matches("^[A-Za-z]+$")) {
                                throw new InvalidInputException("Invalid dept! Name must contain only alphabets.");
                            }

                            List<Employee> list3 = em.createQuery(
                                            "SELECT e FROM Employee e WHERE e.dname = :dept", Employee.class)
                                    .setParameter("dept", dept)
                                    .setHint("org.hibernate.cacheable", true)
                                    .getResultList();

                            if (!list3.isEmpty()) {
                                System.out.println(list3);
                            } else {
                                throw new EmployeeNotFoundException("No employee in dept: " + dept);
                            }
                            break;

                        // ====================== 5. SALARY RANGE ======================
                        case 5:
                            System.out.print("Enter the Salary: ");
                            double sal;

                            try {
                                sal = sc.nextDouble();   // may throw InputMismatchException
                            } catch (InputMismatchException ex) {
                                sc.nextLine();  // clear scanner buffer
                                throw new InvalidInputException("Invalid input! Salary must be a valid number.");
                            }

                            // NEGATIVE SALARY CHECK
                            if (sal < 0) {
                                throw new InvalidInputException("Salary cannot be negative!");
                            }

                            List<Employee> list4 = em.createQuery(
                                            "SELECT e FROM Employee e WHERE e.empSal BETWEEN :min AND :max", Employee.class)
                                    .setParameter("min", sal)
                                    .setParameter("max", Double.MAX_VALUE)  // search above salary
                                    .setHint("org.hibernate.cacheable", true)
                                    .getResultList();

                            if (!list4.isEmpty()) {
                                System.out.println(list4);
                            } else {
                                throw new EmployeeNotFoundException("No employee found with salary >= " + sal);
                            }
                            break;

                        case 6:
                            System.out.println("Exiting Search Menu...");
                            return;

                        default:
                            System.out.println("Invalid choice. Try again.");
                    }

                } catch (EmployeeNotFoundException | PhoneNumberNotValidException  | InvalidInputException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

        } finally {
                em.close();
            }
        }
}

