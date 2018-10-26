/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.PayuslLesson;
import java.util.Comparator;

/**
 *
 * @author Aiglu
 */
public class LessonComparator implements Comparator{
    public int compare(Object obj1, Object obj2){
        PayuslLesson i1 = (PayuslLesson) obj1;
        PayuslLesson i2 = (PayuslLesson) obj2;
        int result = i1.getPayusllessonDate().compareTo(i2.getPayusllessonDate());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
}
