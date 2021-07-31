package hw4;

import api.Expression;
import api.Scope;

/**
 * Node type representing a relational expression
 * with the "less than" operator.  If the left
 * operand evaluates to a smaller value than the 
 * right operand, this expression evaluates to 1; otherwise,
 * it evaluates to zero.
 * <ul>
 *   <li>There are two children; the first is the left operand, and the second 
 *   is the right operand.
 *   <li>The getLabel() method returns a string consisting of the less-than symbol
 *   (&lt;).
 *   <li>The getText() method returns an empty string.
 * </ul>
 */
public class RopLessThan extends ExpressionReference
{
	/**
	 * Constructs an expression with the given left and right sides.
	 * @param lhs
	 *   expression for the left-hand-side operand
	 * @param rhs
	 *   expression for the left-hand-side operand
	 */
	public RopLessThan(Expression lhs, Expression rhs)
	{
		super(lhs, rhs, "<");
	}

	/**
	 * Evaluates if the left-hand-operand is less than the right
	 * @param env
	 *   scope with which this expression is to be evaluated
	 * @return 1 or 0
	 *   1 is returned if the left-hand-operand is smaller than the right
	 *   0 is returned otherwise
	 */
	@Override
	public int eval(Scope env)
	{
		int leftVal = ((Expression) getChild(0)).eval(env);
		int rightVal = ((Expression) getChild(1)).eval(env);
		if (leftVal < rightVal)
		{
			return 1;
		}
		return 0;
	}  
}