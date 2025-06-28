package io.matita08.value;

import io.matita08.Constants;

public class DoubleValue extends Value {
   
   private static final Value c1 = new SingleValue(1, false);
   private Value[] v;
   
   /**
    * Constructs a DoubleValue with an array of new Value instances, one for each address slot.
    */
   public DoubleValue() {
      v = new Value[Constants.AddressSize];
      for (int i = 0; i < Constants.AddressSize; i++) {
         v[i] = Value.getNew();
      }
   }
   
   /**
    * Constructs a DoubleValue containing a single SingleValue initialized with the specified integer.
    *
    * @param n the integer value to initialize the SingleValue element
    */
   public DoubleValue(int n) {
      v = new Value[]{new SingleValue(n)};
   }
   
   /**
    * Constructs a DoubleValue from two Value instances.
    *
    * @param v1 the first Value
    * @param v2 the second Value
    * @throws IllegalArgumentException if either v1 or v2 is already a DoubleValue
    */
   public DoubleValue(Value v1, Value v2) {
      if(v1 instanceof DoubleValue || v2 instanceof DoubleValue) throw new IllegalArgumentException("one of the passed argument is already a DoubleValue: " + v1 + " " + v2);
      this.v = new Value[]{v1, v2};
   }
   
   /**
    * Returns a string representing an unset value repeated for each address slot.
    *
    * The returned string consists of the unset representation from {@code SingleValue.unset()} repeated {@code Constants.AddressSize} times.
    *
    * @return a string of unset value representations for all address slots
    */
   public static String unset() {
      return SingleValue.unset().repeat(Constants.AddressSize);
   }
   
   /**
    * Sets the value of the first element in the array to the specified integer.
    *
    * @param n the integer value to set
    * @return the updated Value instance
    */
   @Override
   public Value set(int n) {
      return v[0].set(n);
   }
   
   /**
    * Sets the value of the first element in the array to the specified value.
    *
    * @param va the value to assign to the first element
    */
   @Override
   public void set(Value va) {
      v[0].set(va);
   }
   
   /**
    * Adds the specified value to the first element of this DoubleValue.
    *
    * @param v2 the value to add
    * @return the result of the addition as a Value
    */
   @Override
   public Value add(Value v2) {
      return v[0].add(v2);
   }
   
   /**
    * Subtracts the specified value from the first element of this DoubleValue.
    *
    * @param v2 the value to subtract
    * @return the result of the subtraction as a Value
    */
   @Override
   public Value sub(Value v2) {
      return v[0].sub(v2);
   }
   
   /**
    * Multiplies the first underlying value by the specified value.
    *
    * @param v2 the value to multiply with the first element
    * @return the result of the multiplication
    */
   @Override
   public Value mul(Value v2) {
      return v[0].mul(v2);
   }
   
   /**
    * Returns the unsigned integer value of the first underlying Value element.
    *
    * @return the unsigned integer representation of the first Value in the array
    */
   @Override
   public int get() {
      return v[0].getUnsigned();
   }
   
   /**
    * Returns the signed integer value of the first underlying {@code Value} element.
    *
    * @return the signed integer representation of the first element in the array
    */
   @Override
   public int getSigned() {
      return v[0].getSigned();
   }
   
   /**
    * Returns the unsigned integer value of the first underlying {@code Value} element.
    *
    * @return the unsigned integer representation of the first element in the array
    */
   @Override
   public int getUnsigned() {
      return v[0].getUnsigned();
   }
   
   /**
    * Returns the current value of the first element and increments it by one.
    *
    * @return a new DoubleValue containing the value of the first element before incrementing
    */
   public Value getAndInc() {
      int before = v[0].get();
      v[0] = v[0].add(c1);
      return new DoubleValue(before);
   }
}
