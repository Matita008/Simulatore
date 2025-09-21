package io.matita08.data;

import io.matita08.*;

/**
 * CPU Flags Management System.
 * <p>
 * This class manages CPU flags that indicate the results of arithmetic and
 * logic operations. Flags are used by conditional instructions and provide
 * status information about the current CPU state.
 * </p>
 *
 * <p>
 * <strong>Supported Flags:</strong>
 * <ul>
 * <li><strong>Zero Flag:</strong> Set when an operation result equals zero</li>
 * <li><strong>Overflow Flag:</strong> Set when an operation exceeds maximum value</li>
 * </ul>
 * </p>
 *
 * <p>
 * Flags are stored as a bitmask int and accessed using {@link FlagsConstants} as indexes.
 * </p>
 *
 * @author Matita008
 * @version 1.5
 * @since 1.0
 */
public class Flags {
   /**
    * Variable to store the flags, using {@link FlagsConstants} as indexes.
    * It defaults to a random state
    */
   private static int flags = Utils.rng.nextInt();
   private static boolean set = false;
   
   /**
    * Get the string representation of all flags
    * @return the bitmask of all the flags if any flag was set as a string or a question mark if on flag was ever set/unset
    */
   public static String get() {
      return set ? Integer.toString(flags, Constants.getRadix()) : "?";
   }
   /**
    *
    * @param flag the {@link FlagsConstants flag} to retrive
    * @return the current flag status
    */
   public static boolean get(FlagsConstants flag) {
      return get(flag.get());
   }
   
   /**
    *
    * @param flag the bitmask of the flag(s) to retrive
    * @return the current flag status
    */
   public static boolean get(int flag) {
      return (flags & flag) == flag;
   }
   
   /**
    * Sets/unsets a flag
    * @param flag the {@link FlagsConstants flag} to modify
    * @param value the new state of the flag
    */
   public static void set(FlagsConstants flag, boolean value) {
      set(flag.get(), value);
   }
   
   /**
    * Sets/unsets a flag
    * @param flag the bitmask of the flag(s) to modify
    * @param value the new state of the flag
    */
   public static void set(int flag, boolean value) {
      if(set) {
         if(value) flags = flags | flag;
         else if((flags & flag) == flag) flags = flags - flag;
      } else {
         set = true;
         flags = flag;
      }
   }
}
