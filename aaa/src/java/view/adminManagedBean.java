package view;

import controller.adminBean;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

/**
 *
 * @author Mis Dess
 */
@Named
@ConversationScoped

public class adminManagedBean implements Serializable {

    private static final long serialVersionUID = 16247164405L;
    private String userId;
    private String password;
    private String message;

    /**
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    @EJB
    adminBean adminbean;
    
    /**
     *
     * @return
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
 
    /**
     *
     */
    public void registerAdmin() {
        if (userId != null && !"".equals(userId)) {
            if (password != null && !"".equals(password)) {
             adminbean.register(userId, password);
            } else {
                message = "Password field is empty";
            }
        } else if (password != null && !"".equals(password)) {
            message = "user name field is empty";
        }
    }
  
            
}
