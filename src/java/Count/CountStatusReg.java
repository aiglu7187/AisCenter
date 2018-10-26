/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Count;

/**
 *
 * @author Aiglu
 */
public class CountStatusReg {
    String status;
    String reg;
    Integer count;
    
    public CountStatusReg(){
        
    }
    
    public String getStatus(){
        return status;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    public String getReg(){
        return reg;
    }
    
    public void setReg(String reg){
        this.reg = reg;
    }
    
    public Integer getCount(){
        return count;
    }
    
    public void setCount(Integer count){
        this.count = count;
    }
}
