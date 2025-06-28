package io.matita08.value;

public abstract class Value {
   
   @SuppressWarnings("StaticInitializerReferencesSubClass") public static final UndefinedValue nullValue = new UndefinedValue();
   
   /**
 * Returns a new instance representing an undefined value.
 *
 * @return a clone of the static undefined value instance
 */
public static Value getNew() {return nullValue.clone();}
   
   /**
 * Creates a new address-like value as a {@code DoubleValue} composed of two newly initialized {@code Value} instances.
 *
 * @return a new {@code DoubleValue} containing two fresh {@code Value} objects
 */
public static Value getNewAddress() {return new DoubleValue(getNew(), getNew());}
   
   /**
 * Creates a new SingleValue initialized with the specified integer.
 *
 * @param n the integer value to initialize the SingleValue with
 * @return a new SingleValue instance containing the given integer
 */
public static Value create(int n) {return new SingleValue(n);}
   
   /**
 * Creates a new SingleValue initialized with the specified integer and signedness.
 *
 * @param n the integer value to initialize the SingleValue with
 * @param signed whether the value should be treated as signed
 * @return a new SingleValue instance with the given value and signedness
 */
public static Value create(int n, boolean signed) {return new SingleValue(n, signed);}
   
   public boolean isUndefined() {return false;}
   
   /**
 * Sets the value using the specified integer.
 *
 * @param n the integer to set as the value
 * @return this instance after setting the value
 */
public abstract Value set(int n);
   
   /**
    * Sets the value of this instance to match the integer value of another {@code Value} instance.
    *
    * @param v the {@code Value} whose integer value will be used to update this instance
    */
   public void set(Value v) {
      set(v.get());
   }
   
   /**
 * Returns a new Value representing the sum of this value and the specified value.
 *
 * @param v2 the value to add
 * @return a new Value containing the result of the addition
 */
public abstract Value add(Value v2);
   
   /**
 * Returns a new Value representing the result of subtracting the specified Value from this Value.
 *
 * @param v2 the Value to subtract
 * @return a new Value containing the subtraction result
 */
public abstract Value sub(Value v2);
   
   public abstract Value mul(Value v2);
   
   @Override
   public String toString() {
      return "Obj: " + super.toString() + "  With default value = " + get() + "     Signed: " + getSigned() + "     Unsigned: " + getUnsigned();
   }
   
   public int get() {
      return getSigned();
   }
   
   public static String unset(){return "";}
   
   /**
 * Returns the signed integer representation of this value.
 *
 * @return the signed integer value
 */
public abstract int getSigned();
   
   /**
 * Returns the unsigned integer representation of this value.
 *
 * @return the value as an unsigned integer
 */
public abstract int getUnsigned();
   
   /**
    * Checks if the value is equal to the specified integer, comparing both unsigned and signed representations.
    *
    * @param n the integer to compare with
    * @return true if either the unsigned or signed value equals {@code n}, false otherwise
    */
   public boolean equals(int n) {
      return getUnsigned() == n || getSigned() == n;
   }
}