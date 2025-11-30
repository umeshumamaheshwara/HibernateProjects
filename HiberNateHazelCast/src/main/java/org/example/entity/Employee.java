package org.example.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empID;

    @Column(nullable = false)
    private String empName;

    @Column(unique = true, nullable = false)
    private String empPhone;

    @Column(nullable = false)
    private Double empSal;

    @Column(nullable = false)
    private String dname;

    public Employee() {}

    public Employee(String empName, String empPhone, Double empSal, String dname) {
        this.empName = empName;
        this.empPhone = empPhone;
        this.empSal = empSal;
        this.dname = dname;
    }




    // Getters & Setters


    public Integer getEmpID() {
        return empID;
    }

    public void setEmpID(Integer empID) {
        this.empID = empID;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public Double getEmpSal() {
        return empSal;
    }

    public void setEmpSal(Double empSal) {
        this.empSal = empSal;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empID=" + empID +
                ", empName='" + empName + '\'' +
                ", empPhone='" + empPhone + '\'' +
                ", empSal=" + empSal +
                ", deptNo='" + dname + '\'' +
                '}';
    }
}
