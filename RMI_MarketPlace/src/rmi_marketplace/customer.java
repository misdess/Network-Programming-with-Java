
package rmi_marketplace;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface customer extends Remote {
    public boolean buyItem(String itemName, float itemPrice) throws RemoteException,RejectedException;
    public void sellItem(String itemName, float itemPrice) throws RemoteException;
    public void addItemWish(String itemName, float itemPrice) throws RemoteException;    
}