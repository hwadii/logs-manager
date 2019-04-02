package anneau.tp3;

import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;

public class ProgrammeSite extends UnicastRemoteObject {
    private static final long serialVersionUID = -5525072849826389368L;
    private int id;
    private int relais;

	public ProgrammeSite(int val) throws RemoteException{
		id = val;
	}

    public void run(){

    }

    public void ecriture() throws RemoteException{

    }

    public void reponseClient() throws RemoteException{

    }
    

	public static void main(String[] args) {
		try {
			//ProgrammeSite ps 
		} catch (Exception e) {
			//TODO: handle exception
		}
	}
}