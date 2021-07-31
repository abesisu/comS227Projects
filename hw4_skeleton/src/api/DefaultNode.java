package api;

import parser.ProgramNode;

/**
 * A default ProgramNode that can be returned by getChild
 * methods in case of an out of range index.
 */
public class DefaultNode implements ProgramNode
{
  private String label;
  private String text;
  
  /**
   * Constructs a DefaultNode with empty text.
   */
  public DefaultNode()
  {
    this("");
  }

  /**
   * Constructs a DefaultNode with the given string as its text.
   * @param msg
   *   error message to include as the node's text
   */
  public DefaultNode(String msg)
  {
    label = "Error";
    text = msg;
  }

  @Override
  public int getNumChildren()
  {
    return 0;
  }

  @Override
  public ProgramNode getChild(int index)
  {
     return null;
  }

  @Override
  public String getLabel()
  {
    return label;
  }

  @Override
  public String getText()
  {
    return text;
  }

  @Override
  public String toString()
  {
    return makeString();
  }
}
