/**
 * Entity class representing an administrator in the system.
 * Extends FanetixUser to include additional attributes specific to administrators.
 *
 * <p>
 * This class is annotated with JPA and JAXB annotations to allow persistence
 * and XML binding.
 * </p>
 *
 * @author Meylin and Irati
 */
package eus.tartanga.crud.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQueries({
    /**
     * Query to retrieve all administrators from the database.
     */
    @NamedQuery(
            name = "findAll",
            query = "SELECT c FROM Administrator c"
    )
    ,
    /**
     * Query to authenticate an administrator by email and password.
     * 
     * @param email the email of the administrator.
     * @param passwd the password of the administrator.
     */
     @NamedQuery(
            name = "adminSignIn",
            query = "SELECT c FROM Administrator c WHERE c.email = :email AND c.passwd = :passwd"
    )
})

@Entity
@Table(name = "administrator", schema = "Fanetix")
@XmlRootElement
public class Administrator extends FanetixUser {

    /**
     * Serial version UID for serialization compatibility.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The date when the administrator was incorporated into the system. Cannot
     * be null.
     */
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date incorporationDate;

    /**
     * Gets the incorporation date of the administrator.
     *
     * @return the incorporation date.
     */
    public Date getIncorporationDate() {
        return incorporationDate;
    }

    /**
     * Sets the incorporation date of the administrator.
     *
     * @param incorporationDate the date to set as the incorporation date.
     */
    public void setIncorporationDate(Date incorporationDate) {
        this.incorporationDate = incorporationDate;
    }
}
