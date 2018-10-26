/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.SprRegion;
import java.util.Comparator;

/**
 *
 * @author admin_ai
 */
public class RegionComparator implements Comparator {
    public int compare(Object obj1, Object obj2){
        SprRegion reg1 = (SprRegion)obj1;
        SprRegion reg2 = (SprRegion)obj2;
        int result = reg1.getSprregOth().compareTo(reg2.getSprregOth());
        if (result != 0) return (int)(result/Math.abs(result));
        result = reg2.getSprregCenter().compareTo(reg1.getSprregCenter());
        if (result != 0) return (int)(result/Math.abs(result));
        result = reg1.getSprregName().compareTo(reg2.getSprregName());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
}
