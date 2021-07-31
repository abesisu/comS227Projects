package hw4;

import api.Expression;
import api.Scope;

/**
 * Node type representing an arithmetic expression 
 * with a division operator.
 * <ul>
 *   <li>There are two children; the first is the left operand, and the second 
 *   is the right operand.
 *   <li>The getLabel() method returns the string "/".
 *   <li>The getText() method returns an empty string.
 * </ul>
 */
public class AopDivide extends ExpressionReference
{
	/**
	 * Constructs an expression with the given left and right sides.
	 * @param lhs
	 *   expression for the left-hand-side operand
	 * @param rhs
	 *   expression for the left-hand-side operand
	 */
	public AopDivide(Expression lhs, Expression rhs)
	{
		super(lhs, rhs, "/");
	}

	/**
	 * Returns the value of this expression in the given scope.  
	 * If the expression contains no variables, the scope is ignored.
	 * @param env
	 *   scope with which this expression is to be evaluated
	 * @return
	 *   int value of the left-hand-operand divided by the right
	 */
	@Override
	public int eval(Scope env)
	{
		// gets the expression for the left and right-hand-operands and uses int value
		int leftVal = ((Expression) getChild(0)).eval(env);
		int rightVal = ((Expression) getChild(1)).eval(env);
		return leftVal / rightVal;
	}
}
