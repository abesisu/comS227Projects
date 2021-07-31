package api;

import hw4.BlockInstruction;
import parser.ProgramNode;

/**
 * Node type representing a function. Code in a function object can only
 * be executed as part of a CallExpression, so this type does not implement
 * the Instruction or Expression interfaces.
 * <ul>
 *   <li>There are three children; the first is the parameter list, the second 
 *   is the function body, and the third is the expression whose value is 
 *   to be returned by the function call.
 *   <li>The getLabel() method returns the string "Function".
 *   <li>The getText() method returns the function's given name.
 * </ul>
 */
public class Function implements ProgramNode
{
  /**
   * Name of this function.
   */
  private String name;
  
  /**
   * Parameter list for this function.
   */
  private ParameterList params;
  
  /**
   * Instruction or block comprising the function body, excluding the return.
   */
  private Instruction body;
  
  /**
   * Expression for the return value.
   */
  private Expression returnExpression;
  
  public Function(String name, ParameterList params, Instruction body, Expression returnExpression)
  {
    this.name = name;
    this.params = params;
    this.body = body;
    this.returnExpression = returnExpression;
  }
  
  @Override
  public ProgramNode getChild(int index)
  {
    switch (index)
    {
      case 0: return params;
      case 1: return body;
      case 2: return returnExpression;
      default: return new DefaultNode();
    }
  }

  @Override
  public String getLabel()
  {
    return "Function";
  }

  @Override
  public String getText()
  {
    return name;
  }

  @Override
  public int getNumChildren()
  {
    return 3;
  }
  
  @Override
  public String toString()
  {
    return makeString();
  }
}
