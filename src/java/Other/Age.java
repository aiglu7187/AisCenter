/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.util.Objects;

/**
 *
 * @author Aiglu
 */
public class Age {
    private Integer years;
    private Integer months;
    
    public Age(){
        years = 0;
        months = 0;
    }
    
    public Age(Integer years, Integer months){
        this.years = years;
        this.months = months;
    }
    
    @Override
    public String toString(){
        String strAge = "0";
        if (years > 0){
            strAge = years.toString();
            if (years == 1){
                strAge += " год";
            } else if ((years > 1 ) && (years < 5)){
                strAge += " года";
            } else if (years >= 5){
                strAge += " лет";
            }
        } else {
            if (months > 0){
                strAge = months.toString();
                if (months == 1){
                    strAge += " месяц";
                } else if ((months > 1) && (months < 5)){
                    strAge += " месяца";
                } else if (months >= 5){
                    strAge += " месяцев";
                }
            }
        }
        return strAge;
    }
    
    public void setYears (Integer years){
        this.years = years;
    }
    
    public void setMonths (Integer months){
        this.months = months;
    }
    
    public Boolean younger(Age age){
        Boolean result = false;
        if (this.years < age.years){
            result = true;
        } else if (this.years == age.years){
            if (this.months <= age.months){
                result = true;
            }                 
        }
        return result;
    }
    
    public Boolean older(Age age){
        Boolean result = false;
        if (this.years > age.years){
            result = true;
        } else if (this.years == age.years){
            if (this.months >= age.months){
                result = true;
            }                 
        }
        return result;
    }
    
    public Integer getYears(){
        return years;
    }
}
