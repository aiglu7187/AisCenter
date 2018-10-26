/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Keys;

/**
 *
 * @author Aiglu класс для парсинга ключевых выражений элемент
 */
public class KeyElement {

    public KeyLevel level;  // уровень, являющийся элементом
    public Boolean isInverted; // признак того, что перед элементом стоит знак отрицания
    public String elementString;    // текстовая часть ключа

    public KeyElement() {
        isInverted = false;
    }

    public Boolean test(String str) {   // проверяем, удовлетворяет ли исходная строка (str) ключу
        Boolean result = false;
        if ((elementString != null) && (!elementString.equals(""))) {
            if (str.contains(elementString)) {
                result = true;
            }
            if (isInverted){
                result = !result;
            }
        } else if (level != null) {
            result = level.test(str);
        }
        return result;
    }
}
