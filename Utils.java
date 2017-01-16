import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.List;
import java.util.Scanner;

public class Utils {
  /**
   *If we do not specify a specific ID for players, we can
   *grab it from this number via getFreeID(), which will also
   *increment it.
   */
  private static int freeID = 0;
  
  /**
   * Utility method for grabbing non-negative integers
   * from a scanner, typically from standard input.
   *
   * May consider making this a special case of a general
   * purpose number parser.
   */
  public static int parseNonNegativeIntegerValue(Scanner sc) {
    int parseVal;
    do {
      if (sc.hasNextInt()) {
        parseVal = sc.nextInt();
        if (parseVal < 0) {
          System.out.println("Please input a non-negative number.");
        } else {
          return parseVal;
        }
      } else {
        parseVal = -1;
        System.out.println("Please input a numerical value.");
      }
    } while(parseVal < 1 && sc.hasNext());
    return 1;
  }

  /**
   * Simple utility function for repeating functions
   * if the incremented value in the for loop isn't used.
   */
  public static BiConsumer<Integer, Runnable> repeat = (n, f) -> {
    for (int i = 0; i < n; ++i) {
        f.run();
    }
  };

  public static int getFreeID() {
    return freeID++;
  }

}