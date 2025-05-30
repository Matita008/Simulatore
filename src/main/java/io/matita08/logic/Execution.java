package io.matita08.logic;

import io.matita08.GUI.*;
import io.matita08.Utils;
import io.matita08.value.Value;

import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;

public class Execution {
   private static final int MAX_CYCLES = 3;
   
   public static boolean stepped = false;
   
   public static void step(int countMax) {
      for (int i = 0; i < countMax; i++) {
         if(!step()) return;
      }
   }
   
   /**
    @return whenever we were able to step or not
    */
   public static boolean step() {
      if(ControlUnit.current == Phase.Execute && ControlUnit.opcode == Operation.Halt) return false;
      ControlUnit.current = ControlUnit.next;
      ControlUnit.currentCycles = ControlUnit.nextCycles;
      ControlUnit.next.run();
      stepped = true;
      SwingUtilities.invokeLater(Display::update);
      return true;
   }
   
   public static void fetch() {
      setMarR(Registers.pc().getAndInc());
      Registers.setIr(Registers.getMDR());
      ControlUnit.next = Phase.Decode;
      ControlUnit.nextCycles = 1;
      ControlUnit.currentCycles = -1;
   }
   
   public static void decode() {
      ControlUnit.next = Phase.Execute;
      ControlUnit.opcode = Operation.get(Registers.getIr().get());
      ControlUnit.nextCycles = ControlUnit.opcode.cycles;
   }
   
   /**
    * Executes the current operation for the active execution cycle.
    *
    * Performs the action associated with the current opcode, passing the current cycle count.
    * Decrements the cycle count; when it reaches zero, resets the opcode to Unknown and sets the next phase to Fetch.
    */
   public static void execute() {
      ControlUnit.opcode.action.accept(ControlUnit.currentCycles);
      if((ControlUnit.nextCycles = ControlUnit.currentCycles - 1) == 0) {
         ControlUnit.next = Phase.Fetch;
         ControlUnit.opcode = Operation.Unknown;
      }
      
   }
   
   /****
    * Sets the Memory Address Register (MAR) to the specified value and updates the Memory Data Register (MDR) with the memory content at that address.
    *
    * @param v the value to set in the MAR, representing a memory address
    */
   public static void setMarR(Value v) {
      Registers.setMAR(v);
      Registers.setMDR(Registers.getMC(v));
   }
   
   /**
    * Initiates a single execution step asynchronously on a new thread.
    *
    * @param ignored unused action event parameter
    */
   public static void step(ActionEvent ignored) {
      Utils.runOnNewThread(Execution::step);
   }
}
