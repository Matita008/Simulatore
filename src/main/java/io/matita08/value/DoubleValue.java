package io.matita08.value;

import io.matita08.Constants;

public class DoubleValue extends Value {
   
   private static final Value c1 = new SingleValue(1, false);
   private Value[] v;
   
   /**
    * Constructs a DoubleValue with an internal Value array sized to Constants.AddressSize.
    *
    * Each element of the array is initialized by calling Value.getNew().
    */
   public DoubleValue() {
      v = new Value[Constants.AddressSize];
      for (int i = 0; i < Constants.AddressSize; i++) {
         v[i] = Value.getNew();
      }
   }
   
   /**
    * Constructs a DoubleValue backed by a single-element array containing a new SingleValue initialized to the given integer.
    *
    * @param n the initial numeric value for the contained SingleValue
    */
   public DoubleValue(int n) {
      v = new Value[]{new SingleValue(n)};
   }
   
   /**
    * Construct a DoubleValue from two component Value instances.
    *
    * The created DoubleValue stores v1 and v2 as its two internal components.
    *
    * @param v1 first component value; must not already be a DoubleValue
    * @param v2 second component value; must not already be a DoubleValue
    * @throws IllegalArgumentException if either v1 or v2 is an instance of DoubleValue
    */
   public DoubleValue(Value v1, Value v2) {
      if(v1 instanceof DoubleValue || v2 instanceof DoubleValue) throw new IllegalArgumentException("one of the passed argument is already a DoubleValue: " + v1 + " " + v2);
      this.v = new Value[]{v1, v2};
   }
   
   /**
    * Returns the "unset" string representation for a DoubleValue.
    *
    * The returned string is the SingleValue unset representation repeated
    * Constants.AddressSize times (one entry per underlying address slot).
    *
    * @return the concatenated unset representation for all address slots
    */
   public static String unset() {
      return SingleValue.unset().repeat(Constants.AddressSize);
   }
   
   /**
    * Set this DoubleValue's primary component to the given integer value.
    *
    * Sets the internal primary element (index 0) to n and returns the resulting Value.
    *
    * @param n the integer value to store
    * @return the Value representing the new stored value (the primary component)
    */
   @Override
   public Value set(int n) {
      return v[0].set(n);
   }
   
   /**
    * Set this DoubleValue's primary component to the given Value.
    *
    * @param va the Value to assign to the first internal component
    */
   @Override
   public void set(Value va) {
      v[0].set(va);
   }
   
   /**
    * Adds the given value to this DoubleValue's primary component and returns the result.
    *
    * The operation is performed on the first underlying Value element; the returned Value reflects the result of that addition.
    *
    * @param v2 the value to add
    * @return the result of adding {@code v2} to this DoubleValue's primary component
    */
   @Override
   public Value add(Value v2) {
      return v[0].add(v2);
   }
   
   /**
    * Subtracts the specified value from this DoubleValue's primary component.
    *
    * The operation is delegated to the first underlying Value (v[0]).
    *
    * @param v2 the value to subtract
    * @return the result of subtracting {@code v2} from this value's primary component
    */
   @Override
   public Value sub(Value v2) {
      return v[0].sub(v2);
   }
   
   /**
    * Multiplies this value by another value.
    *
    * The operation is performed on the primary component (first element) and the
    * resulting Value is returned.
    *
    * @param v2 the value to multiply by
    * @return the product as a Value
    */
   @Override
   public Value mul(Value v2) {
      return v[0].mul(v2);
   }
   
   /**
    * Returns the value of the first internal component interpreted as an unsigned integer.
    *
    * @return the unsigned value of the first component as an int
    */
   @Override
   public int get() {
      return v[0].getUnsigned();
   }
   
   /**
    * Returns this value interpreted as a signed integer.
    *
    * The signed interpretation is obtained from the underlying stored component.
    *
    * @return the signed integer representation of this value
    */
   @Override
   public int getSigned() {
      return v[0].getSigned();
   }
   
   /**
    * Returns the unsigned integer representation of this DoubleValue.
    *
    * The unsigned value is obtained from the first underlying component.
    *
    * @return the unsigned value as an int
    */
   @Override
   public int getUnsigned() {
      return v[0].getUnsigned();
   }
   
   /**
    * Returns the current value of this DoubleValue's primary component and then increments it by one.
    *
    * The method reads the unsigned integer value from the first internal Value, increments that internal
    * Value by 1, and returns a new DoubleValue initialized with the value that was present before the increment.
    *
    * @return a new DoubleValue representing the previous (pre-increment) value of the first component
    */
   public Value getAndInc() {
      int before = v[0].get();
      v[0] = v[0].add(c1);
      return new DoubleValue(before);
   }
}
