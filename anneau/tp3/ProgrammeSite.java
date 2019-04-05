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
	private static String num_sousreseau;
	private GestionnaireInterface gestionnaire;
	private SiteInterface siteSuivant;
	private SiteInterface relai;
	private String log;
	private String tmplog;

	public ProgrammeSite(int val, String sr) throws RemoteException, MalformedURLException, NotBoundException {
		id = val;
		num_sousreseau = sr;
		gestionnaire = (GestionnaireInterface) Naming.lookup("rmi://localhost/SousReseau"+num_sousreseau) ;
		log = "";
		tmplog = "";
	}

	public void run() throws RemoteException, MalformedURLException, NotBoundException {
		idr = gestionnaire.getIdRelai();
		if (idr == -1){
			gestionnaire.setIdRelai(id);
			idr = id;
		}
		relai = (SiteInterface) Naming.lookup("rmi://localhost/Site"+num_sousreseau+idr) ;
		gestionnaire.ajoutSite(id);
		ArrayList<Integer> liste = new ArrayList<Integer>();
		liste.add(id);
		if (siteSuivant != null){
			siteSuivant.election(liste);
			relai.envoieMsgRelai("[" + Util.timestamp() + "] Sous-réseau: " + num_sousreseau + " Site: " + id + " - Nouveau site: " + id);
		}
	}

	public void getSuivant(int suiv) throws RemoteException, NotBoundException, MalformedURLException {
		siteSuivant = (SiteInterface) Naming.lookup("rmi://localhost/Site"+num_sousreseau+suiv) ;
		idSiteSuivant = suiv;
	}

	public void election(ArrayList<Integer> l) throws RemoteException, NotBoundException, MalformedURLException {
		int idRelai = -1;
		for (int idsite : l) 
			if (idsite==id && idRelai == -1){
				idRelai = Collections.max(l);
				idr = idRelai;
				relai = (SiteInterface) Naming.lookup("rmi://localhost/Site"+num_sousreseau+idr) ;
				try {
					relai.envoieMsgRelai("[" + Util.timestamp() + "] " + "Sous-réseau: " + num_sousreseau + " Site: " + id + " - Coordinateur: " + idRelai);
					siteSuivant.coordinateur(id, idr);
				} catch (RemoteException e) {
					relai.envoieMsgRelai("[" + Util.timestamp() + "] " + "Sous-réseau: " + num_sousreseau + " Site: " + id + " - Panne: " + idSiteSuivant);
					gestionnaire.panne(idSiteSuivant);
				}
			}

		if (idRelai==-1){
			ArrayList<Integer> newlist = l;
			newlist.add(id);
			try {
				tmplog += "[" + Util.timestamp() + "] Sous-réseau: " + num_sousreseau + " Site: " + id + " - Election: " + newlist + "\n";
				siteSuivant.election(newlist);
			} catch (RemoteException e) {
				tmplog += "[" + Util.timestamp() + "] Sous-réseau: " + num_sousreseau + " Site: " + id + " - Panne: " + idSiteSuivant + "\n";
				gestionnaire.panne(idSiteSuivant);
			}
		}
	}

	public void coordinateur(int idEmetteur, int idRelai) throws RemoteException, MalformedURLException, NotBoundException {
		if (id == idr) gestionnaire.setIdRelai(id);
		if (id != idEmetteur){
			idr = idRelai;
			relai = (SiteInterface) Naming.lookup("rmi://localhost/Site"+num_sousreseau+idr) ;
			try {
				relai.envoieMsgRelai(tmplog + "[" + Util.timestamp() + "] Sous-réseau: " + num_sousreseau + " Site: " + id + " - Coordinateur: " + idr);
				siteSuivant.coordinateur(idEmetteur, idr);
			} catch (RemoteException e) {
				relai.envoieMsgRelai(tmplog + "[" + Util.timestamp() + "] Sous-réseau: " + num_sousreseau + " Site: " + id + " - Panne: " + idSiteSuivant);
				gestionnaire.panne(idSiteSuivant);
			}
			tmplog = "";
		}
	}

	public void exist() throws RemoteException, MalformedURLException, NotBoundException {
		return;
	}

	public void envoieMsgRelai(String message) throws RemoteException {
		relai.ecriture(message);
	}

	public synchronized void ecriture(String message) throws RemoteException {
		log = log + message + "\n";
		System.out.println("Nouveau log: ");
		System.out.println(log);
	}

	public static void main(String[] args) throws NumberFormatException, RemoteException, MalformedURLException, NotBoundException {
		ProgrammeSite site = new ProgrammeSite(Integer.parseInt(args[0]),args[1]);
		Naming.rebind("rmi://localhost/Site"+num_sousreseau+args[0], site);
		site.run();
	}
}