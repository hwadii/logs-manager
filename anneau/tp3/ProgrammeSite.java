package anneau.tp3;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class ProgrammeSite extends UnicastRemoteObject {
	private static final long serialVersionUID = -5525072849826389368L;
	private int id;
	private String num_sousreseau;
	private GestionnaireInterface gestionnaire;
	private SiteInterface siteSuivant;
	private SiteInterface relai;

	public ProgrammeSite(int val, String sr) throws RemoteException, MalformedURLException, NotBoundException {
		id = val;
		num_sousreseau = sr;
		gestionnaire = (GestionnaireInterface) Naming.lookup("rmi://localhost/SousReseau"+num_sousreseau) ;
	}

	public void run() throws RemoteException {
		gestionnaire.ajoueSite(id);
		ArrayList<Integer> liste = new ArrayList<Integer>();
		liste.add(id);
		siteSuivant.election(liste);		
	}

	public void getSuivant(int suiv) throws RemoteException, NotBoundException, MalformedURLException {
		siteSuivant = (SiteInterface) Naming.lookup("rmi://localhost/Site"+suiv) ;
	}

	public void election(ArrayList<Integer> l) throws RemoteException, NotBoundException, MalformedURLException {
		int idRelai = -1;
		for (int idsite : l) 
			if (idsite==id){
				idRelai = Collections.max(l);
				SiteInterface r = (SiteInterface) Naming.lookup("rmi://localhost/Site"+idRelai) ;
				siteSuivant.coordinateur(id, r);
				return;
			}

		if (idRelai==-1){
			ArrayList<Integer> newlist = l;
			newlist.add(id);
			siteSuivant.election(newlist);
		}
	}

	public void coordinateur(int idEmetteur, SiteInterface r ) throws RemoteException {
		if (id != idEmetteur){
			relai = r;
			siteSuivant.coordinateur(idEmetteur, r);
		}
	}

	public synchronized void ecriture() throws RemoteException {

	}

	public void reponseClient() throws RemoteException {

	}

	public static void main(String[] args) throws NumberFormatException, RemoteException, MalformedURLException, NotBoundException {
		ProgrammeSite site = new ProgrammeSite(Integer.parseInt(args[0]),args[1]);
    	Naming.rebind ("Site"+args[0], site);
	}
}