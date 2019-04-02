package anneau.tp3;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GestionnaireAnneau extends UnicastRemoteObject {

    private static final long serialVersionUID = -273169080821700334L;
    private int id;
    private ArrayList<Integer> liste = new ArrayList<Integer>();

    public GestionnaireAnneau(int val) throws RemoteException {
        id = val;
    }

    //Service appelé par un nouveau site pour s'ajouter à la liste et actualise les services suivants des sites
    //num: id du nouveau site
    public synchronized void ajoueSite(int num) throws RemoteException {
        liste.add(num);
        if (liste.size()!=1){
            SiteInterface sitePrecedent = (SiteInterface) Naming.lookup("rmi://localhost/Site"+(liste.size()-2)) ;
            sitePrecedent.getSuivant(num);
            SiteInterface siteDernier = (SiteInterface) Naming.lookup("rmi://localhost/Site"+(liste.size()-1)) ;
            siteDernier.getSuivant(liste.get(0));
        }
    }

    //Service appelé par le site détectant la panne pour supprimer le site de la liste et actualise les services suivants des sites
    //num: id du site en panne
    public synchronized void panne(int num) throws RemoteException {
        int indexCourant = liste.indexOf(num);
        if (indexCourant!=0){
            SiteInterface sitePrecedent = (SiteInterface) Naming.lookup("rmi://localhost/Site"+(indexCourant-1)) ;
            if (indexCourant != liste.size()-1){
                sitePrecedent.getSuivant(liste.get(indexCourant+1));
            } else {
                sitePrecedent.getSuivant(liste.get(0));
            }
        } else {
            SiteInterface sitePrecedent = (SiteInterface) Naming.lookup("rmi://localhost/Site"+(liste.size()-1)) ;
            sitePrecedent.getSuivant(liste.get(1));
        }
        liste.remove(num);
    }

    //On crée un gestionnaire d'anneau et on expose ses services
    public static void main(String[] args) throws NumberFormatException, RemoteException, MalformedURLException {
        GestionnaireAnneau serveurAnneau = new GestionnaireAnneau(Integer.parseInt(args[0]));
        Naming.rebind ("SousReseau"+args[0], serveurAnneau);
    }

}
