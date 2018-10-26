/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Reestr.ReestrPMPK;
import java.util.Comparator;

/**
 *
 * @author admin_ai
 */
public class ReestrComparator implements Comparator {
    public int compare(Object obj1, Object obj2){
        ReestrPMPK r1 = (ReestrPMPK)obj1;
        ReestrPMPK r2 = (ReestrPMPK)obj2;
        int result = r1.getFio().compareTo(r2.getFio());
        if (result != 0) return (int)(result/Math.abs(result));
        result = r1.getDatep().compareTo(r2.getDatep());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
}
