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
    * Constructs an Operations3Bit enum constant with the specified opcode, action, and cycle count,
    * and initializes its associated Operation wrapper.
    *
    * @param opcode the 3-bit opcode representing the operation
    * @param act the action to perform for this operation, accepting an integer parameter
    * @param cycles the number of cycles required to execute the operation
    */
   Operations3Bit(int opcode, Consumer<Integer> act, int cycles) {
      wrapper = new Operation(opcode, act, cycles, name());
   }
   
   /**
    * Returns the {@link Operation} instance representing the Halt instruction.
    *
    * @return the Operation corresponding to the Halt opcode
    */
   public static Operation getHalt() {
      return Halt.wrapper;
   }
   
   /**
    * Returns the {@code Operation} instance representing an unknown or undefined instruction.
    *
    * @return the {@code Operation} corresponding to the Unknown opcode
    */
   public static Operation getUnknown() {
      return Unknown.wrapper;
   }
}
