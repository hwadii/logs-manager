package anneau.tp3;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface GestionnaireInterface extends Remote {
	/** 
	 * methode affichant un message predefini dans l'objet appele
	 */
	public void ajoueSite(int num) throws RemoteException;
	
	public int suivant(int num) throws RemoteException;

	public void panne(int num) throws RemoteException;
	
	public int electionRelai() throws RemoteException;
}