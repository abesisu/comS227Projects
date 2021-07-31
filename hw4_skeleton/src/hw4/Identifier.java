package hw4;

import api.DefaultNode;
import api.Scope;
import parser.ProgramNode;

/**
 * Expression type representing an identifier (variable).
 * An identifier contains a string to be used as a variable name.
 * Evaluating an identifier expression returns the value
 * in the current Scope that is associated with the variable name.
 * Throws an InterpreterException if the name isn't in the current
 * Scope.
 * <ul>
 *   <li>There are no children.
 *   <li>The getLabel() method returns the string "Id".
 *   <li>The getText() method returns the given name.
 * </ul>
 */
public class Identifier extends ExpressionReference
{
	/**
	 * name for Identifier
	 */
	private String givenName;
	
	/**
	 * Constructs an identifier using the given string name.
	 * @param givenName
	 *   name for this identifier
	 */
	public Identifier(String givenName)
	{
		super("Id");
		this.givenName = givenName;
	}

	@Override
	public String getText()
	{
		return givenName;
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
	 * Evaluates to get the value associated with the name in the current Scope
	 * @param env
	 *   scope with which this expression is to be evaluated
	 * @return 
	 *   value in current scope associated with name in current Scope
	 */
	@Override
	public int eval(Scope env)
	{
		return env.get(givenName);
	}
}
