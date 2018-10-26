/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Entities.Ipra18;
import Entities.SprOtherPmpk;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Aiglu
 */
public class IpraTpmpkRequest {

    private List<Ipra18> ipra18List;
    private SprOtherPmpk sprOtherPmpk;
    private Date reqDate;

    public IpraTpmpkRequest() {

    }

    public void setIpra18List(List<Ipra18> ipra18List) {
        this.ipra18List = ipra18List;
    }

    public List<Ipra18> getIpra18List() {
        return ipra18List;
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
