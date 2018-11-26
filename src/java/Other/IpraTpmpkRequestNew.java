/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.IpraPerechenTpmpk;
import Entities.SprOtherPmpk;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Aiglu
 */
public class IpraTpmpkRequestNew {

    private List<IpraPerechenTpmpk> ipraPerechenTpmpkList;
    private SprOtherPmpk sprOtherPmpk;
    private Date reqDate;

    public IpraTpmpkRequestNew() {

    }

    public void setIpraPerechenTpmpkList(List<IpraPerechenTpmpk> ipraPerechenTpmpkList) {
        this.ipraPerechenTpmpkList = ipraPerechenTpmpkList;
    }

    public List<IpraPerechenTpmpk> getIpraPerechenTpmpkList() {
        return ipraPerechenTpmpkList;
    }

    public void setSprOtherPmpk(SprOtherPmpk sprOtherPmpk) {
        this.sprOtherPmpk = sprOtherPmpk;
    }
    
    public SprOtherPmpk getSprOtherPmpk (){
        return sprOtherPmpk;
    }

    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }
    
    public Date getReqDate(){
        return reqDate;
    }
}
