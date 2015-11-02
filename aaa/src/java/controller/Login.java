package controller;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import model.User1;

/**
 *
 * @author Mis Dess
 */
@SessionScoped
@Named

public class Login implements Serializable {

   private static final long serialVersionUID = 7965455427888195913L;

  @Inject
   private Requester reuester;
   @Inject
   private CustomerController userManager;

   private User1 currentUser;
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
     * Login to the webshop as a customer
     */
    public String login() throws Exception {

      User1 user = userManager.findUser(reuester.getUsername(), reuester.getPassword());

      if (user != null) {

         this.currentUser = user;

        // FacesContext.getCurrentInstance().addMessage(null,

              // new FacesMessage("Welcome, " + currentUser.getName()));
         message=true;
        return "success";
      }
return "failure";
   }

    /**
     *
     * @return
     * logout from the webshop as customer
     */
    public String logout() {

    //  FacesContext.getCurrentInstance().addMessage(null,

      //      new FacesMessage("Goodbye, " + currentUser.getName()));

      currentUser = null;
      return "success";
   }

    /**
     *
     * @return
     */
    public boolean isLoggedIn() {

      return currentUser != null;

   }

    /**
     *
     * @return
     */
    @Produces
  @LoggedIn
   public User1 getCurrentUser() {
      return currentUser;
   }
}