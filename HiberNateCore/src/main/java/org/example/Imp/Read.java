package org.example.Imp;

import org.example.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Read {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Student.class);
        configuration.configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter ID to read: ");
        int id = sc.nextInt();



        Student st = session.get(Student.class, id);

        if (st != null) {
            System.out.println(st);
        } else {
            System.out.println("Student Not Found!");
        }

        session.close();
        sessionFactory.close();
    }

}
