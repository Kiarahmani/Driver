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
	private int _DATABASE_DELAY;
	private boolean _SHOULD_ENFORCE;

	public Schedule(int delay, boolean shouldEnforce) {
		this.seqIndex = 0;
		this.execOrder = new DR_schedule().getSchedule(); // this can and should be changed per application/anomaly
		this._SHOULD_ENFORCE = shouldEnforce;
		this._DATABASE_DELAY = delay;

	}

	// Implementing the interface method
	public void printTestMsg(int insID) {
		// System.out.println("This is a test message from RMI server implementation
		// requested by insID: " + insID);
	}

	public void execRequest(OpType ot) throws RemoteException {
		System.out.println("requested:   " + ot);
		try {
			Thread.sleep(_DATABASE_DELAY);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if (_SHOULD_ENFORCE && ot.getTxnInsID() != -1) {
			OpType lock = execOrder.get(seqIndex);
			// if it's ot's turn
			if (ot.equals(lock)) {
				if (seqIndex < execOrder.size() - 1) {
					OpType nextLock = execOrder.get(seqIndex + 1);
					synchronized (nextLock) {
						seqIndex++;
						System.out.println(seqIndex + ": permitting:  " + ot);
						try {
							Thread.sleep(_DATABASE_DELAY);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						nextLock.notify();
					}
				}
			} else {
				try {
					int myOrder = execOrder.indexOf(ot);
					if (myOrder == -1) {
						System.out.println(seqIndex + ": unspecified: " + ot);
						return;
					}
					// if there is a single specifications for this operations
					// then must go to the end
					if (myOrder == execOrder.lastIndexOf(ot)) {
						if (myOrder <= seqIndex) {
							System.out.println("loop pattrn: " + ot);
							return;
						}
					} else {
						System.out.println("seqIndex:" + seqIndex);
						System.out.println("execOrder.size():" + execOrder.size());
						if (execOrder.lastIndexOf(ot) > seqIndex)
							for (int i = seqIndex; i < execOrder.size(); i++) {
								if (execOrder.get(i).equals(ot)) {
									myOrder = i;
									break;
								}
							}
						else {
							// if there are multiple specs but we have already covered them all
							return;
						}
					}
					System.out.println(seqIndex + ": I am going to wait on: " + myOrder);
					OpType myLock = execOrder.get(myOrder);
					synchronized (myLock) {
						System.out.println(ot.getKind() + "is waiting... on " + myOrder);
						myLock.wait();
						System.out.println("permitting:  " + ot);
						if (seqIndex < execOrder.size() - 1) {
							OpType myNextLock = execOrder.get(execOrder.indexOf(ot) + 1);
							synchronized (myNextLock) {
								seqIndex++;
								// System.out.println("permitting: " + ot);

								try {
									Thread.sleep(_DATABASE_DELAY);
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
								myNextLock.notify();
							}
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
		}
		// System.out.println(".");
	}
}