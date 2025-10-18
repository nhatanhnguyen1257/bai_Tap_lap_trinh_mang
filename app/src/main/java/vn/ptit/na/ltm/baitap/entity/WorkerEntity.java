/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.ptit.na.ltm.baitap.entity;

import java.io.Serializable;

/**
 *
 * @author Windows
 */
public class WorkerEntity implements Serializable {
       
    private Long id;
    private String name;
    private String birthDay;
    private Long salary;
    private Long address;

    public WorkerEntity() {
    }

    public WorkerEntity(Long id, String name, String birthDay, Long salary, Long address) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
        this.salary = salary;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Long getAddress() {
        return address;
    }

    public void setAddress(Long address) {
        this.address = address;
    }
    
}
