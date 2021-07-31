package hw4;

import api.DefaultNode;
import api.Expression;
import api.Scope;
import parser.ProgramNode;

/**
 * Instruction type whose execution causes the value of an expression to
 * be printed to the console.
 * <ul>
 *   <li>There is one child, the expression whose value is to be printed.
 *   <li>The getLabel() method returns the string "Output".
 *   <li>The getText() method returns an empty string.
 * </ul>
 */
public class OutputInstruction extends InstructionReference
{
	/**
	 * The given expression to be printed in execute method of OutputInstruction
	 */
	private Expression expr;
	
	/**
	 * Constructs an output statement for the given expression.
	 * @param expr
	 *   given expression
	 */
	public OutputInstruction(Expression expr)
	{
		super("Output", 1);
		this.expr = expr;
	}

	@Override 
	public ProgramNode getChild(int i)
	{
		if (i == 0)
		{
			return expr;
		}
		else
		{
			return new DefaultNode("Invalid index " + i + " for type " + this.getClass().getName());
		}
	}

	/**
	 * Prints the output of the given expression's value
	 * @param
	 * 	 scope with which this expression is to be evaluated
	 */
	@Override
	public void execute(Scope env)
	{
		System.out.println(expr.eval(env));
	}
}
