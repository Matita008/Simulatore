package io.matita08;

import io.matita08.GUI.Registers;
import io.matita08.value.Value;

import java.io.*;
import java.util.*;

public class Utils {
   public static final Random rng = new Random();
   private final static ThreadGroup tg = new ThreadGroup("Utils-threads");
   
   /**
 * Private constructor to prevent instantiation of this utility class.
 *
 * <p>Always throws a RuntimeException; all members are static and the class is not meant to be constructed.
 */
private Utils() {throw new RuntimeException("You're an idiot");}
   
   /**
 * Asynchronously load MC memory/register values from the given file.
 *
 * Schedules a background thread that reads the file and updates MC positions
 * via loadMCImpl(File). Each line of the file is interpreted by loadMCImpl
 * (e.g., empty or '?' lines become null entries; numeric lines become integer
 * values).
 *
 * @param f the file to read MC values from
 */
public static void loadMC(File f) {runOnNewThread(()->loadMCImpl(f));}
   
   /**
    * Creates and starts a new Thread in the Utils thread group to execute the given task.
    *
    * @param run the Runnable to run on the new thread
    * @return the started Thread instance
    */
   public static Thread runOnNewThread(Runnable run) {
      Thread t = new Thread(tg, run);
      t.start();
      return t;
   }
   
   /**
    * Loads machine-code values from the given file into MC registers starting at position 0.
    *
    * Each non-empty line that does not start with '?' is parsed as an integer and stored via
    * Registers.setMC(pos, Value.create(n)). Empty lines, lines starting with '?', or lines that
    * fail integer parsing are stored as Value.nullValue. Processing advances the register position
    * by one per input line. If the file cannot be opened the method prints an error message and returns.
    *
    * @param f the file to read; each line represents the value for the next MC register position
    */
   private static void loadMCImpl(File f) {
      try {
         Scanner s = new Scanner(f);
         int pos = 0;
         while(s.hasNext()) {
            String st = s.nextLine();
            if(st.startsWith("?") || st.isEmpty()) {
               Registers.setMC(pos, Value.nullValue);
            } else {
               try {
                  int n = Integer.parseInt(st);
                  Registers.setMC(pos, Value.create(n));
               } catch (NumberFormatException ex) {
                  Registers.setMC(pos, Value.nullValue);
               }
            }
            pos++;
         }
      } catch (FileNotFoundException _) {
         System.out.println("The selected file (" + f.getName() + ") doesn't exist or i was unable to open it");
      }
   }
}
