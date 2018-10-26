/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

/**
 *
 * @author Aiglu
 */
public class RolesRight {
    public final int VOLOGDA = 1;
    public final int CHEREPOVETS = 2;
    int role = 0;
        
    public RolesRight(int role){
        this.role = role;
    }
    
    public Boolean isVologda(){
        Boolean result = Boolean.FALSE;
        if ((VOLOGDA & this.role) == VOLOGDA){
            result = Boolean.TRUE;
        }
        return result;
    }
    
    public Boolean isCherepovets(){
        Boolean result = Boolean.FALSE;
        if ((CHEREPOVETS & this.role) == CHEREPOVETS){
            result = Boolean.TRUE;
        }
        return result;
    }
}
