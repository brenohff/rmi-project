
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
				if (msg.endsWith("saiu do chat/deixou") || msg.endsWith("entrou no chat/entrou")) {
					icc.viewBoardMsg(msg.substring(0, msg.length() - 7));
				} else {
					icc.viewBoardMsg(userWhoSent.getNomeUsuario() + " disse: " + msg);
				}
			}
		}
	}

	@Override
	public void login(String nomeUsuario, IChatClient c) throws RemoteException {
		cc.put(nomeUsuario, c);
		System.out.println(nomeUsuario + " entrou - Usuarios online: " + cc.size());

	}

	@Override
	public void logout(String nomeUsuario) throws RemoteException {
		cc.remove(nomeUsuario);
		System.out.println(nomeUsuario + " saiu - Usuarios online: " + cc.size());
	}

	@Override
	public boolean podeLogar(String nomeUsuario) {
		if (!cc.containsKey(nomeUsuario)) {
			return true;
		} else {
			System.out.printf("Ja existe um usuario com o nome de \"%s\" na sessao.", nomeUsuario);
		}
		return false;
	}
}