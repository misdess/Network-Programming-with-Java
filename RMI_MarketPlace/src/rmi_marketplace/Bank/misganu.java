package rmi_marketplace.Bank;

import java.rmi.RemoteException;

/**
 *
 * @author Mis Dess
 */
public class myAccount {
    public static void main(String[] args) throws RemoteException, RejectedException{
    Client client1=new Client();
    client1.bankobj.newAccount("myacount"); 
            
    
    }
    
    
    
}
