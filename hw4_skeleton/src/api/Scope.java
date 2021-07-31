package api;

import java.util.ArrayList;

/**
 * Mapping of variable names to values to be used in evaluating or 
 * executing Expressions or Instructions.
 */
public class Scope
{
  /**
   * List of name/value pairs.
   */
  private ArrayList<NameValuePair> pairs;
    
  /**
   * Constructs an empty scope.
   */
  public Scope() 
  {
    pairs = new ArrayList<NameValuePair>();
  }
  
  /**
   * Returns the value associated with the given variable name.
   * @param name
   *   a variable name
   * @return
   *   the value associated with the name, if any
   * @throws InterpreterException
   *   if the given name doesn't have a value in this scope
   */
  public int get(String name)
  {
    for (NameValuePair p : pairs)
    {
      if (name.equals(p.getName()))
      {
        return p.getValue();
      }
    }
    throw new InterpreterException("Identifier '" + name + "' not found in scope." );
  }

  /**
   * Updates the given name-value mapping in this scope.  If no mapping
   * currently exists for the name, a new one is created.
   * @param name
   *   a variable name
   * @param value
   *   the int value to be associated with the name
   */
  public void put(String name, int value)
  {
    for (NameValuePair p : pairs)
    {
      if (name.equals(p.getName()))
      {
        p.setValue(value);
        return;
      }
    }
    // not found, add a new entry
    pairs.add(new NameValuePair(name, value));
  }


  public void remove(String name)
  {
    for (int i = 0; i < pairs.size(); ++i)
    {
      if (name.equals(pairs.get(i).getName()))
      {
        pairs.remove(i);
        return;
      }
    }
  }

  /**
   * A name and associated integer value.  The value is mutable.
   */
  private class NameValuePair
  {
    /**
     * The name for this pair.
     */
    private String name;
    
    /**
     * The value for this pair.
     */
    private int value;
    
    /**
     * Constructs a pair with the given name and value.
     * @param name
     *   the name for this pair
     * @param value
     *   the value for this pair
     */
    public NameValuePair(String name, int value)
    {
      this.name = name;
      this.value = value;
    }
    
    /**
     * Returns the name for this pair.
     * @return
     *  the name for this pair
     */
    public String getName()
    {
      return name;
    }
    
    /**
     * Returns the associated value for this pair.
     * @return
     *   value for this pair
     */
    public int getValue()
    {
      return value;
    }
    
    /**
     * Update the value to the new value.
     * @param newValue
     *   the new value for this pair
     */
    public void setValue(int newValue)
    {
      value = newValue;
    }
  }

}
