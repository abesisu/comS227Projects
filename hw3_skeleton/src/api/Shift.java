package api;


/**
 * A Shift object describes the motion of one tile (or two tiles in the case
 * of a merge) in the Powers game.  A shift is represented
 * by the old and new indices of the tile or tiles within a one-dimensional array.
 */
public class Shift
{
  /**
   * Old index of the first tile.
   */  
  private final int oldIndex;

  /**
   * Old position of the second tile, in case of a merge.
   */
  private final int oldIndex2;

  /**
   * New position of the tile or tiles.
   */
  private final int newIndex;
  
  /**
   * Indicates whether this shift is a merge of two tiles or not.
   */
  private final boolean merged;
  
  /**
   * Current value in the cell or cells to be moved.
   */
  private final int value;

  /**
   * Constructs a single-tile shift from oldPos to newPos.  Caller should
   * ensure that newPos is strictly less than oldPos. 
   * @param oldIndex
   *   old index of the tile within an array
   * @param newIndex
   *   new index of the tile within an array
   * @param value
   *   current value of the tile
   */
  public Shift(int oldIndex, int newIndex, int value)
  {
    this.oldIndex = oldIndex;
    this.newIndex = newIndex;
    this.oldIndex2 = -1;
    this.value = value;
    merged = false;
  }
  
  /**
   * Constructs a merge from oldIndex and oldIndex2 to newIndex.  Caller should
   * ensure that newIndex is less than or equal to oldIndex and oldIndex is strictly
   * less than oldIndex2.  The value is the <em>current</em> value on the tiles, not the new value
   * that it would have after the merge.
   * @param oldIndex
   *   old index of the tile within an array
   * @param oldIndex2
   *   old index of second tile within an array
   * @param newIndex
   *   new index of the tile within an array
   * @param value
   *   current value of the tile
   */
  public Shift(int oldIndex, int oldIndex2, int newIndex, int value)
  {
    this.oldIndex = oldIndex;
    this.newIndex = newIndex;
    this.oldIndex2 = oldIndex2;
    this.value = value;
    merged = true;
  }

  /**
   * Returns the old index of the first (or only) tile represented by this move.
   * @return
   *   index of first tile
   */
  public int getOldIndex()
  {
    return oldIndex;
  }

  /**
   * Returns the old index of the second tile represented by this move, in case of a merge.
   * Returns -1 if this is not a merge move.
   * @return
   *   index of second tile, or -1 if this is not a merge
   */
  public int getOldIndex2()
  {
    return oldIndex2;
  }
  
  /**
   * Returns the new index of the tile or tiles represented by this move.
   * @return
   *   new index for move
   */
  public int getNewIndex()
  {
    return newIndex;
  }

  /**
   * Determines whether this is a merge move or a single tile move.
   * @return
   *   true if this is a merge move, false otherwise
   */
  public boolean isMerge()
  {
    return merged;
  }

  /**
   * Returns the current (old) value of the tile or tiles represented by this move.
   * @return
   *   value of tiles in this move
   */
  public int getValue()
  {
    return value;
  }

  /**
   * Determines whether this Move object is equal to the given object.
   * @return
   *   true if the given object is a Move and all attributes are the same as those
   *   in this Move
   */
  public boolean equals(Object obj)
  {
 //   if (obj == null || obj.getClass() != this.getClass())
    if (obj == null || !(obj instanceof Shift))
    {
      return false;
    }
    Shift other = (Shift) obj;
    
    if (!merged)
    {
      return oldIndex == other.oldIndex &&
          newIndex == other.newIndex &&
          value == other.value;
    }
    else
    {
    return (oldIndex == other.oldIndex && oldIndex2 == other.oldIndex2 ||
             oldIndex == other.oldIndex2 && oldIndex2 == other.oldIndex) &&
            newIndex == other.newIndex &&
            value == other.value;
    }
  }
  
  /**
   * Returns a string description of this move.
   * @return
   *   a string description of this move
   */
  public String toString()
  {
    if (merged)
    {
      return "Merge " + oldIndex + " and " + oldIndex2 + " to " + newIndex;
    }
    else
    {
      return "Move " + oldIndex + " to " + newIndex;
    }
  }
}