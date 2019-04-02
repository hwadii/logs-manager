package anneau.tp3;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * SiteInterface
 */
public interface SiteInterface extends Remote {

  public void getSuivant(int suiv);

  public void election(ArrayList<Integer> l) throws RemoteException;

  public void coordinateur(int idEmeteur, SiteInterface r) throws RemoteException;

  public void ecriture() throws RemoteException;

  public void reponseClient() throws RemoteException;
}