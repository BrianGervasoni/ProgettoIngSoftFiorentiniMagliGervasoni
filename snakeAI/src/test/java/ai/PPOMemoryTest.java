package ai;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.ActionRegister;
import model.PPOMemory;

public class PPOMemoryTest {

	@Test
	public void testProcessAction() {
		PPOMemory test = new PPOMemory();
		
		ActionRegister[] r = new ActionRegister[]{ new ActionRegister(), new ActionRegister()};
		
		r[0].reward = 4;
		r[1].reward = 2;
		
		r[0].vEstimated = 10;
		r[1].vEstimated = 20;
		
		test.addNewActions(r);
		test.prepareData();
		for(ActionRegister s : test.getMiniBatch()) {
			assertEquals(true,(s.vTarget == 5.98 && s.advantage == -3.128999999999998) ||
					(s.vTarget == 2.0 && s.advantage == -18.0));
		}
	}
}
