/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Reestr.ReestrMonitPMPK;
import java.util.Comparator;

/**
 *
 * @author admin_ai
 */
public class ReestrMonitComparator implements Comparator {
    public int compare(Object obj1, Object obj2){
        ReestrMonitPMPK r1 = (ReestrMonitPMPK)obj1;
        ReestrMonitPMPK r2 = (ReestrMonitPMPK)obj2;
        int result = r1.getFio().compareTo(r2.getFio());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
}
