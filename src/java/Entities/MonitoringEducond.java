/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aiglu
 */
@Entity
@Table(name = "MONITORING_EDUCOND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MonitoringEducond.findAll", query = "SELECT m FROM MonitoringEducond m"),
    @NamedQuery(name = "MonitoringEducond.findByMoniteducondId", query = "SELECT m FROM MonitoringEducond m WHERE m.moniteducondId = :moniteducondId"),
    @NamedQuery(name = "MonitoringEducond.findByMoniteducondExec", query = "SELECT m FROM MonitoringEducond m WHERE m.moniteducondExec = :moniteducondExec"),
    @NamedQuery(name = "MonitoringEducond.findByMoniteducondReason", query = "SELECT m FROM MonitoringEducond m WHERE m.moniteducondReason = :moniteducondReason")})
public class MonitoringEducond implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONITEDUCOND_ID")
    @SequenceGenerator(name = "SEQ_MONITORING_EDUCOND", sequenceName = "SEQ_MONITORING_EDUCOND", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_MONITORING_EDUCOND")
    private Integer moniteducondId;
    @Column(name = "MONITEDUCOND_EXEC")
    private Integer moniteducondExec;
    @Column(name = "MONITEDUCOND_EXEC_OP")
    private Integer moniteducondExecOp;
    @Column(name = "MONITEDUCOND_EXEC_VAR")
    private Integer moniteducondExecVar;
    @Column(name = "MONITEDUCOND_EXEC_LESS")
    private Integer moniteducondExecLess;
    @Column(name = "MONITEDUCOND_EXEC_Eq")
    private Integer moniteducondExecEq;
    @Column(name = "MONITEDUCOND_EXEC_AS")
    private Integer moniteducondExecAs;
    @Size(max = 1000)
    @Column(name = "MONITEDUCOND_REASON")
    private String moniteducondReason;
    @Size(max = 1000)
    @Column(name = "MONITEDUCOND_DETAILS")
    private String moniteducondDetails;
    @JoinColumn(name = "CHILDRENEDUCOND_ID", referencedColumnName = "CHILDRENEDUCOND_ID")
    @ManyToOne
    private ChildrenEducond childreneducondId;
    @JoinColumn(name = "MONITORING_ID", referencedColumnName = "MONITORING_ID")
    @ManyToOne
    private Monitoring monitoringId;

    public MonitoringEducond() {
    }

    public MonitoringEducond(Integer moniteducondId) {
        this.moniteducondId = moniteducondId;
    }

    public Integer getMoniteducondId() {
        return moniteducondId;
    }

    public void setMoniteducondId(Integer moniteducondId) {
        this.moniteducondId = moniteducondId;
    }

    public Integer getMoniteducondExec() {
        return moniteducondExec;
    }

    public void setMoniteducondExec(Integer moniteducondExec) {
        this.moniteducondExec = moniteducondExec;
    }
    
    public Integer getMoniteducondExecOp() {
        return moniteducondExecOp;
    }

    public void setMoniteducondExecOp(Integer moniteducondExecOp) {
        this.moniteducondExecOp = moniteducondExecOp;
    }

    public Integer getMoniteducondExecVar() {
        return moniteducondExecVar;
    }

    public void setMoniteducondExecVar(Integer moniteducondExecVar) {
        this.moniteducondExecVar = moniteducondExecVar;
    }
    
    public Integer getMoniteducondExecLess() {
        return moniteducondExecLess;
    }

    public void setMoniteducondExecLess(Integer moniteducondExecLess) {
        this.moniteducondExecLess = moniteducondExecLess;
    }
    
    public Integer getMoniteducondExecEq() {
        return moniteducondExecEq;
    }

    public void setMoniteducondExecEq(Integer moniteducondExecEq) {
        this.moniteducondExecEq = moniteducondExecEq;
    }
    
    public Integer getMoniteducondExecAs() {
        return moniteducondExecAs;
    }

    public void setMoniteducondExecAs(Integer moniteducondExecAs) {
        this.moniteducondExecAs = moniteducondExecAs;
    }
    
    public String getMoniteducondReason() {
        return moniteducondReason;
    }

    public void setMoniteducondReason(String moniteducondReason) {
        this.moniteducondReason = moniteducondReason;
    }
    
    public String getMoniteducondDetails() {
        return moniteducondDetails;
    }

    public void setMoniteducondDetails(String moniteducondDetails) {
        this.moniteducondDetails = moniteducondDetails;
    }

    public ChildrenEducond getChildreneducondId() {
        return childreneducondId;
    }

    public void setChildreneducondId(ChildrenEducond childreneducondId) {
        this.childreneducondId = childreneducondId;
    }

    public Monitoring getMonitoringId() {
        return monitoringId;
    }

    public void setMonitoringId(Monitoring monitoringId) {
        this.monitoringId = monitoringId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (moniteducondId != null ? moniteducondId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MonitoringEducond)) {
            return false;
        }
        MonitoringEducond other = (MonitoringEducond) object;
        if ((this.moniteducondId == null && other.moniteducondId != null) || (this.moniteducondId != null && !this.moniteducondId.equals(other.moniteducondId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.MonitoringEducond[ moniteducondId=" + moniteducondId + " ]";
    }
    
    public String getFormat2MonDate() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date date = this.getMonitoringId().getMonitoringDate();
        String strDate = "";
        try {
            strDate = format.format(date);
        } catch (Exception ex) {
        }
        return strDate;
    }
}
