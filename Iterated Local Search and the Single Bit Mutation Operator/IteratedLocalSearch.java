package com.aim.lab02;

import java.util.Random;

import com.aimframework.domains.chesc2014_SAT.SAT;
import com.aimframework.satheuristics.SATHeuristic;
import com.aimframework.searchmethods.SinglePointSearchMethod;

	
public class IteratedLocalSearch extends SinglePointSearchMethod {

	// local search / intensification heuristic
	private SATHeuristic oLocalSearchHeuristic;
	
	// mutation / perturbation heuristic
	private SATHeuristic oMutationHeuristic;
	
	// iom parameter setting
	private int iIntensityOfMutation;
	
	// dos parameter setting
	private int iDepthOfSearch;
	
	/**
	 * 
	 * @param oProblem The problem to be solved.
	 * @param oRandom The random number generator, use this one, not your own!
	 * @param oMutationHeuristic The mutation heuristic.
	 * @param oLocalSearchHeuristic The local search heuristic.
	 * @param iIntensityOfMutation The parameter setting for intensity of mutation.
	 * @param iDepthOfSearch The parameter setting for depth of search.
	 */
	public IteratedLocalSearch(SAT oProblem, Random oRandom, SATHeuristic oMutationHeuristic, 
			SATHeuristic oLocalSearchHeuristic, int iIntensityOfMutation, int iDepthOfSearch) {
		
		super(oProblem, oRandom);
		
		this.oMutationHeuristic = oMutationHeuristic;
		this.oLocalSearchHeuristic = oLocalSearchHeuristic;
		this.iIntensityOfMutation = iIntensityOfMutation;
		this.iDepthOfSearch = iDepthOfSearch;
	}

	/**
	 * 
	 * Main loop for ILS. The experiment framework will continually call this loop until
	 * the allocated time has expired.
	 * 
	 * -- ITERATED LOCAL SEARCH PSEUDO CODE --
	 * 
	 * s <- currentSolution
	 * s' <- s
	 * 
	 * // apply mutation heuristic "iIntensityOfMutation" times
	 * FOR 0 -> intensityOfMutation - 1 DO
	 *     s' <- mutation(s')
	 * END_FOR
	 * 
	 * // apply local search heuristic "iDepthOfSearch" times
	 * FOR 0 -> depthOfSearch - 1 DO
	 *     s' <- localSearch(s')
	 * END_FOR
	 * 
	 * IF f(s') ( < | <= ) f(s) THEN
	 *     accept();
	 * ELIF
	 *     reject();
	 * FI
	 */
	protected void runMainLoop() {

		double currentSolution = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
										
		for(iIntensityOfMutation = 0; iIntensityOfMutation<=1; iIntensityOfMutation++)  {
			oMutationHeuristic.applyHeuristic(problem);
		}
		
		for(iDepthOfSearch = 0; iDepthOfSearch<=1; iDepthOfSearch++)  {
			oLocalSearchHeuristic.applyHeuristic(problem);
		}		
		
		double candidate = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
		
		if((candidate < currentSolution) || (candidate <= currentSolution)) {
			problem.copySolution(CURRENT_SOLUTION_INDEX, BACKUP_SOLUTION_INDEX);
		} else {
			problem.copySolution(BACKUP_SOLUTION_INDEX, CURRENT_SOLUTION_INDEX);
		}
	}
	
	public String toString() {
		return "Iterated Local Search";
	}
}
