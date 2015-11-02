package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Mis Dess
 */
@Entity
public class User1 implements UserInter, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String name;
    private String password;
    private String email;
    private int active;

    /**
     *
     */
    public User1() {
    }

    /**
     *
     * @param name
     * @param id
     * @param password
     * @param email
     * @param active
     */
    public User1(String name, String id, String password, String email, int active) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.email = email;
        this.active=active;
        System.out.println("Control is in Entity");
    }
    
    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return name;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     *
     * @return
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     *
     * @return
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return
     */
    public int isActive() {
        return active;
    }

    /**
     *
     * @param active
     */
    public void setActive(int active) {
        this.active = active;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User1)) {
            return false;
        }
        User1 other = (User1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id;
    }

}
