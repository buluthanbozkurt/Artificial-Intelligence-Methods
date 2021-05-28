package com.aim.lab03;

public class GeometricCooling implements CoolingSchedule {
	
	/**
	 * Stores the value of the current temperature.
	 */
	private double dCurrentTemperature;
	
	/**
	 * Stores the value of alpha.
	 */
	private final double dAlpha;
	
	/**
	 * 
	 * @param initialSolutionFitness The objective value of the initial solution.
	 */
	public GeometricCooling(double initialSolutionFitness) {
			
		double c = 1.0d; // set to 100% of the initial solution cost for now
		this.dCurrentTemperature = c * initialSolutionFitness;
		
		// TODO You will need to find a suitable value for alpha 
		//      through prior knowledge and experimentation!
		this.dAlpha = 0.90d;
	}

	@Override
	public double getCurrentTemperature() {
		
		return this.dCurrentTemperature;
	}

	/**
	 * DEFINITION: T_{i + 1} = alpha * T_i
	 */
	@Override
	public void advanceTemperature() {
		
		dCurrentTemperature = dCurrentTemperature * dAlpha;
	
	}
	
	public String toString() {
		
		return "Geometric Cooling";
	}
}
