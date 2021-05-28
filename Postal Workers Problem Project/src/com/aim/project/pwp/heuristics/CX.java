package com.aim.project.pwp.heuristics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;
import com.aim.project.pwp.solution.PWPSolution;


public class CX implements XOHeuristicInterface {
	
	private final Random oRandom;
	
	private ObjectiveFunctionInterface oObjectiveFunction;

	public CX(Random oRandom) {
		
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {
		
		return -1;
		
	}

	@Override
	public double apply(PWPSolutionInterface p1, PWPSolutionInterface p2, PWPSolutionInterface c, double depthOfSearch, double intensityOfMutation) {
		
		int times = 0;
		if(intensityOfMutation < 0.2) {
			times = 1;
		}
		else if(intensityOfMutation < 0.4) {
			times = 2;
		}
		else if(intensityOfMutation < 0.6) {
			times = 3;
		}
		else if(intensityOfMutation < 0.8) {
			times = 4;
		}
		else if(intensityOfMutation < 1.0) {
			times = 5;
		}
		else {
			times = 6;
		}
		
		PWPSolution parent1 = (PWPSolution) p1;
		PWPSolution parent2 = (PWPSolution) p2;
		PWPSolution child = (PWPSolution) c;
		
		
		ArrayList cycle= new ArrayList();
		int[] parent1Genes=parent1.getSolutionRepresentation().getSolutionRepresentation();
		int[] parent2Genes=parent2.getSolutionRepresentation().getSolutionRepresentation();

		int startIndex=0;
		int nextIndex=0;
		for (int i = 0; i < times; i++) {
			cycle= new ArrayList();
			startIndex= this.oRandom.nextInt(parent1.getNumberOfLocations());

			nextIndex=startIndex;
			cycle.add(nextIndex);
			do
			{

				for (int j = 0; j < parent1Genes.length; j++) {
					if(parent1Genes[j]==parent2Genes[nextIndex])
					{
						nextIndex=j;
						break;
					}
				}
				cycle.add(nextIndex);
				
			}while(parent1Genes[startIndex]!=parent2Genes[nextIndex]);
			
			/*child1= parent1Genes.clone();
			child2= parent2Genes.clone();*/
			for (Object o : cycle) {
				int temp= parent1Genes[(int)o];
				parent1Genes[(int)o]= parent2Genes[(int)o];
				parent2Genes[(int)o]=temp;
				
			}
			/*parent1Genes=child1;
			parent2Genes=child2;*/
	
		}
		
		int rnd= this.oRandom.nextInt(1);
		if (rnd==0) {
			child.getSolutionRepresentation().setSolutionRepresentation(parent1Genes);
			child.setObjectiveFunctionValue(this.oObjectiveFunction.getObjectiveFunctionValue(child.getSolutionRepresentation()));
		}
		else
		{
			child.getSolutionRepresentation().setSolutionRepresentation(parent2Genes);
			child.setObjectiveFunctionValue(this.oObjectiveFunction.getObjectiveFunctionValue(child.getSolutionRepresentation()));
		}
		
		return child.getObjectiveFunctionValue();
		
		
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
		
		return false;

	}


	@Override
	public void setObjectiveFunction(ObjectiveFunctionInterface oObjectiveFunction) {
		
		this.oObjectiveFunction = oObjectiveFunction;
	}
}
