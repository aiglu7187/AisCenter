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
public class CorrespondComparator implements Comparator {

    public int compare(Object obj1, Object obj2) {
        Correspond c1 = (Correspond) obj1;
        Correspond c2 = (Correspond) obj2;
        return (c1.getDate() == null) ? ((c2.getDate() == null) ? 0 : (Integer.MIN_VALUE))
                : ((c2.getDate() == null) ? (Integer.MAX_VALUE) : c1.getDate().compareTo(c2.getDate()));
    }
}
