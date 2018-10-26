/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.Ipra;
import java.util.Comparator;

/**
 *
 * @author Aiglu
 */
public class IpraComparator implements Comparator{
    public int compare(Object obj1, Object obj2){
        Ipra i1 = (Ipra) obj1;
        Ipra i2 = (Ipra) obj2;
        int result = i1.getIpraOtchomsu().compareTo(i2.getIpraOtchomsu());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
}

