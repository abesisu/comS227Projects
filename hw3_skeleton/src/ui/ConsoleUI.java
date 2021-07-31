package ui;
import java.util.Random;
import java.util.Scanner;

import api.Direction;
import hw3.Powers;
import hw3.ShiftUtil;

/**
 * Ultra-simple UI for the Powers game.
 * This version does not use the MoveResult object returned
 * from the collapse method.
 */
public class ConsoleUI
{

  public static void main(String[] args)
  {
    Scanner in = new Scanner(System.in);
    ShiftUtil util = new ShiftUtil();
    Random rand = new Random(42);  // use a seed so results are reproducible
    Powers g = new Powers(4, util);
    printGrid(g);
    
    while (true)
    {
      System.out.println("Enter i, j, k, or l: ");
      String s = in.nextLine();
      if (s.startsWith("j"))
      {
        g.doMove(Direction.LEFT);
      }
      else if (s.startsWith("l"))
      {
        g.doMove(Direction.RIGHT);
      }
      else if (s.startsWith("i"))
      {
        g.doMove(Direction.UP);
      }
      else if (s.startsWith("k"))
      {
        g.doMove(Direction.DOWN);
      }
      
      printGrid(g);
    }

  }

  public static void printGrid(Powers game)
  {
    System.out.println("---------------");
    for (int row = 0; row < game.getSize(); row += 1)
    {
      for (int col = 0; col < game.getSize(); col += 1)
      {
        System.out.printf("%4d", game.getTileValue(row, col));
      }
      System.out.println();
    }
    System.out.println("---------------");
  }
  
  public static void print2dArray(int[][] arr)
  {
    System.out.println("---------------");
    for (int row = 0; row < arr.length; row += 1)
    {
      for (int col = 0; col < arr[0].length; col += 1)
      {
        System.out.printf("%4d", arr[row][col]);
      }
      System.out.println();
    }
    System.out.println("---------------");
  }
}
