/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reestr;

import Entities.Children;
import Entities.Pmpk;
import Entities.Priyom;

/**
 *
 * @author Aiglu
 */
public class ReestrEnt {
    private Children child;
    private Priyom priyom;
    private Pmpk pmpk;
    
    public ReestrEnt(){
        
    }
    
    public ReestrEnt(Children child, Priyom priyom, Pmpk pmpk){
        this.child = child;
        this.priyom = priyom;
        this.pmpk = pmpk;
    }
    
    public Children getChild(){
        return child;
    }
    
    public Priyom getPriyom(){
        return priyom;
    }
    
    public Pmpk getPmpk(){
        return pmpk;
    }
}
