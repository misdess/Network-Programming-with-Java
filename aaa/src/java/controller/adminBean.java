package controller;

import java.util.List;
import model.Admin;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Mis Dess
 */
@Stateless

public class adminBean {

   @PersistenceContext(unitName = "aaaPU")
   EntityManager em;
   
   
   private Admin admin;
   
    /**
     *
     * @param id
     * @param password
     */
    public void register(String id, String password){     
   admin=new Admin(id, password);
   em.persist(admin); 
   }
   
    /**
     *
     * @param username
     * @param password
     * @return
     * @throws Exception
     * 
     */
    public Admin findUser(String username, String password) throws Exception {
      @SuppressWarnings("unchecked")
      List<Admin> result = em.createQuery(
                  "select u from  Admin u where u.userName=:username and u.password=:password")
            .setParameter("username", username)
            .setParameter("password", password).getResultList();
      if (result.isEmpty()) {
         return null;
      } else {
         return result.get(0);
      }
   }   
}
