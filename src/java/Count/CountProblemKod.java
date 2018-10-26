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
public class CountProblemKod {
    private Boolean type;
    private Integer kod;
    private String problem;
    private Integer count;
    
    public CountProblemKod(){
        
    }
    
    public Boolean getType(){
        return type;
    }
    
    public void setType(Boolean type){
        this.type = type;
    }
    
    public Integer getKod(){
        return kod;
    }
    
    public void setKod(Integer kod){
        this.kod = kod;
    }
    
    public String getProblem(){
        return problem;
    }
    
    public void setProblem(String problem){
        this.problem = problem;
    }
    
    public Integer getCount(){
        return count;
    }
    
    public void setCount(Integer count){
        this.count = count;
    }
}
