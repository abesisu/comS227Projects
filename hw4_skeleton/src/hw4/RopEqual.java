package hw4;

import api.Expression;
import api.Scope;

/**
 * Node type representing a relational expression
 * with the "equals" operator.  If the two operands
 * evaluate the the same number, this expression evaluates
 * to 1; otherwise, it evaluates to zero.
 * <ul>
 *   <li>There are two children; the first is the left operand, and the second 
 *   is the right operand.
 *   <li>The getLabel() method returns the string "==".
 *   <li>The getText() method returns an empty string.
 * </ul>
 */
public class RopEqual extends ExpressionReference
{
  /**
   * Constructs an expression with the given left and right sides.
   * @param lhs
   *   expression for the left-hand-side operand
   * @param rhs
   *   expression for the left-hand-side operand
   */
  public RopEqual(Expression lhs, Expression rhs)
  {
	super(lhs, rhs, "==");
  }

  /**
   * Evaluates whether or not the left and right-hand-operands are the same number
   * @param env
   *   scope with which this expression is to be evaluated
   * @return 1 or 0
   *   1 is returned if the numbers are equal and 0 is returned if not
   */
  @Override
  public int eval(Scope env)
  {
    int leftVal = ((Expression) getChild(0)).eval(env);
    int rightVal = ((Expression) getChild(1)).eval(env);
    if (leftVal == rightVal)
    {
    	return 1;
    }
    return 0;
  }
}