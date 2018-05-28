
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Board extends UnicastRemoteObject implements IBoard {

	private static final long serialVersionUID = -5078513634955730630L;

	private HashMap<String, IChatClient> cc = new HashMap<String, IChatClient>();

	public Board() throws RemoteException {
	}

	@Override
	public void postMessage(String msg, IChatClient userWhoSent) throws RemoteException {
		Collection<IChatClient> usersOnline = cc.values();
		Iterator<IChatClient> iter = usersOnline.iterator();
		
		while (iter.hasNext()) {
			IChatClient icc = (IChatClient) iter.next();
			if (!userWhoSent.equals(cc.get(icc.getNomeUsuario()))) {
				icc.viewBoardMsg(userWhoSent.getNomeUsuario() + " disse: " + msg);
			}
		}
	}

	@Override
	public void login(String alias, IChatClient c) throws RemoteException {
		cc.put(alias, c);
		System.out.println(alias + " entrou - Usuarios online: " + cc.size());
	}

	@Override
	public void logout(String alias) throws RemoteException {
		cc.remove(alias);
		System.out.println(alias + " saiu - Usuarios online: " + cc.size());
	}
}