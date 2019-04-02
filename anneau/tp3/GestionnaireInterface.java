package anneau.tp3;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface GestionnaireInterface extends Remote {
	/** 
	 * methode affichant un message predefini dans l'objet appele
	 * @throws NotBoundException
	 * @throws RemoteException
	 * @throws MalformedURLException
	 */
	public void ajoueSite(int num) throws MalformedURLException, RemoteException, NotBoundException;

	public void panne(int num) throws RemoteException, MalformedURLException, NotBoundException;
}