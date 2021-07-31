package api;

import java.util.ArrayList;

/**
 * A MoveResult object encapsulates the results of a <code>doMove()</code> operation
 * on an instance of the Powers game.  The result is a list of Descriptor objects,
 * representing the shifts performed in the move, and a TilePosition object describing
 * the newly generated tile in the grid.  If no tiles in the grid were actually shifted, 
 * the descriptor list is empty and the new tile position is null.
 */
public class MoveResult
{
  /**
   * List of descriptors.
   */
  private ArrayList<Descriptor> moves;
  
  /**
   * New tile position.
   */
  private TilePosition newTile;
  
  /**
   * Constructs a MoveResult with an empty list of moves and a null TilePosition.
   */
  public MoveResult()
  {
    moves = new ArrayList<Descriptor>();
    newTile = null;
  }
  
  /**
   * Adds a descriptor to this result's list of descriptors.
   * @param m
   *   move to be added
   */
  public void addMove(Descriptor m)
  {
    moves.add(m);
  }
  
  /**
   * Adds a list of descriptors to this result's list.
   * @param list
   *    moves to be added
   */
  public void addMoves(ArrayList<Descriptor> list)
  {
    moves.addAll(list);
  }
  
  /**
   * Sets this MoveResult's new tile position.
   * @param givenTilePosition
   *   new tile to add to this result
   */
  public void setNewTile(TilePosition givenTilePosition)
  {
    newTile = givenTilePosition;
  }
  
  /**
   * Returns a reference to this result's list of descriptors, possibly empty.
   * @return
   *   list of descriptors
   */
  public ArrayList<Descriptor> getMoves()
  {
    return moves;
  }
  
  /**
   * Returns this result's new tile position.
   * @return
   *   tile position, or null if none has been set
   */
  public TilePosition getNewTile()
  {
    return newTile;
  }
}
