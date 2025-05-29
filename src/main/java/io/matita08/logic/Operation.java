package io.matita08.logic;

import io.matita08.Constants;
import io.matita08.GUI.Registers;

import java.util.function.Consumer;

public enum Operation {
   load(0, n->{}, 2),
   sto(1, n->{}, 2),
   set(2, n->{Registers.setRegB(Registers.getAcc());}, 1),
   in(3, n->{}, 1),
   out(4, n->{}, 1),
   add(5, n->{}, 1),
   sub(6, n->{}, 1),
   jmp(7, n->{
     if(n == 1) {
       Registers.pc().set(Registers.getPointer().get());
     } else {
       Execution.setMarR(Registers.pc().getAndInc());
       Registers.setPointer(Registers.getMDR());
     }
   }, 2),
   jpz(8, n->{}, 1),
   jpo(9, n->{}, 1),
   Halt(Constants.Value_max - 1, n->{}, 1),
   Unknown(Constants.Value_max, n->{}, 1);
   
   public static final Operation[] all = values();
   public final Consumer<Integer> action;
   public final int opcode;
   public final int cycles;
   
   Operation(int opcode, Consumer<Integer> act, int cycles) {
      action = act;
      this.opcode = opcode;
      this.cycles = cycles;
   }
   
   public static Operation get(int opcode) {
      for (Operation op: all) {
         if(op.opcode == opcode) return op;
      }
      System.out.println("Invalid opcode recived: " + opcode);
      return Unknown;
   }
}
