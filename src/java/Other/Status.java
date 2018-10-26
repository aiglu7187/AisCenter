/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.Children;
import Entities.SprStat;

/**
 *
 * @author Aiglu
 * Класс для отображения статусов в jsp
 * Содержит ребёнка, статус, признак checked - есть ли такой статус у ребёнка, 
 * признак enabled - доступен ли данный статус для редактирования (зависит от услуги),
 * mainStatus - родительский статус для статуса (если он относится к уровню подстатусов, иначе = null)
 */
public class Status {
    private Children child;
    private SprStat status;
    private Boolean checked;
    private Boolean enabled;
    private SprStat mainStatus;
    
    public Status(){
        mainStatus = null;
    }
    
    public Children getChild(){
        return child;
    }
    
    public void setChild(Children child){
        this.child = child;
    }
    
    public SprStat getStatus(){
        return status;
    }
    
    public void setStatus(SprStat status){
        this.status = status;
    }
    
    public Boolean getChecked(){
        return checked;
    }
    
    public void setChecked(Boolean checked){
        this.checked = checked;
    }
    
    public Boolean getEnabled(){
        return enabled;
    }
    
    public void setEnabled(Boolean enabled){
        this.enabled = enabled;
    }
    
    public SprStat getMainStatus(){
        return mainStatus;
    }
    
    public void setMainStatus(SprStat mainStatus){
        this.mainStatus = mainStatus;
    }
}
