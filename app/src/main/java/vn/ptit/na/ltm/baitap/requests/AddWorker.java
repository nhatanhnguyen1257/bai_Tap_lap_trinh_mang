/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.ptit.na.ltm.baitap.requests;

import java.io.Serializable;

/**
 *
 * @author Windows
 */
public class AddWorker implements Serializable{
    private Long id;
    private String fullName;
    private String birthday;
    private Long salary;
    private String provinceCode;
    private String districtCode;
    private String wardCode;

    public AddWorker() {
    }

    public AddWorker(Long id, String fullName, String birthday, Long salary, String provinceCode, String districtCode, String wardCode) {
        this.id = id;
        this.fullName = fullName;
        this.birthday = birthday;
        this.salary = salary;
        this.provinceCode = provinceCode;
        this.districtCode = districtCode;
        this.wardCode = wardCode;
    }
    
    

    public AddWorker(String fullName, String birthday, Long salary, String provinceCode, String districtCode, String wardCode) {
        this.fullName = fullName;
        this.birthday = birthday;
        this.salary = salary;
        this.provinceCode = provinceCode;
        this.districtCode = districtCode;
        this.wardCode = wardCode;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public Long getId() {
        return id;
    }
    
    
}
