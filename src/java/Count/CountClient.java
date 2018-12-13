/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Count;

/**
 *
 * @author Aiglu
 */
public class CountClient {

    private String osnUsl;
    private String usl;
    private Integer count;
    private String katClient;
    private Boolean isCenter;

    public CountClient() {
    }

    public String getOsnUsl() {
        return osnUsl;
    }

    public void setOsnUsl(String osnUsl) {
        this.osnUsl = osnUsl;
    }

    public String getUsl() {
        return usl;
    }

    public void setUsl(String usl) {
        this.usl = usl;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getKatClient() {
        return katClient;
    }

    public void setKatClient(String katClient) {
        this.katClient = katClient;
    }

    public Boolean getIsCenter() {
        return isCenter;
    }

    public void setIsCenter(Boolean isCenter) {
        this.isCenter = isCenter;
    }
}
