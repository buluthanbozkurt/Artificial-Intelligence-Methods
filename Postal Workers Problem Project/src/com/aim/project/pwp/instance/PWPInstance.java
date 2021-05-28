package com.aim.project.pwp.instance;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.IntStream;

import com.aim.project.pwp.PWPObjectiveFunction;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.SolutionRepresentationInterface;
import com.aim.project.pwp.solution.SolutionRepresentation;
import com.aim.project.pwp.solution.PWPSolution;


public class PWPInstance implements PWPInstanceInterface {
	
	private final Location[] aoLocations;
	
	private final Location oPostalDepotLocation;
	
	private final Location oHomeAddressLocation;
	
	private final int iNumberOfLocations;
	
	private final Random oRandom;
	
	private ObjectiveFunctionInterface oObjectiveFunction = null;
	
	/**
	 * 
	 * @param numberOfLocations The TOTAL number of locations (including DEPOT and HOME).
	 * @param aoLocations The delivery locations.
	 * @param oPostalDepotLocation The DEPOT location.
	 * @param oHomeAddressLocation The HOME location.
	 * @param random The random number generator to use.
	 */
	public PWPInstance(int numberOfLocations, Location[] aoLocations, Location oPostalDepotLocation, Location oHomeAddressLocation, Random random) {
		
		this.iNumberOfLocations = numberOfLocations;
		this.oRandom = random;
		this.aoLocations = aoLocations;
		this.oPostalDepotLocation = oPostalDepotLocation;
		this.oHomeAddressLocation = oHomeAddressLocation;
	}

	@Override
	public PWPSolution createSolution(InitialisationMode mode) {
		
		// TODO construct a new 'PWPSolution' using RANDOM initialisation
		PWPSolution pwpSolution=null;
		//(SolutionRepresentationInterface representation, double objectiveFunctionValue)
		if(mode == InitialisationMode.RANDOM) {
			
			int [] solition= new int[aoLocations.length];
	
			
			//for random pick number
			ArrayList aList= new ArrayList();
			int rnd=0;
			for (int i = 0; i < solition.length; i++) {
				aList.add(i);
			}
			//**********************/
			
			for (int i = 0; i < aoLocations.length; i++) {
				// random number between 0 and alist size
				rnd= this.oRandom.nextInt(aList.size());
				solition[i]= (int)aList.get(rnd);
				aList.remove(rnd);
			}
			SolutionRepresentation solutionRep = new SolutionRepresentation(solition);
			double objectiveValue= this.getPWPObjectiveFunction().getObjectiveFunctionValue(solutionRep);
			pwpSolution=new PWPSolution(solutionRep,objectiveValue ); // 
		}

		//PWPSolution pwpSolution = new PWPSolution(solutionRep, cost ,this.numberOfLocations);
		return pwpSolution;
				
	}
	
	@Override
	public ObjectiveFunctionInterface getPWPObjectiveFunction() {
		
		if(oObjectiveFunction == null) {
			this.oObjectiveFunction = new PWPObjectiveFunction(this);
		}

		return oObjectiveFunction;
	}

	@Override
	public int getNumberOfLocations() {

		return iNumberOfLocations;
	}

	@Override
	public Location getLocationForDelivery(int deliveryId) {

		return aoLocations[deliveryId];
	}

	@Override
	public Location getPostalDepot() {
		
		return this.oPostalDepotLocation;
	}

	@Override
	public Location getHomeAddress() {
		
		return this.oHomeAddressLocation;
	}
	
	@Override
	public ArrayList<Location> getSolutionAsListOfLocations(PWPSolutionInterface oSolution) {
		
		// TODO return an 'ArrayList' of ALL LOCATIONS in the solution.
		SolutionRepresentation solutionRep = (SolutionRepresentation)((PWPSolutionInterface)oSolution).getSolutionRepresentation();
		int [] solutionList=solutionRep.getSolutionRepresentation();
		
		ArrayList<Location> list =new ArrayList<Location>();
		for (int i = 0; i < solutionList.length; i++) {
			list.add(aoLocations[solutionList[i]]);
		}
		
		return list;
		
	}

}
