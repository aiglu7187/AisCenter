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
public class LetterComparator implements Comparator {

    public int compare(Object obj1, Object obj2) {
        Letter c1 = (Letter) obj1;
        Letter c2 = (Letter) obj2;
        int result = c1.getDate().compareTo(c2.getDate());
        if (result != 0) {
            return (int) (result / Math.abs(result));
        }
        Integer nom1 = 0;
        try {
            nom1 = Integer.parseInt(c1.getNomer().substring(0, c1.getNomer().indexOf("/")));
        } catch (Exception ex) {
        }
        Integer nom2 = 0;
        try {
            nom2 = Integer.parseInt(c2.getNomer().substring(0, c2.getNomer().indexOf("/")));
        } catch (Exception ex) {
        }
        result = nom1.compareTo(nom2);
        return (result != 0) ? (int) (result / Math.abs(result)) : 0;
    }
}
