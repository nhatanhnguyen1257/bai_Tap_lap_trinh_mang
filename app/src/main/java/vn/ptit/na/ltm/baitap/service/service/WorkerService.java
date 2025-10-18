/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.ptit.na.ltm.baitap.service.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import vn.ptit.na.ltm.baitap.requests.AddWorker;
import vn.ptit.na.ltm.baitap.requests.SearchWorker;
import vn.ptit.na.ltm.baitap.requests.WorkerLocationDTO;
import vn.ptit.na.ltm.baitap.service.db.ConnectionDB;

/**
 *
 * @author Windows
 */
public class WorkerService {
    
    
    public Boolean create(AddWorker addWorker) {
        StringBuilder sql = new StringBuilder( "INSERT INTO workers( name, birthday, salary, adderss) VALUES (?, ?, ?, "
                + " (select a.id from addresses a where a.province_code = ? and a.district_code = ? and a.ward_code = ? limit 1)) ");
        try
        {
            try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {
            
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);
                LocalDate ld = LocalDate.parse(addWorker.getBirthday(), fmt);
                
                ps.setString(1, addWorker.getFullName());
                ps.setDate(2, java.sql.Date.valueOf(ld));
                ps.setLong(3, addWorker.getSalary()); 
                ps.setString(4, addWorker.getProvinceCode());
                ps.setString(5, addWorker.getDistrictCode());
                ps.setString(6, addWorker.getWardCode());

                int affected = ps.executeUpdate();
                if (affected == 0) {
                    return Boolean.FALSE;
                }
            }
            return Boolean.TRUE;
        } catch(Exception ex) {
            ex.printStackTrace();
            return Boolean.FALSE;
        }
        
    }
    
    public Boolean update(AddWorker addWorker) {
        StringBuilder sql = new StringBuilder( "UPDATE workers " +
                                                "SET name=?, birthday=?, salary=?, adderss=(select a.id from addresses a where a.province_code = ? and a.district_code = ? and a.ward_code = ? limit 1) " +
                                                " WHERE id= ?  ");
        
        try {
            try (Connection con = ConnectionDB.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {


                ps.setString(1, addWorker.getFullName());
                ps.setString(2, addWorker.getBirthday());
                ps.setLong(3, addWorker.getSalary()); 
                ps.setString(4, addWorker.getProvinceCode());
                ps.setString(5, addWorker.getDistrictCode());
                ps.setString(6, addWorker.getWardCode());
                ps.setLong(6, addWorker.getId());

                int affected = ps.executeUpdate();
                if (affected == 0) {
                    return Boolean.FALSE;
                }
            }

            return Boolean.TRUE;            
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        
    }
    
    public Boolean delete(Long idWorker) {
        StringBuilder sql = new StringBuilder( " DELETE FROM workers WHERE id = ?  ");
        try {
            try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            
                ps.setLong(1, idWorker);

                int affected = ps.executeUpdate();
                if (affected == 0) {
                    return Boolean.FALSE;
                }
            }
            return Boolean.TRUE;
        } catch(Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
    
    public List<WorkerLocationDTO> search(SearchWorker payload)  {
        List<WorkerLocationDTO> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select w.id, w.name, w.salary, to_char(w.birthday, 'YYYY-MM-DD') birthday, p.name provinceName, d.name districtName, wa.name wardName from workers w ");
        sql.append( "\n join addresses a on a.id = w.adderss ");
        sql.append(  "\n join provinces p on p.code = a.province_code ");
        sql.append(  "\n join districts d on d.code = a.district_code ");
        sql.append(  "\n join wards wa on wa.code = a.ward_code ");
        sql.append(  "\n where (? is null or w.name like ? ESCAPE '\\' ) ");
        sql.append("\n and (? = -1 or w.salary = ? )");
        sql.append("\n and (? is null or w.birthday >= ? )");
        sql.append("\n and (? is null or w.birthday < ? )");
        sql.append("\n and (? is null or p.code = ? )");
        sql.append("\n and (? is null or d.code = ? )");
        sql.append("\n and (? is null or wa.code = ? )");
        
        
        try (Connection conn = ConnectionDB.getConnection(); 
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            if (payload.getFullName() == null) {
                ps.setString(1, payload.getFullName());
                ps.setString(2, payload.getFullName()); 
            } else {
                ps.setString(1, "%"+ payload.getFullName()+"%");
                ps.setString(2,  "%"+ payload.getFullName()+"%");
            }
            
            if (payload.getSalary() == null) {
                ps.setLong(3, -1L);
                ps.setLong(4, -1L);
            } else {
                ps.setLong(3, payload.getSalary() );
                ps.setLong(4, payload.getSalary());
            }
            
            String temp = "-01-01";
            if (payload.getBirthStart() == null) {
                ps.setDate(5, null);
                ps.setDate(6, null);
            } else {
                ps.setInt(5, payload.getBirthStart());
                ps.setDate(6, Date.valueOf( Year.of(payload.getBirthStart().intValue()).atDay(1)));
            }
            
            if (payload.getBirthEnd()== null) {
                ps.setDate(7, null);
                ps.setDate(8, null);
            } else {
                ps.setInt(7, payload.getBirthStart());
                ps.setDate(8, Date.valueOf( Year.of(payload.getBirthStart().intValue() + 1).atDay(1)));
            }
            
            ps.setString(9, payload.getProvinceCode());
            ps.setString(10, payload.getProvinceCode());
            
            ps.setString(11, payload.getDistrictCode());
            ps.setString(12, payload.getDistrictCode());
            
            ps.setString(13, payload.getWardCode());
            ps.setString(14, payload.getWardCode());
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new WorkerLocationDTO(
                    rs.getLong("id"),
                    rs.getString("name"),
             rs.getString("birthday"),
                    rs.getBigDecimal("salary"), // hoặc rs.getLong("salary")
                    rs.getString("provinceName"),
                    rs.getString("districtName"),
                    rs.getString("wardName")
            ));
                }
            }
        } catch(Exception e ) {
            e.printStackTrace();
        }
        return list;
    }
    
    public AddWorker detail(Long idWorker) {
        AddWorker detail = new AddWorker();
        StringBuilder sql = new StringBuilder();
        sql.append("select w.id, w.name, w.salary, to_char(w.birthday, 'DD/MM/YYYY') birthday, p.code provinceCode, d.code districtCode, wa.code wardCode from workers w ");
        sql.append( "\n join addresses a on a.id = w.adderss ");
        sql.append(  "\n join provinces p on p.code = a.province_code ");
        sql.append(  "\n join districts d on d.code = a.district_code ");
        sql.append(  "\n join wards wa on wa.code = a.ward_code ");
        sql.append(  "\n where w.id = ? ");
        
        
        try (Connection conn = ConnectionDB.getConnection(); 
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            ps.setLong(1, idWorker);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new AddWorker(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("birthday"),
                    rs.getBigDecimal("salary").longValue(), // hoặc rs.getLong("salary")
                    rs.getString("provinceCode"),
                    rs.getString("districtCode"),
                    rs.getString("wardCode")
                    );
                }
            }
        } catch(Exception e ) {
            e.printStackTrace();
        }
        return detail;
    }
    
}
