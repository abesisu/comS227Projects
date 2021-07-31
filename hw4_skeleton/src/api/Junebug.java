package api;

import java.util.ArrayList;

import hw4.BlockInstruction;
import parser.ProgramNode;

/**
 * Container for a complete Junebug program.  A program consists
 * of a BlockInstruction, plus zero or more Function objects.
 * The first child is always the BlockInstruction, which can be 
 * executed.  There is also a convenience method run() to start
 * execution of the block.
 */
public class Junebug implements ProgramNode
{
  /**
   * The instructions representing the entry point for this program.
   */
  private Instruction block;
  
  /**
   * List of associated functions.
   */
  private ArrayList<Function> functions;
  
  /**
   * Constructs a Junebug program with the given block and an
   * empty list of functions.
   * @param block
   *   given BlockInstruction 
   */
  public Junebug(Instruction block)
  {
    this.block = block;
    this.functions = new ArrayList<Function>();
  }

  /**
   * Adds a function to this program.
   * @param f
   *   the Function object to be added
   */
  public void addFunction(Function f)
  {
    functions.add(f);
  }
  
  /**
   * Begins execution of this program's block.
   * @param env
   *   scope to use for execution
   */
  public void run(Scope env)
  {
    Instruction b = (Instruction) getChild(0);
    b.execute(env);
  }
  
  @Override
  public int getNumChildren()
  {
    return 1 + functions.size();
  }
  
  @Override
  public ProgramNode getChild(int index)
  {
    if (index == 0)
    {
      return block;
    }
    else if (index - 1 < functions.size())
    {
      return functions.get(index - 1);
    }
    else
    {
      return new DefaultNode("Out of range index " + index + " in Junebug node.");
    }
  }

  @Override
  public String getLabel()
  {
    return "Program";
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
