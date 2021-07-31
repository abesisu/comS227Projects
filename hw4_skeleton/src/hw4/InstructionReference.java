package hw4;

import api.Instruction;
import api.Scope;

/**
 * Abstract class for other ""Instruction.java classes to implement 
 * to reduce code duplication. The getText() and toString() methods 
 * are repeated in each class. Each class that inherits from this class 
 * also needs to have an execute(Scope env) method.
 * @author abescheideman
 *
 */
public abstract class InstructionReference implements Instruction
{	
	/**
	 * Label returned by getLabel
	 */
	private String label;
	
	/**
	 * Number of children returned by getNumChildren
	 */
	private int numChildren;

	/**
	 * Offers a way to use inheritance for commonly used methods between child classes
	 * @param label
	 *   the label for the class
	 * @param numChildren
	 *   the number of children for the class
	 */
	protected InstructionReference(String label, int numChildren)
	{
		this.label = label;
		this.numChildren = numChildren;
	}

	@Override
	public String getLabel()
	{
		return label;
	}

	@Override 
	public String getText()
	{
		return "";
	}

	@Override
	public int getNumChildren()
	{
		return numChildren;
	}

	@Override
	public String toString()
	{
		return makeString();
	}

	@Override
	public abstract void execute(Scope env);
}