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
		for(ActionRegister[] z : test.getMiniBatch()) {
			for(ActionRegister s: z) {
				assertEquals(true,(s.vTarget == 25.492900000000002 && s.advantage == 0.9999999998933308) ||
						(s.vTarget == 21.8 && s.advantage == -0.9999999998933308));
			}
		}
	}
}
