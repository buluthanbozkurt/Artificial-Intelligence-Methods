package com.aim.project.pwp.heuristics;

import java.util.ArrayList;
import java.util.Random;

import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.solution.SolutionRepresentation;

public class InversionMutation extends HeuristicOperators implements HeuristicInterface {

	private final Random oRandom;

	public InversionMutation(Random oRandom) {

		super();

		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
		int times = 0;

		if (dIntensityOfMutation < 0.2) {
			times = 1;
		}
		if (dIntensityOfMutation < 0.4) {
			times = 2;
		}
		if (dIntensityOfMutation < 0.6) {
			times = 3;
		}
		if (dIntensityOfMutation < 0.8) {
			times = 4;
		}
		if (dIntensityOfMutation < 1.0) {
			times = 5;
		}
		if (dIntensityOfMutation == 1.0) {
			times = 6;
		}

		SolutionRepresentation solutionRep = (SolutionRepresentation) ((PWPSolutionInterface) oSolution)
				.getSolutionRepresentation();

		int[] solutionMutatedList = solutionRep.getSolutionRepresentation();

		// for random pick number
		ArrayList aList = new ArrayList();
		int rnd1 = 0;
		int rnd2 = 0;
		for (int i = 0; i < solutionMutatedList.length; i++) {
			aList.add(i);
		}
		// **********************/
		int temp = 0;
		for (int i = 0; i < times; i++) {
			//pick random 2 number. numbers can not be same
			rnd1 = oRandom.nextInt(aList.size());
			aList.remove(rnd1);
			rnd2 = oRandom.nextInt(aList.size());
			aList.add(rnd1);
			//**************
			//swap
			temp = solutionMutatedList[rnd1];
			solutionMutatedList[rnd1] = solutionMutatedList[rnd2];
			solutionMutatedList[rnd2] = temp;
			//**************
		}
		solutionRep.setSolutionRepresentation(solutionMutatedList);
		
		return 0;// ask what will return ?
		
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
