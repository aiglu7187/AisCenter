/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.Ped;
import java.util.Comparator;

/**
 *
 * @author Aiglu
 */
public class PedComparator implements Comparator{
    public int compare(Object obj1, Object obj2){
        Ped p1 = (Ped) obj1;
        Ped p2 = (Ped) obj2;
        int result = p1.getPedFam().compareTo(p2.getPedFam());
        if (result != 0) return (int)(result/Math.abs(result));
        result = p1.getPedName().compareTo(p2.getPedName());
        if (result != 0) return (int)(result/Math.abs(result));
        result = p1.getPedPatr().compareTo(p2.getPedPatr());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
}
