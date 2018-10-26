/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.Ipra18;
import java.util.Comparator;

/**
 *
 * @author Aiglu
 */
public class Ipra18Comparator implements Comparator{
    public int compare(Object obj1, Object obj2){
        Ipra18 i1 = (Ipra18) obj1;
        Ipra18 i2 = (Ipra18) obj2;
        int result = i1.getChildId().getSprregId().getSprregName().compareTo(i2.getChildId().getSprregId().getSprregName());
        if (result != 0) return (int)(result/Math.abs(result));
        result = i1.getChildId().getChildFam().compareTo(i2.getChildId().getChildFam());
        if (result != 0) return (int)(result/Math.abs(result));
        result = i1.getChildId().getChildName().compareTo(i2.getChildId().getChildName());
        if (result != 0) return (int)(result/Math.abs(result));
        result = i1.getChildId().getChildPatr().compareTo(i2.getChildId().getChildPatr());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
}

