
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

public class StartClient implements Constantes {

	private Client user;
	private static StartClient chat;
	private HashMap<String, ImplClient> cc;
	private static ImplServer b;

	public StartClient(Client user) {
		this.user = user;
	}

	public static void main(String args[]) throws Exception {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		if (b == null) {
			b = conectar("chat");

			System.out.print("Insira seu usuario: ");
			String nomeUsuario = input.readLine();
			if (b.podeLogar(nomeUsuario)) {
				chat = new StartClient(new Client(nomeUsuario));
				chat.login(chat, b);
				b.postMessage(chat.user.getNomeUsuario() + " entrou no chat/entrou", chat.user);
			} else {
				System.out.println("Encerrando sessao... (ja existe um usuario com este nome na sessao)");
				System.exit(1);
			}
		}

		while (true) {
			String mensagem = input.readLine();

			if (mensagem != null) {
				switch (mensagem) {
				case "/menu":
					chat.mostraMenu();
					break;
				case "/chatPublico":
					chat.login(chat, b);
					break;
				case "/chatPrivado":
					chat.usuariosOnline(b);
					break;
				case "/enviaArquivo":
					chat.enviaArquivo(b);
					break;
				case "/abreArquivo":
					chat.abreArquivo();
					break;
				case "/sair":
					b.postMessage(chat.user.getNomeUsuario() + " saiu do chat/deixou", chat.user);
					b.logout(chat.user.getNomeUsuario());
					input.close();
					System.exit(1);
					break;
				default:
					b.postMessage(mensagem, chat.user);
					break;
				}
			}
		}
	}

	private static ImplServer conectar(String endpoint)
			throws MalformedURLException, RemoteException, NotBoundException {
		return (ImplServer) Naming.lookup("rmi://192.168.56.1/" + endpoint);
	}

	private void login(StartClient chat, ImplServer b) throws RemoteException {
		b.login(chat.user.getNomeUsuario(), chat.user);
	}

	private void mostraMenu() {
		System.out.println("");
		System.out.println("||---------------------------------------||");
		System.out.println("||/menu         -> Mostra menu           ||");
		System.out.println("||/chatPublico  -> Inicia o chat publico ||");
		System.out.println("||/chatPrivado  -> Inicia o chat privado ||");
		System.out.println("||/enviaArquivo -> Enviar arquivo        ||");
		System.out.println("||/abreArquivo  -> Abre arquivo          ||");
		System.out.println("||/sair         -> Sai do programa       ||");
		System.out.println("||---------------------------------------||");
		System.out.println("");
	}

	private void usuariosOnline(ImplServer b) throws RemoteException {
		cc = b.getCC();
		List<ImplClient> clients = new ArrayList<>();
		int count = 1;
		for (Entry<String, ImplClient> usuario : cc.entrySet()) {
			if (!user.getNomeUsuario().equals(usuario.getValue().getNomeUsuario())) {
				clients.add(usuario.getValue());
				System.out.println(count + " - " + usuario.getKey());
				count++;
			}
		}

		Scanner scanner = new Scanner(System.in);
		System.out.print("Insira o numero do usuario que voce deseja enviar msg privada: ");
		Integer numero = scanner.nextInt() - 1;

		b.postPrivateMessage(leMsg(clients.get(numero)), clients.get(numero), user);

	}

	private String leMsg(ImplClient client) throws RemoteException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Insira a msg a ser enviada no privado para o " + client.getNomeUsuario() + ":");
		String msgPrivada = scanner.nextLine();

		return msgPrivada;
	}

	// private void usuariosOnline(ImplServer b)
	// throws RemoteException, MalformedURLException, NotBoundException,
	// AlreadyBoundException {
	// cc = b.getCC();
	// List<ImplClient> clients = new ArrayList<>();
	// int count = 1;
	// for (Entry<String, ImplClient> usuario : cc.entrySet()) {
	// clients.add(usuario.getValue());
	// System.out.println(count + " - " + usuario.getKey());
	// count++;
	// }
	//
	// Scanner scanner = new Scanner(System.in);
	// System.out.print("Insira o numero do usuario que voce deseja conversar: ");
	// Integer numero = scanner.nextInt() - 1;
	//
	// try {
	// b.postMessage(chat.user.getNomeUsuario() + " saiu do chat/deixou",
	// chat.user);
	// b.logout(chat.user.getNomeUsuario());
	// b = conectar(clients.get(numero).getNomeUsuario().toLowerCase());
	// b.zerarCC();
	// chat = new StartClient(new Client(clients.get(numero).getNomeUsuario()));
	// login(chat, b);
	// } catch (Exception e) {
	// try {
	// b.postMessage(chat.user.getNomeUsuario() + " saiu do chat/deixou",
	// chat.user);
	// b.logout(chat.user.getNomeUsuario());
	// b = conectar(chat.user.getNomeUsuario().toLowerCase());
	// b.zerarCC();
	// chat = new StartClient(new Client(chat.user.getNomeUsuario()));
	// login(chat, b);
	// } catch (Exception e2) {
	// Naming.rebind(clients.get(numero).getNomeUsuario().toLowerCase(), new
	// Server());
	// b.postMessage(chat.user.getNomeUsuario() + " saiu do chat/deixou",
	// chat.user);
	// b.logout(chat.user.getNomeUsuario());
	// b = conectar(clients.get(numero).getNomeUsuario().toLowerCase());
	// b.zerarCC();
	// chat = new StartClient(new Client(chat.user.getNomeUsuario()));
	// login(chat, b);
	// return;
	// }
	// }
	// }

	@SuppressWarnings("resource")
	private void enviaArquivo(ImplServer b) throws RemoteException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Insira o path do arquivo: ");

		String pathCompleto = scanner.nextLine();

		b.enviaArquivo(user, pathCompleto);
		String[] file = pathCompleto.split("\\\\");
		b.postMessage("Abra o arquivo " + file[file.length - 1], user);
	}

	@SuppressWarnings("resource")
	private void abreArquivo() throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Insira o path do arquivo: ");

		String path = scanner.nextLine();
		
		File file = new File(path);
		File file2 = new File("..\\\\" + path);

		if (!Desktop.isDesktopSupported()) {
			System.out.println("Funcao de abrir nao e suportada.");
			return;
		} else {
			Desktop desktop = Desktop.getDesktop();
			if (file.exists()) {
				desktop.open(file);
			} else if (file2.exists()) {
				desktop.open(file2);
			} else {
				System.out.println("Arquivo nao encontrado.");
			}
		}
	}

	public static ImplServer getImplServer() {
		return b;
	}
}