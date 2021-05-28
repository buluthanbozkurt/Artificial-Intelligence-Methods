package com.aim.project.pwp;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.SolutionRepresentationInterface;
import com.aim.project.pwp.instance.Location;

public class PWPObjectiveFunction implements ObjectiveFunctionInterface {
	
	private final PWPInstanceInterface oInstance;
	
	public PWPObjectiveFunction(PWPInstanceInterface oInstance) {
		
		this.oInstance = oInstance;
	}

	@Override
	public double getObjectiveFunctionValue(SolutionRepresentationInterface oSolution) {
		
		int[] list = oSolution.getSolutionRepresentation();
		double sum = this.getCostBetweenDepotAnd(list[0]);//set depot and first delivery distance
		
		//add distaces of all delivery
		if(list.length > 0) {	
			for(int i = 0; i < list.length-1; i++) {
				sum += this.getCost(list[i], list[i+1]);
			}	
		}
		
		sum+= this.getCostBetweenHomeAnd(list[list.length-1]);//add home and last delivery distance
		
		return sum;
	}
	
	@Override
	public double getCost(int iLocationA, int iLocationB) {
		//delivery and delivery location distance
		Location locationA = this.oInstance.getLocationForDelivery(iLocationA);
		Location locationB = this.oInstance.getLocationForDelivery(iLocationB);
		
		double xCoordA = locationA.getX();
		double xCoordB = locationB.getX();
		
		double yCoordA = locationA.getY();
		double yCoordB = locationB.getY();
		
		double EuclidDistance =  Math.sqrt(Math.pow((xCoordB-xCoordA), 2)+  Math.pow((yCoordB-yCoordA), 2));
		
		return EuclidDistance;

	}

	@Override
	public double getCostBetweenDepotAnd(int iLocation) {
		//office and delivery location distance
		Location depotLocation = this.oInstance.getPostalDepot();
		Location location = this.oInstance.getLocationForDelivery(iLocation);
		
		double xCoordA = depotLocation.getX();
		double xCoordB = location.getX();
		
		double yCoordA = depotLocation.getY();
		double yCoordB = location.getY();
		
		double EuclidDistance =  Math.sqrt(Math.pow((xCoordB-xCoordA), 2)+  Math.pow((yCoordB-yCoordA), 2));
		
		return EuclidDistance;
		
	}

	@Override
	public double getCostBetweenHomeAnd(int iLocation) {
		//home and delivery location distance
		Location homeLocation = this.oInstance.getHomeAddress();
		Location location = this.oInstance.getLocationForDelivery(iLocation);
		
		double xCoordA = homeLocation.getX();
		double xCoordB = location.getX();
		
		double yCoordA = homeLocation.getY();
		double yCoordB = location.getY();
		
		double EuclidDistance =  Math.sqrt(Math.pow((xCoordB-xCoordA), 2)+  Math.pow((yCoordB-yCoordA), 2));
		
		return EuclidDistance;
		
	}
	
}
