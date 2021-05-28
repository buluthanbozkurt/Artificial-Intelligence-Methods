package com.aim.lab05;

import java.util.Random;

import com.aim.lab05.dependencies.BitMutation;
import com.aim.lab05.dependencies.DBHC_IE;
import com.aim.lab05.dependencies.DBHC_OI;
import com.aim.lab05.dependencies.MemeplexInheritanceMethod;
import com.aim.lab05.dependencies.PTX1;
import com.aim.lab05.dependencies.SDHC_IE;
import com.aim.lab05.dependencies.SDHC_OI;
import com.aim.lab05.dependencies.TournamentSelection;
import com.aim.lab05.dependencies.TransGenerationalReplacementWithElitism;
import com.aimframework.domains.chesc2014_SAT.Meme;
import com.aimframework.domains.chesc2014_SAT.SAT;
import com.aimframework.satheuristics.genetics.CrossoverHeuristic;
import com.aimframework.satheuristics.genetics.PopulationHeuristic;
import com.aimframework.satheuristics.genetics.PopulationReplacement;
import com.aimframework.searchmethods.PopulationBasedSearchMethod;

public class MultiMeme extends PopulationBasedSearchMethod {

	/**
	 * The innovation rate setting
	 */
	private final double dInnovationRate;
	
	private final CrossoverHeuristic oCrossoverOperator;
	private final BitMutation oMutationOperator;
	
	private final PopulationReplacement oReplacementMethod;
	private final TournamentSelection oSelectionMethod;
	private final MemeplexInheritanceMethod oInheritanceStrategy;
	
	/**
	 * The possible local search operators to use.
	 */
	private final PopulationHeuristic[] oLocalSearchHeuristics; 
	
	// Constructor used for testing. Please do not remove!
	public MultiMeme(SAT oProblem, Random oRandom, int iPopulationSize, double dInnovationRate, CrossoverHeuristic oCrossoverOperator, 
			BitMutation oMutationOperator, PopulationReplacement oReplacementMethod, TournamentSelection oSelectionMethod, MemeplexInheritanceMethod oInheritanceStrategy,
			PopulationHeuristic[] oLocalSearchHeuristics) {
		
		super(oProblem, oRandom, iPopulationSize);
		
		this.dInnovationRate = dInnovationRate;
		this.oCrossoverOperator = oCrossoverOperator;
		this.oMutationOperator = oMutationOperator;
		this.oReplacementMethod = oReplacementMethod;
		this.oSelectionMethod = oSelectionMethod;
		this.oInheritanceStrategy = oInheritanceStrategy;
		this.oLocalSearchHeuristics = oLocalSearchHeuristics;
	}
	
	public MultiMeme(SAT oProblem, Random oRandom, int iPopulationSize, double dInnovationRate) {
		
		this(
			oProblem,
			oRandom,
			iPopulationSize,
			dInnovationRate,
			new PTX1(oProblem, oRandom), // crossover
			new BitMutation(oProblem, oRandom), // mutation 
			new TransGenerationalReplacementWithElitism(), // replacement 
			new TournamentSelection(iPopulationSize, oRandom, oProblem), // parent selection
			new SimpleInheritanceMethod(oProblem, oRandom), // memeplex inheritance
			new PopulationHeuristic[] { // create mapping for local search operators used for meme in meme index 1
				new DBHC_OI(oProblem, oRandom), // [0]
				new DBHC_IE(oProblem, oRandom), // [1]
				new SDHC_OI(oProblem, oRandom), // [2]
				new SDHC_IE(oProblem, oRandom)  // [3]
			}
		);
	}

	/**
	 * MMA PSEUDOCODE:
	 * 
	 * INPUT: PopulationSize, MaxGenerations, InnovationRate
	 * 
	 * generateInitialPopulation();
	 * FOR 0 -> MaxGenerations
	 * 
	 ####### BEGIN IMPLEMENTING HERE #######
	 *     FOR 0 -> PopulationSize / 2
	 *         select parents using tournament selection with tournament size = 3
	 *         apply crossover to generate offspring
	 *         inherit memeplex using simple inheritance method
	 *         mutate the memes within each memeplex of each child with  probability dependent on the innovation rate
	 *         apply mutation to offspring with intensity of mutation set for each solution dependent on its meme option
	 *         apply local search to offspring with choice of operator dependent on each solution�s meme option
	 *     ENDFOR
	 *     do population replacement
	 ####### STOP IMPLEMENTING HERE #######
	 * ENDFOR
	 * return s_best;
	 */
	public void runMainLoop() {
		
		for(int iIterationCount = 0; iIterationCount < POPULATION_SIZE; iIterationCount += 2) {
			
			//select parents using tournament solution, size 3
			int parent1 = oSelectionMethod.tournamentSelection(3);
			int parent2 = oSelectionMethod.tournamentSelection(3);
			
			//calculating child indices
			int child1 = POPULATION_SIZE + iIterationCount;
			int child2 = POPULATION_SIZE + iIterationCount + 1;
			
			//apply crossover
			oCrossoverOperator.applyHeuristic(parent1, parent2, child1, child2);
			
			//apply inheritance
			oInheritanceStrategy.performMemeticInheritance(parent1, parent2, child1, child2);
			
			//mutate
			performMutationOfMemeplex(child1);
			performMutationOfMemeplex(child2);
			
			//apply mutation to children, index 0
			applyMutationForChildDependentOnMeme(child1, 0);
			applyMutationForChildDependentOnMeme(child2, 0);
			
			//apply local search, index 1
			applyLocalSearchForChildDependentOnMeme(child1, 1);
			applyLocalSearchForChildDependentOnMeme(child2, 1);
			
		}
		
		//do population replacement
		oReplacementMethod.doReplacement(problem, POPULATION_SIZE);
		
	}
	
	/**
	 * Applies mutation to the child dependent on its current meme option for mutation.
	 * Mapping of meme option to IOM: IntensityOfMutation <- memeOption;
	 * 
	 * @param iChildIndex The solution memory index of the child to mutate.
	 * @param iMemeIndex The meme index used for storing the meme relating to the intensity of mutation setting.
	 */
	public void applyMutationForChildDependentOnMeme(int iChildIndex, int iMemeIndex) {
		
		int intensityOfMutation = problem.getMeme(iChildIndex, iMemeIndex).getMemeOption()+1;
		
		oMutationOperator.setMutationRate(intensityOfMutation);
		oMutationOperator.applyHeuristic(iChildIndex);
		
	}
	
	/**
	 * Applies the local search operator to the child as specified by its current meme option.
	 * 
	 * @param iChildIndex The solution memory index of the child to mutate.
	 * @param iMemeIndex The meme index used for storing the meme relating to the local search operator setting.
	 */
	public void applyLocalSearchForChildDependentOnMeme(int iChildIndex, int iMemeIndex) {
		
		int search = problem.getMeme(iChildIndex, iMemeIndex).getMemeOption();	
		
		oLocalSearchHeuristics[search].applyHeuristic(iChildIndex);
	}
	
	/**
	 * Applies mutation to each meme within the memeplex of the specified solution with probability
	 * dependent on the innovation rate.
	 * 
	 * HINT: mutation does not mean bit flip; it only means in this case 
	 * 		that you should MODIFY the current value of the meme option
	 * 		subject to the above definition.
	 * 
	 * @param iSolutionMemoryIndex The solution memory index of the solution to mutate the memeplex of.
	 */
	public void performMutationOfMemeplex(int iSolutionMemoryIndex) {
		
		for(int i = 0; i < problem.getNumberOfMemes(); i++) {
			
			if(rng.nextDouble() < dInnovationRate) {
				int thisOption;
				Meme thisMeme = problem.getMeme(iSolutionMemoryIndex, i);
				
				do {thisOption = rng.nextInt(thisMeme.getTotalOptions());
				}while(thisOption == thisMeme.getMemeOption());
												
				problem.getMeme(iSolutionMemoryIndex, i).setMemeOption(thisOption);

			}
			
			
		}
	}
	
	public String toString() {
		
		return "Multimeme Memetic Algorithm";
	}
	
}
