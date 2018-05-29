import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class StartServer {

	public StartServer() {

	}

	public static void main(String args[]) {
		try {
			System.setProperty("java.rmi.server.hostname", "10.61.16.36");
			LocateRegistry.createRegistry(1099);
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