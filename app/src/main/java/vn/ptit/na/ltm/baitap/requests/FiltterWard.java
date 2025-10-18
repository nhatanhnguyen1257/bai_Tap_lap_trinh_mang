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
public class FiltterWard implements Serializable {
    
    private String provinceCode;
    private String districtCode;

    public FiltterWard() {
    }

    public FiltterWard(String provinceCode, String districtCode) {
        this.provinceCode = provinceCode;
        this.districtCode = districtCode;
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
    
    
}
