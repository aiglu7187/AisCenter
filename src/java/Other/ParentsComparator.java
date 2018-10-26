/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.Parents;
import java.util.Comparator;

/**
 *
 * @author Aiglu
 */
public class ParentsComparator implements Comparator{
    public int compare(Object obj1, Object obj2){
        Parents p1 = (Parents) obj1;
        Parents p2 = (Parents) obj2;
        int result = p1.getParentFam().compareTo(p2.getParentFam());
        if (result != 0) return (int)(result/Math.abs(result));
        result = p1.getParentName().compareTo(p2.getParentName());
        if (result != 0) return (int)(result/Math.abs(result));
        result = p1.getParentPatr().compareTo(p2.getParentPatr());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
}
