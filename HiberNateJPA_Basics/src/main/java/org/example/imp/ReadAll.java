package org.example.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.entity.Employee;

import java.util.List;

public class ReadAll {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();




        //------------------------Using HQL-------------------------------------
        List<Employee> list =
                entityManager.createQuery("from Employee", Employee.class).getResultList();



        //-------------------Using Criteria API---------------------------------

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> root = cq.from(Employee.class);
        cq.select(root);

        List<Employee> list1 = entityManager.createQuery(cq).getResultList();

        //--------------------Using Native SQL-----------------------------------

        List<Employee> list2 =
                entityManager.createNativeQuery("SELECT * FROM employee", Employee.class)
                        .getResultList();


        System.out.println(list2);



    }
}
