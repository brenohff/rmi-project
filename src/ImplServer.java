import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface ImplServer extends Remote {

	public void postMessage(String msg, ImplClient client) throws RemoteException;

	public void login(String nomeUsuario, ImplClient c) throws RemoteException;

	public void logout(String nomeUsuario) throws RemoteException;
	
	public boolean podeLogar(String nomeUsuario) throws RemoteException;
	
	public HashMap<String, ImplClient> getCC() throws RemoteException;
	
	public boolean enviaArquivo(ImplClient c, String path) throws RemoteException;
	
	public void zerarCC() throws RemoteException;
}