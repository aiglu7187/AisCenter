/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Count;

import java.util.List;

/**
 *
 * @author Aiglu
 */
public class OtchetStatPmpk {
    private String n;
    private String par;
    private List<CountStatPmpk> stat;
    
    public OtchetStatPmpk(){
        
    }
    
    public String getN(){
        return n;
    }
    
    public void setN(String n){
        this.n = n;
    }
    
    public String getPar(){
        return par;
    }
    
    public void setPar(String par){
        this.par = par;
    }
    
    public List<CountStatPmpk> getStat(){
        return stat;
    }
    
    public void setStat(List<CountStatPmpk> stat){
        this.stat = stat;
    }
}
