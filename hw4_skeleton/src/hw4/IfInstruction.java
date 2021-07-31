package hw4;

import api.DefaultNode;
import api.Expression;
import api.Instruction;
import api.Scope;
import parser.ProgramNode;

/**
 * Instruction type representing a conditional.  A conditional has an
 * expression and two instructions.  If the expression evaluates to a 
 * nonzero value (i.e., "true"), the first instruction is executed; otherwise, the 
 * second instruction is executed.
 * <ul>
 *   <li>There are three children; the first is the expression, the second 
 *   is the instruction for the "true", the third is the instruction for
 *   the "false" branch
 *   <li>The getLabel() method returns the string "If".
 *   <li>The getText() method returns an empty string.
 * </ul>
 */
public class IfInstruction extends InstructionReference
{
	/**
	 * Condition for IfInstruction execute method
	 */
	private Expression condition;
	
	/**
	 * Instruction if condition in execute method of IfInstrucion is met
	 */
	private Instruction s0;

	/**
	 * Instruction if condition in execute method of IfInstrucion is not met
	 */
	private Instruction s1;
	
	/**
	 * Constructs a conditional statement from the given condition
	 * and alternative actions.
	 * @param condition
	 *    an expression to use as the condition
	 * @param s0
	 *    statement to be executed if the condition is true (nonzero)
	 * @param s1
	 *    statement to be executed if the condition is false (zero)
	 */
	public IfInstruction(Expression condition, Instruction s0, Instruction s1)
	{
		super("If", 3);
		this.condition = condition;
		this.s0 = s0;
		this.s1 = s1;
	}

	@Override
	public ProgramNode getChild(int i)
	{
		if (i == 0)
		{
			return condition;
		}
		else if (i == 1)
		{
			return s0;
		}
		else if (i == 2)
		{
			return s1;      
		}
		else
		{
			return new DefaultNode("Invalid index " + i + " for type " + this.getClass().getName());
		}
	}

	/**
	 * Evaluates if the given condition is true then executes the corresponding instruction
	 * @param env
	 *   scope with which this expression is to be evaluated
	 */
	@Override
	public void execute(Scope env)
	{
		if (condition.eval(env) != 0)
		{
			s0.execute(env);
		}
		else
		{
			s1.execute(env);
		}
	}
}
