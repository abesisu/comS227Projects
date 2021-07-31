import java.io.FileNotFoundException;

import api.Expression;
import api.Scope;
import hw4.AopAdd;
import hw4.RopLessThan;
import hw4.AssignmentInstruction;
import hw4.Identifier;
import api.Instruction;
import hw4.Literal;
import hw4.LopAnd;
import hw4.LopOr;
import hw4.OutputInstruction;
import hw4.BlockInstruction;
import hw4.WhileInstruction;
import util.ParserUtil;
import viewer.TreeViewer;
import parser.ProgramNode;
import api.Junebug;

public class Basic
{

  public static void main(String[] args) throws FileNotFoundException
  {
    // A literal value.  
    Expression e0 = new Literal(2);
    Expression e1 = new Literal(3);
    
    // create the expression 2 + 3
    Expression aSum = new AopAdd(e0, e1);

    // Note the scope is not actually being used here
    Scope env = new Scope();
    System.out.println(e0.eval(env));   // expected 2
    System.out.println(e1.eval(env));   // expected 3
    System.out.println(aSum.eval(env)); // expected 5

    // create the expression (2 + 3) + 4
    Expression e2 = new Literal(4);
    Expression anotherSum = new AopAdd(aSum, e2);
    System.out.println(anotherSum.eval(env)); // expected 9
    
    // optionally, take a look at the resulting elements using the viewer
    // TreeViewer.start(anotherSum); 

    // create an expression with a variable, say x + 4
    Identifier id = new Identifier("x");
    Expression sumWithVar = new AopAdd(id, e2);
    
    // to evaluate it, we need to provide a scope in which x has a value
    env.put("x", 42);
    System.out.println(sumWithVar.eval(env));  // 46
    
    // we could also give x a value by executing an assignment statement
    AssignmentInstruction a = new AssignmentInstruction(id, new Literal(137));
    a.execute(env);
    System.out.println(sumWithVar.eval(env));  // 141
    
    // we can also use the parser to create a tree from a string or file
    String s = "(fred + 3) + (george + (4 + 5))";
    Expression e = ParserUtil.parseExpression(s);
    // TreeViewer.start(e);
    
    e0 = new Literal(0);
    e1 = new Literal(42);
    e = new LopAnd(e1, e1);
    System.out.println(e.eval(new Scope()));  // expected 1
    e = new LopAnd(e0, e1);
    System.out.println(e.eval(new Scope()));  // expected 0
    //TreeViewer.start(e);
    
    e0 = new Literal(0);
    e1 = new Literal(42);
    e = new LopOr(e0, e1);
    System.out.println(e.eval(new Scope()));  // expected 1
    e = new LopOr(e0, e0);
    System.out.println(e.eval(new Scope()));  // expected 0
    //TreeViewer.start(e);
    
 // we could also give x a value by executing an assignment statement
 // (recallthat anotherSum was the expression (2 + 3) + 4).
    a = new AssignmentInstruction(id, anotherSum);
    
 // executing the assignment will cause the expression to be evaluated
 // and stored as the value of x
    a.execute(env);
    System.out.println(sumWithVar.eval(env));  // 9 + 4 = 13
    
    
    
 // count = 1
    Identifier indexVar = new Identifier("count");
    Instruction initialize = new AssignmentInstruction(indexVar, new Literal(1));
    
 // count < 11
    Expression test = new RopLessThan(indexVar, new Literal(11));
    
 // output(count)
    Instruction display = new OutputInstruction(indexVar);
    
 // count = count + 1
    Expression sum = new AopAdd(indexVar, new Literal(1));
    Instruction increment = new AssignmentInstruction(indexVar, sum);
    
 // create the loop statement using a block for the body
    BlockInstruction block = new BlockInstruction();
    block.addStatement(display);
    block.addStatement(increment);
    Instruction loop = new WhileInstruction(test,block);
    
 // combine initialization and loop into a block
    BlockInstruction main = new BlockInstruction();
    main.addStatement(initialize);
    main.addStatement(loop);
    
 // now run it
    env = new Scope();
    main.execute(env);
    //TreeViewer.start(main);
    
    Expression e4 = new AopAdd(new Literal(2), new Literal(3));
    OutputInstruction out = new OutputInstruction(e4);
    ProgramNode test2 = out.getChild(0);  // child 0 should be e
    System.out.println(e4 == test2);       // expected true
    out.execute(new Scope());            // expected: 5 printed to console
    
    String t = "(2 + 3) < 4 || !(5 == -(6 * 7))";
    Expression e6 = ParserUtil.parseExpression(t);
 // optionally, start the tree viewer
     //TreeViewer.start(e6);
    System.out.println("Value: " + e6.eval(new Scope()));
    Junebug p = new Junebug(null);
    p = ParserUtil.parseProgramFromFile("primes.txt");
    
 // optionally, look at the syntax tree
    TreeViewer.start(p);
    
 // this program needs a variable named 'max' in the environment
    Scope env2 = new Scope();
    env2.put("max", 100);
    p.run(env2);
  }

}
