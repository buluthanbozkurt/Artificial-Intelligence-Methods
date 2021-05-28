package com.aim.lab02;

import java.util.Random;

import com.aimframework.domains.chesc2014_SAT.SAT;
import com.aimframework.satheuristics.SATHeuristic;

public class RandomMutation extends SATHeuristic {
	
	public RandomMutation(Random oRandom) {
		
		super(oRandom);
	}

	/**
	 * PSEUDO-CODE
	 * i <- random \in [0, N];
	 * s' <- flip(i, s);
	 * 
	 * @param oProblem The problem to be solved.
	 */
	@Override
	public void applyHeuristic(SAT oProblem) {

		int i = random.nextInt(oProblem.getNumberOfVariables());
		
		oProblem.bitFlip(i, CURRENT_SOLUTION_INDEX);
		
	}

	@Override
	public String getHeuristicName() {

		return "Single-perturbative Random Mutation";
	}

}
