/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Count;

/**
 *
 * @author Aiglu
 */
public class CountStatPmpk {
    private String reg;
    private Integer count;
    private String parS;
    
    public CountStatPmpk(){
        
    }
    
    public String getReg(){
        return reg;
    }
    
    public void setReg(String reg){
        this.reg = reg;
    }      
    
    public String getParS(){
        return parS;
    }
    
    public void setParS(String par){
        this.parS = par;
    }      
    
    public Integer getCount(){
        return count;
    }
    
    public void setCount(Integer count){
        this.count = count;
    }
}
