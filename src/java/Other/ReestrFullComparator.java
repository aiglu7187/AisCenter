/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Reestr.ReestrPMPKFull;
import java.util.Comparator;

/**
 *
 * @author admin_ai
 */
public class ReestrFullComparator implements Comparator {
    public int compare(Object obj1, Object obj2){
        ReestrPMPKFull r1 = (ReestrPMPKFull)obj1;
        ReestrPMPKFull r2 = (ReestrPMPKFull)obj2;
        int result = r1.getFio().compareTo(r2.getFio());
        if (result != 0) return (int)(result/Math.abs(result));
        result = r1.getDatep().compareTo(r2.getDatep());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
}
