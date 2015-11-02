package view;

import controller.CartBean;
import controller.CustomerController;
import controller.ItemAdd;
import model.UserInter;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import model.Item;

/**
 *
 * @author Mis Dess
 */
@ConversationScoped
@Named
public class CustomerManagedBean implements Serializable {

    private static final long serialVersionUID = 16247164405L;

    @EJB
    CustomerController custController;

    @EJB
    private CartBean cartBean;
    
    @EJB
    ItemAdd itemaddbean;
    
    UserInter newCustomer;
    UserInter user;
    private String fullName;
    private String password;
    private String userId;
    private String email;
    private String message;
    
    private String cartResult=null;

    

    private String genomeName;
    private int amount;

    

    //getters and setters

    /**
     *
     * @return
     */
        public UserInter getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(UserInter user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public String getFullName() {
        return fullName;
    }

    /**
     *
     * @param fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public CustomerController getCustController() {
        return custController;
    }

    /**
     *
     * @return
     */
    public CartBean getCartBean() {
        return cartBean;
    }

    /**
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     *
     * @return
     */
    public String getCartResult() {
        return cartResult;
    }

    /**
     *
     * @param cartResult
     */
    public void setCartResult(String cartResult) {
        this.cartResult = cartResult;
    }
/*
    public boolean loggedIn() {
        return message != null;
    }*/

    /**
     *
     * @return
     */
    public String getGenomeName() {
        return genomeName;
    }

    /**
     *
     * @param genomeName
     */
    public void setGenomeName(String genomeName) {
        this.genomeName = genomeName;
    }

    /**
     *
     * @return
     */
    public int getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    
    //business logic functions

    /**
     *
     * Accept new customer
     */
        public void acceptNewUser() {
        message=custController.registerCustomer(fullName, userId, password, email);
    }

    /**
     *
     * ban a customer
     */
    public void removeUser(){
        System.out.println("the customer id is: "+userId);
    custController.removeCustomer(userId);
    }
    
    
    
    ///to see the complete description of the genomes
    public String getGenomeDetails(){
    itemaddbean.getGenomeDetails(genomeName);
    return "success";
    }
    
    /**
     *
     * Add a genome to the shopping cart
     */
    public void addGenomeToCart() {
        cartResult=cartBean.addGenome(genomeName, amount);
    }

    /**
     *Remove a genome from the shopping cart
     */
    public void removeGenomeFromCart() {
      cartBean.removeGenome(genomeName);
    }
    
    /**
     *
     * @return
     * List the genomes in the shopping cart
     */
    public List<Item> shoppingCartContents() {
        return cartBean.getCartContent();
    }

    /**
     *Make a payment
     */
    public void toPayment() {
                        System.out.println("Control is in payment bean");
        cartBean.toPayment();

    }
    
}
