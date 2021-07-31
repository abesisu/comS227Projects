package api;

/**
 * Exception type used for internal errors in interpreting
 * expressions and executing statements.
 */
public class InterpreterException extends RuntimeException
{
  public InterpreterException()
  {
    super();
  }
  
  public InterpreterException(String msg)
  {
    super(msg);
  }
  
  public InterpreterException(String msg, Exception cause)
  {
    super(msg, cause);
  }
}
