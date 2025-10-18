/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.ptit.na.ltm.baitap.client.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JComboBox;
import vn.ptit.na.ltm.baitap.client.tcp.ClientTCP;
import vn.ptit.na.ltm.baitap.entity.DistrictEntity;
import vn.ptit.na.ltm.baitap.entity.ProvinceEntity;
import vn.ptit.na.ltm.baitap.entity.WardEntity;
import vn.ptit.na.ltm.baitap.requests.BaseRequest;
import vn.ptit.na.ltm.baitap.requests.FiltterWard;
import vn.ptit.na.ltm.baitap.requests.TypeEnumRequest;

/**
 *
 * @author Windows
 */
public class CommomService {
    
    
   private List<ProvinceEntity> getAllProvince() {
       ClientTCP client = new ClientTCP();
       BaseRequest baseRequest = new BaseRequest(TypeEnumRequest.PROVINE, new String());
       List<ProvinceEntity> data = (List<ProvinceEntity>)client.sendServer(baseRequest);
       return data == null ? new ArrayList<>() : data;
   }
   
   private List<DistrictEntity> getDistrictByProvince(ProvinceEntity province) {
       ClientTCP client = new ClientTCP();
       
       BaseRequest baseRequest = new BaseRequest(TypeEnumRequest.DISTRICT, province.getCode());
       List<DistrictEntity> data = (List<DistrictEntity>)client.sendServer(baseRequest);
       return data == null ? new ArrayList<>() : data;
   }
   
   private List<WardEntity> getWardByProvinceAndDistrict(ProvinceEntity province, DistrictEntity district) {
       ClientTCP client = new ClientTCP();
       BaseRequest baseRequest = new BaseRequest(TypeEnumRequest.WARD,
               new FiltterWard(province.getCode(), district.getCode()));
       List<WardEntity> data = (List<WardEntity>)client.sendServer(baseRequest);
       return data == null ? new ArrayList<>() : data;
   }
   
   public void setViewProvince(JComboBox<ProvinceEntity> jComboBox) {
       List<ProvinceEntity> lstProvince = getAllProvince();
       lstProvince.stream().forEach(item-> {
           jComboBox.addItem(item);
       });
   }
    
   public void setViewDistrict(JComboBox<DistrictEntity> jComboBox, ProvinceEntity province) {
       List<DistrictEntity> lsEntitys = getDistrictByProvince(province);
       lsEntitys.stream().forEach(item-> {
           jComboBox.addItem(item);
       });
   }
   
   public void setViewWard(JComboBox<WardEntity> jComboBox, ProvinceEntity province, DistrictEntity district) {
       List<WardEntity> lsEntitys = getWardByProvinceAndDistrict(province, district);
       lsEntitys.stream().forEach(item-> {
           jComboBox.addItem(item);
       });
   }
   
   public static String checkDate(String strDate) {
       try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(strDate, dtf);
            return null;
       } catch(Exception e) {
           return "Khong Hợp lệ: " + strDate;
       }
   }
   
   public static String fromartMonyVND(BigDecimal number) {
       Locale viVN = new Locale("vi", "VN");
       DecimalFormat df = (DecimalFormat) NumberFormat.getCurrencyInstance(viVN);
       DecimalFormatSymbols sym = df.getDecimalFormatSymbols();
       sym.setCurrencySymbol("VNĐ"); // hoặc "VND"
       df.setDecimalFormatSymbols(sym);
       df.setMaximumFractionDigits(0);
       return df.format(number.doubleValue());
   }
    
}
