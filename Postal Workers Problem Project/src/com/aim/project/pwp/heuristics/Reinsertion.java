package com.aim.project.pwp.heuristics;

import java.util.ArrayList;
import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.solution.SolutionRepresentation;

public class Reinsertion extends HeuristicOperators implements HeuristicInterface {

	private final Random oRandom;

	public Reinsertion(Random oRandom) {

		super();

		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {
		int times = 0;

		if (intensityOfMutation < 0.2) {
			times = 1;
		}
		else if (intensityOfMutation < 0.4) {
			times = 2;
		}
		else if (intensityOfMutation < 0.6) {
			times = 3;
		}
		else if (intensityOfMutation < 0.8) {
			times = 4;
		}
		else if (intensityOfMutation < 1.0) {
			times = 5;
		}
		else {
			times = 6;
		}

		SolutionRepresentation solutionRep = (SolutionRepresentation) ((PWPSolutionInterface) solution)
				.getSolutionRepresentation();

		int[] solutionMutatedList = solutionRep.getSolutionRepresentation();
	

		ArrayList mutatedList = new ArrayList();
		for (int i = 0; i < solutionMutatedList.length; i++) {
			mutatedList.add(solutionMutatedList[i]);
		}
		//[3, 6, 5, 8, 9]
		int pick = 0;
		int rnd1=0;
		int rnd2=0;
		for (int i = 0; i < times; i++) {
			// pick random 2 number. numbers can not be same
			rnd1 = oRandom.nextInt(mutatedList.size());// select an index
			pick=(int)mutatedList.remove(rnd1);//get and remove item from list

			rnd2 = oRandom.nextInt(mutatedList.size()); // select another index
			mutatedList.add(rnd2, pick);// add picked item to index 
			
		}
		
		for (int i = 0; i < mutatedList.size(); i++) {
			solutionMutatedList[i]= (int)mutatedList.get(i);
		}
		//set mutated result 
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
