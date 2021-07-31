package hw3;

import java.util.ArrayList;

import api.Shift;

/**
 * Utility class containing the basic logic for performing moves in the
 * Powers game.  All methods operate on a one-dimensional array
 * of integers representing the tiles.  A cell with zero is referred to
 * as "empty" and a nonzero cell is "nonempty".  Tiles are only shifted
 * to the left; that is, tiles that are moved or merged can only move to the 
 * left.  The Powers class can use these methods to shift a row or column
 * in any direction by copying that row or column, forward or backward,
 * into a temporary one-dimensional array.
 * 
 * @author Abe Scheideman
 */
public class ShiftUtil
{
  /**
   * Returns the index of the first nonempty cell that is on or after the 
   * given index <code>start</code>, or -1 if there is none.
   * @param arr
   *   given array
   * @param start
   *   index at which to start looking 
   * @return
   *   index of the first nonempty cell, or -1 if none is found
   */
  public int findNextNonempty(int[] arr, int start)
  {
    for (int i = start; i < arr.length; i++) 
    {
    	if (arr[i] != 0) 
    	{
    		return i;
    	}
    }
    return -1;
  }
  
  /**
   *  Given an array and a starting index, finds a shift that
   *  would merge or move a tile to that index, if such a shift 
   *  exists. This method does not modify the array. If there is no shift
   *  to the given index, returns null.  This method is not required to 
   *  examine cells to the left of <code>index</code>.  Note that in 
   *  case of a merge, the value of the Shift object is the <em>current</em>
   *  value on the tile or tiles, not the new value that it would
   *  have after the merge takes place.
   *  
   *  The logic of this method can be described as follows:
   *  <pre>
   *  if cell at index is occupied (nonzero)
   *      find next occupied cell c to the right of 'index'
   *      if there is one and it is the same value
   *            create a shift to merge c with cell at 'index'
   *  else
   *      find next occupied cell c to the right of 'index'
   *      if there is one
   *           find next occupied cell c2 to the right of c
   *           if there is one, and if they are the same value
   *                create a shift to merge c and c2 into cell at index
   *           else
   *                create a shift that just moves c to 'index'
   *  return the shift object
   *  </pre>
   *            
   * @param arr
   *   array in which to search for possible shift
   * @param index
   *   index for destination of shift
   * @return  
   *   Shift object describing the shift, or null if there is no shift possible
   */
  public Shift findNextPotentialShift(int[] arr, int index)
  {
	Shift shift = null;
	// if the cell at the index is occupied, find the next occupied cell and perform correct shift
    if (arr[index] != 0) {
    	for (int i = index; i < arr.length - 1; i++) 
    	{
    		int c = i + 1;
    		if ((arr[c] != 0) && (arr[c] == arr[index])) 
    		{
    			shift = new Shift(index, c, index, arr[index]);
    			break;
    		}
    		else if (arr[c] != 0) 
    		{
    			break;
    		}
    	}
    }
    else 
    {
    	// find next occupied cell to see if another potential shift needs to happen
    	int c = -1;
    	for (int i = index; i < arr.length - 1; i++) 
    	{
    		c = i + 1;
    		if (arr[c] != 0) 
    		{
    			break;
    		}
    		c = -1;
    	}
    	// If there is a nonzero value after index, c is basically the new index 
    	// and we need to find if there is another value that will merge with c.
    	if (c != -1) 
    	{
    		int c2 = -1;
    		for (int i = c; i < arr.length - 1; i++) 
    		{
    			c2 = i + 1;
    			if (arr[c2] != 0) 
    			{
    				break;
    			}
    		}
    		if (c2 != -1 && arr[c] == arr[c2]) 
    		{
    			shift = new Shift(c, c2, index, arr[c]);
    		}
    		else 
    		{
    			shift = new Shift(c, index, arr[c]);
    		}
    		
    	}	
    }
    return shift;
  }
  
  /**
   * Updates the array according to the given Shift.  This
   * method is not required to check whether the given Shift describes
   * a move or merge that is actually correct in the given array.
   * @param arr
   *   given array to be modified
   * @param shift
   *   the shift to be applied to the array
   */
  public void applyOneShift(int[] arr, Shift shift)
  {
	// if there is only one number to be moved
	if (shift.getOldIndex2() == -1) 
	{
		arr[shift.getNewIndex()] = arr[shift.getOldIndex()];
		arr[shift.getOldIndex()] = 0;
	}
	else 
	{
		// if multiple numbers are moved/merged
		arr[shift.getNewIndex()] = arr[shift.getOldIndex()] + arr[shift.getOldIndex2()];
		arr[shift.getOldIndex2()] = 0;
		if (shift.getNewIndex() != shift.getOldIndex()) 
		{
			arr[shift.getOldIndex()] = 0;
		}
	}
	
  }
  

  /**
   * Collapses the array to the left by performing a sequence of shifts, 
   * and returns a list of shifts that were performed.  
   * <p>
   * The idea is to iterate over the array indices from left to right, 
   * finding a shift to that index and (if one exists) applying it to the array.
   * Note that according to this logic, shifts do not "cascade": once a cell is merged 
   * with another cell, the resulting cell is not merged again during this operation.  
   * For example, when this method is applied to the array [2, 2, 4], the end result 
   * is [4, 4, 0], not [8, 0, 0].
   * @param arr
   *   array to be collapsed
   * @return
   *   list of all shifts performed in the collapse
   */
  public ArrayList<Shift> shiftAll(int[] arr)
  {
	ArrayList<Shift> allShifts = new ArrayList<>();
    for (int i = 0; i < arr.length; i++) 
    {   // consider each potential shift as they are found
    	Shift shift = findNextPotentialShift(arr, i);
    	if (shift != null) 
    	{
    		applyOneShift(arr, shift); 
    		allShifts.add(shift);
    	}
    }
    return allShifts;
  }
  
}
