package com.aim.lab05;

import java.util.Random;

import com.aim.lab05.dependencies.MemeplexInheritanceMethod;
import com.aimframework.domains.chesc2014_SAT.Meme;
import com.aimframework.domains.chesc2014_SAT.SAT;

public class SimpleInheritanceMethod implements MemeplexInheritanceMethod {

	private final SAT oProblem;
	private final Random oRandom;
	
	public SimpleInheritanceMethod(SAT oProblem, Random oRandom) {
		
		this.oProblem = oProblem;
		this.oRandom = oRandom;
	}
	
	/**
	 * Copies the memetic material of the parents to the children
	 * using the Simple Inheritance Method.
	 * 
	 * @param iParent1Index The solution memory index of parent 1.
	 * @param iParent2Index The solution memory index of parent 2.
	 * @param iChild1Index The solution memory index of child 1.
	 * @param iChild2Index The solution memory index of child 2.
	 * 
	 * Simple Inheritance Method PSEUDOCODE:
	 * 
	 * INPUT: parent1, parent2, child1, child2
	 * IF f( parent1 ) == f( parent2 ) THEN
	 * 
	 *     inherit = random \in { parent1, parent2 }
	 *     child1.memeplex <- inherit.memeplex;
	 *     child2.memeplex <- inherit.memeplex;
	 *     
	 * ELSEIF f( parent1 ) < f( parent2 ) THEN
	 * 
	 *     child1.memeplex <- parent1.memeplex;
	 *     child2.memeplex <- parent1.memeplex;
	 *     
	 * ELSE // parent2 is best
	 * 
	 *     child1.memeplex = parent2.memeplex;
	 *     child2.memeplex = parent2.memeplex;
	 *     
	 * ENDIF
	 * return;
	 */
	@Override
	public void performMemeticInheritance(int iParent1Index, int iParent2Index, int iChild1Index, int iChild2Index) {
		
		double parent1Fitness = oProblem.getObjectiveFunctionValue(iParent1Index);
		double parent2Fitness = oProblem.getObjectiveFunctionValue(iParent2Index);
		int inherit;
		
		if(parent1Fitness == parent2Fitness) {
			 inherit = oRandom.nextInt(2);
		} else {
			inherit = 0;
		}
		
		for(int i = 0; i < oProblem.getNumberOfMemes(); i++) { 
			Meme ihrMeme = oProblem.getMeme(inherit, i);
			
			Meme c1Meme = oProblem.getMeme(iChild1Index, i);
			Meme c2Meme = oProblem.getMeme(iChild2Index, i);
			
			c1Meme.setMemeOption(ihrMeme.getMemeOption());
			c2Meme.setMemeOption(ihrMeme.getMemeOption());
			
			int option1 = oProblem.getMeme(iParent1Index, i).getMemeOption();
			
			oProblem.getMeme(iChild1Index, i).setMemeOption(option1);
			oProblem.getMeme(iChild2Index, i).setMemeOption(option1);
		}
		
		
	}
}

