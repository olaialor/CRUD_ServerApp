/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Meylin
 */
@Entity
@Table(name = "administrator", schema = "Fanetix")
@XmlRootElement
public class Administrator extends FanetixUser {
    
    private static final long serialVersionUID = 1L;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date incorporationDate;

    public Date getIncorporationDate() {
        return incorporationDate;
    }

    public void setIncorporationDate(Date incorporationDate) {
        this.incorporationDate = incorporationDate;
    }
}
