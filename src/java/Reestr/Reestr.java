/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reestr;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Aiglu
 */
public class Reestr {
    private Integer idPar;
    private String famPar;
    private String namePar;
    private String patrPar;
    private Integer id;
    private String fam;
    private String name;
    private String patr;
    private Date dr;
    private String reg;
    private Date date;
    private String regPr;
    private String usl;
    private String info;
    
    public Reestr(){
        
    }
    
    public String getFamPar(){
        return famPar;
    }
    
    public void setFamPar(String famPar){
        this.famPar = famPar;
    }
    
    public String getNamePar(){
        return namePar;
    }
    
    public void setNamePar(String namePar){
        this.namePar = namePar;
    }
    
    public String getPatrPar(){
        return patrPar;
    }
    
    public void setPatrPar(String patrPar){
        this.patrPar = patrPar;
    }
    
    public String getFam(){
        return fam;
    }
    
    public void setFam(String fam){
        this.fam = fam;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getPatr(){
        return patr;
    }
    
    public void setPatr(String patr){
        this.patr = patr;
    }
    
    public Date getDr(){
        return dr;
    }
    
    public void setDr(Date dr){
        this.dr = dr;
    }
    
    public Date getDate(){
        return date;
    }
    
    public void setDate(Date date){
        this.date = date;
    }
    
    public String getReg(){
        return reg;
    }
    
    public void setReg(String reg){
        this.reg = reg;
    }
    
    public String getUsl(){
        return usl;
    }
    
    public void setUsl(String usl){
        this.usl = usl;
    }
    
    public String getInfo(){
        return info;
    }
    
    public void setInfo(String info){
        this.info = info;
    }
    
    public String getRegPr(){
        return regPr;
    }
    
    public void setRegPr(String regPr){
        this.regPr = regPr;
    }
    
    public Integer getIdPar(){
        return idPar;
    }
    
    public void setIdPar(Integer idPar){
        this.idPar = idPar;
    }
    
    public Integer getId(){
        return id;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
    public String getFormat2Dr() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        String strDr = "";
        try {
            strDr = format.format(dr);
        } catch (Exception ex) {
        }
        return strDr;
    }
    
    public String getFormat2Date() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        String strDate = "";
        try {
            strDate = format.format(date);
        } catch (Exception ex) {
        }
        return strDate;
    }
}
