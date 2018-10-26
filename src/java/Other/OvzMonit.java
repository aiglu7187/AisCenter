/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.Children;
import Entities.SprObr;
import Entities.SprObrVar;
import Entities.SprRegion;
import java.util.Date;

/**
 *
 * @author Aiglu
 */
public class OvzMonit {

    String fio;
    String dr;
    Children child;
    String op;
    SprObrVar sprObrVar;
    SprObr sprObr;
    String reg;
    String info;
    SprRegion sprRegion;
    String datePmpk;

    public OvzMonit() {

    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getFio() {
        return fio;
    }

    public void setDr(String dr) {
        this.dr = dr;
    }

    public String getDr() {
        return dr;
    }

    public void setChild(Children child) {
        this.child = child;
    }

    public Children getChildren() {
        return child;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getOp() {
        return op;
    }

    public void setSprObrVar(SprObrVar sprObrVar) {
        this.sprObrVar = sprObrVar;
    }

    public SprObrVar getSprObrVar() {
        return sprObrVar;
    }

    public void setSprObr(SprObr sprObr) {
        this.sprObr = sprObr;
    }

    public SprObr getSprObr() {
        return sprObr;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getReg() {
        return reg;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setSprRegion(SprRegion sprRegion) {
        this.sprRegion = sprRegion;
    }

    public SprRegion getSprRegion() {
        return sprRegion;
    }
    
    public void setDatePmpk(String datePmpk) {
        this.datePmpk = datePmpk;
    }

    public String getDatePmpk() {
        return datePmpk;
    }
}
