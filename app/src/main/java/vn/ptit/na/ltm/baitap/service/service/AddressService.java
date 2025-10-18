/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.ptit.na.ltm.baitap.service.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vn.ptit.na.ltm.baitap.entity.DistrictEntity;
import vn.ptit.na.ltm.baitap.entity.ProvinceEntity;
import vn.ptit.na.ltm.baitap.entity.WardEntity;
import vn.ptit.na.ltm.baitap.service.db.ConnectionDB;

/**
 *
 * @author Windows
 */
public class AddressService {

    public List<ProvinceEntity> getLstProvince() throws SQLException {
        List<ProvinceEntity> lst = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection(); 
                PreparedStatement ps = conn.prepareStatement("SELECT code, name FROM provinces "); 
                ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lst.add(mapProvince(rs));
            }
        }
        
        return lst;
    }
    
    public List<DistrictEntity> getLstDistricByProvinceCode(String provinceCode) throws SQLException {
        List<DistrictEntity> lst = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection(); 
                PreparedStatement ps = conn.prepareStatement("select DISTINCT d.code, d.name from provinces p \n"
                                                                + "join addresses a on p.code = a.province_code and p.code = ? \n"
                                                                + "join districts d on d.code = a.district_code ")) {
            ps.setString(1, provinceCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lst.add(mapDistrict(rs));
                }
            }
        }

        return lst;
    }
    
    
    public List<WardEntity> getLstWardByProvinceCodeAndDistrictCode(String provinceCode, String districtCode) throws SQLException {
        List<WardEntity> lst = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection(); 
                PreparedStatement ps = conn.prepareStatement("select DISTINCT w.code, w.name from wards w \n"
                                                                + "join addresses a on a.province_code = ? and a.district_code = ? and w.code = a.ward_code ")) {
            ps.setString(1, provinceCode);
            ps.setString(2, districtCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lst.add(mapWard(rs));
                }
            }
        }
        return lst;
    }
    
     private WardEntity mapWard(ResultSet rs) throws SQLException {
        WardEntity ward = new WardEntity();
        ward.setCode(rs.getString("code"));
        ward.setName(rs.getString("name"));
        return ward;
    }
    
    private DistrictEntity mapDistrict(ResultSet rs) throws SQLException {
        DistrictEntity distrcict = new DistrictEntity();
        distrcict.setCode(rs.getString("code"));
        distrcict.setName(rs.getString("name"));
        return distrcict;
    }

    private ProvinceEntity mapProvince(ResultSet rs) throws SQLException {
        ProvinceEntity province = new ProvinceEntity();
        province.setCode(rs.getString("code"));
        province.setName(rs.getString("name"));
        return province;
    }

}
