/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;
import Entities.Children;
import java.util.Comparator;
/**
 *
 * @author Aiglu
 */
public class ChildrenComparator implements Comparator{
    public int compare(Object obj1, Object obj2){
        Children c1 = (Children) obj1;
        Children c2 = (Children) obj2;
        int result = c1.getChildFam().compareTo(c2.getChildFam());
        if (result != 0) return (int)(result/Math.abs(result));
        result = c1.getChildName().compareTo(c2.getChildName());
        if (result != 0) return (int)(result/Math.abs(result));
        result = c1.getChildPatr().compareTo(c2.getChildPatr());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
}


