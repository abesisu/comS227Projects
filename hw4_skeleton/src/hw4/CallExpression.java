package hw4;

import api.ArgList;
import api.DefaultNode;
import api.Expression;
import api.Function;
import api.Scope;
import parser.ProgramNode;

/**
 * Expression representing the value of a function call. All expressions
 * in the argument list are evaluated with respect to the current scope,
 * and the resulting values are associated with the corresponding parameter names in
 * a new "local" Scope.  This local scope is used to evaluate the function body
 * (its BlockInstruction) and after that, the function return expression. 
 * Variables in the current scope are not accessible during execution of the function
 * body.
 * The eval method of this call expression returns value of the function's
 * return expression.
 * <ul>
 *   <li>There are two children; the first is the Function object, and the second 
 *   is the argument list.
 *   <li>The getLabel() method returns the string "Call".
 *   <li>The getText() method returns the getText() string of the Function
 * </ul>
 */
// Directly implements Expression in order to reduce potential confusion from inheriting from ExpressionReference.
// Because of the different instance variables, constructor, getText, getChild, and complex eval method, I thought
// it would be better to not use inheritance with this important class.
public class CallExpression implements Expression
{
	private Function f;
	private ArgList args;

	/**
	 * Constructs a CallExpression for the given function and argument list.
	 * @param f
	 *   the function to be called
	 * @param args
	 *   the arguments to the function
	 */
	public CallExpression(Function f, ArgList args)
	{
		this.f = f;
		this.args = args;
	}

	@Override
	public String getLabel()
	{
		return "Call";
	}

	@Override
	public String getText()
	{
		return f.getText();
	}

	@Override
	public ProgramNode getChild(int i)
	{
		if (i == 0)
		{
			return f;
		}
		else if (i == 1)
		{
			return args;
		}
		else
		{
			return new DefaultNode("Invalid index " + i + " for type " + this.getClass().getName());
		}
	}

	@Override
	public int getNumChildren()
	{
		return 2;
	}

	/**
	 * The eval method of this call expression returns value
	 * of the function's return expression
	 * @param env
	 *   scope with which this expression is to be evaluated
	 */
	@Override
	public int eval(Scope env)
	{
		Scope scope = new Scope();

		for (int i = 0; i < args.getNumChildren(); ++i)
		{
			// gets the value from the expression
			int value = ((Expression) args.getChild(i)).eval(env); 
			// gets the corresponding name from the parameter list
			String name = f.getChild(0).getChild(i).getText();
			// updates the local scope
			scope.put(name, value);
		}
		
		// BlockInstruction execution
		((BlockInstruction) f.getChild(1)).execute(scope); 
		// return expression value;
		return ((Expression) f.getChild(2)).eval(scope); 
	}

	/**
	 * Sets the Function object for this CallExpression
	 * @param f
	 *   the function to be called
	 */
	public void setFunction(Function f)
	{
		this.f = f;
	}

	@Override
	public String toString()
	{
		return makeString();
	}
}
