package ui;
/**
 * Encapsulates a row and column.
 */
public class Position
{
  int row;
  int col;
  public Position(int givenRow, int givenCol)
  {
    row = givenRow;
    col = givenCol;
  }

  public int getRow()
  {
    return row;
  }
  
  public int getCol()
  {
    return col;
  }
  
  public String toString()
  {
    return "(" + row + ", " + col + ")";
  }
}