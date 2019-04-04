package anneau.tp3;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GestionnaireAnneau extends UnicastRemoteObject implements GestionnaireInterface {

    private static final long serialVersionUID = -273169080821700334L;
    private int id;
    private ArrayList<Integer> liste = new ArrayList<Integer>();

    public GestionnaireAnneau(int val) throws RemoteException {
        id = val;
    }

    // Service appelé par un nouveau site pour s'ajouter à la liste et actualise les
    // services suivants des sites
    // num: id du nouveau site
    public synchronized void ajoueSite(int num) throws MalformedURLException, RemoteException, NotBoundException {
        if (liste.size()>0){
            try {
                if (liste.contains(num)) liste.remove(liste.indexOf(num));
                SiteInterface sitePrecedent = (SiteInterface) Naming.lookup("rmi://localhost/Site"+(liste.get(liste.size()-1)));
                sitePrecedent.getSuivant(num);
                try {
                    SiteInterface siteDernier = (SiteInterface) Naming.lookup("rmi://localhost/Site"+num);
                    siteDernier.getSuivant(liste.get(0));
                    SiteInterface sitePremier = (SiteInterface) Naming.lookup("rmi://localhost/Site" + liste.get(0));
                    sitePremier.exist();
                    liste.add(num);
                    System.out.println("Ajout du site " + num);
                    System.out.println(liste);
                } catch (RemoteException e) {
                    System.out.println("Panne du suivant " + liste.get(0));
                    panne(liste.get(0));
                    ajoueSite(num);
                }
            } catch (RemoteException e) {
                System.out.println("Panne du précédent " + liste.get(liste.size()-1));
                panne(liste.get(liste.size()-1));
                ajoueSite(num);
            }
        } else {
            liste.add(num);
            System.out.println("Ajout du site else " + num);
            System.out.println(liste);
        }
    }

    //Service appelé par le site détectant la panne pour supprimer le site de la liste et actualise les services suivants des sites
    //num: id du site en panne
    public synchronized void panne(int num) throws RemoteException, MalformedURLException, NotBoundException {
        int indexCourant = liste.indexOf(num);
        System.out.println("Panne du site " + num);
        if (indexCourant!=0){
            SiteInterface sitePrecedent = (SiteInterface) Naming.lookup("rmi://localhost/Site"+(indexCourant-1));
            if (indexCourant != liste.size()-1){
                sitePrecedent.getSuivant(liste.get(indexCourant+1));
            } else if (liste.size() > 2) {
                sitePrecedent.getSuivant(liste.get(0));
            }
        } else if (liste.size() > 2) {
            SiteInterface sitePrecedent = (SiteInterface) Naming.lookup("rmi://localhost/Site"+(liste.size()-1)) ;
            sitePrecedent.getSuivant(liste.get(1));
        }

        liste.remove(liste.indexOf(num));
        System.out.println(liste);
    }

    //On crée un gestionnaire d'anneau et on expose ses services
    public static void main(String[] args) throws NumberFormatException, RemoteException, MalformedURLException {
        GestionnaireAnneau serveurAnneau = new GestionnaireAnneau(Integer.parseInt(args[0]));
        Naming.rebind ("SousReseau"+args[0], serveurAnneau);
    }

}
