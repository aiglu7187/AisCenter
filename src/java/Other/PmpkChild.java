/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.Children;
import Entities.Pmpk;

/**
 *
 * @author Aiglu
 */
public class PmpkChild {

    private Children child;
    private Pmpk pmpk;

    public PmpkChild() {

    }

    public Children getChild() {
        return child;
    }

    public void setChild(Children child) {
        this.child = child;
    }

    public Pmpk getPmpk() {
        return pmpk;
    }

    public void setPmpk(Pmpk pmpk) {
        this.pmpk = pmpk;
    }
}
