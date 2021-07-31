package hw4;

import java.util.ArrayList;

import api.DefaultNode;
import api.Instruction;
import api.Scope;
import parser.ProgramNode;

/**
 * Compound instruction representing a sequence of instructions.  Execution
 * of a block iterates through the sequence, calling execute() on each
 * one. When initially constructed, the block contains no instructions.  
 * Instruction objects are added to the sequence using the method
 * <pre>
 *     public void addStatement(Instruction s)
 * </pre>
 * <ul>
 *   <li>The number of children is the number of statements in
 *       this block (possibly zero).
 *   <li>The getLabel() method returns the string "Block".
 *   <li>The getText() method returns an empty string
 * </ul>
 */
public class BlockInstruction extends InstructionReference
{
	/**
	 * ArrayList of all Instructions given in BlockInstruction
	 */
	private ArrayList<Instruction> instructions;
	
	/**
	 * Number of given statements/children
	 */
	private int numStatements;

	/**
	 * Constructs an empty sequence of instructions.
	 */
	public BlockInstruction()
	{
		super("Block", 0);
		numStatements = 0;
	}

	@Override
	public ProgramNode getChild(int i)
	{
		if (i >= 0 && i < instructions.size())
		{
			return instructions.get(i);
		}
		else
		{
			return new DefaultNode("Invalid index " + i + " for type " + this.getClass().getName());
		}
	}

	@Override
	public int getNumChildren()
	{
		return numStatements;
	}

	/**
	 * Runs the execute method for each individual statement given in the block
	 * @param env
	 * 	 scope with which this expression is to be evaluated
	 */
	@Override
	public void execute(Scope env)
	{
		for (int i = 0; i < instructions.size(); ++i)
		{
			instructions.get(i).execute(env);
		}
	}

	/**
	 * Adds an instruction to this block. The instructions will be executed
	 * in the order added.
	 * @param s
	 *   instruction to be added to this block
	 */
	public void addStatement(Instruction s)
	{
		instructions.add(s);
		++numStatements;
	}
}
