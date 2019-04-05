/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Reestr.Reestr;
import java.util.Comparator;

/**
 *
 * @author Aiglu
 */
public class ReestrComparator implements Comparator {
    public int compare(Object obj1, Object obj2){
        Reestr r1 = (Reestr)obj1;
        Reestr r2 = (Reestr)obj2;
        int result = r1.getFam().compareTo(r2.getFam());
        if (result != 0) return (int)(result/Math.abs(result));
        result = r1.getName().compareTo(r2.getName());
        if (result != 0) return (int)(result/Math.abs(result));
        result = r1.getPatr().compareTo(r2.getPatr());
        if (result != 0) return (int)(result/Math.abs(result));
        result = r1.getDate().compareTo(r2.getDate());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
}
