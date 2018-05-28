
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatClient extends UnicastRemoteObject implements IChatClient {
	private static final long serialVersionUID = 6322158345847138669L;
	
	private String alias;

	public ChatClient(String alias) throws RemoteException {
		this.alias = alias;
	}

	@Override
	public String getAlias() throws RemoteException {
		return alias;
	}

	@Override
	public void viewBoardMsg(String msg) throws RemoteException {
		System.out.println(msg);
	}
}