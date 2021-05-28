package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.solution.PWPSolution;


/**
 * 
 * @author Warren G. Jackson
 * Performs adjacent swap, returning the first solution with strict improvement
 *
 */
public class DavissHillClimbing extends HeuristicOperators implements HeuristicInterface {
	
	private final Random oRandom;
	
	public DavissHillClimbing(Random oRandom) {
	
		super();
		
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
		int times = 0;
		if(dDepthOfSearch < 0.2) {
			times = 1;
		}
		else if(dDepthOfSearch < 0.4) {
			times = 2;
		}
		else if(dDepthOfSearch < 0.6) {
			times = 3;
		}
		else if(dDepthOfSearch < 0.8) {
			times = 4;
		}
		else if(dDepthOfSearch < 1.0) {
			times = 5;
		}
		else{
			times = 6;
		}
		int limit=40000;
		PWPSolution pwpSolutionBest = (PWPSolution) oSolution;
		double bestValue = this.oObjectiveFunction.getObjectiveFunctionValue(pwpSolutionBest.getSolutionRepresentation());
		PWPSolution pwpSolutionNext = (PWPSolution) pwpSolutionBest.clone();
		double nextValue=0;
		//[3,5,7,8,1,0]->[3,7,5,8,1,0]
		int [] swapedList=pwpSolutionNext.getSolutionRepresentation().getSolutionRepresentation();
		int rnd1=0;
		int rnd2=0;
		for (int i = 0; i < times; i++) {
			 pwpSolutionBest = (PWPSolution) oSolution;
			 bestValue = pwpSolutionBest.getObjectiveFunctionValue();
			 pwpSolutionNext = (PWPSolution) pwpSolutionBest.clone();
			 nextValue=bestValue+1;
			swapedList=pwpSolutionNext.getSolutionRepresentation().getSolutionRepresentation();
			limit=40000;//for escaping infinitive loop
			while (bestValue <= nextValue && limit >0) {
				
				rnd1= this.oRandom.nextInt(pwpSolutionNext.getNumberOfLocations());
				rnd2= this.oRandom.nextInt(pwpSolutionNext.getNumberOfLocations());
				this.swapDeliveryLocations(swapedList, swapedList[rnd1], swapedList[rnd2]);
				pwpSolutionNext.getSolutionRepresentation().setSolutionRepresentation(swapedList);
				pwpSolutionNext.setObjectiveFunctionValue(this.oObjectiveFunction.getObjectiveFunctionValue(pwpSolutionNext.getSolutionRepresentation()));
				
				
				nextValue=pwpSolutionNext.getObjectiveFunctionValue();
				limit--;
			}
			
			pwpSolutionBest.getSolutionRepresentation().setSolutionRepresentation(swapedList);
			nextValue=0;

		}
		
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
