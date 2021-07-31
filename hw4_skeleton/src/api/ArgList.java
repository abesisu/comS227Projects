package api;

import java.util.ArrayList;

import parser.ProgramNode;

/**
 * Program node type representing the argument list for a function call.
 */
public class ArgList implements ProgramNode
{
  /**
   * The argument list.
   */
  private ArrayList<Expression> args;

  /**
   * Constructs an empty argument list.
   */
  public ArgList()
  {
    args = new ArrayList<>();
  }
  
  /**
   * Adds an expression to this argument list.
   * @param arg
   *   expression to be added
   */
  public void addArg(Expression arg)
  {
    args.add(arg);
  }
  
  @Override 
  public int getNumChildren()
  {
    return args.size();
  }
  
  @Override
  public ProgramNode getChild(int index)
  {
    if (index >= 0 && index < args.size())
    {
      return args.get(index);
    }
    else
    {
      return new DefaultNode("Out of bounds index " + index + " for ArgList.");      
    }
  }

  @Override
  public String getLabel()
  {
    return "Arg list";
  }

  @Override
  public String getText()
  {
    return "";
  }

  @Override
  public String toString()
  {
    return makeString();
  }
}
