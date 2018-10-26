/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Count;

/**
 *
 * @author Aiglu
 * класс для подсчёта статистики
 */
public class CountPriyom {

    private String osnUsl;
    private String usl;
    private Integer count;    
        
    public CountPriyom(){              
    }   
    
    public String getOsnUsl(){
        return osnUsl;
    }
    
    public void setOsnUsl(String osnUsl){
        this.osnUsl = osnUsl;
    }
    
    public String getUsl(){
        return usl;
    }
    
    public void setUsl(String usl){
        this.usl = usl;
    }
    
    public Integer getCount(){
        return count;
    }
    
    public void setCount(Integer count){
        this.count = count;
    }

}
