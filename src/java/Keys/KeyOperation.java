/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Keys;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aiglu
 * класс для парсинга ключевых выражений
 * операция
 */
public class KeyOperation {
    public List<KeyElement> elementList;    // список элементов, связанных операцией
    public String operation;    // тип операции
    // возможные операции
    public static final String AND = "AND"; 
    public static final String OR = "OR";
    
    public KeyOperation(){
        elementList = new ArrayList<>();
    }
    
    
}
