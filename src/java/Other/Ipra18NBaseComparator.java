/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.Ipra18N;
import java.util.Comparator;

/**
 *
 * @author Aiglu
 */
public class Ipra18NBaseComparator implements Comparator {  // сортировка ИПРА по ИД
    public int compare(Object obj1, Object obj2) {
        Ipra18N i1 = (Ipra18N) obj1;
        Ipra18N i2 = (Ipra18N) obj2;
        int result = i2.getIpra18Id().compareTo(i1.getIpra18Id());        
        return (result != 0) ? (int) (result / Math.abs(result)) : 0;
    }
}
