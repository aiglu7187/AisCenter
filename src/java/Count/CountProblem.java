/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Count;

/**
 *
 * @author Aiglu
 * класс для статистики по выявленным проблемам
 */
public class CountProblem {
    Integer ptKod;
    String problemType;
    String problem;
    Integer count;
    
    public CountProblem(){
        
    }
    
    public String getProblemType(){
        return problemType;
    }
    
    public void setProblemType(String problemType){
        this.problemType = problemType;
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
    
    public Integer getPtKod(){
        return ptKod;
    }
    
    public void setPtKod(Integer ptKod){
        this.ptKod = ptKod;
    }
}
