import java.io.FileNotFoundException;

import parser.ProgramNode;
import util.ParserUtil;
import viewer.TreeViewer;
import api.Expression;
import hw4.BlockInstruction;

/**
 * Example using the parser and viewer that does not depend
 * on the ProgramNode hierarchy.
 */
public class TryParserOnly
{
  public static void main(String[] args) throws FileNotFoundException
  {   
    String t = "(2 + 3) < 4 || !(5 == -(6 * 7))";
    ProgramNode v = ParserUtil.parseExpressionRaw(t);
    TreeViewer.start(v); 
    
    //parseFromStringExample();
    //parseFromFileExample();
  }
  
  private static void parseFromStringExample()
  {
    String myProgram = 
        "count = 1; \n" +
        "while(count < 11) \n" +
        "{ \n" +
        "  output(count); \n" +
        "  count = count + 1; \n" +
        "} \n";
    
   System.out.println(myProgram);
    
   ProgramNode v = ParserUtil.parseProgramFromStringRaw(myProgram);
   TreeViewer.start(v);   
  }

  private static void parseFromFileExample() throws FileNotFoundException
  {
    ProgramNode v = ParserUtil.parseProgramFromFileRaw("count.txt");
    TreeViewer.start(v);   
  }
}
