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
    private int idrelai;

    public GestionnaireAnneau(int val) throws RemoteException {
        id = val;
    }

    public synchronized void ajoueSite(int num) throws RemoteException {
        liste.add(num);
    }

    public int suivant(int num) throws RemoteException {
        if (liste.indexOf(num) != liste.size()-1) return liste.get(liste.indexOf(num)+1);
        else return 0;
    }

    public synchronized void panne(int num) throws RemoteException {
        liste.remove(num);
        if (idrelai == num) idrelai = -1;
    }

    public synchronized int electionRelai() throws RemoteException{
        if (idrelai==-1){
            for (int idsite : liste){
                if (idsite > idrelai) idrelai = idsite;
            }
        }
        return idrelai;
    }

    public static void main(String[] args) throws NumberFormatException, RemoteException, MalformedURLException {
        GestionnaireAnneau serveurAnneau = new GestionnaireAnneau(Integer.parseInt(args[0]));
        Naming.rebind ("Site"+args[0], serveurAnneau);
    }

}
