package com.aim.lab03;


import java.util.Random;

import com.aimframework.domains.chesc2014_SAT.SAT;
import com.aimframework.searchmethods.SinglePointSearchMethod;


public class SimulatedAnnealing extends SinglePointSearchMethod {
	
	private CoolingSchedule oCoolingSchedule;
	
	public SimulatedAnnealing(CoolingSchedule schedule, SAT problem, Random random) {
		
		super(problem, random);
		
		this.oCoolingSchedule = schedule;
	}

	/**
	 *
	 * PSEUDOCODE for Simulated Annealing:
	 *
	 * INPUT : T_0 and any other parameters of the cooling schedule
	 * s_0 = generateInitialSolution();
	 * Temp <- T_0;
	 * s_{best} <- s_0;
	 * s* <- s_0;
	 *
	 * REPEAT
	 *     s' <- bitFlip(s*);
	 *     delta <- f(s') - f(s*);
	 *     r <- random \in [0,1];
	 *     IF delta < 0 OR r < P(delta, Temp) THEN
	 *         s* <- s';
	 *     ENDIF
	 *     s_{best} <- updateBest();
	 *     Temp <- advanceTemperature();
	 * UNTIL termination conditions are satisfied;
	 *
	 * RETURN s_{best};
	 * 
	 * 
	 * REMEMBER That the solutions in the CURRENT_SOLUTION_INDEX and BACKUP_SOLUTION_INDEX
	 * 	should be the same before returning from 'runMainLoop()'!
	 *
	 * EXTRA Marks may be awarded for particularly efficient implementations.
	 */
	protected void runMainLoop() {

			double current = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
			int r = random.nextInt(1);
			problem.bitFlip(r, CURRENT_SOLUTION_INDEX);
			
			if(r<Math.pow(current, r)) {
				
			}
			
	}
		
	public String toString() {
		return "Simulated Annealing with " + oCoolingSchedule.toString();
	}
}
