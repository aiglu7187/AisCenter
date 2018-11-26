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
public class Ipra18NFIOComparator implements Comparator {  // сортировка ИПРА по ИД
    public int compare(Object obj1, Object obj2) {
        Ipra18N i1 = (Ipra18N) obj1;
        Ipra18N i2 = (Ipra18N) obj2;
        int result = i1.getChildId().getChildFam().compareTo(i2.getChildId().getChildFam());
        if (result != 0) return (int)(result/Math.abs(result));
        result = i1.getChildId().getChildName().compareTo(i2.getChildId().getChildName());
        if (result != 0) return (int)(result/Math.abs(result));
        result = i1.getChildId().getChildPatr().compareTo(i2.getChildId().getChildPatr());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
}
