import java.util.ArrayList;
import java.util.Arrays;

import api.Shift;
import hw3.ShiftUtil;

public class SimpleTestsUtil
{

  public static void main(String[] args)
  {
    ShiftUtil util = new ShiftUtil();
    int[] arr = {2, 0, 0, 4, 0, 2};
    int index = util.findNextNonempty(arr, 0);
    System.out.println(index);
    System.out.println("Expected 0");
    index = util.findNextNonempty(arr, 1);
    System.out.println(index);
    System.out.println("Expected 3");
    index = util.findNextNonempty(arr, 5);
    System.out.println(index);
    System.out.println("Expected 5");
    index = util.findNextNonempty(arr, 6);
    System.out.println(index);
    System.out.println("Expected -1");
    
    int[] test = {2, 0, 0, 4, 0, 2};
    int[] expected = {2, 4, 0, 0, 0, 2};
    
    // move index 3 to index 1, tile value is 4
    Shift shift = new Shift(3, 1, 4); 
    util.applyOneShift(test, shift);
    System.out.println("Result:   " + Arrays.toString(test));
    System.out.println("Expected: " + Arrays.toString(expected));
    System.out.println();
    
    test = new int[]{2, 0, 2, 4, 0, 4};
    expected = new int[]{2, 0, 2, 8, 0, 0};
    
    // merge index 3 and 5 to index 3, (current) tile value is 4
    shift = new Shift(3, 5, 3, 4); 
    util.applyOneShift(test, shift);
    System.out.println("Result:   " + Arrays.toString(test));
    System.out.println("Expected: " + Arrays.toString(expected));

    // using arr = {2, 0, 0, 4, 0, 2};
    shift = util.findNextPotentialShift(arr, 0);
    System.out.println(shift);
    System.out.println("Expected null");  // no possible shift to index 0
    System.out.println();
    shift = util.findNextPotentialShift(arr, 1);
    Shift expectedShift = new Shift(3, 1, 4);  // move index 3 to index 1, tile value 4
    System.out.println("Result:   " + shift);
    System.out.println("Expected: " + expectedShift);
    System.out.println();
    
    arr = new int[]{2, 0, 2, 4, 0, 4};
    shift = util.findNextPotentialShift(arr, 3);
    expectedShift = new Shift(3, 5, 3, 4);  // merge index 3 and 5 to index 3, tile value 4
    System.out.println("Result:   " + shift);
    System.out.println("Expected: " + expectedShift);
    System.out.println();
    
    // put it all together
    test = new int[]{2, 4, 2, 0, 0, 0};
    expected = new int[]{2, 4, 2, 0, 0, 0};
    util.shiftAll(test);
    System.out.println("Result:   " + Arrays.toString(test));
    System.out.println("Expected: " + Arrays.toString(expected));
    System.out.println();
    ArrayList<Shift> shifts = util.shiftAll(test);
    System.out.println("Result:   " + Arrays.toString(test));
    System.out.println("Expected: " + Arrays.toString(expected));
    System.out.println();
    System.out.println("Return value: ");
    if (shifts == null)
    {
      for (Shift s : shifts)
      {
        System.out.println(s);
      }
    }
    System.out.println();
    System.out.println("Expected: null");
    
    test = new int[]{2, 0, 2, 4, 0, 4};
    expected = new int[]{4, 8, 0, 0, 0, 0};
    util.shiftAll(test);
    System.out.println("Result:   " + Arrays.toString(test));
    System.out.println("Expected: " + Arrays.toString(expected));
    System.out.println();
    
    // the array updates should be correct even if your shiftAll
    // isn't returning the correct list of Shift objects, so
    // check that next
    test = new int[]{4, 0, 2, 4, 0, 4};
    expected = new int[]{4, 2, 8, 0, 0, 0};
    ArrayList<Shift> shiftss = util.shiftAll(test);
    System.out.println("Result:   " + Arrays.toString(test));
    System.out.println("Expected: " + Arrays.toString(expected));
    System.out.println();
    System.out.println("Return value: ");
    if (shifts != null)
    {
      for (Shift s : shiftss)
      {
        System.out.println(s);
      }
    }
    System.out.println();
    System.out.println("Expected (in any order):");
    System.out.println("Move 2 to 1");
    System.out.println("Merge 3 and 5 to 2");
    System.out.println();
    
  }

}
