package controller;

import java.util.List;
import model.UserInter;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import model.User1;

/**
 *
 * @author Mis Dess
 */
@Stateless
public class CustomerController {

    @PersistenceContext(unitName = "aaaPU")
    EntityManager em;

    List<User1> results;
    private User1 id;

    /**
     *
     * @param name
     * @param Id
     * @param password
     * @param email
     * @return
     * Register a new customer with a unique customer Id
     */
    public String registerCustomer(String name, String Id, String password, String email) {
        User1 cust = null;
        cust = em.find(User1.class, Id);

        if (cust == null && findUser(Id) == null) {

            User1 customer = new User1(name, Id, password, email, 1);
            em.persist(customer);
            return "You are registered succefully";
        } else {
            ;
        }
        return "Choose another user id";
    }

    /**
     *
     * @param id
     *Ban a misbehaving customer by deactivating his/her account
     */
    public void removeCustomer(String id) {
        System.out.println("the customer id is and: " + id);

        User1 user = em.find(User1.class, id);
        user.setActive(0);
        em.remove(user);
        em.persist(user); 
    }

    /**
     *
     * @return
     * List all the customers of the webshop
     */
    public List<User1> listCustomers() {
        TypedQuery<User1> query = em.createQuery("SELECT c FROM User1 c", User1.class);
        results = query.getResultList();
        return results;
    }

    /**
     *
     * @param username
     * @param password
     * @return
     * @throws Exception
     * Find a customer in the webshop given his/her customer Id and password
     */
    public User1 findUser(String username, String password) throws Exception {
        @SuppressWarnings("unchecked")
        List<User1> result = em.createQuery(
                "select u from  User1 u where u.id=:username and u.password=:password")
                .setParameter("username", username)
                .setParameter("password", password).getResultList();
        if (result.isEmpty() || result.get(0).isActive() == 0) {//check if customer is banned
            return null;
        } else {
            return result.get(0);
        }
    }

    /**
     *
     * @param username
     * @return
     *      * Find a customer in the webshop given his/her customer Id 
     */
    public User1 findUser(String username) {
        @SuppressWarnings("unchecked")
        List<User1> result = em.createQuery(
                "select u from  User1 u where u.id=:username")
                .setParameter("username", username).getResultList();
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    /**
     *
     * @return
     */
    public User1 getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(User1 id) {
        this.id = id;
    }

}
