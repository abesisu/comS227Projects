package api;

import java.util.ArrayList;

import hw4.Identifier;
import parser.ProgramNode;

/**
 * Program node type representing the parameter list for a function.
 */
public class ParameterList implements ProgramNode
{
  private ArrayList<Identifier> params;
  
  /**
   * Constructs an empty parameter list.
   */
  public ParameterList()
  {
    params = new ArrayList<>();
  }
  
  /**
   * Adds an identifier to this parameter list.
   * @param id
   *   identifier to be added
   */
  public void addParam(Identifier id)
  {
    params.add(id);
  }

  @Override
  public int getNumChildren()
  {
    return params.size();
  }
  
  @Override
  public ProgramNode getChild(int index)
  {
    if (index >= 0 && index < params.size())
    {
      return params.get(index);
    }
    else
    {
      return new DefaultNode("Out of bounds index " + index + " for ParameterList.");      
    }
  }
  
  @Override
  public String getLabel()
  {
    return "Param list";
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
