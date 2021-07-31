package hw3;
import java.util.Random;

import api.Direction;
import static api.Direction.*;
import api.MoveResult;
import api.TilePosition;
import api.Descriptor;
import api.Shift;
import java.util.ArrayList;


/**
 * The Powers class contains the state and logic for an implementation of a game
 * similar to "2048".  The basic underlying state is an n by n grid of tiles, 
 * represented by integer values.  A zero in a cell is considered to be
 * "empty", and all other cells contain some power of two.  The game is played
 * by calling the method <code>shiftGrid()</code>, selecting one of the four
 * directions (LEFT, RIGHT, UP, DOWN). Each row or column is then collapsed
 * according to the algorithm described in the methods of <code>ShiftUtil</code>.
 * <p>
 * Whenever two cells are <em>merged</em>, the score is increased by the 
 * combined value of the two cells.
 * 
 * @author Abe Scheideman
 */
public class Powers
{
  /**
   * The 2D grid for the game.
   */
  private int[][] grid;
  
  /**
   * The length and width are equal to the size of grid
   */
  private int size;
  
  /**
   * Variable to hold the score
   */
  private int score;
  
  /**
   * An instance of ShiftUtil
   */
  private ShiftUtil util;
  
  /**
   * A Random object for generating tiles
   */
  private Random randy;
  
  /**
   * The MoveResult object for this instance of the game
   */
  private MoveResult moveResult;
  
  /**
   * Constructs a game with a grid of the given size, using a default
   * random number generator.  Initially there should be two
   * nonempty cells in the grid selected by the method <code>generateTile()</code>.
   * @param givenSize
   *   size of the grid for this game
   * @param givenUtil
   *   instance of ShiftUtil to be used in this game
   */
  public Powers(int givenSize, ShiftUtil givenUtil)
  {
	size = givenSize;
    grid = new int[size][size];
    util = givenUtil;
    randy = new Random();
    moveResult = new MoveResult();
    // need to generate two random tiles
    TilePosition randTile1 = generateTile();
    grid[randTile1.getRow()][randTile1.getCol()] = randTile1.getValue();
    TilePosition randTile2 = generateTile();
    grid[randTile2.getRow()][randTile2.getCol()] = randTile2.getValue();
  }
  
  /**
   * Constructs a game with a grid of the given size, using the given
   * instance of <code>Random</code> for its random number generator.
   * Initially there should be two nonempty cells in the grid selected 
   * by the method <code>generateTile()</code>.
   * @param givenSize
   *   size of the grid for this game
   * @param givenUtil
   *   instance of ShiftUtil to be used in this game
   * @param givenRandom
   *   given instance of Random
   */
  public Powers(int givenSize, ShiftUtil givenUtil, Random givenRandom)
  {
	  size = givenSize;
	  grid = new int[size][size];
	  util = givenUtil;
	  randy = givenRandom;
	  moveResult = new MoveResult();
	  // need to generate two random tiles
	  TilePosition randTile1 = generateTile();
	  grid[randTile1.getRow()][randTile1.getCol()] = randTile1.getValue();
	  TilePosition randTile2 = generateTile();
	  grid[randTile2.getRow()][randTile2.getCol()] = randTile2.getValue();
  }
  
  /**
   * Returns the value in the cell at the given row and column.
   * @param row
   *   given row
   * @param col
   *   given column
   * @return
   *   value in the cell at the given row and column
   */
  public int getTileValue(int row, int col)
  {
    return grid[row][col];
  }
  
  /**
   * Sets the value of the cell at the given row and column.
   * <em>NOTE: This method should not normally be used by clients outside
   * of a testing environment.</em>
   * @param row
   *   given row
   * @param col
   *   given col
   * @param value
   *   value to be set
   */
  public void setValue(int row, int col, int value)
  {
    grid[row][col] = value;
  }
  
  /**
   * Returns the size of this game's grid.
   * @return
   *   size of the grid
   */
  public int getSize()
  {
    return size;
  }
  
  /**
   * Returns the current score.
   * @return
   *   score for this game
   */
  public int getScore()
  {
    return score;
  }
  
  /**
   * Copy a row or column from the grid into a new one-dimensional array.  
   * There are four possible actions depending on the given direction:
   * <ul>
   *   <li>LEFT - the row indicated by the index <code>rowOrColumn</code> is
   *   copied into the new array from left to right
   *   <li>RIGHT - the row indicated by the index <code>rowOrColumn</code> is
   *   copied into the new array in reverse (from right to left)
   *   <li>UP - the column indicated by the index <code>rowOrColumn</code> is
   *   copied into the new array from top to bottom
   *   <li>DOWN - the row indicated by the index <code>rowOrColumn</code> is
   *   copied into the new array in reverse (from bottom to top)
   * </ul>
   * @param rowOrColumn
   *   index of the row or column
   * @param dir
   *   direction from which to begin copying
   * @return
   *   array containing the row or column
   */
  public int[] getRowOrColumn(int rowOrColumn, Direction dir)
  {
	int[] rowOrColumnCopy = new int[size];
	  
    if (dir == LEFT) 
    { 
    	// copy the indicated row from left to right
    	for (int i = 0; i < size; i++) 
    	{
    		rowOrColumnCopy[i] = grid[rowOrColumn][i];
    	}
    }
    else if (dir == RIGHT) 
    {
    	// copy the indicated row from right to left
    	for (int i = 0; i < size; i++) 
    	{
    		rowOrColumnCopy[i] = grid[rowOrColumn][size - 1 - i];
    	}
    }
    else if (dir == UP) 
    {
    	// copy the indicated row from top to bottom
    	for (int i = 0; i < size; i++) 
    	{
    		rowOrColumnCopy[i] = grid[i][rowOrColumn];
    	}
    }
    else if (dir == DOWN) 
    {
    	// copy the indicated row from top to bottom
    	for (int i = 0; i < size; i++) 
    	{
    		rowOrColumnCopy[i] = grid[size - 1 - i][rowOrColumn];
    	}
    }
    return rowOrColumnCopy;
  }
  
  /**
   * Updates the grid by copying the given one-dimensional array into
   * a row or column of the grid.
   * There are four possible actions depending on the given direction:
   * <ul>
   *   <li>LEFT - the given array is copied into the the row indicated by the 
   *   index <code>rowOrColumn</code> from left to right
   *   <li>RIGHT - the given array is copied into the the row indicated by the 
   *   index <code>rowOrColumn</code> in reverse (from right to left)
   *   <li>UP - the given array is copied into the column indicated by the 
   *   index <code>rowOrColumn</code> from top to bottom
   *   <li>DOWN - the given array is copied into the column indicated by the 
   *   index <code>rowOrColumn</code> in reverse (from bottom to top)
   * </ul>
   * @param arr
   *   the array from which to copy
   * @param rowOrColumn
   *   index of the row or column
   * @param dir
   *   direction from which to begin copying
   */
  public void setRowOrColumn(int[] arr, int rowOrColumn, Direction dir)
  {
	// the array given is copied into the selected row
	if (dir == LEFT) 
	{
		for (int i = 0; i < arr.length; i++) 
		{
			grid[rowOrColumn][i] = arr[i];
		}
	}
	else if (dir == RIGHT) 
	{
		for (int i = 0; i < arr.length; i++) 
		{
			grid[rowOrColumn][i] = arr[arr.length - 1 - i];
		}
	}
	// the array given is read left to right and the values are read into the grid from top to bottom
	else if (dir == UP) 
	{
		for (int i = 0; i < arr.length; i++) 
		{
			grid[i][rowOrColumn] = arr[i];
		}
	}
	// the array give is read right to left and the values are read into the grid from top to bottom
	else if (dir == DOWN) 
	{
		for (int i = 0; i < arr.length; i++) 
		{
			grid[i][rowOrColumn] = arr[arr.length - 1 - i];
		}
	}
  }

  /**
   * Plays one step of the game by shifting the grid in the given direction.
   * Returns a <code>MoveResult</code> object containing a list of Descriptor 
   * objects describing all moves performed, and a <code>TilePosition</code> object describing
   * the position and value of a newly added tile, if any. 
   * If no tiles are actually moved, the returned <code>MoveResult</code> object contains an 
   * empty list and has a null value for the new <code>TilePosition</code>.
   * <p>
   * If any tiles are moved or merged, a new tile is selected according to the 
   * <code>generate()</code> method and is added to the grid.
   * <p>
   * The shifting of each individual row or column must be performed by the 
   * method <code>shiftAll</code> of <code>ShiftUtil</code>. 
   * 
   * @param dir
   *   direction in which to shift the grid
   * @return
   *   MoveResult object containing move descriptors and new tile position
   */
  public MoveResult doMove(Direction dir)
  {
	  moveResult = new MoveResult();
	  for (int i = 0; i < size; i++) 
	  {
		  int[] rowOrColToShift = (getRowOrColumn(i, dir)); // get the row

		  // make an ArrayList of Shift to access each individual shift that takes place
		  ArrayList<Shift> listOfShifts = new ArrayList<>();
		  listOfShifts = util.shiftAll(rowOrColToShift);

		  // for each shift, make a descriptor and add that to the ArrayList moves
		  for (int n = 0; n < listOfShifts.size(); n++) {
			  moveResult.addMove(new Descriptor(listOfShifts.get(n), i, dir));
			  // if the shift merged, add double the value (Shift holds the old value) and add it to score
			  if (listOfShifts.get(n).isMerge() == true) {
				  score += (2 * (listOfShifts.get(n).getValue())); 
			  }
		  }
		// set the row back in the grid
		  setRowOrColumn(rowOrColToShift, i, dir); 
	  }
	  // if there was a move made, generate a random tile
	  if (moveResult.getMoves().size() != 0) 
	  {
		  TilePosition randTile = generateTile();
		  setValue(randTile.getRow(), randTile.getCol(), randTile.getValue());
		  moveResult.setNewTile(randTile);
	  }
    return moveResult;
  }

  /**
   * Use this game's instance of <code>Random</code> to generate
   * a new tile.  The tile's row and column must be an empty cell
   * of the grid, and the tile's value is either 2 or 4. 
   * The tile is selected in such a way that
   * <ul>
   *   <li>All empty cells of the grid are equally likely
   *   <li>The tile's value is a 2 with 90% probability and a 4 with 10% probability
   * </ul>
   * This method does not modify the grid.  If the grid has no
   * empty cells, returns null.
   * @return
   *   a new TilePosition containing the row, column, and value of the
   *   selected new tile, or null if the grid has no empty cells
   */
  public TilePosition generateTile()
  {
	// look over each index to add empty tiles to an ArrayList
	ArrayList<TilePosition> emptyTiles = new ArrayList<>();
    for (int row = 0; row < grid.length; row++) 
    {
    	for (int col = 0; col < grid[0].length; col++) 
    	{
    		if (grid[row][col] == 0) 
    		{
    			emptyTiles.add(new TilePosition(row, col, grid[row][col]));
    		}
    	}
    }
    if (emptyTiles.size() != 0) 
    {
    	// randomly generate the tile in the position of a randomly selected empty cell
    	int randomTile = randy.nextInt(emptyTiles.size());
    	int[] valProbability = {2, 2, 2, 2, 2, 2, 2, 2, 2, 4}; // 90% chance of 2, 10% chance of 4
    	int randomVal = randy.nextInt(valProbability.length);
    	// first position is the random empty row value, second position is the random empty column value, third position is random value
    	return new TilePosition((emptyTiles.get(randomTile)).getRow(), (emptyTiles.get(randomTile)).getCol(), valProbability[randomVal]);
    }
    else 
    {
    	return null;
    }
  }
}