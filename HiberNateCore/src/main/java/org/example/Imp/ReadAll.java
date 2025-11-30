package org.example.Imp;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ReadAll {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Student.class);
        configuration.configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();


        //------------------------Using HQL-------------------------------------
        List<Student> list =
                session.createQuery("from Student", Student.class).list();



        //-------------------Using Criteria API---------------------------------
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        Root<Student> root = cq.from(Student.class);
        cq.select(root);

        List<Student> list1 = session.createQuery(cq).getResultList();

        //--------------------Using Native SQL-----------------------------------

        List<Student> list2 =
                session.createNativeQuery("SELECT * FROM student", Student.class)
                        .list();

        System.out.println(list2);


    }
}
