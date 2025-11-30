package org.example.Imp;

import org.example.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Delete {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Student.class);
        configuration.configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction transaction= session.beginTransaction();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter ID TO Delete: ");
        int id = sc.nextInt();
        sc.nextLine();


        Student st = session.get(Student.class, id);

        if (st != null) {
//            session.delete(st);        //Depricated
            session.remove(st);
            System.out.println("Deleted Successfully!");
        } else {
            System.out.println("Student Not Found!");
        }

        transaction.commit();
        session.close();
        sessionFactory.close();





    }
}
