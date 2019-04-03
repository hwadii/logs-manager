package anneau.tp3;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * SiteInterface
 */
public interface SiteInterface extends Remote {

  public void getSuivant(int suiv) throws RemoteException, NotBoundException, MalformedURLException;

  public void election(ArrayList<Integer> l) throws RemoteException, NotBoundException, MalformedURLException;

  public void coordinateur(int idEmeteur, SiteInterface r, int idRelai) throws RemoteException, MalformedURLException, NotBoundException;

  public void ecriture() throws RemoteException;

  public void reponseClient() throws RemoteException;
}