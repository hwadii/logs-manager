package anneau.tp3;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * ServeurInterface
 */
public interface ServeurInterface extends Remote {

  public void ecritueLogGlobal(String message) throws MalformedURLException, RemoteException, NotBoundException;

  public void ajoutSousReseau(int idSousReseau) throws MalformedURLException, RemoteException, NotBoundException;

  public void demandeLog() throws MalformedURLException, RemoteException, NotBoundException;
}