/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.ptit.na.ltm.baitap.requests;

import java.io.Serializable;

/**
 *
 * @author Windows
 * @param <T>
 */
public class BaseRequest<T> implements Serializable{
    
    
    private TypeEnumRequest type;
    private T data;

    public BaseRequest(TypeEnumRequest type, T data) {
        this.type = type;
        this.data = data;
    }

    public TypeEnumRequest getType() {
        return type;
    }

    public void setType(TypeEnumRequest type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
    
    
}
