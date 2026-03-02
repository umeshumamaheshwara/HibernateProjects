package org.example;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Student.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Scanner sc = new Scanner(System.in);

        boolean f = true;

        while (f) {

            System.out.println("\n1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Fetch All Students");
            System.out.println("5. Find Student By ID");
            System.out.println("6. Exit");

            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {

                // ---------------- ADD ----------------
                case 1: {

                    Session session = sessionFactory.openSession();
                    Transaction tx = session.beginTransaction();

                    try {
                        System.out.print("Enter Student Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Phone Number: ");
                        String phone = sc.nextLine();

                        System.out.print("Enter Address: ");
                        String address = sc.nextLine();

                        Student s = new Student();
                        s.setStudentName(name);
                        s.setPhoneNumber(phone);
                        s.setAddress(address);

                        session.persist(s);

                        tx.commit();
                        System.out.println("Student Added Successfully!");

                    } catch (Exception e) {
                        tx.rollback();
                        e.printStackTrace();
                    } finally {
                        session.close();
                    }
                    break;
                }

                // ---------------- UPDATE ----------------
                case 2: {

                    Session session = sessionFactory.openSession();
                    Transaction tx = session.beginTransaction();

                    try {
                        System.out.print("Enter Student ID to Update: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        Student s = session.get(Student.class, id);

                        if (s != null) {

                            System.out.print("Enter New Name: ");
                            s.setStudentName(sc.nextLine());

                            System.out.print("Enter New Phone: ");
                            s.setPhoneNumber(sc.nextLine());

                            System.out.print("Enter New Address: ");
                            s.setAddress(sc.nextLine());

                            // No update() needed — Hibernate auto-detects changes

                            tx.commit();
                            System.out.println("Student Updated Successfully!");

                        } else {
                            System.out.println("Student Not Found!");
                            tx.rollback();
                        }

                    } catch (Exception e) {
                        tx.rollback();
                        e.printStackTrace();
                    } finally {
                        session.close();
                    }
                    break;
                }

                // ---------------- DELETE ----------------
                case 3: {

                    Session session = sessionFactory.openSession();
                    Transaction tx = session.beginTransaction();

                    try {
                        System.out.print("Enter Student ID to Delete: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        Student s = session.get(Student.class, id);

                        if (s != null) {
                            session.remove(s);
                            tx.commit();
                            System.out.println("Student Deleted Successfully!");
                        } else {
                            System.out.println("Student Not Found!");
                            tx.rollback();
                        }

                    } catch (Exception e) {
                        tx.rollback();
                        e.printStackTrace();
                    } finally {
                        session.close();
                    }
                    break;
                }

                // ---------------- FETCH ----------------
                case 4: {

                    Session session = sessionFactory.openSession();
                    Transaction tx = session.beginTransaction();

                    try {

                        // HQL
                        System.out.println("\n===== HQL =====");
                        List<Student> list =
                                session.createQuery("from Student", Student.class).list();

                        list.forEach(s -> System.out.println(
                                s.getId() + " | " +
                                        s.getStudentName() + " | " +
                                        s.getPhoneNumber() + " | " +
                                        s.getAddress()));

                        // Criteria API
                        System.out.println("\n===== Criteria API =====");
                        CriteriaBuilder cb = session.getCriteriaBuilder();
                        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
                        Root<Student> root = cq.from(Student.class);
                        cq.select(root);

                        List<Student> list1 = session.createQuery(cq).getResultList();

                        list1.forEach(s -> System.out.println(
                                s.getId() + " | " +
                                        s.getStudentName() + " | " +
                                        s.getPhoneNumber() + " | " +
                                        s.getAddress()));

                        // Native SQL
                        System.out.println("\n===== Native SQL =====");
                        List<Student> list2 =
                                session.createNativeQuery("SELECT * FROM student", Student.class)
                                        .list();

                        list2.forEach(s -> System.out.println(
                                s.getId() + " | " +
                                        s.getStudentName() + " | " +
                                        s.getPhoneNumber() + " | " +
                                        s.getAddress()));

                        tx.commit();

                    } catch (Exception e) {
                        tx.rollback();
                        e.printStackTrace();
                    } finally {
                        session.close();
                    }
                    break;
                }

                // ---------------- FIND BY ID ----------------
                case 5: {

                    Session session = sessionFactory.openSession();
                    Transaction tx = session.beginTransaction();

                    try {
                        System.out.print("Enter Student ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        Student s = session.get(Student.class, id);

                        if (s != null) {
                            System.out.println("\nStudent Found:");
                            System.out.println("---------------------------");
                            System.out.println("ID      : " + s.getId());
                            System.out.println("Name    : " + s.getStudentName());
                            System.out.println("Phone   : " + s.getPhoneNumber());
                            System.out.println("Address : " + s.getAddress());
                        } else {
                            System.out.println("Student not found!");
                        }

                        tx.commit();

                    } catch (Exception e) {
                        tx.rollback();
                        e.printStackTrace();
                    } finally {
                        session.close();
                    }
                    break;
                }

                // ---------------- EXIT ----------------
                case 6:
                    f = false;
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }
        }

        sessionFactory.close();
        System.out.println("Application Closed.");
    }
}