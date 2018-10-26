/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Other.Age;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin_ai
 */
@Entity
@Table(name = "CHILDREN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Children.findAll", query = "SELECT c FROM Children c ORDER BY c.childFam, c.childName, c.childPatr"),
    @NamedQuery(name = "Children.findByChildId", query = "SELECT c FROM Children c WHERE c.childId = :childId"),
    @NamedQuery(name = "Children.findByChildNom", query = "SELECT c FROM Children c WHERE c.childNom = :childNom"),
    @NamedQuery(name = "Children.findByChildFam", query = "SELECT c FROM Children c WHERE c.childFam = :childFam"),
    @NamedQuery(name = "Children.findByChildName", query = "SELECT c FROM Children c WHERE c.childName = :childName"),
    @NamedQuery(name = "Children.findByChildPatr", query = "SELECT c FROM Children c WHERE c.childPatr = :childPatr"),
    @NamedQuery(name = "Children.findByChildDr", query = "SELECT c FROM Children c WHERE c.childDr = :childDr")})

public class Children implements Serializable {

    @Column(name = "CHILD_NOM")
    private Integer childNom;
    @OneToMany(mappedBy = "childId")
    private Collection<ChildrenEducond> childrenEducondCollection;
    /*@Column(name = "CHILD_NOM")
    private Integer childNom;*/
    @OneToMany(mappedBy = "childId")
    private Collection<Ipra18> ipra18Collection;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    @OneToMany(mappedBy = "childId")
    private Collection<ChildStatus> childStatusCollection;
    @OneToMany(mappedBy = "childId")
    private Collection<PayDogovor> payDogovorCollection;
    @OneToMany(mappedBy = "childId")
    private Collection<Dogovor> dogovorCollection;
    @OneToMany(mappedBy = "childId")
    private Collection<GroupChild> groupChildCollection;
    @OneToMany(mappedBy = "childId")
    private Collection<GrlesschildP> grlesschildPCollection;
    @OneToMany(mappedBy = "childId")
    private Collection<ChildrenReg> childrenRegCollection;
    @OneToMany(mappedBy = "childId")
    private Collection<Ipra> ipraCollection;
    @OneToMany(mappedBy = "childId")
    private Collection<MonitoringData> monitoringDataCollection;
    @OneToMany(mappedBy = "childId")
    private Collection<ChildEduplace> childEduplaceCollection;
    @OneToMany(mappedBy = "childId")
    private Collection<PriyomSubject> priyomSubjectCollection;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHILD_ID")
    @SequenceGenerator(name = "SEQ_CHILDREN", sequenceName = "SEQ_CHILDREN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_CHILDREN")
    private Integer childId;
    @Size(max = 100)
    @Column(name = "CHILD_FAM")
    private String childFam;
    @Size(max = 100)
    @Column(name = "CHILD_NAME")
    private String childName;
    @Size(max = 100)
    @Column(name = "CHILD_PATR")
    private String childPatr;
    @Column(name = "CHILD_DR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date childDr;
    @Column(name = "DATE_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpd;
    @JoinColumn(name = "SPRREG_ID", referencedColumnName = "SPRREG_ID")
    @ManyToOne
    private SprRegion sprregId;
    @Size(max = 1)
    @Column(name = "CHILD_POL")
    private String childPol;

    public Children() {
    }

    public Children(Integer childId) {
        this.childId = childId;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public Integer getChildNom() {
        return childNom;
    }

    public void setChildNom(Integer childNom) {
        this.childNom = childNom;
    }

    public String getChildFam() {
        return childFam;
    }

    public void setChildFam(String childFam) {
        this.childFam = childFam;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildPatr() {
        return childPatr;
    }

    public void setChildPatr(String childPatr) {
        this.childPatr = childPatr;
    }

    public Date getChildDr() {
        return childDr;
    }

    public void setChildDr(Date childDr) {
        this.childDr = childDr;
    }

    @XmlTransient
    public SprRegion getSprregId() {
        return sprregId;
    }

    public void setSprregId(SprRegion sprregId) {
        this.sprregId = sprregId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (childId != null ? childId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Children)) {
            return false;
        }
        Children other = (Children) object;
        if ((this.childId == null && other.childId != null) || (this.childId != null && !this.childId.equals(other.childId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Children[ childId=" + childId + " ]";
    }

    public String getFIO() {
        if (childPatr != null) {
            return childFam + " " + childName + " " + childPatr;
        } else {
            return childFam + " " + childName;
        }
    }

    public String getFormatDr() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date dr = this.getChildDr();
        String strDr = "";
        try {
            strDr = format.format(dr);
        } catch (Exception ex) {
        }
        return strDr;
    }

    public String getFormat2Dr() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date dr = this.getChildDr();
        String strDr = "";
        try {
            strDr = format.format(dr);
        } catch (Exception ex) {
        }
        return strDr;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Date getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Date dateUpd) {
        this.dateUpd = dateUpd;
    }

    @XmlTransient
    public Collection<PriyomSubject> getPriyomSubjectCollection() {
        return priyomSubjectCollection;
    }

    public void setPriyomSubjectCollection(Collection<PriyomSubject> priyomSubjectCollection) {
        this.priyomSubjectCollection = priyomSubjectCollection;
    }

    @XmlTransient
    public Collection<MonitoringData> getMonitoringDataCollection() {
        return monitoringDataCollection;
    }

    public void setMonitoringDataCollection(Collection<MonitoringData> monitoringDataCollection) {
        this.monitoringDataCollection = monitoringDataCollection;
    }

    @XmlTransient
    public Collection<ChildEduplace> getChildEduplaceCollection() {
        return childEduplaceCollection;
    }

    public void setChildEduplaceCollection(Collection<ChildEduplace> childEduplaceCollection) {
        this.childEduplaceCollection = childEduplaceCollection;
    }

    @XmlTransient
    public Collection<Ipra> getIpraCollection() {
        return ipraCollection;
    }

    public void setIpraCollection(Collection<Ipra> ipraCollection) {
        this.ipraCollection = ipraCollection;
    }

    @XmlTransient
    public Collection<ChildrenReg> getChildrenRegCollection() {
        return childrenRegCollection;
    }

    public void setChildrenRegCollection(Collection<ChildrenReg> childrenRegCollection) {
        this.childrenRegCollection = childrenRegCollection;
    }

    @XmlTransient
    public Collection<Dogovor> getDogovorCollection() {
        return dogovorCollection;
    }

    public void setDogovorCollection(Collection<Dogovor> dogovorCollection) {
        this.dogovorCollection = dogovorCollection;
    }

    @XmlTransient
    public Collection<GroupChild> getGroupChildCollection() {
        return groupChildCollection;
    }

    public void setGroupChildCollection(Collection<GroupChild> groupChildCollection) {
        this.groupChildCollection = groupChildCollection;
    }

    @XmlTransient
    public Collection<GrlesschildP> getGrlesschildPCollection() {
        return grlesschildPCollection;
    }

    public void setGrlesschildPCollection(Collection<GrlesschildP> grlesschildPCollection) {
        this.grlesschildPCollection = grlesschildPCollection;
    }

    @XmlTransient
    public Collection<PayDogovor> getPayDogovorCollection() {
        return payDogovorCollection;
    }

    public void setPayDogovorCollection(Collection<PayDogovor> payDogovorCollection) {
        this.payDogovorCollection = payDogovorCollection;
    }

    @XmlTransient
    public Collection<ChildStatus> getChildStatusCollection() {
        return childStatusCollection;
    }

    public void setChildStatusCollection(Collection<ChildStatus> childStatusCollection) {
        this.childStatusCollection = childStatusCollection;
    }

    @XmlTransient
    public Collection<Ipra18> getIpra18Collection() {
        return ipra18Collection;
    }

    public void setIpra18Collection(Collection<Ipra18> ipra18Collection) {
        this.ipra18Collection = ipra18Collection;
    }

    public Age getAgeOnDate(Date date) {
        Calendar dateC = Calendar.getInstance();
        dateC.setTime(date);
        Calendar drC = Calendar.getInstance();
        drC.setTime(this.getChildDr());
        Age age = new Age();
        int years = dateC.get(Calendar.YEAR) - drC.get(Calendar.YEAR);
        int dateMonth = dateC.get(Calendar.MONTH);
        int drMonth = drC.get(Calendar.MONTH);
        if (years > 0) {
            // корректируем, если текущий месяц в дате проверки меньше месяца даты рождения
            if (dateMonth < drMonth) {
                years--;
            } else if ((dateMonth == drMonth)
                    && (dateC.get(GregorianCalendar.DAY_OF_MONTH) < drC.get(GregorianCalendar.DAY_OF_MONTH))) {
                // отдельный случай - в случае равенства месяцев, 
                // но меньшего дня месяца в дате проверки - корректируем
                years--;
            }
            age.setYears(years);
        }
        if (years == 0) {
            int months = dateMonth - drMonth;
            if (months < 1) {
                months = 0;
            }
            age.setMonths(months);
        }
        return age;
    }

    @XmlTransient
    public Collection<ChildrenEducond> getChildrenEducondCollection() {
        return childrenEducondCollection;
    }

    public void setChildrenEducondCollection(Collection<ChildrenEducond> childrenEducondCollection) {
        this.childrenEducondCollection = childrenEducondCollection;
    }

    public String getChildPol() {
        return childPol;
    }

    public void setChildPol(String childPol) {
        this.childPol = childPol;
    }
}
