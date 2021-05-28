package com.aim.project.pwp.heuristics;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;
import com.aim.project.pwp.solution.PWPSolution;

public class OX implements XOHeuristicInterface {

	private final Random oRandom;

	private ObjectiveFunctionInterface oObjectiveFunction;

	public OX(Random oRandom) {

		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {

		return -1;
	}

	@Override
	public double apply(PWPSolutionInterface p1, PWPSolutionInterface p2, PWPSolutionInterface c, double depthOfSearch,
			double intensityOfMutation) {
		int times = 0;
		if (intensityOfMutation < 0.2) {
			times = 1;
		} else if (intensityOfMutation < 0.4) {
			times = 2;
		} else if (intensityOfMutation < 0.6) {
			times = 3;
		} else if (intensityOfMutation < 0.8) {
			times = 4;
		} else if (intensityOfMutation < 1.0) {
			times = 5;
		} else {
			times = 6;
		}

		PWPSolution parent1 = (PWPSolution) p1;
		PWPSolution parent2 = (PWPSolution) p2;
		PWPSolution child = (PWPSolution) c;
		int cut1 = 0;
		int cut2 = 0;
		int[] parent1Genes = parent1.getSolutionRepresentation().getSolutionRepresentation().clone();
		int[] parent2Genes = parent2.getSolutionRepresentation().getSolutionRepresentation().clone();
		int[] child1 = null;
		int[] child2 = null;
		/*for (int i = 0; i < parent1Genes.length; i++) {
			System.out.print(parent1Genes[i]+" ");
		}
		for (int i = 0; i < parent1Genes.length; i++) {
			System.out.print(parent2Genes[i]+" ");
		}*/
		//[3 5 6 7 9 1 2]
		//[2 9 1 3 5 6 7]
		// 2 , 5
		for (int i = 0; i < times; i++) {
			//for limiting cut size
			cut1 = this.oRandom.nextInt(parent1.getNumberOfLocations() - 4)+1;
			cut2 = this.oRandom.nextInt(4)+1 + cut1;

			child1 = new int[parent1.getNumberOfLocations()];
			child2 = new int[parent1.getNumberOfLocations()];

			for (int j = cut1; j < cut2; j++) {
				child1[j] = parent2Genes[j]; //[0 0 1 3 5 6 0]
				child2[j] = parent1Genes[j];// [0 0 6 7 9 1 0]
			}
			int complater1=cut2;
			int complater2 = cut2;
			
			while (complater2 != cut1) {
				complater1 = complater1 % parent1Genes.length;
				complater2 = complater2 % parent1Genes.length;
				
				//comparision 
				boolean isFind = false;
				for (int j = cut1; j < cut2; j++) {
					if (child1[j] == parent1Genes[complater1]) {
						isFind = true;
						break;
					}
				}
				// if child not has that gen you can put to index
				if (!isFind) {
					child1[complater2] = parent1Genes[complater1];
					complater2++;
				}
				complater1++;
				
			}
			complater1 = cut2;
			complater2 = cut2;
			while (complater2 != cut1) {
				complater1 = complater1 % parent2Genes.length;
				complater2 = complater2 % parent2Genes.length;
			
				boolean isFind = false;
				for (int j = cut1; j < cut2; j++) {
					if (child2[j] == parent2Genes[complater1]) {
						isFind = true;
						break;
					}
				}
				if (!isFind) {
					child2[complater2] = parent2Genes[complater1];
					complater2++;
				}
				complater1++;
			}
			parent1Genes=child1;
			parent2Genes= child2;
			
		}

		int rnd = this.oRandom.nextInt(1);
		if (rnd == 0) {
			child.getSolutionRepresentation().setSolutionRepresentation(parent1Genes);
			child.setObjectiveFunctionValue(
					this.oObjectiveFunction.getObjectiveFunctionValue(child.getSolutionRepresentation()));
		} else {
			child.getSolutionRepresentation().setSolutionRepresentation(parent2Genes);
			child.setObjectiveFunctionValue(
					this.oObjectiveFunction.getObjectiveFunctionValue(child.getSolutionRepresentation()));
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
	public void setObjectiveFunction(ObjectiveFunctionInterface f) {

		this.oObjectiveFunction = f;
	}
}
