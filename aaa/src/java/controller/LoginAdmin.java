package controller;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import model.Admin;

/**
 *
 * @author Mis Dess
 */
@SessionScoped
@Named

public class LoginAdmin implements Serializable {

   private static final long serialVersionUID = 7965455427888195913L;

  @Inject
   private Requester requester;
   @Inject
   private adminBean userManager;

   private Admin currentUser;
   private boolean message=false;

    /**
     *
     * @param message
     */
    public void setMessage(boolean message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    public boolean getMessage() {
        return message;
    }
   
    /**
     *
     * @return
     * @throws Exception
     * Login to the webshop as an admin and perform the admin operations of the webshop
     */
    public String login() throws Exception {
      Admin user = userManager.findUser(requester.getUsername(), requester.getPassword());
System.out.println("Error in the parametres "+requester.getUsername());

      if (user != null) {

         this.currentUser = user;

        // FacesContext.getCurrentInstance().addMessage(null,

//               new FacesMessage("Welcome, " + currentUser.getUserName()));
         message=true;
        return "success";
      }
return "failure";
   }

    /**
     *
     * @return
     * Logout from the webshop
     */
    public String logout() {

      //FacesContext.getCurrentInstance().addMessage(null,

          //  new FacesMessage("Goodbye, " + currentUser.getUserName()));

      currentUser = null;
     // CartBean.
      return "success";
   }

    /**
     *
     * @return
     */
    public boolean isAdminLoggedIn() {

      return currentUser != null;

   }

    /**
     *
     * @return
     */
    @Produces
  @AdminLoggedIn
   public Admin getCurrentUser() {
      return currentUser;
   }


}