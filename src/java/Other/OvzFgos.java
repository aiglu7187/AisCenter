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
public class OvzFgos {

    private String reg;
    private String oo;
    private String fio;
    private Date dr;
    private Date beginEdu;
    private String pmpkName;
    private Date pmpkDate;
    private String np;
    private String obr;
    private Integer pmpkId;
    private String rekLogo;
    private String rekDefOl;
    private String rekDefTiflo;
    private String rekDefSurdo;
    private String rekPsy;
    private String rekAssist;
    private String rekEquip;

    public OvzFgos() {

    }

    public String getReg() {
        return reg;
    }

    public String getOo() {
        return oo;
    }

    public String getFio() {
        return fio;
    }

    public Date getDr() {
        return dr;
    }

    public Date getBeginEdu() {
        return beginEdu;
    }

    public String getPmpkName() {
        return pmpkName;
    }

    public Date getPmpkDate() {
        return pmpkDate;
    }

    public String getNp() {
        return np;
    }

    public String getObr() {
        return obr;
    }

    public Integer getPmpkId() {
        return pmpkId;
    }

    public String getRekLogo() {
        return rekLogo;
    }

    public String getRekDefOl() {
        return rekDefOl;
    }

    public String getRekDefTiflo() {
        return rekDefTiflo;
    }

    public String getRekDefSurdo() {
        return rekDefSurdo;
    }

    public String getRekPsy() {
        return rekPsy;
    }

    public String getRekAssist() {
        return rekAssist;
    }

    public String getRekEquip() {
        return rekEquip;
    }

    public String getRegularFormatDate(Date d) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        String strD = "";
        try {
            strD = format.format(d);
        } catch (Exception ex) {
        }
        return strD;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public void setOo(String oo) {
        this.oo = oo;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setDr(Date dr) {
        this.dr = dr;
    }

    public void setBeginEdu(Date beginEdu) {
        this.beginEdu = beginEdu;
    }

    public void setPmpkName(String pmpkName) {
        this.pmpkName = pmpkName;
    }

    public void setPmpkDate(Date pmpkDate) {
        this.pmpkDate = pmpkDate;
    }

    public void setNp(String np) {
        this.np = np;
    }

    public void setObr(String obr) {
        this.obr = obr;
    }

    public void setPmpkId(Integer pmpkId) {
        this.pmpkId = pmpkId;
    }
    
    public void setRekLogo(String rekLogo) {
        this.rekLogo = rekLogo;
    }

    public void setRekDefOl(String rekDefOl) {
        this.rekDefOl = rekDefOl;
    }

    public void setRekDefTiflo(String rekDefTiflo) {
        this.rekDefTiflo = rekDefTiflo;
    }

    public void setRekDefSurdo(String rekDefSurdo) {
        this.rekDefSurdo = rekDefSurdo;
    }

    public void setRekPsy(String rekPsy) {
        this.rekPsy = rekPsy;
    }

    public void setRekAssist(String rekAssist) {
        this.rekAssist = rekAssist;
    }

    public void setRekEquip(String rekEquip) {
        this.rekEquip = rekEquip;
    }

}
