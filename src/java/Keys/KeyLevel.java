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
 * @author Aiglu класс для парсинга ключевых выражений уровень
 */
public class KeyLevel {

    public List<KeyElement> elementList;    // список элементов
    public List<KeyOperation> operationList;    // список операций (если правильно записан ключ, всегда будет иметь 1 элемент списка)
    public KeyLevel parentLevel; // родительский уровень (на верхнем уровне = null)
    public Integer keyId;   // идентификатор ключевого объекта в БД (только на верхнем уровне, на остальных = null)

    public KeyLevel() {
        elementList = new ArrayList<>();
        operationList = new ArrayList<>();
        parentLevel = null;
        keyId = null;
    }

    public Boolean test(String str) {   // проверяем, удовлетворяет ли исходная строка (str) ключу
        Boolean result = false;
        if (operationList.isEmpty()) {  // на уровне нет операций - значит проверяем только элемент (если он есть)
            if (!elementList.isEmpty()) {
                result = elementList.get(0).test(str);
            }
        } else {                        // на уровне есть операции
            for (KeyOperation o : operationList) {
                String op = o.operation;
                if (op.equals(KeyOperation.AND)) {
                    result = true;
                    for (KeyElement e : o.elementList) {
                        if (!e.test(str)) {
                            result = false;
                            break;
                        }
                    }
                } else if (op.equals(KeyOperation.OR)) {
                    result = false;
                    for (KeyElement e : o.elementList) {
                        if (e.test(str)) {
                            result = true;
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }
}
