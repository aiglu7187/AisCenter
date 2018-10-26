/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Aiglu
 * класс для писем (реестр исходящих ИПРА)
 */
public class Letter {    
    private Date date;      // дата письма
    private String nomer;   // номер письма
    private String name;    // назначение письма (текст для отображения в списке)
    private Integer id;     // ИД исходной таблицы в БД
    private int type;       // для определения типа письма (выбирается их констант)
    private String fio;     // ФИО ребёнка
    // константы для типа
    public static final int IPRA_OTCHET_CENTER = 0;
    public static final int IPRA18_OTCHET_CENTER = 1;
    public static final int IPRA18_TPMPK = 2;
    public static final int IPRA18_PERECHEN = 3;
    
    public Letter(){
        
    }
    
    public Date getDate(){
        return date;
    }
    
    public void setDate(Date date){
        this.date = date;
    }
    
    public String getNomer(){
        return nomer;
    }
    
    public void setNomer(String nomer){
        this.nomer = nomer;
    }
        
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public Integer getId(){
        return id;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
    public int getType(){
        return type;
    }
    
    public void setType(int type){
        this.type = type;
    }
    
    public String getFormat2Date(){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date d = this.date;
        String strD = "";
        try {
            strD = format.format(d);
        } catch (Exception ex) {
        }
        return strD;
    }
    
    public String getFio(){
        return fio;
    }
    
    public void setFio(String fio){
        this.fio = fio;
    }
}
