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
 * Класс для структуры списка консультирований (родитель + ребёнок)
 */
public class ConsultReestr {
    String parentFio;
    String childFio;
    Date childDr;
    Date consDate;
    String sotrud;
    String dolgn;
    
    public ConsultReestr(){
        
    }
    
    public void setParentFio(String parentFio){
        this.parentFio = parentFio;
    }
    
    public String getParentFio(){
        return parentFio;
    }
    
    public void setChildFio(String childFio){
        this.childFio = childFio;
    }
    
    public String getChildFio(){
        return childFio;
    }
    
    public void setSotrud(String sotrud){
        this.sotrud = sotrud;
    }
    
    public String getDolgn(){
        return dolgn;
    }
    
    public void setChildDr(Date childDr){
        this.childDr = childDr;
    }
    
    public Date getChildDr(){
        return childDr;
    }
    
    public void setConsDate(Date consDate){
        this.consDate = consDate;
    }
    
    public Date getConsDate(){
        return consDate;
    }
}
