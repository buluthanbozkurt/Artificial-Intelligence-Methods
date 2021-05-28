package com.aim.project.pwp.heuristics;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;

public class HeuristicOperators {

	//i changed private -> protected
	protected ObjectiveFunctionInterface oObjectiveFunction;

	public HeuristicOperators() {

	}

	public void setObjectiveFunction(ObjectiveFunctionInterface f) {

		this.oObjectiveFunction = f;
	}
	
	public int[] swapDeliveryLocations(int[] locations, int Location1Index, int Location2Index) {
		int temp = locations[Location2Index];
		
		locations[Location2Index] = locations[Location1Index];
		locations[Location1Index] = temp;
		
		return locations;
	}
	
}

