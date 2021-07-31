package api;

import parser.ProgramNode;

/**
 * A program expression that represents an integer value. Integers
 * may also be used as logical values where 0 is interpreted as false
 * and any nonzero number is interpreted as true.
 */
public interface Expression extends ProgramNode
{
  /**
   * Returns the value of this expression in the given scope.  
   * If the expression contains no variables, the scope is ignored.
   * @param env
   *   scope with which this expression is to be evaluated
   * @return
   *   value of the expression in the given scope
   */
  int eval(Scope env);
}
