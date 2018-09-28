package sync;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.print.attribute.standard.MediaSize.Other;

import schedules.DR_schedule;

//Implementing the remote interface 
public class Schedule implements RemoteService {

	private ArrayList<OpType> execOrder;
	private int seqIndex;
	private int _DATABASE_DELAY = 80;

	public Schedule() {
		this.seqIndex = 0;
		this.execOrder = new DR_schedule().getSchedule(); // this can and should be changed per application/anomaly
	}

	// Implementing the interface method
	public void printTestMsg(int insID) {
		// System.out.println("This is a test message from RMI server implementation
		// requested by insID: " + insID);
	}

	public void execRequest(OpType ot) throws RemoteException {
		try {
			Thread.sleep(_DATABASE_DELAY);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if (ot.getTxnInsID() != -1) {
			OpType lock = execOrder.get(seqIndex);
			if (ot.equals(lock)) {
				System.out.println(ot.toString());
				if (seqIndex < execOrder.size() - 1) {
					OpType nextLock = execOrder.get(seqIndex + 1);
					synchronized (nextLock) {
						seqIndex++;
						nextLock.notify();
					}
				}
			} else {
				try {
					OpType myLock = execOrder.get(execOrder.indexOf(ot));

					synchronized (myLock) {
						myLock.wait();
						System.out.println(ot.toString());
						if (seqIndex < execOrder.size() - 1) {
							OpType myNextLock = execOrder.get(execOrder.indexOf(ot) + 1);
							synchronized (myNextLock) {
								seqIndex++;
								myNextLock.notify();
							}
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else
			System.out.println(".");
	}
}