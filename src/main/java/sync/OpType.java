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

	@Override
	public boolean equals(Object obj) {
		OpType op = (OpType) obj;
		return (op.getTxnInsID() == this.txnInsID && op.getSeqNo() == this.seqNo /*&& this.kind.equals(op.getKind())*/);
	}

	public String toString() {
		return kind + "[TID#" + txnInsID + "--Seq#" + seqNo + "]";
	}

	@Override
	public int hashCode() {
		return kind.hashCode() + txnInsID * 100 + seqNo;
	}
}
