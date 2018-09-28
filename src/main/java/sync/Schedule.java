package sync;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import schedules.DR_schedule;

//Implementing the remote interface 
public class Schedule implements RemoteService {

	private ArrayList<OpType> execOrder;
	private HashMap<Integer, Boolean> waitingList;
	private int seqIndex;

	public Schedule() {
		this.seqIndex = 0;
		this.execOrder = new DR_schedule().getSchedule(); // this can and should be changed per application/anomaly
		this.waitingList = new HashMap<Integer, Boolean>();
		// initially only the first instance is allowed to move forward
		int head_tid = execOrder.get(0).getTxnInsID();
		for (OpType ot : execOrder) {
			if (ot.getTxnInsID() == head_tid)
				waitingList.put(head_tid, true);
			else
				waitingList.put(ot.getTxnInsID(), false);
		}
	}

	// Implementing the interface method
	public void printTestMsg(int insID) {
		// System.out.println("This is a test message from RMI server implementation
		// requested by insID: " + insID);
	}

	public void execRequest(OpType ot) throws RemoteException {
		// System.out.println("\n===========================");
		if (ot.getTxnInsID() != -1) {
			System.out.println(ot.toString());
			if (execOrder.get(seqIndex).equals(ot)) {
				// System.out.println("pass: " + ot.toString());
				// prepare the lock for the next operation to proceed
				if (seqIndex < execOrder.size() - 1) {
					seqIndex++;
					waitingList.put(ot.getTxnInsID(), false);
					waitingList.put(execOrder.get(seqIndex).getTxnInsID(), true);
				}
			} else {
				while (!waitingList.get(ot.getTxnInsID())) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
				// System.out.println("pass: " + ot.toString());
				// prepare the lock for the next operation to proceed
				if (seqIndex < execOrder.size() - 1) {
					seqIndex++;
					waitingList.put(ot.getTxnInsID(), false);
					waitingList.put(execOrder.get(seqIndex).getTxnInsID(), true);
				}
			}

		}
		// System.out.println("===========================");
	}
}