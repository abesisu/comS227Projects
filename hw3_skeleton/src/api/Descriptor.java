package api;


/**
 * A Descriptor encapsulates a Shift object along with the direction of the
 * move and the row or column index in which it occurred.  A descriptor is used
 * as part of the MoveResult object to report the old and new positions of
 * tiles in the grid.
 */
public class Descriptor
{
  /**
   * The Shift object describing the move in one dimension.
   */
  private final Shift shift;

  /**
   * Row or column in the grid (depending on direction).
   */
  private final int rowOrColumn;
  
  /**
   * Direction in the grid.
   */
  private final Direction dir;

  /**
   * Constructs a descriptor containing the given shift, row/column, and direction.
   * @param givenShift
   *   Shift object
   * @param givenRowOrColumn
   *   row or column index in which the shift takes place
   * @param givenDir
   *   direction of the shift
   */
  public Descriptor(Shift givenShift, int givenRowOrColumn, Direction givenDir)
  {
    shift = givenShift;
    rowOrColumn = givenRowOrColumn;
    dir = givenDir;
  }
  
  /**
   * Returns the old index of the first (or only) tile represented by the shift.
   * @return
   *   index of first tile
   */
  public int getOldIndex()
  {
    return shift.getOldIndex();
  }

  /**
   * Returns the old index of the second tile represented by the shift, in case of a merge.
   * Returns -1 if this is not a merge.
   * @return
   *   index of second tile, or -1 if this is not a merge
   */
  public int getOldIndex2()
  {
    return shift.getOldIndex2();
  }
  
  /**
   * Returns the new index of the tile or tiles represented by the shift.
   * @return
   *   new index for move
   */
  public int getNewIndex()
  {
    return shift.getNewIndex();
  }

  /**
   * Determines whether this is a merge or a single tile shift.
   * @return
   *   true if this is a merge, false otherwise
   */
  public boolean isMerge()
  {
    return shift.isMerge();
  }

  /**
   * Returns a direction for interpreting this shift within a 2D grid.
   * @return
   *   direction for the shift
   */
  public Direction getDirection()
  {
    return dir;
  }

  /**
   * Returns a row or column index for interpreting this shift within a 2D grid.
   * @return
   *   row or column index
   */
  public int getRowOrColumn()
  {
    return rowOrColumn;
  }

  /**
   * Returns the current (old) value of the tile or tiles represented by the shift.
   * @return
   *   value of tile(s) in this move
   */
  public int getValue()
  {
    return shift.getValue();
  }

  /**
   * Determines whether this Descriptor object is equal to the given object.
   * @return
   *   true if the given object is a Descriptor and all attributes are the same as those
   *   in this Descriptor
   */
  public boolean equals(Object obj)
  {
    if (obj == null || !(obj instanceof Descriptor))
    {
      return false;
    }
    
    Descriptor other = (Descriptor) obj;

    return (shift.equals(other.shift) &&
            rowOrColumn == other.rowOrColumn &&
            dir == other.dir);      
  }
  
  /**
   * Returns a string description of this Descriptor.
   * @return
   *   a string description of this Descriptor
   */
  public String toString()
  {
    String rowAndDirection = "";
    if (rowOrColumn >= 0 && dir != null)
    {
      if (dir == Direction.UP || dir == Direction.DOWN)
      {
        rowAndDirection = " (column " + rowOrColumn + " " + dir + ")";
      }
      else if (dir == Direction.LEFT || dir == Direction.RIGHT)
      {
        rowAndDirection = " (row " + rowOrColumn + " " + dir + ")";
      }

    }
    return shift.toString() + rowAndDirection;
  }
}