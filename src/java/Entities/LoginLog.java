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
@Table(name = "LOGIN_LOG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LoginLog.findAll", query = "SELECT l FROM LoginLog l"),
    @NamedQuery(name = "LoginLog.findByLoginlogId", query = "SELECT l FROM LoginLog l WHERE l.loginlogId = :loginlogId"),
    @NamedQuery(name = "LoginLog.findByLoginlogLogintime", query = "SELECT l FROM LoginLog l WHERE l.loginlogLogintime = :loginlogLogintime"),
    @NamedQuery(name = "LoginLog.findByLoginlogLastact", query = "SELECT l FROM LoginLog l WHERE l.loginlogLastact = :loginlogLastact"),
    @NamedQuery(name = "LoginLog.findByUserId", query = "SELECT l FROM LoginLog l WHERE l.userId = :userId"),
    @NamedQuery(name = "LoginLog.findByUserAndSessId", query = "SELECT l FROM LoginLog l WHERE l.sessId = :sessId AND l.userId = :userId")})
public class LoginLog implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOGINLOG_ID")
    @SequenceGenerator(name = "LOGIN_LOG_SEQ", sequenceName = "LOGIN_LOG_SEQ", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="LOGIN_LOG_SEQ")
    private Integer loginlogId;
    @Column(name = "LOGINLOG_LOGINTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginlogLogintime;
    @Column(name = "LOGINLOG_LASTACT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginlogLastact;
    @Column(name = "SESS_ID")    
    private String sessId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    public LoginLog() {
    }

    public LoginLog(Integer loginlogId) {
        this.loginlogId = loginlogId;
    }

    public Integer getLoginlogId() {
        return loginlogId;
    }

    public void setLoginlogId(Integer loginlogId) {
        this.loginlogId = loginlogId;
    }

    public Date getLoginlogLogintime() {
        return loginlogLogintime;
    }

    public void setLoginlogLogintime(Date loginlogLogintime) {
        this.loginlogLogintime = loginlogLogintime;
    }

    public Date getLoginlogLastact() {
        return loginlogLastact;
    }

    public void setLoginlogLastact(Date loginlogLastact) {
        this.loginlogLastact = loginlogLastact;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }
    
    public String getSessId(){
        return sessId;
    }
    
    public void setSessId(String sessId){
        this.sessId = sessId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loginlogId != null ? loginlogId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoginLog)) {
            return false;
        }
        LoginLog other = (LoginLog) object;
        if ((this.loginlogId == null && other.loginlogId != null) || (this.loginlogId != null && !this.loginlogId.equals(other.loginlogId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.LoginLog[ loginlogId=" + loginlogId + " ]";
    }
    
}
