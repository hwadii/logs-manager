package anneau.tp3;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class ProgrammeSite extends UnicastRemoteObject {
	private static final long serialVersionUID = -5525072849826389368L;
	private int id;
	private int relais;
	private String num_sousreseau;
	private GestionnaireInterface gestionnaire;

	public ProgrammeSite(int val, String sr) throws RemoteException {
		id = val;
		num_sousreseau = sr;
	}

	public void run() throws MalformedURLException, RemoteException, NotBoundException {
		GestionnaireInterface gestionnaire = (GestionnaireInterface) Naming.lookup("rmi://localhost/Site"+num_sousreseau) ;
	}

	public synchronized void ecriture() throws RemoteException {

	}

	public void reponseClient() throws RemoteException {

	}

	public static void main(String[] args) throws NumberFormatException, RemoteException, MalformedURLException {
		ProgrammeSite site = new ProgrammeSite(Integer.parseInt(args[0]),args[1]);
    Naming.rebind ("Site"+args[0], site);
	}
}