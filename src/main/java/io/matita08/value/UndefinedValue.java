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
    * Returns the undefined value resulting from multiplication.
    *
    * For UndefinedValue, multiplication by any other Value produces the same undefined instance;
    * the operand is ignored and no new object is created.
    *
    * @param v2 the multiplier (ignored)
    * @return this undefined Value
    */
   @Override
   public Value mul(Value v2) {
      return this;
   }
   
   /**
    * Indicates that this undefined value is never equal to any integer.
    *
    * @param n the integer to compare against (ignored)
    * @return false always
    */
   @Override
   public boolean equals(int n) {
      return false;
   }
}
