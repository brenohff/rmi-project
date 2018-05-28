import java.rmi.Naming;

public class StartServer {

	public StartServer() {

	}

	public static void main(String args[]) {
		try {
			Server b = new Server();
			Naming.rebind("chat", b);
		} catch (Exception e) {
			System.out.println("Erro ao iniciar servidor.");
			System.out.println(e.getMessage());
			return;
		}
		System.out.println("Servidor pronto...");
	}
}