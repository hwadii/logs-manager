package anneau.tp3;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * ServeurCentral
 */
public class ServeurCentral extends UnicastRemoteObject implements ServeurInterface {

  private static final long serialVersionUID = -2576654020096422139L;
  private String log;
  private ArrayList<GestionnaireInterface> listeSousReseaux;

  public ServeurCentral() throws RemoteException {
    log = "";
    listeSousReseaux = new ArrayList<>();
  }

  @Override
  public void ajoutSousReseau(int idSousReseau) throws MalformedURLException, RemoteException, NotBoundException {
    listeSousReseaux.add((GestionnaireInterface) Naming.lookup("rmi://localhost/SousReseau"+idSousReseau));
  }

  @Override
  public void demandeLog() throws MalformedURLException, RemoteException, NotBoundException {
    ArrayList<Integer> listeIdRelais = new ArrayList<>();
    for (GestionnaireInterface g : listeSousReseaux) {
      listeIdRelais.add(g.getIdRelai());
    }

    SiteInterface relai;
    for (int idRelai : listeIdRelais) {
      relai = (SiteInterface) Naming.lookup("rmi://localhost/Site"+id);
    }
  }

  @Override
  public synchronized void ecritueLogGlobal(String message) throws MalformedURLException, RemoteException, NotBoundException {
    log += message;
  }

  public static void main(String[] args) throws RemoteException, MalformedURLException {
    ServeurCentral serveurCentral = new ServeurCentral();
    Naming.rebind("ServeurCentral", serveurCentral);
  }
}