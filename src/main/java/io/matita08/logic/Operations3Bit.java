package io.matita08.logic;

import io.matita08.GUI.Registers;
import io.matita08.value.*;

import java.util.function.Consumer;

@SuppressWarnings("unused")  //Loaded with reflection
public enum Operations3Bit {//Using prof default table
   sto(0, n->{}, 1 + Operation.getAddressSize()),
   load(1, n->{
      if(n == 1) {
        Registers.setMAR(Registers.getPointer());
      } else Operation.readPointer(n - 1);
   }, 1 + Operation.getAddressSize()),
   out(2, n->{Registers.setBufOut(Registers.getAcc());}, 1),
   in(3, n->{Registers.setAcc(Registers.getBufIn());}, 1),
   add(4, n->{
      Registers.setAcc(Registers.getAcc().add(Registers.getRegB()));
      Registers.setZero(new Flag(Registers.getRegB().equals(0)));
   }, 1),
   set(5, n->Registers.setRegB(Registers.getAcc()), 1),
   jpz(6, n->{
      if(n == Operation.getAddressSize() + 1) {
         if(Registers.getZero().equals(true)) Registers.pc().add(new SingleValue(2, false));
         else Operation.readPointer(n - 1);
      } else if(n != 1) Operation.readPointer(n - 1);
      else Registers.pc().set(Registers.getPointer());
   }, 1 + Operation.getAddressSize()),
   Halt(7, n->{}, 1),
   Unknown(8, n->{}, 1);
   
   public static final Operations3Bit[] all = values();
   public final Operation wrapper;
   
   /**
    * Constructs the enum instance by creating its associated Operation wrapper.
    *
    * The wrapper is initialized with the given opcode, action, cycle count and the enum constant's name.
    *
    * @param opcode numeric opcode for the operation
    * @param act    action executed when the operation runs (accepts the instruction parameter)
    * @param cycles number of cycles the operation consumes
    */
   Operations3Bit(int opcode, Consumer<Integer> act, int cycles) {
      wrapper = new Operation(opcode, act, cycles, name());
   }
   
   /**
    * Returns the Operation wrapper for the Halt instruction.
    *
    * @return the {@link Operation} instance representing the Halt operation
    */
   public static Operation getHalt() {
      return Halt.wrapper;
   }
   
   /**
    * Returns the Operation wrapper representing an unknown or invalid opcode.
    *
    * This is the `Operation` instance associated with the `Unknown` enum constant;
    * use it as a sentinel when decoding or executing an unrecognized 3-bit opcode.
    *
    * @return the Operation for the Unknown operation
    */
   public static Operation getUnknown() {
      return Unknown.wrapper;
   }
}
