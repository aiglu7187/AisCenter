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
public class ReestrPMPK {
    private Integer id;
    private String fio;
    private Date dr;
    private String reg;
    private String ovz;
    private Boolean op;
    private Date datep;
    private String obr;
    private Date datek;
    //private Boolean n;
    
    public ReestrPMPK(){
        
    }
    
    public Integer getId(){
        return id;
    }
    
    public void setId(Integer id){
        this.id = id;
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
    
    public String getOvz(){
        return ovz;
    }
    
    public void setOvz(String ovz){
        this.ovz = ovz;
    }
    
    public Boolean getOp(){
        return op;
    }
    
    public void setOp(Boolean op){
        this.op = op;
    }
    
    public Date getDatep(){
        return datep;
    }
    
    public void setDatep(Date datep){
        this.datep = datep;
    }
    
    public String getObr(){
        return obr;
    }
    
    public void setObr(String obr){
        this.obr = obr;
    }
    
    public Date getDatek(){
        return datek;
    }
    
    public void setDatek(Date datek){
        this.datek = datek;
    }
    
/*    public Boolean getN(){
        return n;
    }
    
    public void setN(Boolean n){
        this.n = n;
    }*/
}
