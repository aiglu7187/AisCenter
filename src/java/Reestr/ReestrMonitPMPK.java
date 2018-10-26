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
public class ReestrMonitPMPK {
    private Integer id;
    private Integer nom;
    private String fio;
    private Date dr;
    private String reg;
    private Date datep;
    private String np;
    
    public ReestrMonitPMPK(){
        
    }
    
    public Integer getId(){
        return id;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
    public Integer getNom(){
        return nom;
    }
    
    public void setNom(Integer nom){
        this.nom = nom;
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
   
    public Date getDatep(){
        return datep;
    }
    
    public void setDatep(Date datep){
        this.datep = datep;
    }
    
    public String getNp(){
        return np;
    }
    
    public void setNp(String np){
        this.np = np;
    }
}
