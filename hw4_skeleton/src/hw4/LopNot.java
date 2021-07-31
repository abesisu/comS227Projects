package hw4;

import api.Expression;
import api.Scope;

/**
 * Node type representing a logical expression 
 * with the "not" operator. If expr evaluates to zero, 
 * then this expression evaluates to 1; otherwise, this
 * expression evaluates to 0. 
 * <ul>
 *   <li>There is one child, the expression to be logically negated
 *   <li>The getLabel() method returns the string "!".
 *   <li>The getText() method returns an empty string.
 * </ul>
 */
// AopNegation should technically be abstract since it technically doesn't implement every method from ExpressionReference
// and LopNot extends it. However, I didn't make it abstract because all functionality works and it is not meant to be that way.
// I believe it is more straight forward for LopNot to be a subclass of AopNegation because of the reduction in code duplication 
// and the logical connection they have.
public class LopNot extends AopNegation
{ 
	/**
	 * Constructs a unary expression that represents the logical negation
	 * of the given expression.  
	 * @param expr
	 *   the logical expression to be negated
	 */
	public LopNot(Expression expr)
	{
		super(expr);
	}

	@Override
	public String getLabel()
	{
		// getLabel not inherited because it is more convenient to inerhit 
		// from AopNegation because of getChild and getNumChild methods
		return "!";
	}
	
	/**
	 * Returns the opposite of whatever the Expression evaluates to.
	 * @param env
	 *   scope with which this expression is to be evaluated
	 * @return 0 or 1
	 *   If it is true, returns 0. If false, returns 1.
	 */
	@Override
	public int eval(Scope env)
	{
		int num = ((Expression) getChild(0)).eval(env);
		if (num == 0)
		{
			return 1;
		}
		return 0;
	}
}
