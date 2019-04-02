package anneau.tp3;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class ProgrammeSite extends UnicastRemoteObject {
	private static final long serialVersionUID = -5525072849826389368L;
	private int id;
	private int relais;
	private String num_sousreseau;
	private GestionnaireInterface gestionnaire;
	private SiteInterface siteSuivant;

	public ProgrammeSite(int val, String sr) throws RemoteException, MalformedURLException, NotBoundException {
		id = val;
		num_sousreseau = sr;
		gestionnaire = (GestionnaireInterface) Naming.lookup("rmi://localhost/SousReseau"+num_sousreseau) ;
	}

	public void run(){
		gestionnaire.ajoueSite(id);
		//Panne détectée ou première élection
		
	}

	public void getSuivant(int suiv) throws RemoteException {
		siteSuivant = (SiteInterface) Naming.lookup("rmi://localhost/Site"+suiv) ;
	}

	public void envoieListe(ArrayList<Integer> l) throws RemoteException {
		for (int idsite : l) if (idsite==id){
			//Collections.max(l);
		}
		ArrayList<Integer> newlist = l;
		newlist.add(id);
		int idsuiv = gestionnaire.suivant(id);
		.envoieListe()
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