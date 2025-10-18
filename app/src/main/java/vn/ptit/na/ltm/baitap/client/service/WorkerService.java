/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.ptit.na.ltm.baitap.client.service;

import java.util.List;
import vn.ptit.na.ltm.baitap.client.tcp.ClientTCP;
import vn.ptit.na.ltm.baitap.requests.AddWorker;
import vn.ptit.na.ltm.baitap.requests.BaseRequest;
import vn.ptit.na.ltm.baitap.requests.SearchWorker;
import vn.ptit.na.ltm.baitap.requests.TypeEnumRequest;
import vn.ptit.na.ltm.baitap.requests.WorkerLocationDTO;

/**
 *
 * @author Windows
 */
public class WorkerService {
    
    public Boolean save(AddWorker worker) {
       ClientTCP client = new ClientTCP();
       BaseRequest baseRequest = new BaseRequest(TypeEnumRequest.ADD, worker);
       return (Boolean)client.sendServer(baseRequest);
    }
    
    public Boolean update(AddWorker worker) {
       ClientTCP client = new ClientTCP();
       BaseRequest baseRequest = new BaseRequest(TypeEnumRequest.UPDATE, worker);
       return (Boolean)client.sendServer(baseRequest);
    }
    
    public Boolean delete(Long workerId) {
       ClientTCP client = new ClientTCP();
       BaseRequest baseRequest = new BaseRequest(TypeEnumRequest.DELETE, workerId);
       return (Boolean)client.sendServer(baseRequest);
    }
    
    public AddWorker detail(Long workerId) {
       ClientTCP client = new ClientTCP();
       BaseRequest baseRequest = new BaseRequest(TypeEnumRequest.DETAIL, workerId);
       return (AddWorker)client.sendServer(baseRequest);
    }
    
    public List<WorkerLocationDTO> search(SearchWorker payload) {
       ClientTCP client = new ClientTCP();
       BaseRequest baseRequest = new BaseRequest(TypeEnumRequest.SEARCH, payload);
       return (List<WorkerLocationDTO>)client.sendServer(baseRequest);
    }
    
}
