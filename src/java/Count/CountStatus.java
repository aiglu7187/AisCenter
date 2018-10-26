/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Count;

/**
 *
 * @author Aiglu
 * класс для подсчёт статистики по статусам детей
 */
public class CountStatus {
    String status;
    Integer count;
    
    public CountStatus(){
        
    }
    
    public String getStatus(){
        return status;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    public Integer getCount(){
        return count;
    }
    
    public void setCount(Integer count){
        this.count = count;
    }
}
