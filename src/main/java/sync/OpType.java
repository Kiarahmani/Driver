package sync;

import java.io.Serializable;

public class OpType implements Serializable {
	/**
	 * 
	 */
	private int txnInsID;
	private String kind;
	private int seqNo;

	public OpType(int txnInsID, String kind, int seqNo) {
		this.kind = kind;
		this.seqNo = seqNo;
		this.txnInsID = txnInsID;
	}

	public int getTxnInsID() {
		return txnInsID;
	}

	public String getKind() {
		return kind;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public boolean isEqual(OpType other) {
		return (other.getTxnInsID() == this.txnInsID && other.getSeqNo() == this.seqNo
				&& this.kind.equals(other.getKind()));
	}
	
	public String toString() {
		return kind+"[TID#"+txnInsID+"--Seq#"+seqNo+"]";
	}

}
