/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.ptit.na.ltm.baitap.service.service;

import com.google.gson.Gson;
import vn.ptit.na.ltm.baitap.requests.AddWorker;
import vn.ptit.na.ltm.baitap.requests.BaseRequest;
import vn.ptit.na.ltm.baitap.requests.FiltterWard;
import vn.ptit.na.ltm.baitap.requests.SearchWorker;
import vn.ptit.na.ltm.baitap.requests.TypeEnumRequest;


/**
 *
 * @author Windows
 */
public class BusinessService {
    
    
    public Object business(BaseRequest request) {
        try {
            switch (request.getType()) {
                case TypeEnumRequest.ADD -> {
                    AddWorker data = (AddWorker)request.getData();
                    return new WorkerService().create(data);
                }
                case TypeEnumRequest.SEARCH -> {
                    SearchWorker data = (SearchWorker)request.getData();
                    return new WorkerService().search(data);
                }
                case TypeEnumRequest.DETAIL -> {
                    Long data = (Long)request.getData();
                    return new WorkerService().detail(data);
                }
                case TypeEnumRequest.UPDATE -> {
                    AddWorker data = (AddWorker)request.getData();
                    return new WorkerService().create(data);
                }
                case TypeEnumRequest.DELETE -> {
                    return new WorkerService().delete((Long)request.getData());
                }
                case TypeEnumRequest.PROVINE -> {
                    return new AddressService().getLstProvince();
                }
                case TypeEnumRequest.DISTRICT -> {
                    return new AddressService().getLstDistricByProvinceCode(request.getData().toString());
                }
                case TypeEnumRequest.WARD -> {
                    FiltterWard data = (FiltterWard)request.getData();
                    return new AddressService().getLstWardByProvinceCodeAndDistrictCode(data.getProvinceCode(), data.getDistrictCode());
                }
                default -> throw new AssertionError();
            }
        
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

}
