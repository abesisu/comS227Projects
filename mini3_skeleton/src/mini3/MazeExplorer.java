
package mini3;

import api.CellStatus;
import api.GridCell;
import api.TwoDGrid;

/**
 * Utility class for searching a maze described by a collection
 * of GridCell objects.
 */
public class MazeExplorer
{
  /**
   * Recursively searches a given GridCell and all of its unexplored 
   * neighbors.  Immediately returns true if the current cell is the goal;
   * otherwise,  immediately returns false if the current cell is a wall 
   * or if the current cell's status is anything other than NOT_STARTED.
   * Otherwise, initiates a recursive search of the neighbors of the current cell.
   * If any search of a neighbor results in the goal being found, the current cell's status
   * is set to CellStatus.FOUND_IT and the method immediately returns true.  If 
   * no search of a neighbor results in the goal being found, the current cell's 
   * status is set to DEAD_END and the method returns false.
   * <p>
   * <strong>Neighbors are always searched in the order up, down, left, then right,</strong>
   * and during a recursive search of a neighbor, the current cell's status
   * is set to SEARCHING_UP, SEARCHING_DOWN, SEARCHING_LEFT, or SEARCHING_RIGHT, 
   * respectively.
   * 
   * @param maze
   *   the 2d grid to be searched
   * @param row
   *   the row for the current cell being searched
   * @param col
   *   the column for the current cell being searched
   * @return
   *   true if a search from the current cell has reached the goal,
   *   false if the goal can't be reached from the current cell
   */
  public static boolean search(TwoDGrid maze, int row, int col)
  {
	GridCell c = maze.getCell(row, col);
    if (c.isGoal()) 
    {
    	return true;
    }
    else if (c.isWall() || c.getStatus() != CellStatus.NOT_STARTED)
    {
    	return false;
    }
    else 
    {
    	// up
    	c.setStatus(CellStatus.SEARCHING_UP);
    	if (search(maze, row - 1, col))
    	{
    		c.setStatus(CellStatus.FOUND_IT);
    		return true;
    	}
    	
    	// down
    	c.setStatus(CellStatus.SEARCHING_DOWN);
    	if (search(maze, row + 1, col))
    	{
    		c.setStatus(CellStatus.FOUND_IT);
    		return true;
    	}
    	
    	// left
    	c.setStatus(CellStatus.SEARCHING_LEFT);
    	if (search(maze, row, col - 1))
    	{
    		c.setStatus(CellStatus.FOUND_IT);
    		return true;
    	}
    	
    	// right
    	c.setStatus(CellStatus.SEARCHING_RIGHT);
    	if (search(maze, row, col + 1))
    	{
    		c.setStatus(CellStatus.FOUND_IT);
    		return true;
    	}
    }
    c.setStatus(CellStatus.DEAD_END);
    return false;
  }
}
