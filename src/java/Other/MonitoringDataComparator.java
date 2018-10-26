/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.MonitoringData;
import java.util.Comparator;

/**
 *
 * @author Aiglu
 */
public class MonitoringDataComparator implements Comparator {
    public int compare(Object obj1, Object obj2){
        MonitoringData s1 = (MonitoringData) obj1;
        MonitoringData s2 = (MonitoringData) obj2;
        int result = s1.getChildId().getChildFam().compareTo(s2.getChildId().getChildFam());
        if (result != 0) return (int)(result/Math.abs(result));
        result = s1.getChildId().getChildName().compareTo(s2.getChildId().getChildName());
        if (result != 0) return (int)(result/Math.abs(result));
        result = s1.getChildId().getChildPatr().compareTo(s2.getChildId().getChildPatr());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
}
