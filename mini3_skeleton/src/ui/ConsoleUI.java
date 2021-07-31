package ui;

import java.util.Scanner;

import api.CellStatus;
import api.GridCell;
import api.GridObserver;
import api.TwoDGrid;
import mini3.MazeExplorer;

/**
 * Simple console UI for the MazeExplorer.  Attaches an observer to
 * the maze so that user has to press ENTER for next cell update.
 */
public class ConsoleUI
{
  
  
  public static void main(String[] args)
  {
    TwoDGrid maze = new TwoDGrid(RunSearcher.MAZE1);
    GridObserver observer = new ConsoleObserver(maze);
    printMaze(maze);
    System.out.println();
    MazeExplorer.search(maze, maze.getStartRow(), maze.getStartColumn());
  }
  
  
  /**
   * Prints the given grid in text form.
   * @param m
   */
  public static void printMaze(TwoDGrid m)
  {
    for (int row = 0; row < m.getNumRows(); row += 1)
    {
      for (int col = 0; col < m.getNumColumns(); col += 1)
      {
        GridCell c = m.getCell(row, col);
        CellStatus s = c.getStatus();              
        char ch = ' ';
        if (c.isWall())
        {
          ch = '#';
        }
        else if (c.isGoal())
        {
          ch = '$';
        }
        else
        {
          switch(s)
          {
            case SEARCHING_DOWN:
            {
              ch = 'v';
              break;
            }
            case SEARCHING_LEFT:
            {
              ch = '<';
              break;
            }
            case SEARCHING_UP:
            {
              ch = '^';
              break;
            }
            case SEARCHING_RIGHT:
            {
              ch = '>';
              break;
            }
            case FOUND_IT:
            {
              ch = '*';
              break;
            }
            case DEAD_END:
            {
              ch = 'x';
              break;
            }
            default:
          }
        }
        System.out.print(ch);
      }
      System.out.println();
    }
  }
}

/**
 * Implementation of GridObserver that prints out the maze
 * and then waits for user to press ENTER before continuing
 * to next step of search.
 */

class ConsoleObserver implements GridObserver
{
  private TwoDGrid maze;
  private Scanner scanner = new Scanner(System.in);
  public ConsoleObserver(TwoDGrid givenMaze)
  {
    maze = givenMaze;
    maze.setObserver(this);
  }
  
  @Override
  public void statusChanged(GridCell cell)
  {
    ConsoleUI.printMaze(maze);
    CellStatus status = maze.getCell(maze.getStartRow(), maze.getStartColumn()).getStatus();
    if (status != CellStatus.FOUND_IT && status != CellStatus.DEAD_END)
    {
      System.out.println("Press ENTER to continue...");
      scanner.nextLine();
    }
  }    
}
