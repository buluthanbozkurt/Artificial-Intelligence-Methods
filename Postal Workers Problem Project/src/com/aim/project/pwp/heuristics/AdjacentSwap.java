package com.aim.project.pwp.heuristics;

import java.util.ArrayList;
import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.solution.SolutionRepresentation;

public class AdjacentSwap extends HeuristicOperators implements HeuristicInterface {

	private final Random oRandom;

	public AdjacentSwap(Random oRandom) {

		super();

		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double depthOfSearch, double intensityOfMutation) {

		int times = 0;

		if (intensityOfMutation < 0.2) {
			times = 1;
		}
		else if (intensityOfMutation < 0.4) {
			times = 2;
		}
		else if (intensityOfMutation < 0.6) {
			times = 4;
		}
		else if (intensityOfMutation < 0.8) {
			times = 8;
		}
		else if (intensityOfMutation < 1.0) {
			times = 16;
		}
		else {
			times = 32;
		}
		SolutionRepresentation solutionRep = (SolutionRepresentation) ((PWPSolutionInterface) oSolution)
				.getSolutionRepresentation();

		int[] solutionMutatedList = solutionRep.getSolutionRepresentation();

		int temp = 0;
		int rnd = 0;
		int next = 0;
		for (int i = 0; i < times; i++) {
			rnd = oRandom.nextInt(solutionMutatedList.length);
			// swap
			next = rnd + 1;
			if (next == solutionMutatedList.length) {
				next = 0;
			}
			//swapping
			temp = solutionMutatedList[rnd];
			solutionMutatedList[rnd] = solutionMutatedList[next];
			solutionMutatedList[next] = temp;
			// **************
		}
		
		solutionRep.setSolutionRepresentation(solutionMutatedList);
		return 0;// ask what will return?
	}

	@Override
	public boolean isCrossover() {

		return false;

	}

	@Override
	public boolean usesIntensityOfMutation() {

		return true;

	}

	@Override
	public boolean usesDepthOfSearch() {

		return false;

	}

}
