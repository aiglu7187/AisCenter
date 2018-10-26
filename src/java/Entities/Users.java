/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin_ai
 */
@Entity
@Table(name = "USERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserId", query = "SELECT u FROM Users u WHERE u.userId = :userId"),
    @NamedQuery(name = "Users.findByUserName", query = "SELECT u FROM Users u WHERE upper(u.userName) = upper(:userName)"),
    @NamedQuery(name = "Users.findByUserPassword", query = "SELECT u FROM Users u WHERE u.userPassword = :userPassword"),
    @NamedQuery(name = "Users.findBySotrudId", query = "SELECT u FROM Users u WHERE u.sotrudId = :sotrudId")})
public class Users implements Serializable {

    @Column(name = "USER_ACTIVE")
    private Integer userActive;
    /*@Column(name = "USER_ACTIVE")
    private Integer userActive;*/
    @Column(name = "CENTER_ROLE")
    private Integer centerRole;
    /*@Column(name = "CENTER_ROLE")
    private Integer centerRole;*/
    
    @OneToMany(mappedBy = "userId")
    private Collection<PriyomSotrud> priyomSotrudCollection;    
    @OneToMany(mappedBy = "userId")
    private Collection<Priyom> priyomCollection;    
    @OneToMany(mappedBy = "userId")
    private Collection<Ipra18> ipra18Collection;
    @OneToMany(mappedBy = "userId")
    private Collection<Ipra18Prikaz> ipra18PrikazCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Children> childrenCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<SprOtherPmpk> sprOtherPmpkCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<SprOmsu> sprOmsuCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<SprMse> sprMseCollection;    
    @OneToMany(mappedBy = "userId")
    private Collection<ChildStatus> childStatusCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Parents> parentsCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<PayuslClient> payuslClientCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Payment> paymentCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Payusllespos> payusllesposCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<PayuslLesson> payuslLessonCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<PayuslSotrud> payuslSotrudCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<PayDogovor> payDogovorCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<PayUsl> payUslCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<LoginControl> loginControlCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<LoginLog> loginLogCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<UsersRegion> usersRegionCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "USER_ID")
    @SequenceGenerator(name = "SEQ_USERS", sequenceName = "SEQ_USERS", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="SEQ_USERS")
    private Integer userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USER_NAME")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USER_PASSWORD")
    private String userPassword;
    @JoinColumn(name = "SOTRUD_ID", referencedColumnName = "SOTRUD_ID")
    @ManyToOne
    private Sotrud sotrudId;
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")
    @ManyToOne
    private Roles roleId;
    

    public Users() {
    }

    public Users(Integer userId) {
        this.userId = userId;
    }

    public Users(Integer userId, String userName, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Sotrud getSotrudId() {
        return sotrudId;
    }

    public void setSotrudId(Sotrud sotrudId) {
        this.sotrudId = sotrudId;
    }
    
    public Roles getRoleId() {
        return roleId;
    }

    public void setRoleId(Roles roleId) {
        this.roleId = roleId;
    }

    public Integer getCenterRole() {
        return centerRole;
    }

    public void setCenterRole(Integer centerRole) {
        this.centerRole = centerRole;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Users[ userId=" + userId + " ]";
    }

    public Integer getUserActive() {
        return userActive;
    }

    public void setUserActive(Integer userActive) {
        this.userActive = userActive;
    }

    @XmlTransient
    public Collection<UsersRegion> getUsersRegionCollection() {
        return usersRegionCollection;
    }

    public void setUsersRegionCollection(Collection<UsersRegion> usersRegionCollection) {
        this.usersRegionCollection = usersRegionCollection;
    }

    @XmlTransient
    public Collection<LoginLog> getLoginLogCollection() {
        return loginLogCollection;
    }

    public void setLoginLogCollection(Collection<LoginLog> loginLogCollection) {
        this.loginLogCollection = loginLogCollection;
    }

    @XmlTransient
    public Collection<LoginControl> getLoginControlCollection() {
        return loginControlCollection;
    }

    public void setLoginControlCollection(Collection<LoginControl> loginControlCollection) {
        this.loginControlCollection = loginControlCollection;
    }

    @XmlTransient
    public Collection<PayUsl> getPayUslCollection() {
        return payUslCollection;
    }

    public void setPayUslCollection(Collection<PayUsl> payUslCollection) {
        this.payUslCollection = payUslCollection;
    }

    @XmlTransient
    public Collection<PayDogovor> getPayDogovorCollection() {
        return payDogovorCollection;
    }

    public void setPayDogovorCollection(Collection<PayDogovor> payDogovorCollection) {
        this.payDogovorCollection = payDogovorCollection;
    }

    @XmlTransient
    public Collection<PayuslSotrud> getPayuslSotrudCollection() {
        return payuslSotrudCollection;
    }

    public void setPayuslSotrudCollection(Collection<PayuslSotrud> payuslSotrudCollection) {
        this.payuslSotrudCollection = payuslSotrudCollection;
    }

    @XmlTransient
    public Collection<PayuslLesson> getPayuslLessonCollection() {
        return payuslLessonCollection;
    }

    public void setPayuslLessonCollection(Collection<PayuslLesson> payuslLessonCollection) {
        this.payuslLessonCollection = payuslLessonCollection;
    }

    @XmlTransient
    public Collection<Payusllespos> getPayusllesposCollection() {
        return payusllesposCollection;
    }

    public void setPayusllesposCollection(Collection<Payusllespos> payusllesposCollection) {
        this.payusllesposCollection = payusllesposCollection;
    }

    @XmlTransient
    public Collection<Payment> getPaymentCollection() {
        return paymentCollection;
    }

    public void setPaymentCollection(Collection<Payment> paymentCollection) {
        this.paymentCollection = paymentCollection;
    }


    @XmlTransient
    public Collection<PayuslClient> getPayuslClientCollection() {
        return payuslClientCollection;
    }

    public void setPayuslClientCollection(Collection<PayuslClient> payuslClientCollection) {
        this.payuslClientCollection = payuslClientCollection;
    }

    @XmlTransient
    public Collection<Parents> getParentsCollection() {
        return parentsCollection;
    }

    public void setParentsCollection(Collection<Parents> parentsCollection) {
        this.parentsCollection = parentsCollection;
    }

    @XmlTransient
    public Collection<ChildStatus> getChildStatusCollection() {
        return childStatusCollection;
    }

    public void setChildStatusCollection(Collection<ChildStatus> childStatusCollection) {
        this.childStatusCollection = childStatusCollection;
    }

    @XmlTransient
    public Collection<SprMse> getSprMseCollection() {
        return sprMseCollection;
    }

    public void setSprMseCollection(Collection<SprMse> sprMseCollection) {
        this.sprMseCollection = sprMseCollection;
    }

 
    @XmlTransient
    public Collection<SprOmsu> getSprOmsuCollection() {
        return sprOmsuCollection;
    }

    public void setSprOmsuCollection(Collection<SprOmsu> sprOmsuCollection) {
        this.sprOmsuCollection = sprOmsuCollection;
    }

    @XmlTransient
    public Collection<Ipra18> getIpra18Collection() {
        return ipra18Collection;
    }

    public void setIpra18Collection(Collection<Ipra18> ipra18Collection) {
        this.ipra18Collection = ipra18Collection;
    }

    @XmlTransient
    public Collection<Ipra18Prikaz> getIpra18PrikazCollection() {
        return ipra18PrikazCollection;
    }

    public void setIpra18PrikazCollection(Collection<Ipra18Prikaz> ipra18PrikazCollection) {
        this.ipra18PrikazCollection = ipra18PrikazCollection;
    }

    @XmlTransient
    public Collection<Children> getChildrenCollection() {
        return childrenCollection;
    }

    public void setChildrenCollection(Collection<Children> childrenCollection) {
        this.childrenCollection = childrenCollection;
    }

    @XmlTransient
    public Collection<SprOtherPmpk> getSprOtherPmpkCollection() {
        return sprOtherPmpkCollection;
    }

    public void setSprOtherPmpkCollection(Collection<SprOtherPmpk> sprOtherPmpkCollection) {
        this.sprOtherPmpkCollection = sprOtherPmpkCollection;
    }

    @XmlTransient
    public Collection<Priyom> getPriyomCollection() {
        return priyomCollection;
    }

    public void setPriyomCollection(Collection<Priyom> priyomCollection) {
        this.priyomCollection = priyomCollection;
    }

    @XmlTransient
    public Collection<PriyomSotrud> getPriyomSotrudCollection() {
        return priyomSotrudCollection;
    }

    public void setPriyomSotrudCollection(Collection<PriyomSotrud> priyomSotrudCollection) {
        this.priyomSotrudCollection = priyomSotrudCollection;
    }

}
