package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.SolutionRepresentationInterface;
import com.aim.project.pwp.solution.PWPSolution;
import com.aim.project.pwp.solution.SolutionRepresentation;

/**
 * 
 * @author Warren G. Jackson Performs adjacent swap, returning the first
 *         solution with strict improvement
 *
 */
public class NextDescent extends HeuristicOperators implements HeuristicInterface {

	private final Random oRandom;

	public NextDescent(Random oRandom) {

		super();

		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
		int times = 0;

		if (dDepthOfSearch < 0.2) {
			times = 1;
		}
		else if (dDepthOfSearch < 0.4) {
			times = 2;
		}
		else if (dDepthOfSearch < 0.6) {
			times = 3;
		}
		else if (dDepthOfSearch < 0.8) {
			times = 4;
		}
		else if (dDepthOfSearch < 1.0) {
			times = 5;
		}
		else {
			times = 6;
		}

		PWPSolution pwpSolutionBest = (PWPSolution) oSolution;
		double bestValue = this.oObjectiveFunction
				.getObjectiveFunctionValue(pwpSolutionBest.getSolutionRepresentation());
		int[] listNext = pwpSolutionBest.getSolutionRepresentation().getSolutionRepresentation().clone();

		for (int i = 0; i < times; i++) {
			int rnd = this.oRandom.nextInt(listNext.length-2);
			int indexStart = rnd;
			int index = rnd;
			int next1 = index + 1;
			int next2 = index + 2;
			double next1Distance = 0;
			double next2Distance = 0;
			while (next2 != indexStart) {

		
				next1Distance = this.oObjectiveFunction.getCost(listNext[index], listNext[next1]);
				next2Distance = this.oObjectiveFunction.getCost(listNext[index], listNext[next2]);
				//next  descent
				if (next1Distance >= next2Distance) 
				{
					listNext=this.swapDeliveryLocations(listNext, next1, next2);
					break;
				}

				index = index + 1;
				next1 = next1 + 1;
				next2 = next2 + 1;
				// indexes return zero
				if (next1 >= listNext.length) {
					next1 = 0;
				}
				if (next2 >= listNext.length) {
					next2 = 0;
				}
				if (index == listNext.length) {
					index = 0;

				}
				//System.out.println(next2+"   "+indexStart);
			}

		}
		
		pwpSolutionBest.getSolutionRepresentation().setSolutionRepresentation(listNext);

		pwpSolutionBest.setObjectiveFunctionValue(this.oObjectiveFunction.getObjectiveFunctionValue(pwpSolutionBest.getSolutionRepresentation()));
		return pwpSolutionBest.getObjectiveFunctionValue();

	}

	@Override
	public boolean isCrossover() {

		return false;

	}

	@Override
	public boolean usesIntensityOfMutation() {

		return false;

	}

	@Override
	public boolean usesDepthOfSearch() {

		return true;

	}
}
