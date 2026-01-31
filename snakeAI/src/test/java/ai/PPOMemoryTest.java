package ai;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import progettoAI.snakeAI.model.ActionRegister;
import progettoAI.snakeAI.model.PPOMemory;

public class PPOMemoryTest {

	@Test
	public void testProcessAction() {
		PPOMemory test = new PPOMemory();
		
		ActionRegister[] r = new ActionRegister[]{ new ActionRegister(), new ActionRegister()};
		
		r[0].reward = 4;
		r[1].reward = 2;
		
		r[0].vEstimated = 10;
		r[1].vEstimated = 20;
		
		test.addNewActions(r,0);
		test.prepareData();
		System.out.println(test.toString());
		for(ActionRegister[] z : test.getMiniBatch()) {
			for(ActionRegister s: z) {
				assertEquals(true,(s.vTarget == 7.419999999999998 && s.advantage == -2.580000000000002) ||
						(s.vTarget == 2.0 && s.advantage == -18.0));
			}
		}
	}
}
