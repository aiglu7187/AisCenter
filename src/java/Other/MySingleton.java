/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.IpraIshnom;
import Entities.IpraVhnom;
import Sessions.IpraIshnomFacade;
import Sessions.IpraVhnomFacade;

/**
 *
 * @author Aiglu
 */
public class MySingleton {
    private static MySingleton ms;         // создаём объект, он у нас будет всем нуждающимся выдаваться, по требованию
       
    private MySingleton () {        // делаем конструктор приватным, чтобы ни у кого, кроме класса не было доступа

    }

    public static MySingleton getInstance () {
        if (ms == null) {
            ms = new MySingleton();
        }
        return ms;
    }

    public synchronized String getNextNomer (IpraIshnomFacade ipraIshnomFacade) {
        IpraIshnom ipraIshnom = ipraIshnomFacade.getIpraIshnom();
        ipraIshnom.setIpraishnomNom(ipraIshnom.getIpraishnomNom() + 1);
        ipraIshnomFacade.edit(ipraIshnom);
        String nomer = ipraIshnom.getIpraishnomNom() + ipraIshnom.getIpraishnomSuffix();        
        return nomer;
    }
    
    public synchronized String getNextNomer(IpraVhnomFacade ipraVhnomFacade){
        IpraVhnom ipraVhnom = ipraVhnomFacade.getIpraVhnom();
        ipraVhnom.setIpravhnomNom(ipraVhnom.getIpravhnomNom() + 1);
        ipraVhnomFacade.edit(ipraVhnom);
        String nomer = ipraVhnom.getIpravhnomNom() + ipraVhnom.getIpravhnomSuffix();
        return nomer;
    }
}
