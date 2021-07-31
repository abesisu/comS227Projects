package api;

import parser.ProgramNode;

/**
 * Instruction that can be executed.  Executing an instruction does not
 * return a value, but instructions may
 * update the scope (via assignments) or may produce output. 
 */
public interface Instruction extends ProgramNode
{
  /**
   * Executes the given instruction in the given scope.
   * Note that an instruction may update the scope by
   * creating variables or assigning new values to variables.
   * @param env
   *   scope in which this statement will be executed
   */
  void execute(Scope env);
}
