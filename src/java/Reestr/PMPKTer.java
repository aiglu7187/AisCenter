/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reestr;

import java.util.Date;

/**
 *
 * @author Aiglu
 */
public class PMPKTer {
    String fio;
    Date dr;
    String reg;
    String ter;
    
    public PMPKTer(){
        
    }
    
    public String getFio(){
        return fio;
    }
    
    public void setFio(String fio){
        this.fio = fio;
    }
    
    public Date getDr(){
        return dr;
    }
    
    public void setDr(Date dr){
        this.dr = dr;
    }
    
    public String getReg(){
        return reg;
    }
    
    public void setReg(String reg){
        this.reg = reg;
    }
    
    public String getTer(){
        return ter;
    }
    
    public void setTer(String ter){
        this.ter = ter;
    }
}
