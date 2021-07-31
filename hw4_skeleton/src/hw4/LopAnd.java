package hw4;

import api.Expression;
import api.Scope;

/**
 * Node type representing a logical expression 
 * with the "and" operator. If both operands evaluate
 * to a nonzero value, then this expression evaluates to 1;
 * otherwise, this expression evaluates to zero.
 * <ul>
 *   <li>There are two children; the first is the left operand, and the second 
 *   is the right operand.
 *   <li>The getLabel() method returns a string consisting of a double ampersand
 *   (&amp;&amp;).
 *   <li>The getText() method returns an empty string.
 * </ul>
 */
public class LopAnd extends ExpressionReference
{
	/**
	 * Constructs an expression with the given left and right sides.
	 * @param lhs
	 *   expression for the left-hand-side operand
	 * @param rhs
	 *   expression for the left-hand-side operand
	 */
	public LopAnd(Expression lhs, Expression rhs)
	{
		super(lhs, rhs, "&&");
	}

	/**
	 * Evaluates the given Expressions to see if either one returns false.
	 * @param env
	 *   scope with which this expression is to be evaluated
	 * @return 0 or 1
	 *   depending on whether the expressions evaluate to false or not, 
	 *   0 is returned if one is false and 1 is returned if both are true
	 */
	@Override
	public int eval(Scope env)
	{
		int leftVal = ((Expression) getChild(0)).eval(env);
		int rightVal = ((Expression) getChild(1)).eval(env);

		// check to see if either value is false
		if (leftVal == 0 || rightVal == 0)
		{
			return 0;
		}
		return 1;
	}
}