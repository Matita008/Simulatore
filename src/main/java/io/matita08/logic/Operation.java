package io.matita08.logic;

import io.matita08.Constants;

import java.lang.reflect.*;
import java.util.function.Consumer;

public class Operation {
   
   public static Operation Halt;
   public static Operation Unknown;
   public static Operation[] all;
   public final Consumer<Integer> action;
   public final int opcode;
   public final int cycles;
   public final String name;

   static {
      try {
         Class<?> c = Class.forName(Constants.OperationEnumName);
         Object allField = c.getField("all").get(null);
         Field wr = c.getField("wrapper");
         int sz = Array.getLength(allField);
         all = new Operation[sz];
         for (int i = 0; i < sz; i++) {
            all[i] = (Operation)wr.get(Array.get(allField, i));
         }
         Halt = (Operation)c.getMethod("getHalt").invoke(null);
         Unknown = (Operation)c.getMethod("getUnknown").invoke(null);
         
      } catch (ClassNotFoundException e) {
         throw new AssertionError("Constants.OperationEnumName is set to an invalid class name: " + Constants.OperationEnumName, e);
      } catch (NoSuchFieldException e) {
         throw new AssertionError("Constants.OperationEnumName is set to a class who doesn't have a static all[] field: " + Constants.OperationEnumName, e);
      } catch (IllegalAccessException e) {
         throw new InternalError(e);
      } catch (NullPointerException npe) {
         throw new AssertionError("Constants.OperationEnumName is set to a class who doesn't have a static all[] field or doesn't have a wrapper object: " + Constants.OperationEnumName, npe);
      } catch (InvocationTargetException e) {
         throw new RuntimeException(e);
      } catch (NoSuchMethodException e) {
         throw new AssertionError("Constants.OperationEnumName is set to a class who doesn't have a static getUnknown and static getHalt field: " + Constants.OperationEnumName, e);
      }
   }
   
   /**
    * Creates a new Operation instance.
    *
    * @param opcode numeric opcode that uniquely identifies this operation
    * @param act    action performed by the operation; receives the current cycle (1-based)
    * @param cycles number of machine cycles the operation consumes
    * @param name   human-readable name for the operation
    */
   Operation(int opcode, Consumer<Integer> act, int cycles, String name) {
      action = act;
      this.opcode = opcode;
      this.cycles = cycles;
      this.name = name;
   }
   private static boolean err = true;
   /**
    * Returns the Operation matching the given opcode or {@code Unknown} if no match is found.
    *
    * Searches the static {@code all} array for an Operation whose {@code opcode} equals the
    * provided value. If no match is found this method prints "Invalid opcode received: <opcode>".
    * Additionally, if the internal error flag is false it will (once) set the flag and print
    * all known opcode/name pairs for debugging.
    *
    * @param opcode numeric opcode to look up
    * @return the matching Operation, or {@code Unknown} if not found
    */
   public static Operation get(int opcode) {
      for (Operation op: all) {
         if(op.opcode == opcode) return op;
      }
      System.out.println("Invalid opcode received: " + opcode);
      if(!err) {
         err = true;
         for(Operation op : all) {
            System.out.println(op.opcode + " " + op.name);
         }
      }
      return Unknown;
   }
   
   /**
    * Returns the configured address size used by the system.
    *
    * @return the number of bytes used to encode addresses (Constants.AddressSize)
    */
   public static int getAddressSize() {
      return Constants.AddressSize;
   }
   
   /**
    * Validates that a pointer-read cycle index is within the allowed address size.
    *
    * Ensures {@code cycle} is between 1 and {@link Constants#AddressSize} (inclusive).
    *
    * @param cycle the 1-based cycle index to validate
    * @throws IllegalArgumentException if {@code cycle} is not in the range [1, {@link Constants#AddressSize}]
    */
   public static void readPointer(int cycle) {
      if(cycle > Constants.AddressSize) throw new IllegalArgumentException("Passed cycle value must be between 1 and " + Constants.AddressSize + ", instead got: " + cycle);
   }
}
