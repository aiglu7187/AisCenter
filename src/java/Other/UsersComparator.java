/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.Users;
import java.util.Comparator;

/**
 *
 * @author Aiglu
 */
public class UsersComparator implements Comparator{
    public int compare(Object obj1, Object obj2){
        Users u1 = (Users) obj1;
        Users u2 = (Users) obj2;
        int result = u1.getUserName().compareTo(u2.getUserName());
        return (result != 0) ? (int)(result/Math.abs(result)) : 0;
    }
}
