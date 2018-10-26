/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.SotrudDolgn;
import java.util.Comparator;

/**
 *
 * @author admin_ai
 */
public class SotrudDolgnComparator implements Comparator{
    public int compare(Object obj1, Object obj2){
        SotrudDolgn s1 = (SotrudDolgn) obj1;
        SotrudDolgn s2 = (SotrudDolgn) obj2;
        int result = s1.getSotrudId().getSotrudFam().compareTo(s2.getSotrudId().getSotrudFam());
        if (result != 0) return (int)(result/Math.abs(result));
        result = s1.getSotrudId().getSotrudName().compareTo(s2.getSotrudId().getSotrudName());
        if (result != 0) return (int)(result/Math.abs(result));
        result = s1.getSotrudId().getSotrudPatr().compareTo(s2.getSotrudId().getSotrudPatr());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
    
}
