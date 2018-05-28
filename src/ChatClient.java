
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatClient extends UnicastRemoteObject implements IChatClient {
	private static final long serialVersionUID = 6322158345847138669L;
	
	private String nomeUsuario;

	public ChatClient(String nomeUsuario) throws RemoteException {
		this.nomeUsuario = nomeUsuario;
	}

	@Override
	public String getNomeUsuario() throws RemoteException {
		return nomeUsuario;
	}

	@Override
	public void viewBoardMsg(String msg) throws RemoteException {
		System.out.println(msg);
	}
}