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
public class ReestrPMPKFull {
    private Integer id;
    private String fio;
    private Date dr;
    private String reg;
    private String ovz;
    private Boolean op;
    private Boolean di;
    private Boolean ds;
    private Date datep;
    private String obr;
    private Date datek;
    private Integer age;
    private String sex;
    private Boolean defolig;
    private Boolean defsurdo;
    private Boolean deftiflo;
    private Boolean logo;
    private Boolean psy;
    private Boolean soc;
    private Boolean tutor;
    private Boolean assist;
    
    public ReestrPMPKFull(){
        
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
    
    public Boolean getDi(){
        return di;
    }
    
    public void setDi(Boolean di){
        this.di = di;
    }
    
    public Boolean getDs(){
        return ds;
    }
    
    public void setDs(Boolean ds){
        this.ds = ds;
    }
 
    public Integer getAge(){
        return age;
    }
    
     public void setAge(Integer age){
        this.age = age;
    }
    
    public String getSex(){ 
        return sex;
    }
    
    public void setSex(String sex){ 
        this.sex = sex;
    }
    
    public Boolean getDefolig(){ 
        return defolig;
    }
    
    public void setDefolig(Boolean defolig){ 
        this.defolig = defolig;
    }
    
    public Boolean getDefsurdo(){ 
        return defsurdo;
    }
    
    public void setDefsurdo(Boolean defsurdo){ 
        this.defsurdo = defsurdo;
    }
    
    public Boolean getDeftiflo(){ 
        return deftiflo;
    }
    
    public void setDeftiflo(Boolean deftiflo){ 
        this.deftiflo = deftiflo;
    }
    
    public Boolean getLogo(){ 
        return logo;
    }
    
    public void setLogo(Boolean logo){ 
        this.logo = logo;
    }
    
    public Boolean getPsy(){ 
        return psy;
    }
    
    public void setPsy(Boolean psy){ 
        this.psy = psy;
    }
    
    public Boolean getSoc(){ 
        return soc;
    }
    
    public void setSoc(Boolean soc){ 
        this.soc = soc;
    }
    
    public Boolean getTutor(){ 
        return tutor;
    }
    
    public void setTutor(Boolean tutor){ 
        this.tutor = tutor;
    }
    
    public Boolean getAssist(){ 
        return assist;
    }    
    
    public void setAssist(Boolean assist){ 
        this.assist = assist;
    }
}
