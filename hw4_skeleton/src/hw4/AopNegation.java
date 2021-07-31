package hw4;

import api.DefaultNode;
import api.Expression;
import api.Scope;
import parser.ProgramNode;

/**
 * Arithmetic negation expression (unary minus).  
 * <ul>
 *   <li>There is one child, the expression to be negated
 *   <li>The getLabel() method returns the string "Negative".
 *   <li>The getText() method returns an empty string.
 * </ul>
 */
public class AopNegation extends ExpressionReference
{
	/**
	 * Expression given to be made opposite
	 */
	private Expression expr;
	
	/**
	 * Constructs a new unary expression representing the negative
	 * of the given expression.
	 * @param expr
	 *   arithmetic expression to be negated
	 */
	public AopNegation(Expression expr)
	{
		super("Negative");
		this.expr = expr;
	}

	@Override
	public ProgramNode getChild(int i)
	{
		if (i == 0)
		{
			return expr;
		}
		else
		{
			return new DefaultNode("Invalid index " + i + " for type " + this.getClass().getName());
		}
	}

	@Override
	public int getNumChildren()
	{
		return 1;
	}

	/**
	 * Returns the value of this expression in the given scope.  
	 * If the expression contains no variables, the scope is ignored.
	 * @param env
	 *   scope with which this expression is to be evaluated
	 * @return
	 *   int value of the value given made negative
	 */
	@Override
	public int eval(Scope env)
	{
		int num = expr.eval(env);
		return -num;
	}
}
