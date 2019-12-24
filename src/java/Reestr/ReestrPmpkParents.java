/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reestr;

import Entities.Children;
import Entities.Parents;
import Entities.Priyom;
import Entities.Sotrud;
import java.util.Objects;

/**
 *
 * @author Aiglu
 */
public class ReestrPmpkParents {
    public Priyom priyom;
    public Sotrud sotrud;
    public Children child;
    public Parents parent;    
    
    public ReestrPmpkParents(){}
    
    public ReestrPmpkParents(Priyom priyom, Sotrud sotrud, Children child, Parents parent){
        this.child = child;
        this.parent = parent;
        this.priyom = priyom;
        this.sotrud = sotrud;
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if ( !(o instanceof ReestrPmpkParents)) {
            return false;
        }
        ReestrPmpkParents r = (ReestrPmpkParents) o;
        return ( (r.child == this.child) && (r.priyom == this.priyom) && (r.parent == this.parent) );
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(child, parent, priyom);
    }
}
