package io.matita08.logic;

import io.matita08.GUI.Registers;

import java.util.function.Consumer;

@SuppressWarnings("unused")  //Loaded with reflection
public enum Operations4Bit {
   load(0,n->{}, 2),
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
   Halt(15, n->{}, 1),
   Unknown(16, n->{}, 1);
   
   public static final Operations4Bit[] all = values();
   public final Operation wrapper;
   
   /**
    * Creates the enum constant's Operation wrapper.
    *
    * Constructs a new Operation using the provided opcode, action, cycle count and the enum constant's name,
    * and assigns it to the constant's `wrapper` field.
    *
    * @param opcode the numeric opcode for this operation (expected 4-bit range)
    * @param act    the action to execute for this operation; accepts the instruction operand
    * @param cycles the number of cycles this operation consumes
    */
   Operations4Bit(int opcode, Consumer<Integer> act, int cycles) {
      wrapper = new Operation(opcode, act, cycles, name());
   }
   
   /**
    * Returns the Operation wrapper for the Halt instruction.
    *
    * @return the {@link Operation} instance representing the Halt operation
    */
   public static Operation getHalt(){
      return Halt.wrapper;
   }
   
   /**
    * Returns the Operation wrapper for the Unknown (undefined opcode) operation.
    *
    * @return the Operation representing an unknown or invalid 4-bit opcode
    */
   public static Operation getUnknown(){
      return Unknown.wrapper;
   }
}
