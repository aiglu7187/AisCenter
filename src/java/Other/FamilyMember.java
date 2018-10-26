/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Aiglu
 */
public class FamilyMember {
    String fio;
    Date dateR;
    Integer id;
    String kat;

    public FamilyMember() {

    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getFio() {
        return fio;
    }

    public void setDateR(Date dateR) {
        this.dateR = dateR;
    }

    public Date getDateR() {
        return dateR;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setKat(String kat) {
        this.kat = kat;
    }

    public String getKat() {
        return kat;
    }
    
    public String getFormatDateR(){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date dr = this.getDateR();
        String strDr = "";
        try{
            strDr  = format.format(dr);
        }
        catch(Exception ex){            
        }
        return strDr;
    }
}
