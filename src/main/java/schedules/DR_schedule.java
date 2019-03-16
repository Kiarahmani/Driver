package schedules;

import java.util.ArrayList;
import java.util.Collections;

import sync.OpType;

public final class DR_schedule {
	ArrayList<OpType> execOrder;

	public DR_schedule() {
		execOrder = new ArrayList<OpType>();

		//execOrder.add(new OpType(1, "", 0));
		execOrder.add(new OpType(1, "", 0));
		execOrder.add(new OpType(2, "", 0));
		//execOrder.add(new OpType(2, "", 1));
//		execOrder.add(new OpType(1, "", 0));
//		execOrder.add(new OpType(2, "", 0));
//		execOrder.add(new OpType(3, "", 0));
//		execOrder.add(new OpType(4, "", 0));
//		
//		execOrder.add(new OpType(1, "", 1));
//		execOrder.add(new OpType(2, "", 1));
//		execOrder.add(new OpType(3, "", 1));
//		execOrder.add(new OpType(4, "", 1));

	}

	public ArrayList<OpType> getSchedule() {
		return this.execOrder;
	}

}
