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
    * Perform the current opcode's action for the current cycle and advance cycle state.
    *
    * Invokes the currently selected operation's action with the current cycle index, then
    * decrements the remaining cycle count. If the decrement reaches zero, schedules the
    * next phase as Fetch and resets the opcode to Operation.Unknown.
    *
    * Side effects:
    * - Calls ControlUnit.opcode.action.accept(ControlUnit.currentCycles)
    * - Updates ControlUnit.nextCycles with the decremented cycle count
    * - May set ControlUnit.next to Phase.Fetch and ControlUnit.opcode to Operation.Unknown
    */
   public static void execute() {
      ControlUnit.opcode.action.accept(ControlUnit.currentCycles);
      if((ControlUnit.nextCycles = ControlUnit.currentCycles - 1) == 0) {
         ControlUnit.next = Phase.Fetch;
         ControlUnit.opcode = Operation.Unknown;
      }
      
   }
   
   /**
    * Set the Memory Address Register (MAR) to the given value and load the Memory Data Register (MDR)
    * with the memory contents at that address.
    *
    * @param v the address value to store in MAR; MDR is updated with the memory cell read at this address
    */
   public static void setMarR(Value v) {
      Registers.setMAR(v);
      Registers.setMDR(Registers.getMC(v));
   }
   
   /**
    * Handles an ActionEvent by scheduling a single processor step on a background thread.
    *
    * The provided ActionEvent is ignored; it exists only for ActionListener compatibility.
    *
    * @param ignored the triggering ActionEvent (unused)
    */
   public static void step(ActionEvent ignored) {
      Utils.runOnNewThread(Execution::step);
   }
}
