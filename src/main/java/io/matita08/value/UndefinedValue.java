package io.matita08.value;

import io.matita08.Constants;

public class UndefinedValue extends Value implements Cloneable{
   UndefinedValue() {
   }
   
   private int rand() {
      return (int)(Math.random() * Constants.Value_max * 2);
   }
   
   @Override
   public int getSigned() {
      return rand() - Constants.Value_max;
   }
   
   @Override
   public int getUnsigned() {
      return rand() % Constants.Value_max;
   }
   
   @SuppressWarnings("MethodDoesntCallSuperMethod")
   @Override
   public UndefinedValue clone() {
      return this;
   }
   
   public Value set(int n) {
      return new SingleValue(n);
   }
   
   @Override
   public boolean isUndefined() {return true;}
   
   @Override
   public Value add(Value v2) {
      return this;
   }
   
   @Override
   public Value sub(Value v2) {
      return this;
   }
   
   /**
    * Returns this instance when multiplied by any value, preserving the undefined state.
    *
    * @param v2 the value to multiply with
    * @return this instance, indicating multiplication with undefined yields undefined
    */
   @Override
   public Value mul(Value v2) {
      return this;
   }
   
   /**
    * Indicates that this undefined value is never equal to any integer.
    *
    * @param n the integer to compare with
    * @return always {@code false}
    */
   @Override
   public boolean equals(int n) {
      return false;
   }
}
