package anneau.tp3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
  private ArrayList<Integer> listeSousReseaux;
  private ArrayList<Integer> listeIdRelais;

  public ServeurCentral() throws RemoteException {
    log = "";
    listeSousReseaux = new ArrayList<>();
    listeIdRelais = new ArrayList<>();
  }

  @Override
  public void ajoutSousReseau(int idSousReseau) throws MalformedURLException, RemoteException, NotBoundException {
    listeSousReseaux.add(idSousReseau);
  }

  public void run() throws MalformedURLException, RemoteException, NotBoundException {
    System.out.println("je suis dans le run");
    listeIdRelais.clear();
    GestionnaireInterface g;
    for (int id : listeSousReseaux) {
      g = (GestionnaireInterface) Naming.lookup("rmi://localhost/SousReseau"+id);
      listeIdRelais.add(g.getIdRelai());
    }
    
    System.out.println("Sous reseaux: " + listeSousReseaux);
    System.out.println("Id Relais " + listeIdRelais);
    SiteInterface relai;
    for (int i = 0; i < listeIdRelais.size(); i++) {
      try {
        if (listeIdRelais.get(i) != -1) {
          relai = (SiteInterface) Naming.lookup("rmi://localhost/Site"+listeSousReseaux.get(i)+listeIdRelais.get(i));
          relai.ecritureGlobal();
          System.out.println(listeSousReseaux.get(i) + " " +listeIdRelais.get(i));
        }
      } catch (RemoteException  e) {
        g = (GestionnaireInterface) Naming.lookup("rmi://localhost/SousReseau"+listeSousReseaux.get(i));
        g.panne(listeIdRelais.get(i));
      }
    }

    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt"));
      writer.write(log);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // System.out.println(log);
  }

  @Override
  public synchronized void ecritureLogGlobal(String message) throws MalformedURLException, RemoteException, NotBoundException {
    log += message;
  }

  public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException, InterruptedException{
    ServeurCentral serveurCentral = new ServeurCentral();
    Naming.rebind("ServeurCentral", serveurCentral);
    while (true) {
      serveurCentral.run();
      Thread.sleep(5000);
    }
  }
}