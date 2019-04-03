package anneau.tp3;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;

public class ProgrammeSite extends UnicastRemoteObject implements SiteInterface {
	private static final long serialVersionUID = -5525072849826389368L;
	private int id;
	private int idr;
	private int idSiteSuivant;
	private String num_sousreseau;
	private GestionnaireInterface gestionnaire;
	private SiteInterface siteSuivant;
	private SiteInterface relai;

	public ProgrammeSite(int val, String sr) throws RemoteException, MalformedURLException, NotBoundException {
		id = val;
		num_sousreseau = sr;
		gestionnaire = (GestionnaireInterface) Naming.lookup("rmi://localhost/SousReseau"+num_sousreseau) ;
	}

	public void run() throws RemoteException, MalformedURLException, NotBoundException {
		gestionnaire.ajoueSite(id);
		ArrayList<Integer> liste = new ArrayList<Integer>();
		liste.add(id);
		if (siteSuivant != null)
			siteSuivant.election(liste);
	}

	public void getSuivant(int suiv) throws RemoteException, NotBoundException, MalformedURLException {
		siteSuivant = (SiteInterface) Naming.lookup("rmi://localhost/Site"+suiv) ;
		idSiteSuivant = suiv;
	}

	public void election(ArrayList<Integer> l) throws RemoteException, NotBoundException, MalformedURLException {
		int idRelai = -1;
		for (int idsite : l) 
			if (idsite==id && idRelai == -1){
				idRelai = Collections.max(l);
				idr = idRelai;
				SiteInterface r = (SiteInterface) Naming.lookup("rmi://localhost/Site"+idRelai) ;
				try {
					System.out.println("Coordinateur: id " + id + ", r " + idRelai);
					siteSuivant.coordinateur(id, r, idr);
				} catch (RemoteException e) {
					System.out.println("Panne");
					gestionnaire.panne(idSiteSuivant);
				}
			}

		if (idRelai==-1){
			ArrayList<Integer> newlist = l;
			newlist.add(id);
			try {
				System.out.println("Election " + newlist);
				siteSuivant.election(newlist);
			} catch (RemoteException e) {
				System.out.println("Panne");
				gestionnaire.panne(idSiteSuivant);
			}
		}
	}

	public void coordinateur(int idEmetteur, SiteInterface r, int idRelai) throws RemoteException, MalformedURLException, NotBoundException {
		if (id != idEmetteur){
			relai = r;
			idr = idRelai;
			try {
				System.out.println("Coordinateur: " + idr);
				siteSuivant.coordinateur(idEmetteur, r, idr);
			} catch (RemoteException e) {
				System.out.println("Panne");
				gestionnaire.panne(idSiteSuivant);
			}
		}
	}

	public void exist() throws RemoteException, MalformedURLException, NotBoundException {
		return;
	}

	public synchronized void ecriture() throws RemoteException {

	}

	public void reponseClient() throws RemoteException {

	}

	public static void main(String[] args) throws NumberFormatException, RemoteException, MalformedURLException, NotBoundException {
		ProgrammeSite site = new ProgrammeSite(Integer.parseInt(args[0]),args[1]);
		Naming.rebind ("rmi://localhost/Site"+args[0], site);
		site.run();
	}
}