package sync;

//Implementing the remote interface 
public class Schedule implements RemoteService {

	// Implementing the interface method
	public void printTestMsg(int insID) {
		System.out.println("This is a test message from RMI server implementation requested by insID: " + insID);
	}
}