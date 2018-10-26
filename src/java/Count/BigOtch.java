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
public class BigOtch {
    private String usl;
    private Integer cent;
    private List<CountKatBig> countKatBigList;
    private List<CountKatBig> countKatDistList;
    private List<CountStatus> countStatusList;
    private List<StandartCount> countAgeList;
    private Integer manyStatus;
    private List<StandartCount> countMonitList;
    private List<CountProblemKod> countProblemList;
    private List<CountDate> countDateList;
    
    public BigOtch(){
        
    }
    
    public BigOtch(String usl, Integer cent, List<CountKatBig> countKatBigList, List<CountStatus> countStatusList,
            List<StandartCount> countAgeList, Integer manyStatus, List<StandartCount> countMonitList,
            List<CountProblemKod> countProblemList, List<CountKatBig> countKatDistList){
        this.usl = usl;
        this.cent = cent;
        this.countKatBigList = countKatBigList;
        this.countStatusList = countStatusList;
        this.countAgeList = countAgeList;
        this.manyStatus = manyStatus;
        this.countMonitList = countMonitList;
        this.countProblemList = countProblemList;
        this.countKatDistList = countKatDistList;
    }
    
    public BigOtch(String usl, List<CountDate> countDateList){
        this.usl = usl;
        this.countDateList = countDateList;
    }
    
    public String getUsl(){
        return usl;
    }
    
    public void setUsl(String usl){
        this.usl = usl;
    }
    
    public List<CountKatBig> getCountKatBigList(){
        return countKatBigList;
    }
    
    public void setCountKatBigList(List<CountKatBig> countKatBigList){
        this.countKatBigList = countKatBigList;
    }
    
    public List<CountStatus> getCountStatusList(){
        return countStatusList;
    }
    
    public void setCountStatusList(List<CountStatus> countStatusList){
        this.countStatusList = countStatusList;
    }
    
    public List<StandartCount> getCountAgeList(){
        return countAgeList;
    }
    
    public void setCountAgeList(List<StandartCount> countAgeList){
        this.countAgeList = countAgeList;
    }
    
    public Integer getManyStatus(){
        return manyStatus;
    }
    
    public void setManyStatus(Integer manyStatus){
        this.manyStatus = manyStatus;                
    }
    
    public List<StandartCount> getCountMonitList(){
        return countMonitList;
    }
    
    public void setCountMonitList(List<StandartCount> countMonitList){
        this.countMonitList = countMonitList;
    }
    
    public List<CountProblemKod> getCountProblemList(){
        return countProblemList;
    }
    
    public void setCountProblemList(List<CountProblemKod> countProblemList){
        this.countProblemList = countProblemList;
    }
    
    public List<CountDate> getCountDateList(){
        return countDateList;
    }
    
    public void setCountDateList(List<CountDate> countDateList){
        this.countDateList = countDateList;
    }
    
    public Integer getCent(){
        return cent;
    }
    
    public void setCent(Integer cent){
        this.cent = cent;
    }
    
    public List<CountKatBig> getCountKatDistList(){
        return countKatDistList;
    }
    
    public void setCountKatDistList(List<CountKatBig> countKatDistList){
        this.countKatDistList = countKatDistList;
    }
}
