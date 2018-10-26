/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;


import Entities.SprProblemType;
import java.util.Comparator;

/**
 *
 * @author Aiglu
 */
public class ProblemTypeComparator implements Comparator{
    public int compare(Object obj1, Object obj2){
        SprProblemType pr1 = (SprProblemType) obj1;
        SprProblemType pr2 = (SprProblemType) obj2;
        int result = pr1.getSprproblemtypeKod().compareTo(pr2.getSprproblemtypeKod());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
}
