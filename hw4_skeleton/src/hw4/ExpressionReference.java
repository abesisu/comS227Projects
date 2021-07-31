package hw4;

import api.Expression;
import api.Scope;
import parser.ProgramNode;
import api.DefaultNode;

/**
 * Abstract class to reduce code duplication for all binary operating classes
 * and Literal.java. Implements two different constructors in order to work 
 * with both different types of classes. The getText(), getChild(), getNumChildren(), 
 * and toString() methods are all implemented to eliminate duplicate code. Each class
 * that inherits from this class has to have an eval(Scope env) method.
 * @author abescheideman
 *
 */
public abstract class ExpressionReference implements Expression
{
	/**
	 * Left-hand-side operand
	 */
	private Expression lhs;
	
	/**
	 * Right-hand-side operand
	 */
	private Expression rhs;
	
	/**
	 * Label for the method getLabel
	 */
	private String label;

	/**
	 * Constructs an expression with the given left and right sides.
	 * @param lhs
	 *   expression for the left-hand-side operand
	 * @param rhs
	 *   expression for the left-hand-side operand
	 * @param label
	 *   String to be returned by getLabel
	 */
	protected ExpressionReference(Expression lhs, Expression rhs, String label)
	{
		this.lhs = lhs;
		this.rhs = rhs;
		this.label = label;
	}

	/**
	 * A simple constructor option for classes to use common methods
	 * such as getLabel, without needing a right and left hand value.
	 * @param expr
	 *   String to be returned by getLabel
	 */
	protected ExpressionReference(String label) 
	{
		this.label = label;
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
	public ProgramNode getChild(int i)
	{
		if (i == 0)
		{
			return lhs;
		}
		else if (i == 1)
		{
			return rhs;
		}
		else
		{
			return new DefaultNode("Invalid index " + i + " for type " + this.getClass().getName());
		}
	}

	@Override
	public int getNumChildren()
	{
		return 2;
	}

	@Override
	public abstract int eval(Scope env);

	@Override
	public String toString()
	{
		return makeString();
	}
}
