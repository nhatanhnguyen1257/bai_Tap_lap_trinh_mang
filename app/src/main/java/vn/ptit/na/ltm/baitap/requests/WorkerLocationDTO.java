/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.ptit.na.ltm.baitap.requests;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Windows
 */
public class WorkerLocationDTO implements Serializable {
    private Long id;
    private String name;
    private String birthday;
    private java.math.BigDecimal salary; // hoặc Long
    private String provinceName;
    private String districtName;
    private String wardName;

    public WorkerLocationDTO() {}

    public WorkerLocationDTO(Long id, String name, String birthday, BigDecimal salary, String provinceName, String districtName, String wardName) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.salary = salary;
        this.provinceName = provinceName;
        this.districtName = districtName;
        this.wardName = wardName;
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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    
    
}
