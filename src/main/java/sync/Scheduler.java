package sync;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Scheduler extends Schedule {
	public Scheduler() {
	}

	public static void main(String args[]) {
		try {
			// Instantiating the implementation class
			Schedule obj = new Schedule();

			// Exporting the object of implementation class
			// (here we are exporting the remote object to the stub)
			RemoteService stub = (RemoteService) UnicastRemoteObject.exportObject(obj, 0);

			// Binding the remote object (stub) in the registry
			LocateRegistry.createRegistry(1099);
			Registry registry = LocateRegistry.getRegistry();

			registry.bind("RemoteService", stub);
			System.err.println("Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}