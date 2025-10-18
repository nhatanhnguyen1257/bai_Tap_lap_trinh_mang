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
public class SearchWorker implements Serializable {
    
    private String fullName;
    private Integer birthStart;
    private Integer birthEnd;
    private Long salary;
    private String provinceCode;
    private String districtCode;
    private String wardCode;

    public SearchWorker() {
    }

    public SearchWorker(String fullName, Integer birthStart, Integer birthEnd, Long salary, String provinceCode, String districtCode, String wardCode) {
        this.fullName = fullName;
        this.birthStart = birthStart;
        this.birthEnd = birthEnd;
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

    public Integer getBirthStart() {
        return birthStart;
    }

    public void setBirthStart(Integer birthStart) {
        this.birthStart = birthStart;
    }

    public Integer getBirthEnd() {
        return birthEnd;
    }

    public void setBirthEnd(Integer birthEnd) {
        this.birthEnd = birthEnd;
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
    
}
