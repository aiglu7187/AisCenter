/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "LOGIN_CONTROL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LoginControl.findAll", query = "SELECT l FROM LoginControl l"),
    @NamedQuery(name = "LoginControl.findByLogincontrolId", query = "SELECT l FROM LoginControl l WHERE l.logincontrolId = :logincontrolId"),
    @NamedQuery(name = "LoginControl.findByUserId", query = "SELECT l FROM LoginControl l WHERE l.userId = :userId"),
    @NamedQuery(name = "LoginControl.findByLogincontrolCount", query = "SELECT l FROM LoginControl l WHERE l.logincontrolCount = :logincontrolCount"),
    @NamedQuery(name = "LoginControl.findByLogincontrolDatetime", query = "SELECT l FROM LoginControl l WHERE l.logincontrolDatetime = :logincontrolDatetime")})
public class LoginControl implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOGINCONTROL_ID")
    @SequenceGenerator(name = "SEQ_LOGIN_CONTROL", sequenceName = "SEQ_LOGIN_CONTROL", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_LOGIN_CONTROL")
    private Integer logincontrolId;
    @Column(name = "LOGINCONTROL_COUNT")
    private Integer logincontrolCount;
    @Column(name = "LOGINCONTROL_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logincontrolDatetime;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    public LoginControl() {
    }

    public LoginControl(Integer logincontrolId) {
        this.logincontrolId = logincontrolId;
    }

    public Integer getLogincontrolId() {
        return logincontrolId;
    }

    public void setLogincontrolId(Integer logincontrolId) {
        this.logincontrolId = logincontrolId;
    }

    public Integer getLogincontrolCount() {
        return logincontrolCount;
    }

    public void setLogincontrolCount(Integer logincontrolCount) {
        this.logincontrolCount = logincontrolCount;
    }

    public Date getLogincontrolDatetime() {
        return logincontrolDatetime;
    }

    public void setLogincontrolDatetime(Date logincontrolDatetime) {
        this.logincontrolDatetime = logincontrolDatetime;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logincontrolId != null ? logincontrolId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoginControl)) {
            return false;
        }
        LoginControl other = (LoginControl) object;
        if ((this.logincontrolId == null && other.logincontrolId != null) || (this.logincontrolId != null && !this.logincontrolId.equals(other.logincontrolId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.LoginControl[ logincontrolId=" + logincontrolId + " ]";
    }
    
}
