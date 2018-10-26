/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Aiglu
 */
public class MyCalendar {

    Date date;
    Integer year;
    Integer month;
    Integer day;
    String monthName;
    Integer dayOfWeek;
    Boolean isWeekend;
    Boolean isLastDay;

    public MyCalendar(Date date) {
        this.date = date;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH) + 1;
        this.day = c.get(Calendar.DATE);
        switch (this.month) {
            case 1:
                this.monthName = "Январь";
                break;
            case 2:
                this.monthName = "Февраль";
                break;
            case 3:
                this.monthName = "Март";
                break;
            case 4:
                this.monthName = "Апрель";
                break;
            case 5:
                this.monthName = "Май";
                break;
            case 6:
                this.monthName = "Июнь";
                break;
            case 7:
                this.monthName = "Июль";
                break;
            case 8:
                this.monthName = "Август";
                break;
            case 9:
                this.monthName = "Сентябрь";
                break;
            case 10:
                this.monthName = "Октябрь";
                break;
            case 11:
                this.monthName = "Ноябрь";
                break;
            case 12:
                this.monthName = "Декабрь";
                break;
        }
        if (c.get(Calendar.DAY_OF_WEEK) > 1) {
            this.dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        } else if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            this.dayOfWeek = 7;
        }

        if ((this.dayOfWeek == 6) || (this.dayOfWeek == 7)) {
            this.isWeekend = Boolean.TRUE;
        } else {
            this.isWeekend = Boolean.FALSE;
        }
        
        if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE)){
            this.isLastDay = Boolean.TRUE;
        } 
        else {
            this.isLastDay = Boolean.FALSE;
        }
    }

    public Date getDate() {
        return date;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getDay() {
        return day;
    }

    public String getMonthName() {
        return monthName;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public Boolean getIsWeekend() {
        return isWeekend;
    }
    
   public Boolean getIsLastDay() {
        return isLastDay;
    } 
   
   public void setIsWeekend(Boolean isWeekend){
       this.isWeekend = isWeekend;
   }
}
