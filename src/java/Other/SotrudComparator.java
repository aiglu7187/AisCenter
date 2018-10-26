/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.Sotrud;
import java.util.Comparator;

/**
 *
 * @author admin_ai
 */
public class SotrudComparator implements Comparator{
    public int compare(Object obj1, Object obj2){
        Sotrud s1 = (Sotrud) obj1;
        Sotrud s2 = (Sotrud) obj2;
        int result = s1.getSotrudFam().compareTo(s2.getSotrudFam());
        if (result != 0) return (int)(result/Math.abs(result));
        result = s1.getSotrudName().compareTo(s2.getSotrudName());
        if (result != 0) return (int)(result/Math.abs(result));
        result = s1.getSotrudPatr().compareTo(s2.getSotrudPatr());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
    
}
