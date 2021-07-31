package hw4;

import api.DefaultNode;
import api.Scope;
import parser.ProgramNode;

/**
 * Expression type representing a literal (int) value.
 * Evaluating a literal expression returns the given value.
 * <ul>
 *   <li>There are no children.
 *   <li>The getLabel() method returns the string "Int".
 *   <li>The getText() method returns the given value as a string.
 * </ul>
 */
public class Literal extends ExpressionReference
{  
	/**
	 * int value for Literal.java
	 */
	private int value;
	
	/**
	 * Constructs a literal with the given value.
	 * @param value
	 *   int value for this literal.
	 */
	public Literal(int value)
	{
		super("Int");
		this.value = value;
	}

	@Override
	public String getText()
	{
		return "" + value;
	}

	@Override
	public ProgramNode getChild(int i)
	{
		return new DefaultNode("Invalid index " + i + " for type " + this.getClass().getName());
	}

	@Override
	public int getNumChildren()
	{
		return 0;
	}

	/**
	 * Returns the value of the expression as an int value
	 * @param env
	 *   scope with which this expression is to be evaluated
	 * @return
	 *   int value of the expression
	 */
	@Override
	public int eval(Scope env)
	{
		return value;
	}
}
