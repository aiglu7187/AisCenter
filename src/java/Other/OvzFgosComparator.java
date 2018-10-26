/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.util.Comparator;

/**
 *
 * @author Aiglu
 */
public class OvzFgosComparator implements Comparator{
    public int compare(Object obj1, Object obj2){
        OvzFgos of1 = (OvzFgos) obj1;
        OvzFgos of2 = (OvzFgos) obj2;
        int result = of1.getFio().compareTo(of2.getFio());
        return(result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
}
