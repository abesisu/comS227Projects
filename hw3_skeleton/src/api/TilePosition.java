package api;

/**
 * Data container for describing the position and value of a 
 * new "tile" in the Powers game.
 */
public class TilePosition
{
  /**
   * Row index.
   */
  private final int row;
  
  /**
   * Column index.
   */
  private final int col;
  
  /**
   * Number on the tile.
   */
  private final int value;
  
  /**
   * Constructs a new TilePosition with the given row, column, and value.
   * @param givenRow
   *   row index for this 
   * @param givenCol
   *   column index for this tile
   * @param givenValue
   *   value for this tile
   */
  public TilePosition(int givenRow, int givenCol, int givenValue)
  {
    row = givenRow;
    col = givenCol;
    value = givenValue;
  }

  /**
   * Returns the row index for this tile position.
   * @return
   *   row index
   */
  public int getRow()
  {
    return row;
  }

  /**
   * Returns the column index for this tile position.
   * @return
   *   column index
   */
  public int getCol()
  {
    return col;
  }

  /**
   * Returns the value for this tile position.
   * @return
   *   value on this tile
   */
  public int getValue()
  {
    return value;
  }
  
  /**
   * Determines whether this TilePosition is equal to the given object.
   * @return
   *   true if the given object is a TilePosition with the same
   *   attributes as this one
   */
  public boolean equals(Object obj)
  {
    if (obj == null || obj.getClass() != this.getClass())
    {
      return false;
    }
    TilePosition other = (TilePosition) obj;
    return row == other.row && col == other.col && value == other.value;
  }
  
  /**
   * Returns a string description of this object.
   * @return 
   *   a string description of this object
   */
  public String toString()
  {
    return "Position (" + row + ", " + col + ") value " + value;
  }
}
