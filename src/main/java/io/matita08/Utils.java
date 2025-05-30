package io.matita08;

import io.matita08.GUI.Registers;
import io.matita08.value.Value;

import java.io.*;
import java.util.*;

public class Utils {
   public static final Random rng = new Random();
   private final static ThreadGroup tg = new ThreadGroup("Utils-threads");
   
   /****
 * Prevents instantiation of the Utils class.
 *
 * This private constructor throws a RuntimeException if called.
 */
private Utils() {throw new RuntimeException("You're an idiot");}
   
   /**
 * Asynchronously loads and processes the specified file, updating the MC registers.
 *
 * The file is read line-by-line on a new thread. Each line is parsed and used to set the corresponding position in the MC register, with invalid or empty lines resulting in a null value.
 *
 * @param f the file to be loaded and processed
 */
public static void loadMC(File f) {runOnNewThread(()->loadMCImpl(f));}
   
   /****
    * Starts a new thread in the utility thread group to execute the given runnable.
    *
    * @param run the runnable task to execute in a new thread
    * @return the started thread instance
    */
   public static Thread runOnNewThread(Runnable run) {
      Thread t = new Thread(tg, run);
      t.start();
      return t;
   }
   
   /**
    * Loads data from the specified file into the MC register array, interpreting each line as an integer value or null.
    *
    * Each line in the file is processed in order: if a line is empty or starts with '?', the corresponding register is set to a null value; otherwise, the line is parsed as an integer and stored. If parsing fails, the register is set to null. If the file cannot be opened, an error message is printed.
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
