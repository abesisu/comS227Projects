package hw4;

import api.Expression;
import api.Scope;

/**
 * Node type representing a logical expression 
 * with the "or" operator.  If both operands evaluate
 * to zero, then this expression evaluates to 0;
 * otherwise, this expression evaluates to 1.
 * <ul>
 *   <li>There are two children; the first is the left operand, and the second 
 *   is the right operand.
 *   <li>The getLabel() method returns the string "||".
 *   <li>The getText() method returns an empty string.
 * </ul>
 */
public class LopOr extends ExpressionReference
{
	/**
	 * Constructs an expression with the given left and right sides.
	 * @param lhs
	 *   expression for the left-hand-side operand
	 * @param rhs
	 *   expression for the left-hand-side operand
	 */
	public LopOr(Expression lhs, Expression rhs)
	{
		super(lhs, rhs, "||");
	}

	/**
	 * Evaluates both expressions to see if both one is true.
	 * @param env
	 *   scope with which this expression is to be evaluated
	 * @return 0 or 1
	 *   If both are false, 0 is returned. If one is true, 1 is returned.
	 */
	@Override
	public int eval(Scope env)
	{
		int leftVal = ((Expression) getChild(0)).eval(env);
		int rightVal = ((Expression) getChild(1)).eval(env);
		if (leftVal == 0 && rightVal == 0)
		{
			return 0;
		}
		return 1;
	}
}