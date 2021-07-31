package hw4;

import api.DefaultNode;
import api.Expression;
import api.Scope;
import parser.ProgramNode;

/**
 * Implementation of Instruction that represents an assignment to a 
 * variable in the current scope.  Execution of an assignment instruction
 * causes the scope to be updated with the new value of the variable.
 * If the variable name is not already in the scope, it is added.
 * <ul>
 *   <li>There are two children; the first is the identifier, and the second 
 *   is the expression.
 *   <li>The getLabel() method returns the string "Assign".
 *   <li>The getText() method returns the getText() string of the identifier
 * </ul>
 */
public class AssignmentInstruction extends InstructionReference
{
	/**
	 * Identifier used to find the name in AssignmentInstruction
	 */
	private Identifier v;
	
	/**
	 * The expression to be assigned to the identifier in the execute method of AssignmentInstruction
	 */
	private Expression e;
	
	/**
	 * Constructs an assignment instruction representing v = e.
	 * @param v
	 *   the identifier
	 * @param e
	 *   the expression to be assigned
	 */
	public AssignmentInstruction(Identifier v, Expression e)
	{
		// initialize the parent class with Expression value
		super("Assign", 2);
		this.v = v;
		this.e = e;
		
	}

	@Override
	public String getText()
	{
		return v.getText();
	}

	@Override
	public ProgramNode getChild(int i)
	{
		if (i == 0)
		{
			return v;
		}
		else if (i == 1)
		{
			return e;
		}
		else
		{
			return new DefaultNode("Invalid index " + i + " for type " + this.getClass().getName());      
		}
	}

	/**
	 * Enters the new identifier and literal into the scope
	 * @param env
	 * 	 scope with which this expression is to be evaluated
	 */
	@Override
	public void execute(Scope env)
	{
		String name = v.getText();
		int value = e.eval(env);

		// update the scope with the new value
		env.put(name, value);
	}
}
