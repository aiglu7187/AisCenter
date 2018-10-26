/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.ChildStat;
import java.util.Comparator;

/**
 *
 * @author Aiglu
 */
public class ChildStatComparator implements Comparator {
    public int compare(Object obj1, Object obj2){
        ChildStat c1 = (ChildStat) obj1;
        ChildStat c2 = (ChildStat) obj2;
        int result = c1.getSprstatId().getSprstatV().compareTo(c2.getSprstatId().getSprstatV());
        if (result != 0) return (int)(result/Math.abs(result));
        result = c1.getSprstatId().getSprstatId().compareTo(c2.getSprstatId().getSprstatId());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
}
