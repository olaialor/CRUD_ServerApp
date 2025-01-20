package eus.tartanga.crud.entities;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * This is a base class representing a user in the Fanetix system. It contains
 * the common attributes and methods shared by all user entities such as email
 * and password. The class is mapped as a superclass, meaning it will not be
 * directly persisted, but its fields can be inherited by other entities like
 * FanetixClient.
 *
 * @author Meylin
 */
@MappedSuperclass
public class FanetixUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String email;
    private String passwd;

    /**
     * Gets the email of the user.
     *
     * @return the email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password of the user.
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * Sets the password of the user.
     *
     * @param passwd the password to set.
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * Computes the hash code for the FanetixUser based on the email.
     *
     * @return the hash code for the FanetixUser.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    /**
     * Checks whether two FanetixUser objects are equal based on their email.
     *
     * @param object the object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the email fields are not set
        if (!(object instanceof FanetixUser)) {
            return false;
        }
        FanetixUser other = (FanetixUser) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the FanetixUser, which includes the
     * email as the unique identifier.
     *
     * @return a string representation of the FanetixUser.
     */
    @Override
    public String toString() {
        return "eus.tartanga.crud.entities.FanetixUser[ id=" + email + " ]";
    }

}
