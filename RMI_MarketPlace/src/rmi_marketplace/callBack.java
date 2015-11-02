
package rmi_marketplace;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface callBack extends Remote{
    public void wishItemFound(String cutomerNameame, String ItemName, float price)  throws RemoteException;
    public void itemSold(String name)  throws RemoteException; 
    public void lowBalance(String name)  throws RemoteException; 
    public String getName() throws RemoteException;
}